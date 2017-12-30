package Utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParser {

    // constructor
    public XMLParser() {

    }


    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        /*
         name = parser.getName();
         Log.v("parser.getName",parser.getName());
         if (name.equals("site")) {
         partner = new Partners();
         partner.id = parser.getAttributeValue(null, "id");
         partner.company = parser.getAttributeValue(null, "company");
         partner.guid = parser.getAttributeValue(null, "guid");
         }
         */
        return this.getElementValue(n.item(0));
    }
}
