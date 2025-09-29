/*
 * Author: Alejandro Herranz Vera
 * UCID: 30216628
 * Version: 2.14
 * Last edited: 2025-04-10
 */

package edu.ucalgary.oop;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class LanguageManager {
    private Map<String, String> translations = new HashMap<>();
    private String language;

    /**
     * @param selected language
     */
    public LanguageManager(String language) {
        this.language = language;
        loadLangFile(language);
    }

    /**
     * @param selected language
     */
    public void loadLangFile(String language) {
        try {

            File xmlFile = new File("data/" + language + ".xml");
            if (!xmlFile.exists()) {
                System.out.println("Language file not found. Defaulting to en-CA.");
                language = "en-CA";  // Default language
                xmlFile = new File("data/" + language + ".xml");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();


            NodeList translationNodes = document.getElementsByTagName("translation");
            for (int i = 0; i < translationNodes.getLength(); i++) {
                Node node = translationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String key = element.getElementsByTagName("key").item(0).getTextContent();
                    String value = element.getElementsByTagName("value").item(0).getTextContent();
                    translations.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading language file. Defaulting to en-CA.");
            e.printStackTrace();
        }
    }

    /**
     * @param key
     * @return translation to the selected language
     */
    public String getTranslation(String key) {
        return translations.getOrDefault(key, key);
    }
    
    /**
     * @param selected language
     * @return language object
     */
    public static LanguageManager whatLanguage(int selectedLang) {
        LanguageManager langManager = new LanguageManager("en-CA"); // In English by default
    	if (selectedLang == 1) {
            langManager = new LanguageManager("en-CA");
    	}
    	else if (selectedLang == 2) {
            langManager = new LanguageManager("esp-CA"); // Not yet supported
    	}
    	return langManager;
    }
}