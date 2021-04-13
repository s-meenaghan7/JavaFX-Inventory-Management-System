package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 FXML Add Product Screen Controller class. 
 @author Sean
 */
public class AddProductController implements Initializable {

    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productStockTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    
    @FXML private TextField partsSearch;
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;
    
    @FXML private Label associatedPartsErrorLabel;
    @FXML private Button addAssociatedPartButton;
    
    @FXML private TableView<Part> associatedPartsTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> associatedPartCostColumn;
    
    @FXML private Label nameExceptionText;
    @FXML private Label stockExceptionText;
    @FXML private Label priceExceptionText;
    @FXML private Label maxExceptionText;
    @FXML private Label minExceptionText;
    
    @FXML private Label removePartErrorLabel;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button addProductSaveButton;
    @FXML private Button addProductCancelButton;
        
    private Product temp;
    
    /**
     Called to initialize the Add Product controller class after its root element has been completely processed. 
     TableView Columns are set up, a temporary Product object is created, and the partsSearch TextField search functionality is implemented.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     @param rb The resources used to localize the root object, or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in Parts TableView
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //set up the columns in the associatedParts TableView
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //create a dummy Product for storing associated Parts, until the newProduct is ready
        //to be initialized
        temp = new Product(0, "temp", 0, 0, 0, 0);
        associatedPartsTableView.setItems(temp.getAllAssociatedParts());
        
        //Implement partsSearch functionality
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);
        partsSearchHandler(partsTableView, partsSearch, filteredParts);
        
    }
    
    /**
     This method listens for the Add button on the Add Product screen being pressed. The Add button will add a Part from the total list of available Parts to the associated Parts list
     for the Product to be created on this screen. If the user has not made a selection, an error is generated indicating this to the user.
     With a selection made on the Parts TableView, the method checks that the selected Part is not already included in the associated Parts list.
     After passing these checks, the Part is added to the associated Parts list.
     @param event the ActionEvent object representing the Add button for associated Parts on the Add Product screen being pressed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void addAssociatedPartButtonPushed(ActionEvent event) throws IOException {
        //check if a selection has been made
        if (partsTableView.getSelectionModel().isEmpty()) {
            associatedPartsErrorLabel.setText("Error: No part selected.");
            
        } else {
            Part associatedPart = partsTableView.getSelectionModel().getSelectedItem();
            
            // check that the Part is not already in the associatedPartsList
            if (temp.getAllAssociatedParts().contains(associatedPart)) {
                associatedPartsErrorLabel.setText("Selected part already associated with product.");
            } else {
                // After passing checks, add the associatedPart
                associatedPartsErrorLabel.setText("");
                temp.addAssociatedPart(associatedPart);
            }
        }
    }
    
    /**
     This method listens for the Remove button on the Add Product screen being pressed. The method will allow the user to remove an associated Part from the associated Parts list on this screen. 
     The method checks that a selection has been made. If so, a confirmation dialog is generated to the user. Selecting the Remove button on this dialog will remove the associated Part from the list.
     @param event the ActionEvent object representing the Remove button for associated Parts on the Add Product screen being pressed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void removeAssociatedPartButtonPushed(ActionEvent event) throws IOException {
        // check if a selection has been made
        if (associatedPartsTableView.getSelectionModel().isEmpty()) {
            removePartErrorLabel.setText("Error: No part selected.");
            
        } else {
            removePartErrorLabel.setText("");
            Part partToRemove = associatedPartsTableView.getSelectionModel().getSelectedItem();
            String partName = partToRemove.getName();
            
            //create dialog asking if the user is sure they want to remove the part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Associated Part Confirmation");
            alert.setHeaderText("Are you sure you would like to remove the chosen part, "
                    + partName + "?");
            alert.setContentText(partName + " will no longer be associated with this product.");
            
            ButtonType remove = new ButtonType("Remove");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(remove, cancel);
            Optional<ButtonType> result = alert.showAndWait();
            
            //if they click Remove, then temp.deleteAssociatedPart(selectedAssociatedPart);
            if (result.get() == remove) {
                temp.deleteAssociatedPart(partToRemove);
                removePartErrorLabel.setText("Part removed successfully.");
            }
        }
        
    }
    
    /**
     This method listens for the Save button on the Add Product screen being pressed. 
     The method first validates user input with the validateUserInputProducts() method. With user input validated, a new Product object is generated and added the the total list of Products. 
     The method then changes the scene back to the main screen via the changeSceneTo() method.
     @param event the ActionEvent object representing the Save button on the Add Product screen being pressed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void addProductSaveButtonPushed(ActionEvent event) throws IOException {
        if(!validateUserInputProducts()) return; // If user input !valid, do not continue.
        
        //create a new product based on the textFields, generating a new unique ID w/ method
        Product newProduct = new Product(createProductId(),
                                         productNameTextField.getText(),
                                         Double.valueOf(productPriceTextField.getText()),
                                         Integer.valueOf(productStockTextField.getText()),
                                         Integer.valueOf(productMinTextField.getText()),
                                         Integer.valueOf(productMaxTextField.getText()));
        
        //add the associatedPartsList to the newProduct's associated parts
        ObservableList<Part> associatedPartsList = temp.getAllAssociatedParts();
        for (Part part : associatedPartsList) {
            newProduct.addAssociatedPart(part);
        }
        
        //add new Product to allProducts
        Inventory.addProduct(newProduct);
        
        changeSceneTo(event, "/View_Controller/mainScreen.fxml");
        
    }
    
    /**
     This method listens for the Cancel button being pressed. 
     Pressing the Cancel button will change the scene back to the main screen.
     @param event the ActionEvent object representing the Cancel button on the Add Part screen being pressed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        
        changeSceneTo(event, "/View_Controller/mainScreen.fxml");
        
    }
    
    /**
     This method contains the code required to change scenes using JavaFX. 
     Several different objects are created to change scenes to the FXML file path indicated by the FXMLPath parameter.
     This method was created to cut down on repetitive code required by many of this program's various methods to change scenes.
     @param event the ActionEvent object required to change scenes.
     @param FXMLPath the .fxml file's relative path. This is the scene we are changing to.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void changeSceneTo(ActionEvent event, String FXMLPath)throws IOException {
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
     This method validates the user's input on the Add and Modify Product forms. 
     The method is called within the button handlers for the Save buttons, on the Add Product and Modify Product forms.
     The method checks each TextField individually, ensuring input is valid. If any of the TextFields input's are invalid, an error message will be displayed to the user and the method will return false.
     @return returns true if there are no error messages present. If any error messages are still present, returns false.
     */
    public boolean validateUserInputProducts() {
        //check that the productNameTextField is valid input
        if (productNameTextField.getText().isBlank() ||
            isNumber(productNameTextField.getText()) ||
            (!productNameTextField.getText().trim().matches("^[a-zA-Z\\s]+$"))) { //spaces only allowed between words
                
            nameExceptionText.setText("Name field input invalid. Numbers and special characters are forbidden.");

        } else nameExceptionText.setText("");
        
        //check that the partStockTextField is valid input
        //should be an int and between the max and min values.
        if (productStockTextField.getText().isBlank() ||
           (!isNumber(productStockTextField.getText()))) {
            
            stockExceptionText.setText("Inventory field input invalid. Value must be a whole number.");
            
        } else if (!productStockTextField.getText().isBlank()) {
            
            try {
                
                if (productMaxTextField.getText().isBlank() || productMinTextField.getText().isBlank()) {
                    stockExceptionText.setText("Inventory field cannot be checked against blank min and/or max fields.");
                    
                } else if ((Integer.valueOf(productStockTextField.getText()) > Integer.valueOf(productMaxTextField.getText())) ||
                           (Integer.valueOf(productStockTextField.getText()) < Integer.valueOf(productMinTextField.getText()))) {
                    
                    stockExceptionText.setText("Inventory field value must be a number between the min and max field values.");
                    
                } else stockExceptionText.setText("");
                
            } catch (NumberFormatException e) {
                stockExceptionText.setText("Error: Inventory field cannot be checked against blank min and/or max fields.");
            }
                    
        } else stockExceptionText.setText("");
        
        //check that the partPriceTextField is valid input. Must be a double/float (int OK)
        if (productPriceTextField.getText().isBlank() ||
           (!productPriceTextField.getText().matches("[+-]?([0-9]*[.])?[0-9]+"))) {
            
            priceExceptionText.setText("Price field value must be a number (decimal points are OK).");
            
        } else priceExceptionText.setText("");
        
        //check the partMaxTextField. Value must be a number, greater than partMinTextField.
        if (productMaxTextField.getText().isBlank() ||
           (!isNumber(productMaxTextField.getText()))) {
            
            maxExceptionText.setText("Max field value must be a number. Value must be greater than Min field value.");
        
        } else maxExceptionText.setText("");
        
        //check the partMinTextField. Value must be a number, less than partMaxTextField.
        if (productMinTextField.getText().isBlank() ||
            (!isNumber(productMinTextField.getText()))) {
            
            minExceptionText.setText("Min field value must be a number. Value must be lesser than Max field value.");
            
        } else minExceptionText.setText("");
        
        //Returns false if errors are still present, true if all exceptionText's are "" (empty)
        return nameExceptionText.getText().equals("") &&
                stockExceptionText.getText().equals("") &&
                priceExceptionText.getText().equals("") &&
                maxExceptionText.getText().equals("") &&
                minExceptionText.getText().equals("");
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
    
    
    private static int productsIdCounter = 1000;

    /**
     This static method is responsible for generating a unique Product ID each time a Product object is instantiated.
     This method returns the value of the ++productsIdCounter, a static int variable.
     @return ++productsIdCounter the value of the productsIdCounter incremented by 1 is returned each time this method is called.
     */
    public static int createProductId() {
        return ++productsIdCounter;
    }
    
}
