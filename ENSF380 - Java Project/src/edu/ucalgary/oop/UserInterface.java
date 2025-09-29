/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * @param arguments
     * @param selected language
     */
    public static void main(String[] args, int selectedLang) {
    	
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        DisasterVictim victim = new DisasterVictim();
    	
        while (true) {
            try {
                System.out.println("\n" + langManager.getTranslation("ui_disaster_relief_system"));
                String choice = scanner.nextLine();

                switch (choice) {
                
                    case "1":               
                    	victim.addNewVictim(selectedLang);
                    	break;
                    	
                    case "2":
                    	Inquirer inqr = new Inquirer();
                    	inqr.makeAnInquiry(selectedLang);
                    	break;
                    	
                    case "3":
                    	allocateSupplies(selectedLang);
                    	break;
                    	
                    case "4":
                    	MedicalRecord.addNewMedicalRecord(selectedLang);
                    	break;
                    	
                    case "5":
                    	exitProgram(selectedLang);
                    	return;
                    	
                    default:
                        System.out.println(langManager.getTranslation("ui_invalid_option2"));
                }
            } catch (Exception e) {
                logError(e, selectedLang);
                System.out.println(langManager.getTranslation("ui_critical_error"));
                return;
            }
        }
    }

    /**
     * @param selected language
     */
    private static void allocateSupplies(int selectedLang) {
    	
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
    	
        System.out.println(langManager.getTranslation("ui_allocate_supplies"));
        String option = scanner.nextLine();

        switch (option) {
            case "1":
            	Cot ct = new Cot();
            	ct.allocateCot(selectedLang);
                break;
            case "2":
            	Blanket bnkt = new Blanket();
            	bnkt.allocateBlanket(selectedLang);
                break;
            case "3":
            	Water wtr = new Water();
            	wtr.allocateWater(selectedLang);
                break;
            case "4":
            	IndividualItem inditm = new IndividualItem();
            	inditm.allocateIndividualItem(selectedLang);
                break;
            default:
                System.out.println(langManager.getTranslation("ui_invalid_option3"));
        }
    }

    /**
     * @param selected language
     */
    private static void logError(Exception e, int selectedLang) {

        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
    	
        try {
            String dir = "data";
            Files.createDirectories(Paths.get(dir));
            FileWriter fw = new FileWriter(dir + "/errorlog.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]");
            e.printStackTrace(out);
            out.println();
            out.close();
        } catch (IOException ioe) {
            System.err.println(langManager.getTranslation("ui_could_not_write_error_log") + ioe.getMessage());
        }
    }

    /**
     * @param selected language
     */
    private static void exitProgram(int selectedLang) {
        LanguageManager langManager = LanguageManager.whatLanguage(selectedLang);
        System.out.println(langManager.getTranslation("ui_exit_program_option"));
    }
}
