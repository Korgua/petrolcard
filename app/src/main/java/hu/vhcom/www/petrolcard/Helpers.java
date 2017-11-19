package hu.vhcom.www.petrolcard;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;

public class Helpers extends AppCompatActivity{

    private Context context;



    public Helpers(Context ctx){
        context = ctx;
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean CheckPetrolcard(){
        CheckPetrolcardAsync checkPetrolcardAsync = new CheckPetrolcardAsync();
        try {
            boolean b = checkPetrolcardAsync.execute().get();
            Log.v("CheckPetrolcard", String.valueOf(b));
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    static class CheckPetrolcardAsync extends AsyncTask<Void, Void, Boolean>{
        private boolean b = false;
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(b);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Log.v("checkUrlWithPort","before declaration");
                int timeOutInMillis = 1500;
                Socket sock = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("91.82.85.251",7621);
                sock.connect(socketAddress, timeOutInMillis);
                sock.close();
                Log.v("checkUrlWithPort","after close");
                b = true;
                return b;
            }
            catch (Exception e){
                e.printStackTrace();
                b = false;
                return b;
            }
        }

    }
}
