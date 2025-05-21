package com.nekomart.servlet;
import com.nekomart.model.Review;
import com.nekomart.model.User;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;// Basic servlet features (request, response, filter, context)
import jakarta.servlet.http.*;// Extra features for HTTP (sessions, cookies, HttpServlet)
import java.io.IOException;// Used when input/output operations might fail
import java.util.*;// Includes List, ArrayList, HashMap, etc.
import java.text.SimpleDateFormat;// Lets you convert date/time into a readable string

/**
 * ReviewServlet - Review Management Component
 * 
 * This is like a friendly toy store helper who collects and manages what people think about toys!
 * 
 * OOP Concepts (in simple terms):
 * 1. Encapsulation: We keep all review-related tasks in one place, like having a special review box
 * 2. Single Responsibility: This helper only handles toy reviews, just like a real review collector
 * 3. Inheritance: This helper is a special type of web helper (extends HttpServlet)
 * 
 * Relationships:
 * - Inheritance: Extends HttpServlet
 * - Dependency: Uses Review model
 * - Dependency: Uses User model
 * - Dependency: Uses FileUtil utility class
 * - Association: Manages Review objects
 * - Association: Interacts with User objects through session
 * 
 * CRUD Operations (what this helper can do):
 * CREATE: Add new reviews (like putting new toy reviews in the review box)
 * READ: Look at reviews (like reading what people think about toys)
 * UPDATE: Change reviews (like fixing a typo in a review)
 * DELETE: Remove reviews (like taking down inappropriate reviews)
 */
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

        // CREATE: Add new review
        if ("add".equals(action)) {
            int toyId = Integer.parseInt(req.getParameter("toyId"));
            int rating = Integer.parseInt(req.getParameter("rating"));
            String comment = req.getParameter("comment");
            
            List<Review> reviews = FileUtil.readReviews();
            int newId = (int) (System.currentTimeMillis() % 100000);
            String date = dateFormat.format(new Date());
            
            reviews.add(new Review(newId, toyId, user.getUsername(), rating, comment, date));
            FileUtil.writeReviews(reviews);
        } 
        // DELETE: Remove review
        else if ("delete".equals(action)) {
            int reviewId = Integer.parseInt(req.getParameter("id"));
            List<Review> reviews = FileUtil.readReviews();
            reviews.removeIf(r -> r.getId() == reviewId && 
                               (r.getUsername().equals(user.getUsername()) || "admin".equals(user.getRole())));
            FileUtil.writeReviews(reviews);
        }
        
        resp.sendRedirect("toy-details.jsp?id=" + req.getParameter("toyId"));
    }
} 