package Model;

/**
 This class extends the Part class, defining and instantiating OutsourcedParts.
 @author Sean
 */
public class OutsourcedPart extends Part {
    
    private String companyName;
    
    /**
     This constructor sets the int id, String name, double price, int stock, int min, int max, and String companyName for new OutsourcedPart objects. The constructor calls it's superclass (Part) constructor to 
     initialize the id, name, price, stock, min, and max fields. Then, the companyName field is set by this constructor. 
     @param id the OutsourcedPart's id. 
     @param name the OutsourcedPart's name. 
     @param price the OutsourcedPart's price. 
     @param stock the OutsourcedPart's stock. 
     @param min the OutsourcedPart's min. 
     @param max the OutsourcedPart's max. 
     @param companyName  the OutsourcedPart's companyName. 
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     This method is a mutator (setter) for OutsourcedPart's companyName. Simply sets the given OutsourcedPart's companyName to the provided String parameter companyName.
     @param companyName the companyName to set for this OutsourcedPart. 
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     This method is an accessor (getter) for the given OutsourcedPart's companyName. Simply returns this OutsourcedPart's companyName. 
     @return companyName the companyName of this OutsourcedPart. 
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
