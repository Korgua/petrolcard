package hu.vhcom.www.petrolcard;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Helpers extends AppCompatActivity{

    public Helpers(){}

    public boolean ConnectionTesting(String url, int port){
        ConnectionTestingAsync connectionTestingAsync = new ConnectionTestingAsync();
        String params[] = {url,Integer.toString(port)};
        try {
            boolean b = connectionTestingAsync.execute(params).get();
            Log.v("ConnectionTesting", String.valueOf(b));
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    static class ConnectionTestingAsync extends AsyncTask<String, Void, Boolean>{
        private boolean b = false;
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(b);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String url = strings[0];
            int port = Integer.parseInt(strings[1]);
            try {
                Log.v("ConnectionTestingAsync",url+":"+Integer.toString(port));
                int timeOutInMillis = 3000;
                Socket sock = new Socket();
                SocketAddress socketAddress = new InetSocketAddress(url,port);
                sock.connect(socketAddress, timeOutInMillis);
                sock.close();
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
