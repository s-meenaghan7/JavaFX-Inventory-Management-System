package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 This class is responsible for initializing an Inventory, containing static ObservableLists for storing both Parts and Products, and static methods for various operations on these Lists. 
 @author Sean
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
     This method is used for adding a part to the allParts ObservableList. Simply adds the newPart parameter to the allParts ObservableList. 
     @param newPart the newPart to add. 
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    /**
     This method is used to lookup a Part in the allParts list by its partId. Once the Part is found, it is returned. If the Part is not found, null will be returned. 
     @param partId the partId to search for. 
     @return foundPart the Part object found by this method. 
     */
    public static Part lookupPart(int partId) {
        Part foundPart = null;
        
        for (Part part : allParts) {
            if (part.getId() == partId) foundPart = part;
        }
        return foundPart;
    }
    
    /**
     This method is used to lookup Parts via their partName. All Parts in the allParts List that match (fully or partially) the provided partName parameter will be added to 
     an ObservableList, partsFound, which is then returned. 
     @param partName the partName to search for. 
     @return the ObservableList of Part's found matching (fully or partially) the partName string. 
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String lowerCaseFilter = partName.toLowerCase();
        
        for (Part part : allParts) {
            //if the Part's name contains (any amount of) the input (partName)
            if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                //add that part
                partsFound.add(part);
            }
        }
        
        return partsFound;
    }
    
    /**
     This method is used to update a Part in the allParts list. The index parameter is used to identify a Part by its Part ID. This Part is then removed from allParts. 
     Then, the selectedPart parameter is added to the allParts list, effectively replacing the previously removed Part.
     @param index the index is equal to the Part ID of the part being modified
     @param selectedPart the selectedPart to update
     */
    public static void updatePart(int index, Part selectedPart) {
        //create a 2nd list to add Parts whose id == index
        ObservableList<Part> valuesToRemove = FXCollections.observableArrayList();
        
        for (Part part : allParts) {
            if (part.getId() == index) valuesToRemove.add(part);
        }
        //valuesToRemove contains the (modified/now outdated) Part to remove
        allParts.removeAll(valuesToRemove);
        
        //add the newly created/modified Part to allParts
        allParts.add(selectedPart);
    }
    
    /**
     This method is used to delete a Part from the allParts list. If the selectedPart parameter is confirmed to be of Part type, it is removed. 
     @param selectedPart the Part to remove from the allParts list. 
     @return true the true or false value determining if part was found/deleted. Returns true if the Part was found and removed; false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart instanceof Part) {
            allParts.remove(selectedPart);
            return true;
        } 
        return false;
    }
    
    /**
     This method acts as an accessor (getter) for the allParts ObservableList. Simply returns the allParts ObservableList. 
     @return allParts the Inventory's allParts ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    /**
     This method is used for adding a Product to the allProducts ObservableList. Simply adds the newProduct parameter to the allProducts ObservableList. 
     @param newProduct the newProduct to add. 
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    /**
     This method is used to lookup a Product in the allProducts list by its productId. Once the Product is found, it is returned. If the Product is not found, null will be returned.
     @param productId the productId to search for
     @return foundProduct the Product found
     */
    public static Product lookupProduct(int productId) {
        Product foundProduct = null;
        
        for (Product product : allProducts) {
            if (product.getId() == productId) foundProduct = product;
        }
        return foundProduct;
    }
    
    /**
     This method is used to lookup Products via their productName. All Products in the allProducts List that match (fully or partially) the provided productName parameter will be added to 
     an ObservableList, productsFound, which is then returned. 
     @param productName the productName to search for
     @return the ObservableList of Products found matching (fully or partially) the productName string
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String lowerCaseFilter = productName.toLowerCase();
        
        for (Product product : allProducts) {
            //if the Part's name contains (any amount of) the input (partName)
            if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                //add that part
                productsFound.add(product);
            }
        }
        
        return productsFound;
    }
    
    /**
     This method is used to update a Product in the allProducts list. The index parameter is used to identify a Product by its productId. This Product is then removed from allProducts. 
     Then, the selectedProduct parameter is added to the allProducts list, effectively replacing the previously removed Product.
     @param index the index of the Product to be deleted. 
     @param selectedProduct the selectedProduct to add. 
     */
    public static void updateProduct(int index, Product selectedProduct) {
        //create a 2nd list to add Product whose id == index
        ObservableList<Product> valuesToRemove = FXCollections.observableArrayList();
        
        for (Product product : allProducts) {
            if (product.getId() == index) valuesToRemove.add(product);
        }
        //valuesToRemove contains the (modified/now outdated) Product to remove
        allProducts.removeAll(valuesToRemove);
        
        //add the newly created/modified Product to allProducts
        allProducts.add(selectedProduct);
    }
    
    /**
     This method deletes a Product from the allProducts list. 
     @param selectedProduct the selectedProduct to delete. 
     @return true the true or false value indicating if the selectedProduct was found and deleted. 
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }
    
    /**
     This method functions as an accessor (getter) for the allProducts ObservableList. Simply returns the allProducts ObservableList. 
     @return the Inventory's allProducts ObservableList 
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
