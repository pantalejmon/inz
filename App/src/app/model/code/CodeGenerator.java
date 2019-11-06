/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.code;

/**
 *
 * @author janek
 */
import app.model.projects.Project;
import app.model.widgets.Widget;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.SAXException;

public class CodeGenerator {

    private Project owner;

    public CodeGenerator(Project projekt) {
        this.owner = projekt;
    }

    public File GenerateCode()
            throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        ProcessingInstruction importLibary0 = (ProcessingInstruction) doc.createProcessingInstruction("import",
                "javafx.scene.control.*");
        ProcessingInstruction importLibary1 = (ProcessingInstruction) doc.createProcessingInstruction("import",
                "javafx.scene.layout.*");
        ProcessingInstruction importLibary2 = (ProcessingInstruction) doc.createProcessingInstruction("import",
                "java.lang.*");

        doc.appendChild(importLibary0);
        doc.appendChild(importLibary1);
        doc.appendChild(importLibary2);

        Element Panel = doc.createElement("AnchorPane");

        Attr maxHeight = doc.createAttribute("maxHeight");
        maxHeight.setValue("-Infinity");
        Panel.setAttributeNode(maxHeight);

        Attr maxWidth = doc.createAttribute("maxWidth");
        maxWidth.setValue("-Infinity");
        Panel.setAttributeNode(maxWidth);

        Attr minHeight = doc.createAttribute("minHeight");
        minHeight.setValue("-Infinity");
        Panel.setAttributeNode(minHeight);

        Attr minWidth = doc.createAttribute("minWidth");
        minWidth.setValue("-Infinity");
        Panel.setAttributeNode(minWidth);

        Attr prefHeight = doc.createAttribute("prefHeight");
        prefHeight.setValue(String.format("%d", this.owner.getSizeY()) + ".0");
        Panel.setAttributeNode(prefHeight);

        Attr prefWidth = doc.createAttribute("prefWidth");
        prefWidth.setValue(String.format("%d", this.owner.getSizeX()) + ".0");
        Panel.setAttributeNode(prefWidth);

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue("-Infinity");
        Panel.setAttributeNode(xmlns);

        Element Children = doc.createElement("children");

        for (Widget t : this.owner.getWidgets()) {
            Children.appendChild(t.getCode(doc));
        }

        Panel.appendChild(Children);

        doc.appendChild(Panel);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
        // "3");
        // transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(this.owner.getFile());
        StringWriter writer = new StringWriter();
        StreamResult code = new StreamResult(writer);
        transformer.transform(source, result);
        transformer.transform(source, code);
        String strResult = writer.toString();

        strResult = new StringBuilder(strResult).insert(strResult.indexOf("l.*?>") + 5, "\n").toString();
        strResult = new StringBuilder(strResult).insert(strResult.indexOf("t.*?>") + 5, "\n").toString();
        strResult = new StringBuilder(strResult).insert(strResult.indexOf("g.*?>") + 5, "\n\n").toString();
        // PrintWriter out = new PrintWriter(this.owner.getFile());

        // out.println(strResult);

        this.owner.setXmlcode(strResult);

        System.out.print(strResult);
        this.owner.setXmlviewer(new XmlViewer(owner));
        return this.owner.getFile();

    }

}
