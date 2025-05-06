package com.nekomart.servlet;
import com.nekomart.model.*;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.*;


public class CartServlet extends HttpServlet{
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        ToyLinkedList toys = FileUtil.readToys();

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
        } else if ("remove".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            cart.removeIf(item -> item.getToy().getId() == id);
            session.setAttribute("cart", cart);
            resp.sendRedirect("cart.jsp");
        } else if ("update".equals(action)) {
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
