/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.View_Controller;

import cskims.Model.InhousePart;
import static cskims.Model.Inventory.getAllParts;
import static cskims.Model.Inventory.getPartIdCount;
import static cskims.Model.Inventory.updatePart;
import cskims.Model.OutsourcedPart;
import static cskims.Model.Part.isPartValid;
import static cskims.View_Controller.MainPageController.modifyPart;
import static cskims.View_Controller.MainPageController.modifyPartIndex;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {

    @FXML
    private Label lblModifyPart;

    @FXML
    private RadioButton radioModifyPartInhouse;

    @FXML
    private ToggleGroup Part;

    @FXML
    private RadioButton radioModifyPartOutsourced;

    @FXML
    private TextField txtModifyPartId;

    @FXML
    private TextField txtModifyPartName;

    @FXML
    private TextField txtModifyPartInv;

    @FXML
    private TextField txtModifyPartPrice;

    @FXML
    private TextField txtModifyPartMax;

    @FXML
    private Label lblModifyPartId;

    @FXML
    private TextField txtModifyPartMin;

    @FXML
    private TextField txtModifyPartDyn;

    @FXML
    private Label lblModifyPartDyn;

    @FXML
    private Button btnModifyPartSave;

    @FXML
    private Button btnModifyPartCancel;
    
    private boolean isOutsourced;
    String exceptionMessage;

    @FXML
    void handleModifyPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you wish to cancel?");
        alert.setContentText("Information not saved will be lost!");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene modifyPartScene = new Scene (modifyPartParent);
            Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();
        }
    }

    @FXML
    void handleModifyPartSave(ActionEvent event) throws IOException {
        String partName = txtModifyPartName.getText();
        String partInStock = txtModifyPartInv.getText();
        String partPrice = txtModifyPartPrice.getText();
        String partMax = txtModifyPartMax.getText();
        String partMin = txtModifyPartMin.getText();
            
        try {
            exceptionMessage = isPartValid(partName, Integer.parseInt(partInStock), Integer.parseInt(partMax), Integer.parseInt(partMin), Double.parseDouble(partPrice));
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
                    tempPart.setCompanyName(txtModifyPartDyn.getText());
                    updatePart(modifyPartIndex, tempPart);
                    System.out.println("Outsourced part updated! Part name: " + partName);
                } else {
                    InhousePart tempPart = new InhousePart();
                    tempPart.setPartId(getPartIdCount());
                    tempPart.setName(partName);
                    tempPart.setInStock(Integer.parseInt(partInStock));
                    tempPart.setPrice(Double.parseDouble(partPrice));
                    tempPart.setMax(Integer.parseInt(partMax));
                    tempPart.setMin(Integer.parseInt(partMin));
                    tempPart.setMachineId(Integer.parseInt(txtModifyPartDyn.getText()));
                    updatePart(modifyPartIndex, tempPart);
                    System.out.println("Inhouse part updated! Part Name: " + partName);    
                } 
            }
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene modifyPartScene = new Scene (modifyPartParent);
            Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();
        } 
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding part");
            alert.setContentText("Form contains empty fields!");
            alert.showAndWait();
        }
    }

    @FXML
    void modifyPartInhouseRadio(ActionEvent event) {
        isOutsourced = false;
        lblModifyPartDyn.setText("Machine ID");
        txtModifyPartDyn.setPromptText("Machine ID");
        txtModifyPartDyn.setText("");
    }

    @FXML
    void modifyPartOutsourcedRadio(ActionEvent event) {
        isOutsourced = true;
        lblModifyPartDyn.setText("Company Name");
        txtModifyPartDyn.setPromptText("Company Name");
        txtModifyPartDyn.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtModifyPartId.setText("Auto Gen: " + Integer.toString(modifyPart.getPartId()));
        txtModifyPartName.setText(modifyPart.getName());
        txtModifyPartPrice.setText(Double.toString(modifyPart.getPrice()));
        txtModifyPartInv.setText(Integer.toString(modifyPart.getInStock()));
        txtModifyPartMax.setText(Integer.toString(modifyPart.getMax()));
        txtModifyPartMin.setText(Integer.toString(modifyPart.getMin()));
        if (modifyPart instanceof InhousePart) {
            lblModifyPartDyn.setText("Machine ID");
            txtModifyPartDyn.setText(Integer.toString(((InhousePart) getAllParts().get(modifyPartIndex)).getMachineId()));
            radioModifyPartInhouse.setSelected(true);
            isOutsourced = false;
        } else {
            lblModifyPartDyn.setText("Company Name");
            txtModifyPartDyn.setText(((OutsourcedPart) getAllParts().get(modifyPartIndex)).getCompanyName());
            radioModifyPartOutsourced.setSelected(true);
            isOutsourced = true;
        }
    }    
    
}
