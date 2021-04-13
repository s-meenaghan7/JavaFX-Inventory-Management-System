package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 FXML Modify Part Screen Controller class. 
 @author Sean
 */
public class ModifyPartController implements Initializable {

    @FXML private ToggleGroup partTypeToggleGroup = new ToggleGroup();
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    
    @FXML private TextField partIdTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partStockTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField partTypeField;
    
    @FXML private Button modifyPartSaveButton;
    @FXML private Button modifyPartCancelButton;
    @FXML private Label partTypeLabel;
    
    @FXML private Label nameExceptionText;
    @FXML private Label stockExceptionText;
    @FXML private Label priceExceptionText;
    @FXML private Label maxExceptionText;
    @FXML private Label minExceptionText;
    @FXML private Label partTypeExceptionText;
    
    public static Part selectedPart;
    
    /**
     Called to initialize the Modify Part controller class after its root element has been completely processed. 
     Also sets up the ToggleGroup for the two RadioButtons.  
     @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     @param rb The resources used to localize the root object, or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.inHouseRadioButton.setToggleGroup(partTypeToggleGroup);
        this.outsourcedRadioButton.setToggleGroup(partTypeToggleGroup);
    }    
    
    /**
     This method controls changing the partTypeField, depending on the radio button selection made.
     Depending on the selection, the partTypeField will read "Machine ID" or "Company Name."
     The selection of the radio button is used to indicate the subclass of added Parts. 
     */
    public void radioButtonChanged() {
        
        if (partTypeToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
            partTypeField.clear();
            partTypeLabel.setText("Machine ID");
        }
        
        if (partTypeToggleGroup.getSelectedToggle().equals(outsourcedRadioButton)) {
            partTypeField.clear();
            partTypeLabel.setText("Company Name");
        }
        
    }
    
