package hu.vhcom.www.petrolcard;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import Utils.VH_CONSTANTS;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;


class GetCookieAsync extends AsyncTask<Void, Void, String> {
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
            if(phpSessionID == null){
                return null;
            }
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

class GetRealmAsync extends AsyncTask<String, Void, String> {
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
            if(realmCode == null){
                return null;
            }
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

class AuthenticateAsync extends AsyncTask<String, Void, Response> {
    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);

    }

    @Override
    protected Response doInBackground(String... strings) {
        Log.v("OkHttpClient","doInBackground");
        final String password = strings[0];
        final String cookie = strings[1];
        Log.v("password",password);
        Log.v("cookie",cookie);

        OkHttpClient builder = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS).
                        authenticator(new Authenticator() {
                            public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                                Log.v("Request authenticate","Request authenticate");
                                String credential = Credentials.basic(VH_CONSTANTS.getPetrolcardUsername(), password);
                                if (responseCount(response) >= 3) {
                                    Log.v("responseCount", ">=3");
                                    return null;
                                }
                                return response.request().newBuilder().header("Authorization", credential).build();
                            }
                        }).build();
        Request request = new Request.Builder()
                .addHeader("Cookie", cookie)
                .addHeader("Origin", VH_CONSTANTS.getPetrolcardUrl())
                .addHeader("Referer", VH_CONSTANTS.getPetrolcardHttpReferer())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .url(VH_CONSTANTS.getPetrolcardAuth())
                .build();
        try {
            return builder.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }
    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}

class DownloadXmlAsync extends AsyncTask<String, Void, String> {
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

    }

    @Override
    protected String doInBackground(String... strings) {
        Log.v("DownloadXmlAsync","doInBackground");
        final String cookie = strings[0];
        Log.v("cookie",cookie);

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
                .url(VH_CONSTANTS.getPetrolcardData())
                .build();
        try{
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if(responseBody != null){
                return responseBody.string();
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}