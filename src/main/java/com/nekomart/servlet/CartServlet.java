package com.nekomart.servlet;
import com.nekomart.model.*;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*; // Basic servlet features (request, response, filter, context)
import jakarta.servlet.http.*;// Extra features for HTTP (sessions, cookies, HttpServlet)
import java.io.IOException;// Used when input/output operations might fail
import java.util.*;// Includes List, ArrayList, HashMap, etc.

/**
 * CartServlet - Cart Management Component
 * 
 * This is like a shopping cart helper at the toy store who helps you with your toy shopping!
 * 
 * OOP Concepts:
 * 1. Encapsulation: We keep all shopping cart tasks in one place, like having a special cart area
 * 2. Inheritance: This helper is a special type of web helper (extends HttpServlet)
 * 
 * Relationships:
 * - Inheritance: Extends HttpServlet
 * - Dependency: Uses CartItem model
 * - Dependency: Uses Toy model
 * - Dependency: Uses FileUtil utility class
 * - Association: Manages CartItem objects through session
 * - Association: Interacts with Toy objects through CartItem
 *
 * CRUD Operations (what this helper can do):
 * CREATE: Put toys in your cart (like picking up toys to buy)
 * READ: Look at what's in your cart (like checking your shopping cart)
 * UPDATE: Change how many toys you want (like adding more of the same toy)
 * DELETE: Take toys out of your cart (like putting toys back on the shelf)
 */
public class CartServlet extends HttpServlet{
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        ToyLinkedList toys = FileUtil.readToys();

        // CREATE: Add item to cart
        if ("add".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Toy toy = toys.get(id);
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getToy().getId() == id) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) cart.add(new CartItem(toy, 1));
            session.setAttribute("cart", cart);
            resp.sendRedirect("cart.jsp");
        } 
        // DELETE: Remove item from cart
        else if ("remove".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            cart.removeIf(item -> item.getToy().getId() == id);
            session.setAttribute("cart", cart);
            resp.sendRedirect("cart.jsp");
        } 
        // UPDATE: Update item quantity
        else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int qty = Integer.parseInt(req.getParameter("quantity"));
            for (CartItem item : cart) {
                if (item.getToy().getId() == id) {
                    item.setQuantity(qty);
                    break;
                }
            }
            session.setAttribute("cart", cart);
            resp.sendRedirect("cart.jsp");
        }
    }
}
