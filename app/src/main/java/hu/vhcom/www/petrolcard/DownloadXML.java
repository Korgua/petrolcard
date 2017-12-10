package hu.vhcom.www.petrolcard;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import Utils.VH_CONSTANTS;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;

import java.util.concurrent.TimeUnit;

//import static Utils.DownloadXML.setDataXml;


public class DownloadXML extends Activity {

    public static String cookie = "DEFAULT_COOKIE";

    //public Data data;
    public Context context;
    public static JSONObject PETROLCARD_DATA = new JSONObject();
    private static String dataXml;

    public String getDataXml() {
        return dataXml;
    }

    public static void setDataXml(String data_xml) {
        dataXml = data_xml;
    }

    public static JSONObject getPetrolcardData() {
        return PETROLCARD_DATA;
    }

    public static void setPetrolcardData(JSONObject petrolcardData) {
        PETROLCARD_DATA = petrolcardData;
    }

    /*public Data getData() {
        return data;
    }*/

    public Context getContext() {
        return context;
    }


    private static OkHttpClient createAuthenticatedClient(final String password) {
        // build client with authentication information.
        return new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS).
                        authenticator(new Authenticator() {
                            public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                                String credential = Credentials.basic(VH_CONSTANTS.getPetrolcardUsername(), password);
                                if (responseCount(response) >= 3) {
                                    return null;
                                }
                                return response.request().newBuilder().header("Authorization", credential).build();
                            }
                        }).build();
    }

    public static String getCookie(OkHttpClient httpClient, String url)throws Exception{
        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        httpClient.newCall(request).execute();
        String phpSessionID = response.headers("Set-Cookie").toString();
        String[] phpSessIDParts = phpSessionID.split(";");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < phpSessIDParts[0].length(); i++) {
            sb.append(phpSessIDParts[0].charAt(i));
        }
        return sb.toString();
    }

    public static String getRealmCode(OkHttpClient httpClient, String anyURL, String cookie) throws Exception {
        Request request = new Request.Builder()
                .url(anyURL)
                .header("Cookie", cookie)
                .addHeader("Origin", VH_CONSTANTS.getPetrolcardUrl())
                .addHeader("Referer", VH_CONSTANTS.getPetrolcardHttpReferer())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .build();
        Response response = httpClient.newCall(request).execute();
        String realmCode = response.header("WWW-Authenticate");
        String[] realmCodeParts = realmCode.split("-");
        realmCode = realmCodeParts[1];
        return realmCode;
    }

    public static void fakeHttpRequest(OkHttpClient httpClient, String anyURL, String cookie) throws Exception {
        Request request = new Request.Builder().url(anyURL).header("Cookie", cookie).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.i("fakeHttpRequest", "Succesfull request");
        }
    }

    public static String connectPetrolcardData(OkHttpClient httpClient, String anyURL, String phpSessionId) throws Exception {
        try {
            Request request = new Request.Builder()
                    .url(anyURL)
                    .header("Cookie", phpSessionId)
                    .addHeader("Origin", VH_CONSTANTS.getPetrolcardUrl())
                    .addHeader("Referer", VH_CONSTANTS.getPetrolcardHttpReferer())
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                    .build();
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String exportXMLfromPetrolcard() {
        try {
            String phpSessionID = getPetrolcardData().getString("cookie");
            String xml = DownloadXML.connectPetrolcardData(new OkHttpClient(), VH_CONSTANTS.getPetrolcardData(), phpSessionID);
            Log.i("exportXML", xml);
            //FileOutputStream fos = new FileOutputStream("alma.txt",false);
            return xml;
            /*
            String file_name = "petrolcard_data.xml";
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file_name, Context.MODE_PRIVATE));
                outputStreamWriter.write(xml);
                outputStreamWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } catch (Exception e) {
            Log.i("exportXMLfromPetrolcard", e.toString());
        }
        return null;
    }

    public static JSONObject authenticate(OkHttpClient httpClient, String anyURL, String serviceCode) throws Exception {
        try {
            Log.i("authenticate --> url", anyURL);
            //getCookieAndRealmCode(httpClient, anyURL);
            String phpSessionID = PETROLCARD_DATA.getString("cookie");
            String realmCode = PETROLCARD_DATA.getString("realmCode");

            fakeHttpRequest(httpClient, anyURL, phpSessionID);

            Log.i("auth-->realmCode", realmCode);
            Log.i("auth-->cookie", phpSessionID);
            String realmResponseCode = new CodeCalculator(realmCode, serviceCode).Calc();
            httpClient = createAuthenticatedClient(realmResponseCode);
            Request request = new Request.Builder().url(anyURL).header("Cookie", phpSessionID).build();
            Response response = httpClient.newCall(request).execute();
            Log.i("authenticate", response.message());
            if (!response.isSuccessful())
                throw new Exception();

            return PETROLCARD_DATA;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*public DownloadXML(Context context) throws Exception {
        this.context = context;
        this.data = new Data(this.context);
        JSONObject jsonObject = getPetrolcardData();
        try {
            jsonObject.put(Utils.getServiceCodeKey(), data.getData(Utils.getServiceCodeKey()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setPetrolcardData(jsonObject);
        Log.i("DownloadXML", "before AsyncRequest");
        AsyncRequest asyncRequest = new AsyncRequest(context);
        asyncRequest.execute();
        Log.i("DownloadXML", "After AsyncRequest");
    }*/
}
    /*class AsyncRequest extends AsyncTask<Data, Void, DownloadXML> {

        private String xml = "";

        private final WeakReference<Context> contextWeakReference;

        AsyncRequest(Context ctx) {
            contextWeakReference = new WeakReference<>(ctx);
        }


        @Override
        public void onPostExecute(DownloadXML v) {

            Log.i("onPostExecute", "before set xml");
            super.onPostExecute(v);
            Log.i("xml","vajh üres? -->"+xml);
            if(xml != null){
                setDataXml(xml);
            }
            Log.i("onPostExecute", "After set xml");

            //v.setDataXml(xml);
        }

        @Override
        protected DownloadXML doInBackground(Data... context) {
            try {
                Context ctx = contextWeakReference.get();
                Log.i("ctx.getPackageName()",ctx.getPackageName());
                String url = Utils.getPetrolcardAuth();

                OkHttpClient.Builder client = new OkHttpClient()
                        .newBuilder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS);
                String service_code = DownloadXML.getPetrolcardData().getString(Utils.getServiceCodeKey());
                DownloadXML.authenticate(client.build(), url, service_code);
                Log.i("DownloadXML doInBackground", "before exportXML");
                xml = DownloadXML.exportXMLfromPetrolcard();
                //Log.i("xml: ",xml);
                Log.i("doInBackground", "After exportXML");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/