    /**
     This method listens for the Save button on the Modify Part screen being pressed. 
     The method first validates user input via the validateUserInputParts() method. Next, this method will check for the RadioButton selection made by the user (InHouse or Outsourced).
     A new Part of either Part subclass is created based on the radio button selection. Then, the list of Parts is updated, removing the old, outdated Part from the list, and adding this newly
     created Part to the list. Finally, the scene is changed back to the Main screen by calling changeSceneTo() method.
     @param event the ActionEvent object representing the Save button on the Add Part screen being pressed.
     @throws IOException the potential IOException that must be caught or declared to be thrown.
     */
    public void modifyPartSaveButtonPushed(ActionEvent event) throws IOException {
        if(!validateUserInputParts()) return; // If user input !valid, do not continue.
        
        if (this.partTypeToggleGroup.getSelectedToggle().equals(this.inHouseRadioButton)) {
            //create new InHousePart, with same ID (not a new one)
            InHousePart newInHousePart = new InHousePart(Integer.parseInt(partIdTextField.getText()),
                                                         partNameTextField.getText(),
                                                         Double.parseDouble(partPriceTextField.getText()),
                                                         Integer.parseInt(partStockTextField.getText()),
                                                         Integer.parseInt(partMinTextField.getText()),
                                                         Integer.parseInt(partMaxTextField.getText()),
                                                         Integer.parseInt(partTypeField.getText()));
            
            Inventory.updatePart(selectedPart.getId(), newInHousePart);
            
            //switch back to mainScreen
            changeSceneTo(event, "/View_Controller/mainScreen.fxml");
        }
        
        if (this.partTypeToggleGroup.getSelectedToggle().equals(this.outsourcedRadioButton)) {
            //create new OutsourcedPart, with same ID (not a new one)
            OutsourcedPart newOutsourcedPart = new OutsourcedPart(Integer.parseInt(partIdTextField.getText()),
                                                                  partNameTextField.getText(),
                                                                  Double.parseDouble(partPriceTextField.getText()),
                                                                  Integer.parseInt(partStockTextField.getText()),
                                                                  Integer.parseInt(partMinTextField.getText()),
                                                                  Integer.parseInt(partMaxTextField.getText()),
                                                                  partTypeField.getText());
            
            Inventory.updatePart(newOutsourcedPart.getId(), newOutsourcedPart);
            
            //switch back to mainScreen
            changeSceneTo(event, "/View_Controller/mainScreen.fxml");
        }
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
     This method validates the user's input on the Add and Modify Part forms. 
     The method is called within the button handlers for the Save buttons, on the Add Part and Modify Part forms.
     The method checks each TextField individually, ensuring input is valid. If any of the TextFields input's are invalid, an error message will be displayed to the user and the method will return false.
     @return returns true if there are no error messages present. If any error messages are still present, returns false.
     */
    public boolean validateUserInputParts() {
        //check that the partNameTextField is valid input
        if (partNameTextField.getText().isBlank() ||
            isNumber(partNameTextField.getText()) ||
            (!partNameTextField.getText().trim().matches("^[a-zA-Z\\s]+$"))) { //spaces only allowed between words
                
            nameExceptionText.setText("Name field input invalid. Numbers and special characters are forbidden.");

        } else nameExceptionText.setText("");
        
        //check that the partStockTextField is valid input
        //should be an int and between the max and min values.
        if (partStockTextField.getText().isBlank() ||
           (!isNumber(partStockTextField.getText()))) {
            
            stockExceptionText.setText("Inventory field input invalid. Value must be a whole number.");
            
        } else if (!partStockTextField.getText().isBlank()) {
            
            try {
                
                if (partMaxTextField.getText().isBlank() || partMinTextField.getText().isBlank()) {
                    stockExceptionText.setText("Inventory field cannot be checked against blank min and/or max fields.");
                    
                } else if ((Integer.valueOf(partStockTextField.getText()) > Integer.valueOf(partMaxTextField.getText())) ||
                           (Integer.valueOf(partStockTextField.getText()) < Integer.valueOf(partMinTextField.getText()))) {
                    
                    stockExceptionText.setText("Inventory field value must be a number between the min and max field values.");
                    
                } else stockExceptionText.setText("");
                
            } catch (NumberFormatException e) {
                stockExceptionText.setText("Error: Inventory field cannot be checked against blank min and/or max fields.");
            }
                    
        } else stockExceptionText.setText("");
        
        //check that the partPriceTextField is valid input. Must be a double/float (int OK)
        if (partPriceTextField.getText().isBlank() ||
           (!partPriceTextField.getText().matches("[+-]?([0-9]*[.])?[0-9]+"))) {
            
            priceExceptionText.setText("Price field value must be a number (decimal points are OK).");
            
        } else priceExceptionText.setText("");
        
        //check the partMaxTextField. Value must be a number, greater than partMinTextField.
        if (partMaxTextField.getText().isBlank() ||
           (!isNumber(partMaxTextField.getText()))) {
            
            maxExceptionText.setText("Max field value must be a number. Value must be greater than Min field value.");
        
        } else maxExceptionText.setText("");
        
        //check the partMinTextField. Value must be a number, less than partMaxTextField.
        if (partMinTextField.getText().isBlank() ||
            (!isNumber(partMinTextField.getText()))) {
            
            minExceptionText.setText("Min field value must be a number. Value must be lesser than Max field value.");
            
        } else minExceptionText.setText("");

        //check the partTypeField. If inhouse is selected, value must be int. If Outsourced selected, value must be String.
        if (partTypeToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
            
            if (partTypeField.getText().isBlank() || (!isNumber(partTypeField.getText()))) {
                
                partTypeExceptionText.setText("Machine ID field value must be a whole number.");
                
            } else partTypeExceptionText.setText("");
            
        } else if (partTypeToggleGroup.getSelectedToggle().equals(outsourcedRadioButton)) {
            
            if (partTypeField.getText().isBlank() || isNumber(partTypeField.getText())) {
                
                partTypeExceptionText.setText("Company Name field value should not be blank or only contain numbers.");
            
            } else partTypeExceptionText.setText("");
            
        }
        
        //Returns false if errors are still present, true if all exceptionText's are "" (empty)
        return nameExceptionText.getText().equals("") &&
                stockExceptionText.getText().equals("") &&
                priceExceptionText.getText().equals("") &&
                maxExceptionText.getText().equals("") &&
                minExceptionText.getText().equals("") &&
                partTypeExceptionText.getText().equals("");
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
     This method is responsible for preemptively initializing the Modify Part form fields. 
     When a Part on the main screen is selected and the Modify button is pressed, this method is called to load the Part object's information into the Modify Part TextFields and
     determine the subclass of the Part, so that the correct radio button is selected.
     */
    public void initializeFields() {
        
        partIdTextField.setText(Integer.toString(selectedPart.getId()));
        partNameTextField.setText(selectedPart.getName());
        partStockTextField.setText(Integer.toString(selectedPart.getStock()));
        partPriceTextField.setText(Double.toString(selectedPart.getPrice()));
        partMaxTextField.setText(Integer.toString(selectedPart.getMax()));
        partMinTextField.setText(Integer.toString(selectedPart.getMin()));
        
        if (selectedPart instanceof InHousePart) {
            inHouseRadioButton.setSelected(true);
            radioButtonChanged();
            partTypeField.setText(Integer.toString(((InHousePart) selectedPart).getMachineId()));
            
        } else if (selectedPart instanceof OutsourcedPart) {
            outsourcedRadioButton.setSelected(true);
            radioButtonChanged();
            partTypeField.setText(((OutsourcedPart) selectedPart).getCompanyName());
        }
    }
}
