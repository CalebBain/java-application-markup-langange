package Assemble;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class Assembler {
    public static void  main(String[] args)
    {
        Assembler a = new Assembler();
        a.input();
    }

    public void input() {
        try {
            File fXmlFile = new File("jaml_files/main.jaml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) parse(doc.getChildNodes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(NodeList nodeList){
        printNote(nodeList);
        Node node = nodeList.item(0);
        if(node instanceof Element && node.getNodeName().equals("jaml")){
            Element docElement = (Element)node;
            Node style = docElement.getElementsByTagName("style").item(0);
            if(style != null) new StyleParser();
            Node window = docElement.getElementsByTagName("window").item(0);
            if(window != null) ParseFlavor(window, window.getChildNodes());
        }else throw new SyntaxException("the file must begin with a jaml tag");
    }

    private void ParseFlavor(Node window, NodeList nodeList){
        NamedNodeMap nodeMap = window.getAttributes();
        String s = nodeMap.getNamedItem("flavor").getNodeValue();
        switch (s.toLowerCase()){
            case "qt": QT app = new QT(nodeList, new String[0]); break;
        }
    }

    private void printNote(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                // get node name and value
                System.out.println("\nNode Name = " + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value = " + tempNode.getTextContent());
                if (tempNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        System.out.print("attr name : " + node.getNodeName());
                        System.out.print("\t:\t");
                        System.out.println("attr value : " + node.getNodeValue());
                    }
                }
                if (tempNode.hasChildNodes()) printNote(tempNode.getChildNodes()); // loop again if has child nodes
                System.out.println("Node Name = " + tempNode.getNodeName() + " [CLOSE]");
            }
        }
    }
}