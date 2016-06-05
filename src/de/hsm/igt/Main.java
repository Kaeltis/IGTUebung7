package de.hsm.igt;

import java.io.IOException;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static boolean DEBUG = false;

    private static final String SCHEMA_URL = "http://www.omg.org/spec/BPMN/20100501/BPMN20.xsd";
    private static final String FILE_PATH = "resources/email.bpmn";

    private static final String ATTRIBUTE_NAME = "name";

    //Signavio
    //private static final String[] TAG_NAMES = {"userTask", "task", "serviceTask", "sendTask", "scriptTask", "receiveTask", "manualTask", "choreographyTask"};
    //BPMN
    private static final String[] TAG_NAMES = {"semantic:userTask", "semantic:task", "semantic:serviceTask", "semantic:sendTask", "semantic:scriptTask", "semantic:receiveTask", "semantic:manualTask", "semantic:choreographyTask"};

    public static void main(String[] args) {
        // Validate BPMN XML

        XMLValidator xmlValidator = new XMLValidator(SCHEMA_URL, FILE_PATH);

        try {
            xmlValidator.checkValid();
            if (DEBUG)
                System.out.println("XML is valid!");
        } catch (SAXException e) {
            System.out.println("XML is invalid!");
            System.out.println(e.getMessage());
            System.exit(2);
        } catch (IOException e) {
            System.out.println("File not Found: " + e.getMessage());
            System.exit(1);
        }

        try {
            xmlValidator.checkWellFormed();
            if (DEBUG)
                System.out.println("XML is well-formed!");
        } catch (SAXException e) {
            System.out.println("XML is not well-formed!");
            System.out.println(e.getMessage());
            System.exit(3);
        } catch (IOException e) {
            System.out.println("File not Found: " + e.getMessage());
            System.exit(1);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfigurationException: " + e.getMessage());
            System.exit(4);
        }

        // Parse BPMN XML

        XMLParser xmlParser = new XMLParser(FILE_PATH);

        try {
            xmlParser.parse();
        } catch (Exception e) {
            System.out.println("Error parsing file!");
            System.out.println(e.getMessage());
            System.exit(5);
        }

        List<String> taskList = new ArrayList<>();

        for (String tag :
                TAG_NAMES) {
            taskList.addAll(xmlParser.getAttributeContentsByTagName(tag, ATTRIBUTE_NAME));
        }

        if (DEBUG)
            System.out.println(taskList);

        // Find webservices

        for (String task :
                taskList) {
            try {
                WebserviceSearch.findAPIs(task);
            } catch (IOException e) {
                System.out.println("Couldn't connect to Webservice List");
            }
        }
    }
}
