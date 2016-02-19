package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public final class ComponentParser {
    private final InlineStyleParser styles = new InlineStyleParser();
    private final EventParser events = new EventParser();
    private final ChildParser children = new ChildParser();
    private Map<String, Style> stylesSheet = new HashMap<>();
    private static List<String> namedComponents = new ArrayList<>();
    private static List<String> components = new ArrayList<>();
    private Map<String, String> methodCalls;
    private final FunctionParser functions;
    private final String file;
    private StringBuilder sb;

    public static List<String> getNamedComponents() {
        return namedComponents;
    }

    public static List<String> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public ComponentParser(String file, Map<String, String> methodCalls, Node node) {
        this.sb = new StringBuilder();
        this.methodCalls = methodCalls;
        this.file = file.replaceFirst("\\.jaml", "");
        functions = new FunctionParser();
        NamedNodeMap nodeMap = node.getAttributes();
        String name = "window";
        String methods = methodCalls.get("window");
        sb.append("import com.trolltech.qt.core.*;\nimport com.trolltech.qt.gui.*;\n");
        sb.append("public class qt extends QApplication{\npublic qt() { super(new String[0]); run(); }\n");
        sb.append("public void run() {\n");
        sb.append(String.format("QMainWindow %s = new QMainWindow()", name));
        try{
            if (!methods.isEmpty()) sb.append(String.format("{\n%s}", methods));
        }catch (NullPointerException ignored){
        }
        sb.append(";\n");
        styles.MainWindow(name, stylesSheet, sb, nodeMap);
        functions.Window(name, sb, nodeMap);
        nodeLoop("window", node);
        String styles = StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("this.setStyleSheet(\"%s\");\n", styles));
        sb.append("window.show();\n");
        sb.append("this.exec();\n}\n}");
    }

    private String StyleSheet(){
        StringBuilder sb = new StringBuilder();
        Collection<Style> temp = stylesSheet.values();
        Style[] styles = temp.toArray(new Style[temp.size()]);
        for(int i = 0; i < styles.length;){
            sb.append(styles[i].toString());
            if(++i < styles.length) sb.append("\\n\"+\n\"");
        }
        return sb.toString();
    }

    private String nodeLoop(String layoutName, Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            String component =  elementsSwitch(n.getNodeName(), layoutName, n);
            String[] parts = component.split(":");
            if (parts[0].equals("layout")) nodeLoop(parts[1], n);
        }
        return sb.toString();
    }

    private String elementsSwitch(String name, String layoutName, Node node){
        String component = "";
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        NamedNodeMap nodeMap = node.getAttributes();
        String methods = methodCalls.get(name);
        String n;
        switch (name) {
            case "section":
                n  = methodName(name, "", nodeMap);
                styles.Section(n, stylesSheet, sb, nodeMap);
                functions.Widget(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "label":
                n  = methodName(name, node.getTextContent(), nodeMap);
                styles.Label(n, stylesSheet, sb, nodeMap);
                functions.Label(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "check-box":
                n  = methodName(name, "", nodeMap);
                styles.CheckBox(n, stylesSheet, sb, nodeMap);
                functions.CheckBox(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "radio":
                n  = methodName(name, "", nodeMap);
                styles.RadioButton(n, stylesSheet, sb, nodeMap);
                functions.AbstractButton(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "number":
                n  = methodName(name, "", nodeMap);
                styles.LCDNumber(n, stylesSheet, sb, nodeMap);
                functions.Number(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "slider":
                n  = methodName(name, "", nodeMap);
                styles.Slider(n, stylesSheet, sb, nodeMap);
                functions.AbstractSlider(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "grid":
                n  = methodName(name, "", nodeMap);
                styles.Grid(n, stylesSheet, sb, nodeMap);
                functions.Widget(n, sb, nodeMap);
                children.addChild(layoutName, layout, "layout", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "menubar":
                n = "menubar";
                events.Events(file, n, name, "", methods, sb, nodeMap);
                styles.MenuBar(n, stylesSheet, sb, nodeMap);
                functions.MenuBar(n, sb, nodeMap);
                children.addChild(layoutName, layout, "menubar", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "menu":
                n  = methodName(name, "", nodeMap);
                styles.Menu(n, stylesSheet, sb, nodeMap);
                functions.Menu(n, sb, nodeMap);
                children.addChild(layoutName, layout, "menu", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "list":
                n = methodName(name, "", nodeMap);
                styles.List(n, stylesSheet, sb, nodeMap);
                functions.list(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "item":
                n = methodName(name, node.getTextContent(), nodeMap);
                styles.Item(n, stylesSheet, sb, nodeMap);
                children.addChild(layoutName, layout, "item", n, sb, nodeMap);
                break;
            case "button":
                n = methodName(name, node.getTextContent(), nodeMap);
                styles.PushButton(n, stylesSheet, sb, nodeMap);
                functions.AbstractButton(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "action":
                n = Utils.trySetName("name", name, nodeMap);
                String text = Utils.tryEmpty("text", "action", nodeMap);
                events.ActionEvents(file, n, name, text, methods, sb, nodeMap);
                styles.Action(n, stylesSheet, sb, nodeMap);
                functions.Action(n, sb, nodeMap);
                children.addChild(layoutName, layout, "action", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "separator":
                n = Utils.trySetName("name", name, nodeMap);
                children.addChild(layoutName, layout, "separator", n, sb, nodeMap);
                break;
            case "splitter":
                n = methodName(name, "", nodeMap);
                styles.Splitter(n, stylesSheet, sb, nodeMap);
                functions.Splitter(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "text-area":
                n = methodName(name, "", nodeMap);
                styles.TextEdit(n, stylesSheet, sb, nodeMap);
                functions.LineEdit(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "group":
                n = methodName(name, "", nodeMap);
                styles.Group(n, stylesSheet, sb, nodeMap);
                functions.Group(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                component = "layout:" + n;
                break;
        }
        return component;
    }

    private String methodName(String name, String text, NamedNodeMap nodeMap){
        String n = Utils.trySetName("name", name.replaceAll("-", "_"), nodeMap);
        String methods = methodCalls.get(n);
        events.Events(file, n, name, text, methods, sb, nodeMap);
        return n;
    }
}