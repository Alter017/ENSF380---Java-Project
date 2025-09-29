/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.util.Scanner;

public class FamilyGroup {
    private static final Scanner scanner = new Scanner(System.in);
    private int familyGroup;
    
    public FamilyGroup() {}
    
    /**
     * @return family group
     */
    public int getFamilyGroup() {
        return familyGroup;
    }

    /**
     * @param family group
     */
    public void setFamilyGroup(int familyGroup) {
        this.familyGroup = familyGroup;
    }
    
    /**
     * @param langauge selected
     * @return family group
     */
	public int selectFamilyGroup(int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        familyGroup = -1;
        while (familyGroup < 0) {
            System.out.print(langManager.getTranslation("fg_group_num"));
            try {
                familyGroup = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(langManager.getTranslation("fg_invalid_num"));
            }
        }
        return familyGroup;
	}
}