/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.View_Controller;

import cskims.Model.Inventory;
import static cskims.Model.Inventory.addProduct;
import static cskims.Model.Inventory.getAllParts;
import static cskims.Model.Inventory.getProductIdCount;
import static cskims.Model.Inventory.lookupPart;
import cskims.Model.Part;
import cskims.Model.Product;
import static cskims.Model.Product.isProductValid;
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
public class AddProductController implements Initializable {

    @FXML
    private Label lblAddProduct;

    @FXML
    private TextField txtAddProductName;

    @FXML
    private TextField txtAddProductInv;

    @FXML
    private TextField txtAddProductPrice;

    @FXML
    private TextField txtAddProductMin;

    @FXML
    private TextField txtAddProductMax;

    @FXML
    private Button btnAddProductSearch;

    @FXML
    private TextField txtAddProductSearch;

    @FXML
    private TableView<Part> tvAddProductAdd;
    
    @FXML
    private TableColumn<Part, Integer> tvAddProductAddIdColumn;

    @FXML
    private TableColumn<Part, String> tvAddProductAddNameColumn;

    @FXML
    private TableColumn<Part, Integer> tvAddProductAddInvColumn;

    @FXML
    private TableColumn<Part, Double> tvAddProductAddPriceColumn;
    
    @FXML
    private TableView<Part> tvAddProductDelete;
    
    @FXML
    private TableColumn<Part, Integer> tvAddProductDeleteIdColumn;

    @FXML
    private TableColumn<Part, String> tvAddProductDeleteNameColumn;

    @FXML
    private TableColumn<Part, Integer> tvAddProductDeleteInvColumn;

    @FXML
    private TableColumn<Part, Double> tvAddProductDeletePriceColumn;

    @FXML
    private Button btnAddProductSave;

    @FXML
    private Button btnAddProductCancel;

    @FXML
    private Button btnAddProductDelete;

    @FXML
    private Button btnAddProductAdd;
    
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    String exceptionMessage;

    @FXML
    void handleAdd(ActionEvent event) {
        if (tvAddProductAdd.getSelectionModel().getSelectedItem() == null) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding part!");
            alert.setContentText("Please ensure a part is selected to be added.");
            alert.showAndWait();
        } else {
            Part selectedPart = tvAddProductAdd.getSelectionModel().getSelectedItem();
            currentParts.add(selectedPart);
            updateAddProductDeleteTableView();
        }
    }
        

    @FXML
    void handleAddProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you wish to cancel?");
        alert.setContentText("Information not saved will be lost!");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent addProductParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene addProductScene = new Scene(addProductParent);
            Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addProductStage.setScene(addProductScene);
            addProductStage.show();
        }
    }

    @FXML
    void handleAddProductSave(ActionEvent event) throws IOException {
        String productName = txtAddProductName.getText();
        String productInv = txtAddProductInv.getText();
        String productPrice = txtAddProductPrice.getText();
        String productMax = txtAddProductMax.getText();
        String productMin = txtAddProductMin.getText();
        
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
                addProduct(tempProduct);
                tempProduct.associatedParts = currentParts;
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
    void handleAddProductSearch(ActionEvent event) {
        String search = txtAddProductSearch.getText();
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
            tvAddProductAdd.setItems(tempPartList);
            
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        if (tvAddProductDelete.getSelectionModel().getSelectedItem() == null) {
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
                Part selectedPart = tvAddProductDelete.getSelectionModel().getSelectedItem();
                currentParts.remove(selectedPart);
                updateAddProductDeleteTableView();
            }
        }
    }
    
    public void updateAddProductAddTableView () {
        tvAddProductAdd.setItems(getAllParts());
    }
    
    public void updateAddProductDeleteTableView() {
        tvAddProductDelete.setItems(currentParts);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tvAddProductAddIdColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIdProperty().asObject());
        tvAddProductAddNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        tvAddProductAddInvColumn.setCellValueFactory(cellData -> cellData.getValue().getPartInStockProperty().asObject());
        tvAddProductAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        tvAddProductDeleteIdColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIdProperty().asObject());
        tvAddProductDeleteNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        tvAddProductDeleteInvColumn.setCellValueFactory(cellData -> cellData.getValue().getPartInStockProperty().asObject());
        tvAddProductDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        updateAddProductAddTableView();
        updateAddProductDeleteTableView();
    }    
    
}
