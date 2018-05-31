/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ckeller22
 */
public class Inventory {
    
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static int partIdCount = 0;
    public static int productIdCount = 0;
    
    public static int getPartIdCount () {
        if (allParts.isEmpty()) {
            partIdCount ++;
            return partIdCount;
        } else {
            partIdCount = allParts.size();
            partIdCount ++;
            return partIdCount;
        }
        
    }
    
    public static int getProductIdCount () {
        if (products.isEmpty()) {
            productIdCount ++;
            return partIdCount;
        } else {
            productIdCount = products.size();
            productIdCount ++;
            return productIdCount;
        }
        
    }
    
    public static ObservableList<Product> getProducts () {
        return products;
    }

    
    public static void addProduct(Product product){
        products.add(product);
    }
    
    
    public static void deleteProduct(Product product) {
        products.remove(product);
        
    }
    
    
    public static int lookupProduct(String searchTerm) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < products.size(); i++) {
                if (Integer.parseInt(searchTerm) == products.get(i).getProductId()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (searchTerm.equalsIgnoreCase(products.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound == true) {
            System.out.println("Product found");
            return index;
            
        } else {
            System.out.println("No products found.");
            return -1;
        }
    }
    
    //
    public static void updateProduct(int index, Product product){
        products.set(index, product);
    }
    
    public static ObservableList<Part> getAllParts () {
        return allParts;
    }
    //
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    //
    public static void deletePart(Part part){
        allParts.remove(part);
    }
    
    //
    public static int lookupPart(String searchTerm){
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(searchTerm) == allParts.get(i).getPartId()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (searchTerm.equalsIgnoreCase(allParts.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound == true) {
            System.out.println("Part found");
            return index;
            
        } else {
            System.out.println("No parts found.");
            return -1;
        }
       
    }
    
    //
    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }
    
    public static boolean validateProductDelete (Product product) {
        if (product.associatedParts.size() > 0){
            return false;
        } else
            return true;
    }
    
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
