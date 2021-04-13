package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 FXML Main Screen Controller class.
 @author Sean
 */
public class MainScreenController implements Initializable {

    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;
    
    @FXML private TextField partsSearch;
    @FXML private Button partsAddButton;
    @FXML private Button partsModifyButton;
    @FXML private Button partsDeleteButton;
    
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productCostColumn;
    
    @FXML private TextField productsSearch;
    @FXML private Button productsAddButton;
    @FXML private Button productsModifyButton;
    @FXML private Button productsDeleteButton;
    
    @FXML private Label partsConfirmationText;
    @FXML private Label productsConfirmationText;
    
    @FXML private Button mainScreenExitButton;
    
    /**
     Called to initialize the Main Screen controller after its root element has been completely processed. 
     Initializes values for TableViewColumns and implements the searchHandlers for searching the Part and Product TableViews.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in each TableView (Parts, Products, respectively)
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Implement partsSearch functionality
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);
        partsSearchHandler(partsTableView, partsSearch, filteredParts);
        
        //Implement productsSearch functionality
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.getAllProducts(), p -> true);
        productsSearchHandler(productsTableView, productsSearch, filteredProducts);
        
    }
    
    /**
     This method acts as a listener for the Add button on the Parts pane being pushed. Simply changes scenes to the Add Part screen using the changeSceneTo() method.
     @param event the required ActionEvent object, representing when the Add button is pushed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void addPartButtonPushed(ActionEvent event) throws IOException {
        
        changeSceneTo(event, "/View_Controller/addPart.fxml");
        
    }
    
    /**
     This method acts as a listener for the Modify button on the Parts pane being pushed. 
     If no selection is made, an error is displayed to the user.
     With a selection made, a copy of the selected object from the Parts TableView is created. The subclass of the Part object is identified, and the partToModify is cast to that subclass.
     Next, an FXMLLoader is created to access the Modify Part controller, in order to pass partToModify's data into the Modify Part screen's TextFields.
     This is done by calling the initializeFields() method. Finally, the scene is changed to the Modify Part screen.
     @param event the required ActionEvent object, representing when the Modify button on the Parts pane is pushed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void modifyPartButtonPushed(ActionEvent event) throws IOException {
        
        //check if a selection in the partsTableView has been made or not
        if (!partsTableView.getSelectionModel().isEmpty()) {
            //store the selected Part from the partsTableView in selectedPart
            Part partToModify = partsTableView.getSelectionModel().getSelectedItem();
            
            //determine subclass of partToModify, then create it for later use
            if (partToModify instanceof InHousePart) {
                ModifyPartController.selectedPart = (InHousePart) partToModify;
                
            } else if (partToModify instanceof OutsourcedPart) {
                ModifyPartController.selectedPart = (OutsourcedPart) partToModify;
            }
            
            //create FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/modifyPart.fxml"));
            Parent newScene = loader.load();
            
            //access the controller from the loader and call method to pass data into fields
            ModifyPartController controller = loader.getController();
            controller.initializeFields();
            
            //use part of changeSceneTo() method to switch scenes
            Scene scene = new Scene(newScene);

            // this line gets the stage information
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
            
        } else { // if no selection is made, display error message.
            partsConfirmationText.setText("Error: No Part selected.");
        }
        
    }
    
    /**
     This method listens for when the Delete button on the Parts pane is pushed. 
     If there is no selection made, the method creates a dialog box indicating this error.
     If a selection has been made, the user is prompted via dialog box for confirmation; the user may Cancel or Delete the selected Part.
     @param event the ActionEvent object representing the Delete button on the Parts pane being pushed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void deletePartButtonPushed(ActionEvent event) throws IOException {
        // check that a selection has been made
        if (!partsTableView.getSelectionModel().isEmpty()) {
            // With a selection made, display an alert asking the user if they are sure
            Part partToDelete = partsTableView.getSelectionModel().getSelectedItem();
            String partName = partToDelete.getName();
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Part Confirmation");
            alert.setHeaderText("Are you sure you would like to delete the chosen part, " + partName + "?");
            alert.setContentText("This action is permanent and cannot be undone.");
            
            ButtonType delete = new ButtonType("Delete");
            ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            
            alert.getButtonTypes().setAll(delete, cancel);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == delete) {
                Inventory.deletePart(partToDelete);
                partsConfirmationText.setText("Part deleted successfully.");
                
            } else if (result.get() == cancel) partsConfirmationText.setText("Part was not deleted.");
            
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to delete, then click Delete.");
            alert.showAndWait();
        }        
        
    }
    
    /**
     This method listens for the Add button on the Products pane being pushed. Simply changes scenes to the Add Product screen using the changeSceneTo() method.
     @param event the ActionEvent object representing the Add button on the Products pane being pushed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void addProductButtonPushed(ActionEvent event) throws IOException {
        
        changeSceneTo(event, "/View_Controller/addProduct.fxml");
        
    }
    
    /**
     This method listens for the Modify button on the Products pane being pushed. 
     If no selection is made on the Product pane, the method displays an error to the user.
     With a selection made, a copy of the selected Product in the Products TableView is created.
     Next, an FXMLLoader is created to access the Modify Product controller, in order to pass selectedProduct's data into the Modify Product screen's TextFields.
     @param event the ActionEvent object representing the Modify button on the Products pane being pushed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void modifyProductButtonPushed(ActionEvent event) throws IOException {
        //check if a selection in the productsTableView has been made or not
        if (!productsTableView.getSelectionModel().isEmpty()) {
            //store the selected Product from the productsTableView in public static selectedProduct
            ModifyProductController.selectedProduct = Inventory.lookupProduct(productsTableView.getSelectionModel().getSelectedItem().getId());
            
            //create FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/modifyProduct.fxml"));
            Parent newScene = loader.load();
            
            //access the controller from the loader and call initializeFields() method to pass data into fields
            ModifyProductController controller = loader.getController();
            controller.initializeFields();
            
            //use part of changeSceneTo() method to switch scenes
            Scene scene = new Scene(newScene);

            // this line gets the stage information
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
            
        } else { // if no selection is made, display error message.
            productsConfirmationText.setText("Error: No Product selected.");
        }
    }
    
    /**
     This method listens for the Delete button on the Products pane being pressed. 
     If no selection has been made, a dialog indicates this error to the user.
     With a selection made, a copy of the selected Product is created, and a String that stores this Product's name.
     Next, a confirmation dialog is shown to the user, allowing them to Delete the Product or Cancel this action.
     Finally, if the Delete button is pressed, the Product is deleted from the list of Products.
     @param event the ActionEvent object representing the Delete button on the Product's pane being pushed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void deleteProductButtonPushed(ActionEvent event) throws IOException {
        // check that a selection has been made
        if (!productsTableView.getSelectionModel().isEmpty()) {
            
            Product productToDelete = productsTableView.getSelectionModel().getSelectedItem();
            
            if (productToDelete.getAllAssociatedParts().isEmpty()) {

                String productName = productToDelete.getName();

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Product Confirmation");
                alert.setHeaderText("Are you sure you would like to delete the chosen product, " + productName + "?");
                alert.setContentText("This action is permanent and cannot be undone.");

                ButtonType delete = new ButtonType("Delete");
                ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(delete, cancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete) {
                    Inventory.deleteProduct(productToDelete);
                    productsConfirmationText.setText("Product deleted successfully.");

                } else if (result.get() == cancel) productsConfirmationText.setText("Product was not deleted.");
            
            } else {
                //This block will execute if the selected Product has associated Parts (and therefore is not allowed to be deleted).
                Alert alert = new Alert(AlertType.ERROR);
                alert.setResizable(true);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                alert.setTitle("Error Deleting Product");
                alert.setHeaderText("Product cannot be deleted.");
                alert.setContentText("This Product cannot be deleted because it has Parts associated with it.\n"
                        + "\nTo delete this Product, remove all associated Parts from this Product, then try again.");
                alert.showAndWait();
                
            }
                
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Product Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete, then click Delete.");
            alert.showAndWait();
        }
    }
    
    /**
     This method listens for the Exit button on the Main screen being pressed.
     Once pressed, a dialog will ask for the user to confirm that they would like to exit the application.
     The user may select OK to exit or Cancel to prevent closing the application by mistake.
     @param event the ActionEvent object representing the Exit button being pressed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void exitButtonPushed(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit IMS");
        alert.setHeaderText("Would you like to close the application?");
        alert.setContentText("Data will not be saved.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) mainScreenExitButton.getScene().getWindow();
            stage.close();
        }
        
    }
    
    /**
     This method contains the code required to change scenes using JavaFX. 
     Several different objects are created to change scenes to the FXML file path indicated by the FXMLPath parameter.
     This method was created to cut down on repetitive code required by many of this program's various methods to change scenes.
     @param event the ActionEvent object required to change scenes.
     @param FXMLPath the .fxml file's relative path. This is the scene we are changing to.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void changeSceneTo(ActionEvent event, String FXMLPath) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource(FXMLPath));
        Scene scene = new Scene(newScene);

        // this line gets the stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    
    /**
     This method checks whether a specified String is, or is not, a number.
     @param s the String this method is checking for whether or not it is a number.
     @return true Returns true if the specified String s is a number. If s is not a number, returns false.
     */
    public boolean isNumber(String s) {
        //loop through the input String's characters
        for (int i = 0; i < s.length(); i++) {
            
            if (Character.isDigit(s.charAt(i)) == false) return false;
            
        }
        //returns true only if none of the String's characters are digits
        return true;
    }
    
