package com.nekomart.util;

import com.nekomart.model.Toy;
import com.nekomart.model.ToyLinkedList;

/**
 * SortUtil - Toy Sorting Helper
 * 
 * This is like a toy store organizer who helps arrange toys in different ways!
 * 
 * OOP Concepts (in simple terms):
 * 1. Utility Class: This is like a special helper that knows how to organize toys in different ways
 * 2. Static Methods: These are like different organizing rules that anyone can use, like sorting instructions
 * 3. Single Responsibility: This helper only handles sorting, just like a store organizer only arranges items
 * 
 * What this helper can do:
 * 1. Sort by Age: Arranges toys by age group (like putting all baby toys together)
 * 2. Sort by Price: Arranges toys by price (like putting all expensive toys in one section)
 * 3. Sort by Name: Arranges toys alphabetically (like putting all 'A' toys first)
 * 
 * Think of this as a very organized toy arranger that:
 * - Uses selection sort (like picking the smallest toy first)
 * - Works with any list of toys (like being able to organize any toy shelf)
 * - Makes everything neat and easy to find (like a well-organized toy store)
 */
public class SortUtil {
    
    /**
     * Selection sort implementation for toys by age group
     * Algorithm Pattern: Selection Sort
     * Time Complexity: O(n²)
     * Space Complexity: O(n) for temporary array
     */
    public static void sortByAgeGroup(ToyLinkedList toys) {
        Toy[] toyArray = toys.getAll();
        int n = toyArray.length;
        
        // Selection sort algorithm
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (toyArray[j].getAgeGroup() < toyArray[minIdx].getAgeGroup()) {
                    minIdx = j;
                }
            }
            
            if (minIdx != i) {
                Toy temp = toyArray[i];
                toyArray[i] = toyArray[minIdx];
                toyArray[minIdx] = temp;
            }
        }
        
        // Update the linked list with sorted array
        ToyLinkedList sortedList = new ToyLinkedList();
        for (Toy toy : toyArray) {
            sortedList.add(toy);
        }
        
        // Copy sorted data back to original list
        Toy[] originalToys = toys.getAll();
        for (int i = 0; i < n; i++) {
            originalToys[i] = toyArray[i];
        }
    }
    
    /**
     * Selection sort implementation for toys by price
     * Algorithm Pattern: Selection Sort
     * Time Complexity: O(n²)
     * Space Complexity: O(n) for temporary array
     */
    public static void sortByPrice(ToyLinkedList toys) {
        Toy[] toyArray = toys.getAll();
        int n = toyArray.length;
        
        // Selection sort algorithm
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (toyArray[j].getPrice() < toyArray[minIdx].getPrice()) {
                    minIdx = j;
                }
            }
            
            if (minIdx != i) {
                Toy temp = toyArray[i];
                toyArray[i] = toyArray[minIdx];
                toyArray[minIdx] = temp;
            }
        }
        
        // Update the linked list with sorted array
        ToyLinkedList sortedList = new ToyLinkedList();
        for (Toy toy : toyArray) {
            sortedList.add(toy);
        }
        
        // Copy sorted data back to original list
        Toy[] originalToys = toys.getAll();
        for (int i = 0; i < n; i++) {
            originalToys[i] = toyArray[i];
        }
    }
    
    /**
     * Selection sort implementation for toys by name
     * Algorithm Pattern: Selection Sort
     * Time Complexity: O(n²)
     * Space Complexity: O(n) for temporary array
     */
    public static void sortByName(ToyLinkedList toys) {
        Toy[] toyArray = toys.getAll();
        int n = toyArray.length;
        
        // Selection sort algorithm
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (toyArray[j].getName().compareTo(toyArray[minIdx].getName()) < 0) {
                    minIdx = j;
                }
            }
            
            if (minIdx != i) {
                Toy temp = toyArray[i];
                toyArray[i] = toyArray[minIdx];
                toyArray[minIdx] = temp;
            }
        }
        
        // Update the linked list with sorted array
        ToyLinkedList sortedList = new ToyLinkedList();
        for (Toy toy : toyArray) {
            sortedList.add(toy);
        }
        
        // Copy sorted data back to original list
        Toy[] originalToys = toys.getAll();
        for (int i = 0; i < n; i++) {
            originalToys[i] = toyArray[i];
        }
    }
} 