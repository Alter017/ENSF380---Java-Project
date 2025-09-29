/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

public class IndividualItem {
	String type = "Individual Item";
    Supply individualItem = new Supply();
    
    /**
     * @return Supply type
     */
    public String getSupplyType() {
        return type;
    }

    /**
     * @param Supply type
     */
    public void setSupplyType(String type) {
        this.type = type;
    }
	
    public IndividualItem() {}
    
    /**
     * @param selected language
     */
    public void allocateIndividualItem(int selectedLang) {
        individualItem.allocateSupply(type, selectedLang);
    }
}