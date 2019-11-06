/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author janek
 */
public class ConfigurationController {

    private int windowSizeX;
    private int windowSizeY;
    private int defaultProjectX;
    private int defaultProjectY;
    private int defaultButtonSizeX;
    private int defaultButtonSizeY;
    private int defaultLabelSizeX;
    private int defaultLabelSizeY;
    private int defaultCheckBoxSizeX;
    private int defaultCheckBoxSizeY;
    private int defaultRadioButtonSizeX;
    private int defaultRadioButtonSizeY;

    public ConfigurationController() throws ParserConfigurationException, SAXException, IOException,
            TransformerConfigurationException, TransformerException {

        File xmlInputFile = new File("config.xml");
        if (xmlInputFile.exists()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputFile);
            doc.getDocumentElement().normalize();
            defaultProjectX = Integer.parseInt(doc.getElementsByTagName("defaultProjectX").item(0).getTextContent());
            defaultProjectY = Integer.parseInt(doc.getElementsByTagName("defaultProjectY").item(0).getTextContent());
            windowSizeX = Integer.parseInt(doc.getElementsByTagName("windowSizeX").item(0).getTextContent());
            windowSizeY = Integer.parseInt(doc.getElementsByTagName("windowSizeY").item(0).getTextContent());
            defaultButtonSizeX = Integer
                    .parseInt(doc.getElementsByTagName("defaultButtonSizeX").item(0).getTextContent());
            defaultButtonSizeY = Integer
                    .parseInt(doc.getElementsByTagName("defaultButtonSizeY").item(0).getTextContent());
            defaultLabelSizeX = Integer
                    .parseInt(doc.getElementsByTagName("defaultLabelSizeX").item(0).getTextContent());
            defaultLabelSizeY = Integer
                    .parseInt(doc.getElementsByTagName("defaultLabelSizeY").item(0).getTextContent());
            defaultCheckBoxSizeX = Integer
                    .parseInt(doc.getElementsByTagName("defaultCheckBoxSizeX").item(0).getTextContent());
            defaultCheckBoxSizeY = Integer
                    .parseInt(doc.getElementsByTagName("defaultCheckBoxSizeY").item(0).getTextContent());
            defaultRadioButtonSizeX = Integer
                    .parseInt(doc.getElementsByTagName("defaultRadioButtonSizeX").item(0).getTextContent());
            defaultRadioButtonSizeY = Integer
                    .parseInt(doc.getElementsByTagName("defaultRadioButtonSizeY").item(0).getTextContent());
        } else {

            defaultProjectX = 800;
            defaultProjectY = 600;
            windowSizeX = 1200;
            windowSizeY = 950;
            defaultButtonSizeX = 60;
            defaultButtonSizeY = 30;
            defaultLabelSizeX = 40;
            defaultLabelSizeY = 20;
            defaultCheckBoxSizeX = 60;
            defaultCheckBoxSizeY = 40;
            defaultRadioButtonSizeX = 60;
            defaultRadioButtonSizeY = 30;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element root = doc.createElement("config");

            Element edefaultProjectX = doc.createElement("defaultProjectX");
            edefaultProjectX.setTextContent(String.format("%d", defaultProjectX));
            Element edefaultProjectY = doc.createElement("defaultProjectY");
            edefaultProjectY.setTextContent(String.format("%d", defaultProjectY));

            Element ewindowSizeX = doc.createElement("windowSizeX");
            ewindowSizeX.setTextContent(String.format("%d", windowSizeX));
            Element ewindowSizeY = doc.createElement("windowSizeY");
            ewindowSizeY.setTextContent(String.format("%d", windowSizeY));

            Element edefaultButtonSizeX = doc.createElement("defaultButtonSizeX");
            edefaultButtonSizeX.setTextContent(String.format("%d", defaultButtonSizeX));
            Element edefaultButtonSizeY = doc.createElement("defaultButtonSizeY");
            edefaultButtonSizeY.setTextContent(String.format("%d", defaultButtonSizeY));

            Element edefaultLabelSizeX = doc.createElement("defaultLabelSizeX");
            edefaultLabelSizeX.setTextContent(String.format("%d", defaultLabelSizeX));
            Element edefaultLabelSizeY = doc.createElement("defaultLabelSizeY");
            edefaultLabelSizeY.setTextContent(String.format("%d", defaultLabelSizeY));

            Element edefaultCheckBoxSizeX = doc.createElement("defaultCheckBoxSizeX");
            edefaultCheckBoxSizeX.setTextContent(String.format("%d", defaultCheckBoxSizeX));
            Element edefaultCheckBoxSizeY = doc.createElement("defaultCheckBoxSizeY");
            edefaultCheckBoxSizeY.setTextContent(String.format("%d", defaultCheckBoxSizeX));

            Element edefaultRadioButtonSizeX = doc.createElement("defaultRadioButtonSizeX");
            edefaultRadioButtonSizeX.setTextContent(String.format("%d", defaultRadioButtonSizeX));
            Element edefaultRadioButtonSizeY = doc.createElement("defaultRadioButtonSizeY");
            edefaultRadioButtonSizeY.setTextContent(String.format("%d", defaultRadioButtonSizeY));

            root.appendChild(ewindowSizeX);
            root.appendChild(ewindowSizeY);

            root.appendChild(edefaultProjectX);
            root.appendChild(edefaultProjectY);

            root.appendChild(edefaultButtonSizeX);
            root.appendChild(edefaultButtonSizeY);
            root.appendChild(edefaultLabelSizeX);
            root.appendChild(edefaultLabelSizeY);
            root.appendChild(edefaultCheckBoxSizeX);
            root.appendChild(edefaultCheckBoxSizeY);
            root.appendChild(edefaultRadioButtonSizeX);
            root.appendChild(edefaultRadioButtonSizeY);
            doc.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult("config.xml");
            transformer.transform(source, result);

        }
    }

    public int getWindowSizeX() {
        return windowSizeX;
    }

    public void setWindowSizeX(int windowSizeX) {
        this.windowSizeX = windowSizeX;
    }

    public int getWindowSizeY() {
        return windowSizeY;
    }

    public void setWindowSizeY(int windowSizeY) {
        this.windowSizeY = windowSizeY;
    }

    public int getDefaultProjectX() {
        return defaultProjectX;
    }

    public void setDefaultProjectX(int defaultProjectX) {
        this.defaultProjectX = defaultProjectX;
    }

    public int getDefaultProjectY() {
        return defaultProjectY;
    }

    public void setDefaultProjectY(int defaultProjectY) {
        this.defaultProjectY = defaultProjectY;
    }

}
