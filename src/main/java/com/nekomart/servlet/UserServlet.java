package com.nekomart.servlet;
import com.nekomart.model.User;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * UserServlet - User Management Component
 * 
 * This is like a friendly receptionist at the toy store who helps people sign in and out!
 * 
 * OOP Concepts (in simple terms):
 * 1. Encapsulation: We keep all user-related tasks in one place, like having a special desk for signing in
 * 2. Single Responsibility: This receptionist only handles user sign-ins, just like a real receptionist
 * 3. Inheritance: This receptionist is a special type of web helper (extends HttpServlet)
 * 
 * CRUD Operations (what this receptionist can do):
 * CREATE: Help new people sign up (like getting a new store membership card)
 * READ: Check if people are allowed in (like checking membership cards)
 * UPDATE: Change user information (like updating a membership card)
 * DELETE: Help people sign out (like returning membership cards)
 */
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // CREATE: Register new user
        if ("register".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            List<User> users = FileUtil.readUsers();
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    req.setAttribute("error", "Username exists!");
                    req.getRequestDispatcher("register.jsp").forward(req, resp);
                    return;
                }
            }
            // Set role to admin if username is admin, otherwise user
            String role = "admin".equals(username) ? "admin" : "user";
            users.add(new User(username, password, role));
            FileUtil.writeUsers(users);
            resp.sendRedirect("login.jsp");
        } 
        // READ: User login
        else if ("login".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            List<User> users = FileUtil.readUsers();
            for (User u : users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", u);
                    resp.sendRedirect("toys.jsp");
                    return;
                }
            }
            req.setAttribute("error", "Invalid credentials!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } 
        // DELETE: User logout (session invalidation)
        else if ("logout".equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect("login.jsp");
        }
    }
}
