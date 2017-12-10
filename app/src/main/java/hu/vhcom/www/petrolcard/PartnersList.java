package hu.vhcom.www.petrolcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PartnersList extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_list);
        textView = findViewById(R.id.textView);
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = openFileInput("partnerlist.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            ArrayList<Partners> partners =  parseXML(parser);
            StringBuilder sb = new StringBuilder();
            String text="";

            for(Partners partner:partners) {
                sb.append("id : " + partner.getId() + "\n");
                sb.append("company: "+partner.company+"\n");
            }
            textView.setText(sb.toString());



        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private ArrayList<Partners> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<Partners> partners = null;
        int eventType = parser.getEventType();
        Partners partner = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    Log.v("eventType","START_DOCUMENT");
                    partners = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    Log.v("parser.getName",parser.getName());
                    if (name.equals("site")) {
                        partner = new Partners();
                        partner.id = parser.getAttributeValue(null, "id");
                        partner.company = parser.getAttributeValue(null, "company");
                        partner.guid = parser.getAttributeValue(null, "guid");
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("site") && partner != null){
                        partners.add(partner);
                    }
            }
            eventType = parser.next();
        }

        return partners;

    }


    private String readFromFile(){
        String result = null;
        try {
            InputStream inputStream = openFileInput("partnerlist.xml");
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
