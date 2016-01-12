package QtComponents;

import StyleComponents.Style;
import com.trolltech.qt.gui.QMainWindow;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QMainWindow implements Component {

    private Style style;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Window(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = check("name");
        this.Class = check("class");
        if (!Name.isEmpty()) {
            this.style = new Style(Name);
            this.setAccessibleName(Name);
        } else
            this.style = new Style("number");
    }

    private String check(String keyword) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    private void Title(String prop) {

    }

    private void AltBackground(String prop) {

    }

    @Override
    public void setStyle() {
        String prop;
        this.setWindowTitle(tr((!(prop = check("title")).isEmpty()) ? prop : "JAML Applicaiton"));
        if (!(prop = check("alt-background-color")).isEmpty()) style.addAttrabute("alternate-background-color", prop);
        if (!(prop = check("background")).isEmpty()) style.addAttrabute("background", prop);
        if (!(prop = check("background-color")).isEmpty()) style.addAttrabute("background-color", prop);
        if (!(prop = check("background-image")).isEmpty()) style.addAttrabute("background-image", prop);
        if (!(prop = check("background-repeat")).isEmpty()) style.addAttrabute("background-repeat", prop);
        if (!(prop = check("background-position")).isEmpty()) style.addAttrabute("background-position", prop);
        if (!(prop = check("background-attachment")).isEmpty()) style.addAttrabute("background-attachment", prop);
        if (!(prop = check("background-clip")).isEmpty()) style.addAttrabute("background-clip", prop);
        if (!(prop = check("background-origin")).isEmpty()) style.addAttrabute("background-origin", prop);
        if (!(prop = check("border")).isEmpty()) style.addAttrabute("border", prop);
        if (!(prop = check("border-top")).isEmpty()) style.addAttrabute("border-color", prop);
        if (!(prop = check("border-right")).isEmpty()) style.addAttrabute("border-style", prop);
        if (!(prop = check("border-bottom")).isEmpty()) style.addAttrabute("border-width", prop);
        if (!(prop = check("border-left")).isEmpty()) style.addAttrabute("border-top", prop);
        if (!(prop = check("border-image")).isEmpty()) style.addAttrabute("border-top-color", prop);
        if (!(prop = check("border-color")).isEmpty()) style.addAttrabute("border-top-style", prop);
        if (!(prop = check("border-top-color")).isEmpty()) style.addAttrabute("border-top-width", prop);
        if (!(prop = check("border-right-color")).isEmpty()) style.addAttrabute("border-right", prop);
        if (!(prop = check("border-bottom-color")).isEmpty()) style.addAttrabute("border-right-color", prop);
        if (!(prop = check("border-left-color")).isEmpty()) style.addAttrabute("border-right-style", prop);
        if (!(prop = check("border-style")).isEmpty()) style.addAttrabute("border-right-width", prop);
        if (!(prop = check("border-top-style")).isEmpty()) style.addAttrabute("border-bottom", prop);
        if (!(prop = check("border-right-style")).isEmpty()) style.addAttrabute("border-bottom-color", prop);
        if (!(prop = check("border-bottom-style")).isEmpty()) style.addAttrabute("border-bottom-style", prop);
        if (!(prop = check("border-left-style")).isEmpty()) style.addAttrabute("border-bottom-width", prop);
        if (!(prop = check("border-width")).isEmpty()) style.addAttrabute("border-left", prop);
        if (!(prop = check("border-top-width")).isEmpty()) style.addAttrabute("border-left-color", prop);
        if (!(prop = check("border-right-width")).isEmpty()) style.addAttrabute("border-left-style", prop);
        if (!(prop = check("border-bottom-width")).isEmpty()) style.addAttrabute("border-left-width", prop);
        if (!(prop = check("border-left-width")).isEmpty()) style.addAttrabute("border-image", prop);
        if (!(prop = check("border-radius")).isEmpty()) style.addAttrabute("border-radius", prop);
        if (!(prop = check("border-top-left-radius")).isEmpty()) style.addAttrabute("border-top-left-radius", prop);
        if (!(prop = check("border-top-right-radius")).isEmpty()) style.addAttrabute("border-top-right-radius", prop);
        if (!(prop = check("border-bottom-right-radius")).isEmpty()) style.addAttrabute("border-bottom-right-radius", prop);
        if (!(prop = check("border-bottom-left-radius")).isEmpty()) style.addAttrabute("border-bottom-left-radius", prop);
        if (!(prop = check("top")).isEmpty()) style.addAttrabute("top", prop);
        if (!(prop = check("right")).isEmpty()) style.addAttrabute("right", prop);
        if (!(prop = check("bottom")).isEmpty()) style.addAttrabute("bottom", prop);
        if (!(prop = check("left")).isEmpty()) style.addAttrabute("left", prop);
        if (!(prop = check("height")).isEmpty()) style.addAttrabute("height", prop);
        if (!(prop = check("width")).isEmpty()) style.addAttrabute("width", prop);
        if (!(prop = check("gridline-color")).isEmpty()) style.addAttrabute("gridline-color", prop);
        if (!(prop = check("button-layout")).isEmpty()) style.addAttrabute("button-layout", prop);
        if (!(prop = check("button-icon")).isEmpty()) style.addAttrabute("color", prop);
        if (!(prop = check("color")).isEmpty()) style.addAttrabute("dialogbuttonbox-buttons-have-icons", prop);
        if (!(prop = check("font")).isEmpty()) style.addAttrabute("font", prop);
        if (!(prop = check("font-family")).isEmpty()) style.addAttrabute("font-family", prop);
        if (!(prop = check("font-size")).isEmpty()) style.addAttrabute("font-size", prop);
        if (!(prop = check("font-style")).isEmpty()) style.addAttrabute("font-style", prop);
        if (!(prop = check("font-weight")).isEmpty()) style.addAttrabute("font-weight", prop);
        if (!(prop = check("icon-size")).isEmpty()) style.addAttrabute("icon-size", prop);
        if (!(prop = check("image")).isEmpty()) style.addAttrabute("image", prop);
        if (!(prop = check("image-position")).isEmpty()) style.addAttrabute("image-position", prop);
        if (!(prop = check("margin")).isEmpty()) style.addAttrabute("margin", prop);
        if (!(prop = check("margin-top")).isEmpty()) style.addAttrabute("margin-top", prop);
        if (!(prop = check("margin-right")).isEmpty()) style.addAttrabute("margin-right", prop);
        if (!(prop = check("margin-bottom")).isEmpty()) style.addAttrabute("margin-bottom", prop);
        if (!(prop = check("margin-left")).isEmpty()) style.addAttrabute("margin-left", prop);
        if (!(prop = check("max-height")).isEmpty()) style.addAttrabute("max-height", prop);
        if (!(prop = check("max-width")).isEmpty()) style.addAttrabute("max-width", prop);
        if (!(prop = check("textbox-interaction")).isEmpty()) style.addAttrabute("messagebox-text-interaction-flags", prop);
        if (!(prop = check("min-height")).isEmpty()) style.addAttrabute("min-height", prop);
        if (!(prop = check("min-width")).isEmpty()) style.addAttrabute("min-width", prop);
        if (!(prop = check("opacity")).isEmpty()) style.addAttrabute("opacity", prop);
        if (!(prop = check("padding")).isEmpty()) style.addAttrabute("padding", prop);
        if (!(prop = check("padding-top")).isEmpty()) style.addAttrabute("padding-top", prop);
        if (!(prop = check("padding-right")).isEmpty()) style.addAttrabute("padding-right", prop);
        if (!(prop = check("padding-bottom")).isEmpty()) style.addAttrabute("padding-bottom", prop);
        if (!(prop = check("padding-left")).isEmpty()) style.addAttrabute("padding-left", prop);
        if (!(prop = check("alt-empty-row-color")).isEmpty()) style.addAttrabute("paint-alternating-row-colors-for-empty-area", prop);
        if (!(prop = check("position")).isEmpty()) style.addAttrabute("position", prop);
        if (!(prop = check("select-background-color")).isEmpty()) style.addAttrabute("selection-background-color", prop);
        if (!(prop = check("select-color")).isEmpty()) style.addAttrabute("selection-color", prop);
        if (!(prop = check("select-decoration")).isEmpty()) style.addAttrabute("show-decoration-selected", prop);
        if (!(prop = check("spacing")).isEmpty()) style.addAttrabute("spacing", prop);
        if (!(prop = check("subcontrol-origin")).isEmpty()) style.addAttrabute("subcontrol-origin", prop);
        if (!(prop = check("subcontrol-position")).isEmpty()) style.addAttrabute("subcontrol-position", prop);
        if (!(prop = check("text-align")).isEmpty()) style.addAttrabute("text-align", prop);
        if (!(prop = check("text-decoration")).isEmpty()) style.addAttrabute("text-decoration", prop);
    }

    @Override
    public String Name() {
        return Name;
    }

    @Override
    public String Class() {
        return Class;
    }

    @Override
    public String Component() {
        return this.getClass().getName();
    }

    @Override
    public QMainWindow Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}