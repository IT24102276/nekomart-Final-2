package com.nekomart.servlet;
import com.nekomart.model.Review;
import com.nekomart.model.User;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

public class ReviewServlet extends HttpServlet {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        if ("add".equals(action)) {
            int toyId = Integer.parseInt(req.getParameter("toyId"));
            int rating = Integer.parseInt(req.getParameter("rating"));
            String comment = req.getParameter("comment");
            
            List<Review> reviews = FileUtil.readReviews();
            int newId = (int) (System.currentTimeMillis() % 100000);
            String date = dateFormat.format(new Date());
            
            reviews.add(new Review(newId, toyId, user.getUsername(), rating, comment, date));
            FileUtil.writeReviews(reviews);
        } else if ("delete".equals(action)) {
            int reviewId = Integer.parseInt(req.getParameter("id"));
            List<Review> reviews = FileUtil.readReviews();
            reviews.removeIf(r -> r.getId() == reviewId && 
                               (r.getUsername().equals(user.getUsername()) || "admin".equals(user.getRole())));
            FileUtil.writeReviews(reviews);
        }
        
        resp.sendRedirect("toy-details.jsp?id=" + req.getParameter("toyId"));
    }
} 