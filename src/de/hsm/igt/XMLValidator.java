package de.hsm.igt;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLValidator {
    private String schemaURL;
    private String filePath;

    public XMLValidator(String schemaURL, String sourceFile) {
        this.schemaURL = schemaURL;
        this.filePath = sourceFile;
    }

    public void checkValid() throws IOException, SAXException {
        Source xmlFile = new StreamSource(new File(filePath));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        schema = schemaFactory.newSchema(new URL(schemaURL));
        Validator validator = schema.newValidator();
        validator.validate(xmlFile);
    }

    public void checkWellFormed() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.parse(new InputSource(filePath));
    }
}