package hu.vhcom.www.petrolcard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

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
}
