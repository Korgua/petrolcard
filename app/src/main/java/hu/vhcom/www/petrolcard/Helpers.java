package hu.vhcom.www.petrolcard;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import Utils.VH_CONSTANTS;

public class Helpers extends AppCompatActivity{

    public void setContext(){}

    public Helpers(){}
    public boolean isOnline() {
        IsOnlineAsync isOnlineAsync = new IsOnlineAsync();
        try {
            boolean b = isOnlineAsync.execute().get();
            Log.v("isOnlineAsync", String.valueOf(b));
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static class IsOnlineAsync extends AsyncTask<Void, Void, Boolean>{
        private boolean b = false;
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(b);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Log.v("doInBackground --> url", "www.google.com");
                Log.v("doInBackground --> port", "80");
                int timeOutInMillis = 1000;
                Socket sock = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("www.google.com", 80);
                sock.connect(socketAddress, timeOutInMillis);
                sock.close();
                Log.v("checkUrlWithPort", "after close");
                b = true;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                b = false;
                return false;
            }
        }

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
                int timeOutInMillis = 3000;
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
