package de.hsm.igt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
    private Document document;
    private String filePath;

    public XMLParser(String filePath) {
        this.filePath = filePath;
    }

    public void parse() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();
    }

    private NodeList getNodesByTag(String tagName) {
        Element element = document.getDocumentElement();
        return element.getElementsByTagName(tagName);
    }

    public List<String> getAttributeContentsByTagName(String tagName, String attributeName) {
        return getAttributeContentsFromNodeList(getNodesByTag(tagName), attributeName);
    }

    private List<String> getAttributeContentsFromNodeList(NodeList nodeList, String attributeName) {
        List<String> tagList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node temp = nodeList.item(i);
            if (temp.getAttributes() != null) {
                if (!temp.getAttributes().getNamedItem(attributeName).getNodeValue().isEmpty()) {
                    tagList.add(temp.getAttributes().getNamedItem(attributeName).getNodeValue());
                }
            }
        }

        return tagList;
    }
}
