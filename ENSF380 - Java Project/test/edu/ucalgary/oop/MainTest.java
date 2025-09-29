/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

 package edu.ucalgary.oop;

 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 
 import java.io.*;
 
 import static org.hamcrest.CoreMatchers.containsString;
 import static org.junit.Assert.assertThat;
 
 public class MainTest {
 
     private final InputStream originalIn = System.in;
     private final PrintStream originalOut = System.out;
 
     private ByteArrayInputStream testIn;
     private ByteArrayOutputStream testOut;
 
     @Before
     public void setUpStreams() {
         testOut = new ByteArrayOutputStream();
         System.setOut(new PrintStream(testOut));
     }
 
     @After
     public void restoreStreams() {
         System.setIn(originalIn);
         System.setOut(originalOut);
     }
 
     @Test
     public void testMain_SelectEnglishLanguage_PrintsWelcome() {
         String simulatedInput = "1\n"; // user selects English
         testIn = new ByteArrayInputStream(simulatedInput.getBytes());
         System.setIn(testIn);
 
         // Call a safe version of main (overriding UI call)
         MainStub.main(new String[]{});
 
         String output = testOut.toString();
         assertThat(output, containsString("Welcome"));  // Depends on actual translation
     }
 
     @Test
     public void testMain_SelectSpanishFallbacksToEnglish() {
         String simulatedInput = "2\n"; // Spanish selection (not supported)
         testIn = new ByteArrayInputStream(simulatedInput.getBytes());
         System.setIn(testIn);
 
         MainStub.main(new String[]{});
 
         String output = testOut.toString();
         assertThat(output, containsString("not supported"));
         assertThat(output, containsString("Welcome"));
     }
 
     @Test
     public void testMain_InvalidInputThenValid() {
         String simulatedInput = "abc\n1\n"; // invalid then valid
         testIn = new ByteArrayInputStream(simulatedInput.getBytes());
         System.setIn(testIn);
 
         MainStub.main(new String[]{});
 
         String output = testOut.toString();
         assertThat(output, containsString("Invalid option"));  // Assuming key is translated to this
         assertThat(output, containsString("Welcome"));
     }
 
     /**
      * Stub version of Main to override actual UI call
      */
     public static class MainStub extends Main {
         public static void main(String[] args) {
             // Replicate logic without calling UserInterface
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
                         langManager = new LanguageManager("en-CA"); // fallback to English
                         selectedLang = 1;
                         System.out.println(langManager.getTranslation("main_language_not_supported"));
                         System.out.println(langManager.getTranslation("main_english_switch") + "\n");
                         break;
                     default:
                         System.out.println(langManager.getTranslation("main_invalid_option"));
                 }
             }
 
             System.out.println(langManager.getTranslation("main_welcome_message"));
             // Skip calling UserInterface.main(args, selectedLang)
             scanner.close();
         }
     }
 }
 