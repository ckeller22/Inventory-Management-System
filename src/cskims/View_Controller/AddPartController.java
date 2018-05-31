/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.View_Controller;

import cskims.Model.InhousePart;
import static cskims.Model.Inventory.allParts;
import static cskims.Model.Inventory.getPartIdCount;
import cskims.Model.OutsourcedPart;
import static cskims.Model.Part.isPartValid;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class AddPartController implements Initializable {

    @FXML
    private Label lblAddPart;

    @FXML
    private RadioButton radioAddPartInhouse;

    @FXML
    private ToggleGroup Part;

    @FXML
    private RadioButton radioAddPartOutsourced;

    @FXML
    private TextField txtAddPartId;

    @FXML
    private TextField txtAddPartName;

    @FXML
    private TextField txtAddPartInv;

    @FXML
    private TextField txtAddPartPrice;

    @FXML
    private TextField txtAddPartMax;

    @FXML
    private TextField txtAddPartMin;

    @FXML
    private TextField txtAddPartDyn;

    @FXML
    private Label lblAddPartDyn;

    @FXML
    private Button btnAddPartSave;

    @FXML
    private Button btnAddPartCancel;
    
    private boolean isOutsourced = true;
    String exceptionMessage;
    
    
    @FXML
    void addPartInhouseRadio(ActionEvent event) {
        isOutsourced = false;
        lblAddPartDyn.setText("Machine ID");
        txtAddPartDyn.setPromptText("Machine ID");
    }

    @FXML
    void addPartOutsourcedRadio(ActionEvent event) {
        isOutsourced = true;
        lblAddPartDyn.setText("Company Name");
        txtAddPartDyn.setPromptText("Company Name");
    }

    @FXML
    void handleAddPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you wish to cancel?");
        alert.setContentText("Information not saved will be lost!");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent mainPageParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene mainPageScene = new Scene(mainPageParent);
            Stage mainPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainPageStage.setScene(mainPageScene);
            mainPageStage.show();
        }
    }

    @FXML
    void handleAddPartSave(ActionEvent event) throws IOException {
        String partName = txtAddPartName.getText();
        String partInStock = txtAddPartInv.getText();
        String partPrice = txtAddPartPrice.getText();
        String partMax = txtAddPartMax.getText();
        String partMin = txtAddPartMin.getText();
       
        try {
            exceptionMessage = isPartValid(partName, Integer.parseInt(partInStock), Integer.parseInt(partMax), Integer.parseInt(partMin), Double.parseDouble(partPrice));
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else {
                if (isOutsourced == true) {
                    OutsourcedPart tempPart = new OutsourcedPart();
                    tempPart.setPartId(getPartIdCount());
                    tempPart.setName(partName);
                    tempPart.setInStock(Integer.parseInt(partInStock));
                    tempPart.setPrice(Double.parseDouble(partPrice));
                    tempPart.setMax(Integer.parseInt(partMax));
                    tempPart.setMin(Integer.parseInt(partMin));
                    tempPart.setCompanyName(txtAddPartDyn.getText());
                    allParts.add(tempPart);
                    System.out.println("Outsourced proudct saved! Part name: " + partName);
                } else {
                    InhousePart tempPart = new InhousePart();
                    tempPart.setPartId(getPartIdCount());
                    tempPart.setName(partName);
                    tempPart.setInStock(Integer.parseInt(partInStock));
                    tempPart.setPrice(Double.parseDouble(partPrice));
                    tempPart.setMax(Integer.parseInt(partMax));
                    tempPart.setMin(Integer.parseInt(partMin));
                    tempPart.setMachineId(Integer.parseInt(txtAddPartDyn.getText()));
                    allParts.add(tempPart);
                    System.out.println("Inhouse product saved! Part Name: " + partName);
                } 
            Parent addPartSaveParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene addPartSaveScene = new Scene(addPartSaveParent);
            Stage addPartSaveStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addPartSaveStage.setScene(addPartSaveScene);
            addPartSaveStage.show(); 
            }
        } 
        catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding part");
            alert.setContentText("Form contains empty fields!");
            alert.showAndWait();
        }   
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
