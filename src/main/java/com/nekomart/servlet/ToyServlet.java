package com.nekomart.servlet;
import com.nekomart.model.Toy;
import com.nekomart.model.ToyLinkedList;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.UUID;

/**
 * ToyServlet - Toy Management Component
 * 
 * This is like a toy store manager that handles all toy-related tasks!
 * 
 * OOP Concepts (in simple terms):
 * 1. Encapsulation: We keep all toy-related tasks in one place, like keeping all toys in a toy box
 * 2. Single Responsibility: This manager only handles toys, just like a toy store manager only manages toys
 * 3. Inheritance: This manager is a special type of web manager (extends HttpServlet)
 * 
 * CRUD Operations (what this manager can do):
 * CREATE: Add new toys to the store (like putting new toys on the shelf)
 * READ: Look at toys (like customers browsing the toy store)
 * UPDATE: Change toy information (like updating a toy's price tag)
 * DELETE: Remove toys from the store (like taking broken toys off the shelf)
 */
@MultipartConfig // This helps us handle toy pictures!
public class ToyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action"); // gets what the user wants to do (add, delete, sort or update)
        ToyLinkedList toys = FileUtil.readToys(); // load all existing toys from files into ToyLinkedList

        // CREATE: Add new toy
        if ("add".equals(action)) {
            int id = (int) (System.currentTimeMillis() % 100000);
            String name = req.getParameter("name");
            String desc = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            int age = Integer.parseInt(req.getParameter("ageGroup"));
            
            // Handle image upload
            String imageFilename = null;
            Part filePart = req.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                imageFilename = UUID.randomUUID().toString() + extension;
                
                // Save the file
                String uploadPath = getServletContext().getRealPath("") + File.separator + "images" + File.separator + "toys";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                
                filePart.write(uploadPath + File.separator + imageFilename);
            }
            
            toys.add(new Toy(id, name, desc, price, age, imageFilename));
            FileUtil.writeToys(toys);
            resp.sendRedirect("toys.jsp");
        } 
        // DELETE: Remove toy
        else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Toy toy = toys.get(id);
            if (toy != null && toy.getImage() != null) {
                // Delete the image file
                String imagePath = getServletContext().getRealPath("") + File.separator + "images" + 
                                 File.separator + "toys" + File.separator + toy.getImage();
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }
            toys.remove(id);
            FileUtil.writeToys(toys);
            resp.sendRedirect("toys.jsp");
        }
        // UPDATE: Update toy information
        else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String desc = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            int age = Integer.parseInt(req.getParameter("ageGroup"));
            
            // Handle image upload for update
            String imageFilename = null;
            Part filePart = req.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                imageFilename = UUID.randomUUID().toString() + extension;
                
                // Save the file
                String uploadPath = getServletContext().getRealPath("") + File.separator + "images" + File.separator + "toys";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                
                filePart.write(uploadPath + File.separator + imageFilename);
            }
            
            toys.update(new Toy(id, name, desc, price, age, imageFilename));
            FileUtil.writeToys(toys);
            resp.sendRedirect("toys.jsp");
        } else if ("sort".equals(action)) {
            toys.sortByAgeGroup();
            FileUtil.writeToys(toys);
            resp.sendRedirect("toys.jsp");
        }
    }
}
