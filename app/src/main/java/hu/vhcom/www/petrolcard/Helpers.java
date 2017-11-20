package hu.vhcom.www.petrolcard;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import Utils.VH_CONSTANTS;

public class Helpers extends AppCompatActivity{

    private Context context;
    public void setContext(Context context) {
        this.context = context;
    }

    public Helpers(){}
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
                Log.v("doInBackground --> url",VH_CONSTANTS.getPetrolcardBaseUrl());
                Log.v("doInBackground --> port",Integer.toString(VH_CONSTANTS.getPetrolcardPort()));
                int timeOutInMillis = 1500;
                Socket sock = new Socket();
                SocketAddress socketAddress = new InetSocketAddress(VH_CONSTANTS.getPetrolcardBaseUrl(),VH_CONSTANTS.getPetrolcardPort());
                sock.connect(socketAddress, timeOutInMillis);
                sock.close();
                Log.v("checkUrlWithPort","after close");
                b = true;
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                b = false;
                return false;
            }
        }

    }
}
