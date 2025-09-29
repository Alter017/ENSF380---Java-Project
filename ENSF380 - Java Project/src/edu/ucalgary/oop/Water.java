/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

public class Water {
	String type = "Water";
    Supply water = new Supply();
	
    public Water() {}
    
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
    
    /**
     * @param selected language
     */
    public void allocateWater(int selectedLang) {
        water.allocateSupply(type, selectedLang);
    }
}