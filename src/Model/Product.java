package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 This class defines and instantiates Product objects. 
 @author Sean
 */
public class Product {
    
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     This is the constructor for the Product objects. This constructor takes int id, String name, double price, int stock, int min, and int max as parameters. 
     Product objects also contain an ObservableList of Parts; this constructor instantiates that list to a new (and empty) ObservableList, for Parts to be added to at a different time.
     @param id the Product's ID. 
     @param name the Product's name. 
     @param price the Product's price. 
     @param stock the Product's stock. 
     @param min the Product's minimum amount. 
     @param max the Product's maximum amount. 
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /**
     This method is a mutator (setter) for Products. Simply sets the Product's id to the id parameter.
     @param id the id to set. 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     This method is an accessor (getter) for a given Product's ID. Simply returns a Product's ID. 
     @return id the Product's id returned. 
     */
    public int getId() {
        return this.id;
    }
    
    /**
     This method is a mutator (setter) for Products. Simply sets the Product's name to the specified name parameter. 
     @param name the name to set. 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     This method is an accessor (getter) for the given Product's name. Simply returns a Product's name. 
     @return name the name of the Product is returned. 
     */
    public String getName() {
        return this.name;
    }
    
    /**
     This method is a mutator (setter) for a Product's price. Simply sets the Product's price to the specified price parameter. 
     @param price the price to set. 
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     This method is an accessor (getter) for the given Product's price. Simply returns a Product's price. 
     @return price the price of the Product that is returned. 
     */
    public double getPrice() {
        return this.price;
    }
    
    /**
     This method is a mutator (setter) for the given Product's stock. Simply sets the Product's stock to the parameter stock. 
     @param stock the stock amount to set for this Product. 
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     This method is an accessor (getter) for the given Product's stock. Simply returns the Product's stock amount. 
     @return stock the stock amount 
     */
    public int getStock() {
        return this.stock;
    }
    
    /**
     This method is a mutator (setter) for the given Product's min amount. Simply sets the Product's min value to the parameter min. 
     @param min the min amount to set 
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     This method is an accessor (getter) for the given Product's min amount. Simply returns the Product's min value. 
     @return min the min amount to return
     */
    public int getMin() {
        return this.min;
    }
    
    /**
     This method is a mutator (setter) for the given Product's max amount. Simply sets the Product's max value to the parameter max. 
     @param max the max amount to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     This method is an accessor (getter) for a given Product's max amount. Simply returns the Product's max value. 
     @return max the max amount of the Product returned. 
     */
    public int getMax() {
        return this.max;
    }
    
    /**
     This method adds a Part object to this Product's associatedParts list. Simply adds the Part part parameter to the given Product's associatedParts.
     @param part the part to add to this Product's associatedParts. 
     */
    public void addAssociatedPart(Part part) {
        
        this.associatedParts.add(part);
        
    }
    
    /**
     This method deletes a Part from a given Product's associatedParts list. Simply removes the specified Part selectedAssociatedPart parameter from the Product's associatedParts list. 
     @param selectedAssociatedPart the Part to remove from this Product's associatedParts list. 
     @return true returns true after removing the Part as confirmation. 
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        
        this.associatedParts.remove(selectedAssociatedPart);

        return true;
    }
    
    /**
     This method is an accessor (getter) for a given Product's associatedParts list. Simply returns this Product's associatedParts list. 
     @return associatedParts the list of associatedParts of this Product. 
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
