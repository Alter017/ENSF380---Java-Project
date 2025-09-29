/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

 package edu.ucalgary.oop;

 import org.junit.Before;
 import org.junit.Test;
 
 import static org.hamcrest.CoreMatchers.*;
 import static org.junit.Assert.*;
 
 public class LanguageManagerTest {
 
     private LanguageManager langManager;
 
     @Before
     public void setUp() {
         // Default English-Canadian assumed to exist as fallback
         langManager = new LanguageManager("en-CA");
     }
 
     @Test
     public void testGetTranslationReturnsKeyIfNotFound() {
         String key = "nonexistent_key";
         String translation = langManager.getTranslation(key);
         assertThat("Should return key if translation not found", translation, is(key));
     }
 
     @Test
     public void testWhatLanguageReturnsCorrectLanguageManager_enCA() {
         LanguageManager manager = LanguageManager.whatLanguage(1);
         assertNotNull("Should not return null for language 1", manager);
         String fallback = manager.getTranslation("any_key");
         assertThat(fallback, notNullValue());
     }
 
     @Test
     public void testWhatLanguageReturnsCorrectLanguageManager_espCA() {
         LanguageManager manager = LanguageManager.whatLanguage(2);
         assertNotNull("Should not return null for language 2", manager);
         String fallback = manager.getTranslation("any_key");
         assertThat(fallback, notNullValue());
     }
 
     @Test
     public void testLanguageManagerFallsBackToDefaultOnInvalidLanguage() {
         LanguageManager invalidLang = new LanguageManager("zz-ZZ");
         String result = invalidLang.getTranslation("any_key");
         assertThat("Should return key as fallback", result, is("any_key"));
     }
 }
 