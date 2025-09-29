/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.util.Scanner;

public class Main {
    /**
     * @param arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LanguageManager langManager = new LanguageManager("en-CA");
        
        int selectedLang = -1;
        
        while (selectedLang < 0) {
            System.out.println(langManager.getTranslation("main_select_language"));
            System.out.println(langManager.getTranslation("main_english_selection"));
            System.out.println(langManager.getTranslation("main_spanish_selection"));
            String languageSelected = scanner.nextLine();
            
            switch (languageSelected) {
            case "1":
            	selectedLang = 1;
                langManager = new LanguageManager("en-CA");
            	break;
            case "2":
            	selectedLang = 2;
                langManager = new LanguageManager("en-CA"); // Swaps to English because Spanish is not yet supported
                selectedLang = 1;							// Swaps to English because Spanish is not yet supported
                System.out.println(langManager.getTranslation("main_language_not_supported"));
                System.out.println(langManager.getTranslation("main_english_switch") + "\n");
            	break;
            default:
                System.out.println(langManager.getTranslation("main_invalid_option"));
            }
        }

        System.out.println(langManager.getTranslation("main_welcome_message"));
        UserInterface.main(args, selectedLang);
     
        scanner.close();
    }
}