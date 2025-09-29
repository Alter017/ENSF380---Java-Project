/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

public class Cot {
	String type = "Cot";
    Supply cot = new Supply();
	
    public Cot() {}
    
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
    public void allocateCot(int selectedLang) {
        cot.allocateSupply(type, selectedLang);
    }
}