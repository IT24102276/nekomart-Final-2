package com.nekomart.servlet;
import com.nekomart.model.User;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;


public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
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
        } else if ("login".equals(action)) {
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
        } else if ("logout".equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect("login.jsp");
        }
    }
}
