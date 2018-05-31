/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.Model;


import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ckeller22
 */
public class Product {
    
    private final IntegerProperty productId;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    //Name
    public void setName(String name){
       this.name.set(name);
   }
   
   public String getName(){
       return this.name.get();
   }
   
   public StringProperty getProductNameProperty () {
       return name; 
   }
   
   //Price
   public void setPrice(double price){
       this.price.set(price);
   }
   
   public double getPrice(){
       return this.price.get();
   }
   
   public DoubleProperty getProductPriceProperty () {
       return price;
   }
   
   //inStock
   public void setInStock(int stock){
       this.inStock.set(stock);
   }
   
   public int getInStock(){
       return this.inStock.get();
   }
   
   public IntegerProperty getProductInStockProperty () {
       return inStock;
   }
   
   //Min
   public void setMin(int min){
       this.min.set(min);
   }
   
   public int getMin(){
       return this.min.get();
   }
   
   //Max
   public void setMax(int max){
       this.max.set(max);
   }
   
   public int getMax(){
       return this.max.get();
   }
   
   //productId
   public void setProductId(int productId){
       this.productId.set(productId);
   }
   
   public int getProductId(){
       return this.productId.get();
   }
   
   public IntegerProperty getProductIdProperty () {
       return productId;
   }
   
   
   //Constructor
   public Product() {
        productId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
         
    }
   
   /*public Product(int productId, String name, double price, int inStock, int min, int max){
       setProductId(productId);
       setName(name);
       setPrice(price);
       setInStock(inStock);
       setMin(min);
       setMax(max);
   }
   */
   
   
   public void addAssociatedPart(Part part){
       associatedParts.add(part);
   }
   
   
   public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
   } 
   
   
   public int lookupAssociatedPart(String searchTerm){
       boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < associatedParts.size(); i++) {
                if (Integer.parseInt(searchTerm) == associatedParts.get(i).getPartId()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < associatedParts.size(); i++) {
                if (searchTerm.equalsIgnoreCase(associatedParts.get(i).getName())) {
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
   
   public static ObservableList<Part> getAssociatedParts (Product product) {
       return associatedParts;
   }
   
   public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
   
   public static String isProductValid (String name, int inStock, int max, int min, double price, ObservableList<Part> parts ) {
       String errorMessage = "";
       double sumOfParts = 0.00;
       if (name == null) {
           errorMessage = errorMessage + "The name field is required. ";
       }
       if (price <= 0) {
           errorMessage = errorMessage + "The price must be greater than 0.";
       }
       if (max < min) {
           errorMessage = errorMessage + "The maximum must be greater than the minimum.";
       }
       if (min > max) {
           errorMessage = errorMessage + "The minimum must be less than the maximum.";
       }
       if (inStock > max || inStock < min) {
           errorMessage = errorMessage + "Inventory must fall between the minimum and maximum ranges.";
       }
       if (parts.isEmpty()) {
           errorMessage = errorMessage + "Product must have at least one part associated.";
       }
       for (int i = 0; i < parts.size(); i++) {
           sumOfParts = sumOfParts + parts.get(i).getPrice();
       }
       if (price < sumOfParts) {
           errorMessage = errorMessage + "Product price must be greater than the value of the sum of the parts.";
       }
       
       return errorMessage; 
   }
}
