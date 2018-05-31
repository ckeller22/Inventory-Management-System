/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ckeller22
 */
public class OutsourcedPart extends Part {
    
    private final StringProperty companyName;
    
    public OutsourcedPart() {
        super();
        companyName = new SimpleStringProperty();
    }
    /*public Outsourced(int productId, String name, double price, int inStock, int min, int max, String companyName){
        super(productId, name, price, inStock, min, max);
        setCompanyName(companyName);
    }
    */
    public void setCompanyName(String name){
        this.companyName.set(name);
    }
    
    public String getCompanyName(){
        return this.companyName.get();
    }
}
