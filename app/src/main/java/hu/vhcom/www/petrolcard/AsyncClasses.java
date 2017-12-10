package hu.vhcom.www.petrolcard;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import Utils.VH_CONSTANTS;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


class getCookieAsync extends AsyncTask<Void, Void, String> {
    @Override
    protected void onPostExecute(String cookie) {
            super.onPostExecute(cookie);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String url = VH_CONSTANTS.getPetrolcardAuth();
        OkHttpClient.Builder clientBuilder = new OkHttpClient()
                .newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();
        Request request = new Request.Builder()
                .addHeader("Origin", VH_CONSTANTS.getPetrolcardUrl())
                .addHeader("Referer", VH_CONSTANTS.getPetrolcardHttpReferer())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).execute();
            String phpSessionID = response.headers("Set-Cookie").toString();
            String[] phpSessIDParts = phpSessionID.split(";");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < phpSessIDParts[0].length(); i++) {
                sb.append(phpSessIDParts[0].charAt(i));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class getRealmAsync extends AsyncTask<String, Void, String> {
    @Override
    protected void onPostExecute(String realm) {
        super.onPostExecute(realm);
    }

    @Override
    protected String doInBackground(String... strings) {
        String cookie = strings[0];
        String url = VH_CONSTANTS.getPetrolcardAuth();
        OkHttpClient.Builder clientBuilder = new OkHttpClient()
                .newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();
        Request request = new Request.Builder()
                .addHeader("Cookie", cookie)
                .addHeader("Origin", VH_CONSTANTS.getPetrolcardUrl())
                .addHeader("Referer", VH_CONSTANTS.getPetrolcardHttpReferer())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.v("response",response.toString());
            String realmCode = response.header("WWW-Authenticate");
            Log.v("realmCode",realmCode);
            String[] realmCodeParts = realmCode.split("-");
            realmCode = realmCodeParts[1];
            return realmCode;
        } catch (Exception e) {
            //e.printStackTrace();
            Log.v("error","RealmCode nem található");
        }
        return null;
    }
}