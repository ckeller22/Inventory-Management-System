/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.View_Controller;

import cskims.Model.Inventory;
import static cskims.Model.Inventory.getAllParts;
import static cskims.Model.Inventory.getProductIdCount;
import static cskims.Model.Inventory.lookupPart;
import static cskims.Model.Inventory.updateProduct;
import cskims.Model.Part;
import cskims.Model.Product;
import static cskims.Model.Product.getAssociatedParts;
import static cskims.Model.Product.isProductValid;
import static cskims.View_Controller.MainPageController.modifyProduct;
import static cskims.View_Controller.MainPageController.modifyProductIndex;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class ModifyProductController implements Initializable {

    @FXML
    private Label lblModifyProduct;

    @FXML
    private TextField txtModifyProductId;

    @FXML
    private TextField txtModifyProductName;

    @FXML
    private TextField txtModifyProductInv;

    @FXML
    private TextField txtModifyProductPrice;

    @FXML
    private TextField txtModifyProductMin;

    @FXML
    private TextField txtModifyProductMax;

    @FXML
    private Button btnModifyProductSearch;

    @FXML
    private TextField txtModifyProductSearch;
    
    @FXML
    private TableView<Part> tvModifyProductAdd;
    
    @FXML
    private TableColumn<Part, Integer> tvModifyProductAddIdColumn;

    @FXML
    private TableColumn<Part, String> tvModifyProductAddNameColumn;

    @FXML
    private TableColumn<Part, Integer> tvModifyProductAddInvColumn;

    @FXML
    private TableColumn<Part, Double> tvModifyProductAddPriceColumn;
    
    @FXML
    private TableView<Part> tvModifyProductDelete; 
    
    @FXML
    private TableColumn<Part, Integer> tvModifyProductDeleteIdColumn;

    @FXML
    private TableColumn<Part, String> tvModifyProductDeleteNameColumn;

    @FXML
    private TableColumn<Part, Integer> tvModifyProductDeleteInvColumn;

    @FXML
    private TableColumn<Part, Double> tvModifyProductDeletePriceColumn;

    @FXML
    private Button btnModifyProductSave;

    @FXML
    private Button btnModifyProductCancel;

    @FXML
    private Button btnModifyProductDelete;

    @FXML
    private Button btnModifyProductAdd;
    
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage;

    @FXML
    void handleAdd(ActionEvent event) {
        if (tvModifyProductAdd.getSelectionModel().getSelectedItem() == null) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding part!");
            alert.setContentText("Please ensure a part is selected to be added.");
            alert.showAndWait();
        } else {
            Part selectedPart = tvModifyProductAdd.getSelectionModel().getSelectedItem();
            currentParts.add(selectedPart);
            updateModifyProductDeleteTableView();
        }
    }
    
    @FXML
    void handleDelete(ActionEvent event) {
        if (tvModifyProductDelete.getSelectionModel().getSelectedItem() == null) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting part!");
            alert.setContentText("Please ensure a part is selected to be removed.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Are you sure you wish to delete the part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Part selectedPart = tvModifyProductDelete.getSelectionModel().getSelectedItem();
                currentParts.remove(selectedPart);
                updateModifyProductDeleteTableView();
            }
        }
    
    }

    @FXML
    void handleModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you wish to cancel?");
        alert.setContentText("Information not saved will be lost!");
        
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            Parent modifyProductParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene modifyProductScene = new Scene (modifyProductParent);
            Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyProductStage.setScene(modifyProductScene);
            modifyProductStage.show();
        }
    }

    @FXML
    void handleModifyProductSave(ActionEvent event) throws IOException {
        String productName = txtModifyProductName.getText();
        String productInv = txtModifyProductInv.getText();
        String productPrice = txtModifyProductPrice.getText();
        String productMax = txtModifyProductMax.getText();
        String productMin = txtModifyProductMin.getText();
        
        try {
            exceptionMessage = isProductValid(productName, Integer.parseInt(productInv), Integer.parseInt(productMax), Integer.parseInt(productMin), Double.parseDouble(productPrice), currentParts);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error adding product.");
                    alert.setContentText(exceptionMessage);
                    alert.showAndWait();
                    exceptionMessage = "";
            } else {
                Product tempProduct = new Product();
                tempProduct.setProductId(getProductIdCount());
                tempProduct.setName(productName);
                tempProduct.setInStock(Integer.parseInt(productInv));
                tempProduct.setPrice(Double.parseDouble(productPrice));
                tempProduct.setMax(Integer.parseInt(productMax));
                tempProduct.setMin(Integer.parseInt(productMin));
                tempProduct.associatedParts = currentParts;
                updateProduct(modifyProductIndex, tempProduct);
                System.out.println("Product added! Name: " + productName);

                Parent addProductParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Scene addProductScene = new Scene(addProductParent);
                Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addProductStage.setScene(addProductScene);
                addProductStage.show();
                } 
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
    void handleModifyProductSearch(ActionEvent event) {
        String search = txtModifyProductSearch.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(search) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found!");
            alert.setContentText("Search term does not match any known parts.");
            alert.showAndWait();
        } else {
            partIndex = lookupPart(search);
            Part tempPart = getAllParts().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            tvModifyProductAdd.setItems(tempPartList);
            
        }
    }
    
    public void updateModifyProductAddTableView () {
        tvModifyProductAdd.setItems(getAllParts());
    }
    
    public void updateModifyProductDeleteTableView() {
        tvModifyProductDelete.setItems(currentParts);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtModifyProductId.setText("Auto gen: " + Integer.toString((modifyProduct.getProductId())));
        txtModifyProductName.setText(modifyProduct.getName());
        txtModifyProductInv.setText(Integer.toString(modifyProduct.getInStock()));
        txtModifyProductPrice.setText(Double.toString(modifyProduct.getPrice()));
        txtModifyProductMax.setText(Integer.toString(modifyProduct.getMax()));
        txtModifyProductMin.setText(Integer.toString(modifyProduct.getMin()));
        
        currentParts = getAssociatedParts(modifyProduct);
        tvModifyProductAddIdColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIdProperty().asObject());
        tvModifyProductAddNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        tvModifyProductAddInvColumn.setCellValueFactory(cellData -> cellData.getValue().getPartInStockProperty().asObject());
        tvModifyProductAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        tvModifyProductDeleteIdColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIdProperty().asObject());
        tvModifyProductDeleteNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        tvModifyProductDeleteInvColumn.setCellValueFactory(cellData -> cellData.getValue().getPartInStockProperty().asObject());
        tvModifyProductDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        updateModifyProductAddTableView();
        updateModifyProductDeleteTableView();
        
    }    
    
}
