package com.nekomart.servlet;
import com.nekomart.model.CartItem;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;


public class CheckoutServlet extends HttpServlet{
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) cart.clear();
        session.setAttribute("cart", cart);
        req.setAttribute("message", "Thank you for your purchase!");
        req.getRequestDispatcher("checkout.jsp").forward(req, resp);
    }
}
