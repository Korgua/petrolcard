package hu.vhcom.www.petrolcard;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    private JSONObject Partner,Client;
    private JSONArray Clients;
    private String partnerGUID,partnerName,partnerAddress,partnerPublicIP,partnerLastUpdated,partnerOsSign;
    private String clientMachineName,clientIP,clientOsSign,clientCassaAp;
    private int status, fwalert,fwsyncalert,serverRdpPort;
    /*
    status
    EE: offline pinkish
    1 : online  green

    fwalert
    1: RED

    fwsyncalert
    1: ORANGE

    os_sign
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String xml = readFromFile(VH_CONSTANTS.getPartnerlistFileName());
        ArrayList<HashMap<String, JSONObject>> menuItems = new ArrayList<>();
        ArrayList<HashMap<String, String>> _menuItems = new ArrayList<>();
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(xml); // getting DOM element
        NodeList server = doc.getElementsByTagName(KEY_ITEM);

        for (int i = 0; i < server.getLength(); i++) {
            // creating new HashMap
            Partner = new JSONObject();
            Clients = new JSONArray();

            HashMap<String, JSONObject> map = new HashMap<>();
            HashMap<String, String> _map = new HashMap<>();
            Element serverElement = (Element) server.item(i);

            NodeList clients = serverElement.getElementsByTagName("client");
            partnerGUID = serverElement.getAttribute("guid");
            partnerName = serverElement.getAttribute("company");
            partnerAddress = serverElement.getAttribute("address");
            partnerPublicIP = serverElement.getAttribute("ip");
            partnerLastUpdated = serverElement.getAttribute("updated");
            partnerOsSign = serverElement.getAttribute("os_sign");
            _map.put("partnerName",partnerName);
            _map.put("partnerAddress",partnerAddress);
            _map.put("partnerLastUpdated",partnerLastUpdated);
            _menuItems.add(_map);
            status = Integer.parseInt(serverElement.getAttribute("status"));
            fwalert = Integer.parseInt(serverElement.getAttribute("fwalert"));
            fwsyncalert = Integer.parseInt(serverElement.getAttribute("fwsyncalert"));
            if(serverElement.getAttribute("rdpport").length()>0){
                serverRdpPort = Integer.parseInt(serverElement.getAttribute("rdpport"));
            }
            else {
                serverRdpPort = 0;
            }

            try {
                Partner.put("guid",partnerGUID);
                Partner.put("name",partnerName);
                Partner.put("address",partnerAddress);
                Partner.put("ip",partnerPublicIP);
                Partner.put("os",partnerOsSign);
                Partner.put("status",status);
                Partner.put("fwalert",fwalert);
                Partner.put("fwsyncalert",fwsyncalert);
                Partner.put("updated",partnerLastUpdated);
                Partner.put("rdp",serverRdpPort);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int j=0;j<clients.getLength();j++){
                Client = new JSONObject();
                Element clientsElement = (Element)clients.item(j);
                clientMachineName = clientsElement.getAttribute("machinename");
                clientIP = clientsElement.getAttribute("localip");
                clientOsSign = clientsElement.getAttribute("os_sign");
                clientCassaAp = clientsElement.getAttribute("cassaap");
                try{
                    Client.put("machinename",clientMachineName);
                    Client.put("ip",clientIP);
                    Client.put("os",clientOsSign);
                    Client.put("ap",clientCassaAp);
                    Clients.put(Client);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
                Partner.put("clients",Clients);
                map.put("Partner",Partner);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            menuItems.add(map);
        }
        for(int i=0;i<menuItems.size();i++){
            try {
                JSONObject alma = menuItems.get(i).get("Partner");
                Log.v("complete json object",alma.toString());
                //Log.v("company",menuItems.get(i).get("Partner").getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ListAdapter adapter = new SimpleAdapter(PartnersList.this,_menuItems,R.layout.activity_selected_partner,new String[]{"partnerName"},new int[]{R.id.TextViewSelectedPartner});
        setListAdapter(adapter);
        ListView lv = getListView();

    }
//https://www.androidhive.info/2011/11/android-xml-parsing-tutorial/

    private String readFromFile(String file){
        String result = null;
        try {
            InputStream inputStream = openFileInput(file);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String tempString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((tempString = bufferedReader.readLine()) != null){
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