    /**
     This method is responsible for the Parts pane's search bar's functionality. 
     This method creates a listener for the specified searchField TextField. There is no need for a button to be pressed - as the user types, this method will narrow the results down to either a part ID or to match a part(s) name.
     The list of data, filteredData, has the setPredicate method called on it, which indicates a predicate that each value in the FilteredList will return true or false for.
     If the list is empty or no value is entered in the searchField, all values will return true.
     Next, the input value (newValue) is checked for if it is a number or a String. If the input text is a number, each part's ID is looked up to check if it matches the input number. If it does, that part returns true and is displayed in the TableView.
     If the input value is a String, each part's name is checked against the input value. If the part's name at least starts with the input value, it returns true and is shown in the TableView. Multiple values can be displayed to the user in this way.
     The value(s) that return true remain in the filteredData, while any values that return false are not included.
     Finally, the FilteredList is wrapped in a SortedList, the SortedList comparator is bound to the TableView's comparator, and then the values are set to display on the indicated TableView.
     @param tableView the TableView that the searchField will be searching, and for setting the items to the TableView, post-search.
     @param searchField the textField that will be utilized for searching through a TableView of values. 
     @param filteredData the FilteredList of data that is being searched through. This FilteredList wraps an ObservableList that the objects present in the TableView are stored in.
     */
    public void partsSearchHandler(TableView<Part> tableView, TextField searchField, FilteredList<Part> filteredData) {
        //Listener allows search field to automatically narrow search results to the predicate
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(part -> { //part from the list (filteredData)
                // if empty, display all parts
                if (newValue == null || newValue.isEmpty())
                    return true;
                
                String lowerCaseValue = newValue.toLowerCase(); // toLowerCase of newValue, the input
                // if newValue is a number
                if (isNumber(newValue)) {
                    Part currentPart = Inventory.lookupPart(part.getId());
                    
                    if (String.valueOf(currentPart.getId()).contains(lowerCaseValue))
                        return true; // filter matches partId
                    
                } else if (!isNumber(newValue)) { // newValue is a String
                    ObservableList<Part> partsFound = Inventory.lookupPart(part.getName());
                    
                    for (Part foundPart : partsFound) {
                        if (foundPart.getName().toLowerCase().startsWith(lowerCaseValue))
                            return true; // partName startsWith the input
                    }
                }
                return false; // part does not match
            });
        });
        
        // Wrap the FilteredList (filteredData) in a SortedList (sortedData)
        SortedList<Part> sortedData = new SortedList<>(filteredData);
        
        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        
        // Set sorted and filtered data to tableView
        tableView.setItems(sortedData);
    }
    
    /**
     This method is responsible for the Products pane's search bar's functionality. 
     This method creates a listener for the specified searchField TextField. There is no need for a button to be pressed - as the user types, this method will narrow the results down to either a product ID or to match a product(s) name.
     The list of data, filteredData, has the setPredicate method called on it, which indicates a predicate that each value in the FilteredList will return true or false for.
     If the list is empty or no value is entered in the searchField, all values will return true.
     Next, the input value (newValue) is checked for if it is a number or a String. If the input text is a number, each product's ID is looked up to check if it matches the input number. If it does, that product returns true and is displayed in the TableView.
     If the input value is a String, each product's name is checked against the input value. If the product's name at least starts with the input value, it returns true and is shown in the TableView. Multiple values can be displayed to the user in this way.
     The value(s) that return true remain in the filteredData, while any values that return false are not included.
     Finally, the FilteredList is wrapped in a SortedList, the SortedList comparator is bound to the TableView's comparator, and then the values are set to display on the indicated TableView.
     @param tableView the TableView that the searchField will be searching, and for setting the items to the TableView, post-search.
     @param searchField the textField that will be utilized for searching through a TableView of values. 
     @param filteredData the FilteredList of data that is being searched through. This FilteredList wraps an ObservableList that the objects present in the TableView are stored in. 
     */
    public void productsSearchHandler(TableView<Product> tableView, TextField searchField, FilteredList<Product> filteredData) {
        //Listener allows search field to automatically narrow search results to the predicate
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> { //product from the list (filteredData)
                // if empty, display all parts
                if (newValue == null || newValue.isEmpty())
                    return true;
                
                String lowerCaseValue = newValue.toLowerCase(); // toLowerCase of newValue, the input
                // if newValue is a number
                if (isNumber(newValue)) {
                    Product currentProduct = Inventory.lookupProduct(product.getId());
                    
                    if (String.valueOf(currentProduct.getId()).contains(lowerCaseValue))
                        return true; // filter matches partId
                    
                } else if (!isNumber(newValue)) { // newValue is a String
                    ObservableList<Product> productsFound = Inventory.lookupProduct(product.getName());
                    
                    for (Product foundProduct : productsFound) {
                        if (foundProduct.getName().toLowerCase().startsWith(lowerCaseValue))
                            return true; // partName startsWith the input
                    }
                }
                return false; // part does not match
            });
        });
        
        // Wrap the FilteredList (filteredData) in a SortedList (sortedData)
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        
        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        
        // Set sorted and filtered data to tableView
        tableView.setItems(sortedData);
    }
}
