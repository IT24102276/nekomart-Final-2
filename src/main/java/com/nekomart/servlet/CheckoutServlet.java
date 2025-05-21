package com.nekomart.servlet;
import com.nekomart.model.CartItem;
import jakarta.servlet.*;// Basic servlet features (request, response, filter, context)
import jakarta.servlet.http.*;// Extra features for HTTP (sessions, cookies, HttpServlet)
import java.io.IOException;// Used when input/output operations might fail
import java.util.List;// Specifically importing the List interface

/**
 * CheckoutServlet - Checkout Management Component
 * 
 * This is like a friendly cashier at the toy store who helps complete purchases!
 * 
 * OOP Concepts (in simple terms):
 * 1. Encapsulation: We keep all checkout-related tasks in one place, like having a dedicated checkout counter
 * 2. Single Responsibility: This cashier only handles completing purchases, just like a real cashier
 * 3. Inheritance: This cashier is a special type of web helper (extends HttpServlet)
 * 
 * Relationships:
 * - Inheritance: Extends HttpServlet
 * - Dependency: Uses CartItem model
 * - Association: Manages CartItem objects through session
 * - Association: Interacts with cart.jsp and checkout.jsp views
 * 
 * CRUD Operations (what this cashier can do):
 * DELETE: Clear the cart after purchase (like emptying the shopping cart after checkout)
 */
public class CheckoutServlet extends HttpServlet {
    /**
     * Handles the checkout process when a user completes their purchase
     * 
     * Process:
     * 1. Gets the current shopping cart from the session
     * 2. Clears the cart after successful purchase
     * 3. Updates the session with the empty cart
     * 4. Shows a thank you message to the user
     * 
     * @param req The HTTP request containing the checkout action
     * @param resp The HTTP response to send back to the user
     */
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the current shopping cart from the session
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        // Clear the cart after successful purchase
        if (cart != null) cart.clear();
        
        // Update the session with the empty cart
        session.setAttribute("cart", cart);
        
        // Set success message and forward to checkout page
        req.setAttribute("message", "Thank you for your purchase!");
        req.getRequestDispatcher("checkout.jsp").forward(req, resp);
    }
}
