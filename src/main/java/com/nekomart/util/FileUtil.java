package com.nekomart.util;
import com.nekomart.model.User;
import com.nekomart.model.Toy;
import com.nekomart.model.ToyLinkedList;
import com.nekomart.model.Review;

import java.io.*;// For reading/writing files or data (e.g., PrintWriter, FileReader)
import java.util.*;// Includes List, ArrayList, HashMap, etc.
import java.text.SimpleDateFormat;// Lets you convert date/time into a readable string

/**
 * FileUtil - File Management Helper
 * 
 * This is like a toy store's filing cabinet helper who keeps track of all important information!
 * 
 * OOP Concepts (in simple terms):
 * 1. Utility Class: This is like a special helper that everyone can use, just like a store's filing system
 * 2. Static Methods: These are like pre-written instructions that anyone can follow, like a filing system manual
 * 3. Single Responsibility: This helper only handles files, just like a filing clerk only handles paperwork
 * 
 * Relationships:
 * - Dependency: Uses User model
 * - Dependency: Uses Toy model
 * - Dependency: Uses ToyLinkedList model
 * - Dependency: Uses Review model
 * - Association: Manages persistence of User, Toy, and Review objects
 * 
 * What this helper does:
 * 1. User Files: Keeps track of all store members (like a membership card file)
 * 2. Toy Files: Keeps track of all toys in the store (like a toy inventory list)
 * 3. Review Files: Keeps track of what people think about toys (like a review notebook)
 * 
 * Think of this as a very organized filing system that:
 * - Reads information (like opening a file drawer)
 * - Writes information (like putting new papers in the drawer)
 * - Keeps everything neat and organized (like a well-maintained filing system)
 */
public class FileUtil {
    private static final String USER_FILE = "users.txt";
    private static final String TOY_FILE = "toys.txt";
    private static final String REVIEW_FILE = "reviews.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String getDataDir() {
        // Use the specified path
        return "D:\\nekomart\\data";
    }

    private static void ensureDataDirExists() {
        File dataDir = new File(getDataDir());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    // User/Admin CRUD
    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        ensureDataDirExists();
        File userFile = new File(getDataDir(), USER_FILE);
        System.out.println("Reading users from: " + userFile.getAbsolutePath());
        
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length == 3) {
                    users.add(new User(arr[0], arr[1], arr[2]));
                    System.out.println("Loaded user: " + arr[0]);
                }
            }
        } catch (IOException e) { 
            System.out.println("Error reading users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public static void writeUsers(List<User> users) {
        ensureDataDirExists();
        File userFile = new File(getDataDir(), USER_FILE);
        System.out.println("Writing users to: " + userFile.getAbsolutePath());
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            for (User u : users) {
                String line = u.getUsername() + "," + u.getPassword() + "," + u.getRole();
                bw.write(line);
                bw.newLine();
                System.out.println("Saved user: " + u.getUsername());
            }
        } catch (IOException e) { 
            System.out.println("Error writing users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Toy CRUD
    public static ToyLinkedList readToys() {
        ToyLinkedList toys = new ToyLinkedList();
        ensureDataDirExists();
        File toyFile = new File(getDataDir(), TOY_FILE);
        System.out.println("Reading toys from: " + toyFile.getAbsolutePath());
        
        try (BufferedReader br = new BufferedReader(new FileReader(toyFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length == 6) {
                    Toy toy = new Toy(
                            Integer.parseInt(arr[0]),
                            arr[1], arr[2],
                            Double.parseDouble(arr[3]),
                            Integer.parseInt(arr[4]),
                            arr[5] // image filename
                    );
                    toys.add(toy);
                    System.out.println("Loaded toy: " + toy.getName());
                }
            }
        } catch (IOException e) { 
            System.out.println("Error reading toys: " + e.getMessage());
            e.printStackTrace();
        }
        return toys;
    }

    public static void writeToys(ToyLinkedList toys) {
        ensureDataDirExists();
        File toyFile = new File(getDataDir(), TOY_FILE);
        System.out.println("Writing toys to: " + toyFile.getAbsolutePath());
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(toyFile))) {
            for (Toy toy : toys.getAll()) {
                String line = toy.getId() + "," + toy.getName() + "," + 
                            toy.getDescription() + "," + toy.getPrice() + "," + 
                            toy.getAgeGroup() + "," + toy.getImage();
                bw.write(line);
                bw.newLine();
                System.out.println("Saved toy: " + toy.getName());
            }
        } catch (IOException e) { 
            System.out.println("Error writing toys: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Review CRUD
    public static List<Review> readReviews() {
        List<Review> reviews = new ArrayList<>();
        ensureDataDirExists();
        File reviewFile = new File(getDataDir(), REVIEW_FILE);
        System.out.println("Reading reviews from: " + reviewFile.getAbsolutePath());
        
        try (BufferedReader br = new BufferedReader(new FileReader(reviewFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length == 6) {
                    reviews.add(new Review(
                            Integer.parseInt(arr[0]),
                            Integer.parseInt(arr[1]),
                            arr[2],
                            Integer.parseInt(arr[3]),
                            arr[4],
                            arr[5]
                    ));
                }
            }
        } catch (IOException e) { 
            System.out.println("Error reading reviews: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    public static void writeReviews(List<Review> reviews) {
        ensureDataDirExists();
        File reviewFile = new File(getDataDir(), REVIEW_FILE);
        System.out.println("Writing reviews to: " + reviewFile.getAbsolutePath());
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reviewFile))) {
            for (Review r : reviews) {
                String line = r.getId() + "," + r.getToyId() + "," + 
                            r.getUsername() + "," + r.getRating() + "," + 
                            r.getComment() + "," + r.getDate();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) { 
            System.out.println("Error writing reviews: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Review> getReviewsForToy(int toyId) {
        List<Review> allReviews = readReviews();
        List<Review> toyReviews = new ArrayList<>();
        for (Review r : allReviews) {
            if (r.getToyId() == toyId) {
                toyReviews.add(r);
            }
        }
        return toyReviews;
    }

    public static double getAverageRating(int toyId) {
        List<Review> reviews = getReviewsForToy(toyId);
        if (reviews.isEmpty()) return 0.0;
        
        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        return sum / reviews.size();
    }
}
