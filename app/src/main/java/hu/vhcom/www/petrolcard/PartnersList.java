package hu.vhcom.www.petrolcard;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Utils.VH_CONSTANTS;
import Utils.XMLParser;


public class PartnersList extends ListActivity {
    static final String KEY_ITEM = "site"; // parent node

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String xml = readFromFile(VH_CONSTANTS.getPartnerlistFileName());
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_ITEM);

        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap


            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);

            NodeList ll = e.getElementsByTagName("client");

            Log.v("Element company",e.getAttribute("company")+": "+e.getAttribute("address")+", inner:"+ll.getLength());
            for(int j=0;j<ll.getLength();j++){
                Element el = (Element)ll.item(j);
                Log.v("inner Element",el.getAttribute("machinename"));
            }
            menuItems.add(map);
        }

    }
//https://www.androidhive.info/2011/11/android-xml-parsing-tutorial/

    private String readFromFile(String file){
        String result = null;
        try {
            InputStream inputStream = openFileInput(file);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((tempString = bufferedReader.readLine()) != null){
                    //tempString = arr.toString();
                    stringBuilder.append(tempString);
                    stringBuilder.append('\n');

                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
