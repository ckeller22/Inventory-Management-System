/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ckeller22
 */
public abstract class Part {
    
    private final IntegerProperty partId;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    //Constructor
    public Part() {
        partId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }
    
    /*public Part(int partId, String name, double price, int inStock, int min, int max){
       setPartId(partId);
       setName(name);
       setPrice(price);
       setInStock(inStock);
       setMin(min);
       setMax(max);
    }
    */
    
    
    //name
    public void setName(String name){
        this.name.set(name);
    }
    
    public String getName(){
        return this.name.get();
    }
    
    public StringProperty getPartNameProperty () {
        return name;
    }
    
    //price
    public void setPrice(double price){
        this.price.set(price);
    }
    
    public double getPrice(){
        return this.price.get();
    }
    
    public DoubleProperty getPartPriceProperty () {
        return price; 
    }
    
    //inStock
    public void setInStock(int stock){
        this.inStock.set(stock);
    }
    
    public int getInStock(){
        return this.inStock.get();
    }
    
    public IntegerProperty getPartInStockProperty () {
        return inStock;
    }
    
    //min
    public void setMin(int min){
        this.min.set(min);
    }
    
    public int getMin(){
        return this.min.get();
    }
    
    //max
    public void setMax(int max){
        this.max.set(max);
    }
    
    public int getMax(){
        return this.max.get();
    }
    
    //partId
    public void setPartId(int id){
        this.partId.set(id);
    }
    
    public int getPartId(){
        return this.partId.get();
    }
    
    public IntegerProperty getPartIdProperty () {
        return partId;
    }
    
    public static String isPartValid (String name, int inStock, int max, int min, double price) {
       String errorMessage = "";
       if (name == null) {
           errorMessage = errorMessage + "The name field is required. ";
       }
       if (price <= 0) {
           errorMessage = errorMessage + "The price must be greater than 0. ";
       }
       if (max < min) {
           errorMessage = errorMessage + "The maximum must be greater than the minimum. ";
       }
       if (min > max) {
           errorMessage = errorMessage + "The minimum must be less than the maximum. ";
       }
       if (inStock > max || inStock < min) {
           errorMessage = errorMessage + "Inventory must fall between the minimum and maximum ranges. ";
       }
       return errorMessage; 
   }
   
}
