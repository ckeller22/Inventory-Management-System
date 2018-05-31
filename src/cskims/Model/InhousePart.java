/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author ckeller22
 */
public class InhousePart extends Part{
    
    private final IntegerProperty machineId;
    
    public InhousePart() {
        super();
        machineId = new SimpleIntegerProperty();
    }
    /*public Inhouse(int productId, String name, double price, int inStock, int min, int max, int machineId){
        super(productId, name, price, inStock, min, max);
        setMachineId(machineId);
    }
    */
    
    public void setMachineId(int id){
        this.machineId.set(id);
    }
    
    public int getMachineId(){
        return this.machineId.get();
    }
}
