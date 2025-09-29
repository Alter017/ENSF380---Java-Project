/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.util.Scanner;

public class Gender {
    private static final Scanner scanner = new Scanner(System.in);
    
    public Gender() {}
    
    /**
     * @param selected language
     * @return gender
     */
	public String selectGender(int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        String gender = null;
        while (gender == null) {
            System.out.print(langManager.getTranslation("gender_selection"));
            String g = scanner.nextLine();
            switch (g) {
                case "1": gender = "Man"; break;
                case "2": gender = "Woman"; break;
                case "3": gender = "Non-binary"; break;
                default:
                    System.out.println(langManager.getTranslation("gender_selection_failed"));
            }
        }
        return gender;
	}
}