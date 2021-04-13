package Model;

/**
 This class extends the Part class, defining and instantiating InHouseParts.
 @author Sean
 */
public class InHousePart extends Part {
    
    private int machineId;
    
    /**
     This constructor sets the int id, String name, double price, int stock, int min, int max, and int machineId for new InHousePart objects. The constructor calls it's superclass (Part) constructor to 
     initialize the id, name, price, stock, min, and max fields. Then, the machineId field is set by this constructor. 
     @param id the InHousePart's id
     @param name the InHousePart's name
     @param price the InHousePart's price
     @param stock the InHousePart's stock
     @param min the InHousePart's min
     @param max the InHousePart's max
     @param machineId  the InHousePart's machineId
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {        
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    /**
     This method is a mutator (setter) for the InHousePart's machineId. Simply sets the machineId for this InHousePart to the provided parameter value, int machineId. 
     @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    /**
     This method is an accessor (getter) for the given InHousePart's machineId. Simply returns this InHousePart's machineId. 
     @return the machineId of this InHousePart
     */
    public int getMachineId() {
        return this.machineId;
    }
}
