package com.nekomart.servlet;
import com.nekomart.model.Toy;
import com.nekomart.model.ToyLinkedList;
import com.nekomart.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.UUID;

@MultipartConfig
public class ToyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ToyLinkedList toys = FileUtil.readToys();

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
        } else if ("delete".equals(action)) {
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
        } else if ("update".equals(action)) {
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
