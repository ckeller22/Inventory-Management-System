/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskims.View_Controller;

import cskims.Model.Inventory;
import static cskims.Model.Inventory.deletePart;
import static cskims.Model.Inventory.getAllParts;
import static cskims.Model.Inventory.lookupPart;
import static cskims.Model.Inventory.*;
import cskims.Model.Part;
import cskims.Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class MainPageController implements Initializable {

    @FXML
    private Label lblParts;

    @FXML
    private TableView<Part> tvParts;
    
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TextField txtPartSearch;

    @FXML
    private Button btnPartsSearch;

    @FXML
    private Button btnPartsAdd;

    @FXML
    private Button btnPartsModify;

    @FXML
    private Button btnPartDelete;

    @FXML
    private Label lblProducts;

    @FXML
    private TableView<Product> tvProducts;
    
    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInvColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TextField txtProductSearch;

    @FXML
    private Button btnProductsSearch;

    @FXML
    private Button btnProductAdd;

    @FXML
    private Button btnProductModify;

    @FXML
    private Button btnProductDelete;

    @FXML
    private Button btnExit;
    
    public static Part modifyPart;
    public static int modifyPartIndex;
    public static Product modifyProduct;
    public static int modifyProductIndex;

    @FXML
    void handleExit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you wish to exit?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
                               
    }

    @FXML
    void handlePartDelete(ActionEvent event) {
        if (tvParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting part!");
            alert.setContentText("Please ensure a part is selected to be removed.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Are you sure you wish to delete the part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Part part = tvParts.getSelectionModel().getSelectedItem();
                deletePart(part);
                updatePartTableView();
            }
        }
    }

    @FXML
    void handlePartModify(ActionEvent event) throws IOException {
        if (tvParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating part!");
            alert.setContentText("Please ensure a part is selected to be updated.");
            alert.showAndWait();
        } else {
            modifyPart = tvParts.getSelectionModel().getSelectedItem();
            modifyPartIndex = getAllParts().indexOf(modifyPart);
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartScene = new Scene (modifyPartParent);
            Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();
        }
    }
    
    @FXML
    void handlePartsAdd(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    @FXML
    void handlePartsSearch(ActionEvent event) {
        String search = txtPartSearch.getText();
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
            tvParts.setItems(tempPartList);
            
        }

    }

    @FXML
    void handleProductAdd(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    @FXML
    void handleProductDelete(ActionEvent event) {
        if (tvProducts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting product!");
            alert.setContentText("Please ensure a product is selected to be removed.");
            alert.showAndWait();
        } else {
            Product tempProduct = tvProducts.getSelectionModel().getSelectedItem();
            if (validateProductDelete(tempProduct) == false) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Error deleting product");
                alert.setContentText("Product cannot be deleted while parts are associated.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Confirm Delete");
                alert.setContentText("Are you sure you wish to delete the product?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Product product = tvProducts.getSelectionModel().getSelectedItem();
                    deleteProduct(product);
                    updateProductTableView();
                }
            }   
        }
    }
    
    @FXML
    void handleProductModify(ActionEvent event) throws IOException {
        if (tvProducts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating part!");
            alert.setContentText("Please ensure a product is selected to be updated.");
            alert.showAndWait();
        } else {
            modifyProduct = tvProducts.getSelectionModel().getSelectedItem();
            modifyProductIndex = getProducts().indexOf(modifyProduct);
            Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modifyProductScene = new Scene (modifyProductParent);
            Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyProductStage.setScene(modifyProductScene);
            modifyProductStage.show();
        }
    }
    
    @FXML
    void handleProductsSearch(ActionEvent event) {
        String search = txtProductSearch.getText();
        int productIndex = -1;
        if (Inventory.lookupProduct(search) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found!");
            alert.setContentText("Search term does not match any known products.");
            alert.showAndWait();
        } else {
            productIndex = lookupProduct(search);
            Product tempProduct = getProducts().get(productIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            tvProducts.setItems(tempProductList);
            
        }
    }
    
    //Update table views
    public void updatePartTableView() {
        tvParts.setItems(getAllParts());
    }
    
    public void updateProductTableView() {
        tvProducts.setItems(getProducts());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIdProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        partInvColumn.setCellValueFactory(cellData -> cellData.getValue().getPartInStockProperty().asObject());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().getProductIdProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProductNameProperty());
        productInvColumn.setCellValueFactory(cellData -> cellData.getValue().getProductInStockProperty().asObject());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getProductPriceProperty().asObject());
        updatePartTableView();
        updateProductTableView();
    }    
    
}
