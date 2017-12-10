package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.concurrent.TimeUnit;

import Utils.VH_CONSTANTS;
import okhttp3.OkHttpClient;

import static Utils.ViewReplacer.replaceView;

public class init extends AppCompatActivity {

    //private TextView textViewInternet,textViewInforep,textViewServiceCode,textViewDownload;
    // imageViewInternet;
    private ProgressBar progressBarInternetConncection,progressBarInforep,progressServiceCode,progressBarDownload;
    private Helpers helpers;
    private SharedPreferences sharedPreferences;
    private static final int DURATION = VH_CONSTANTS.getAnimationDuration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        sharedPreferences = getSharedPreferences(VH_CONSTANTS.getPrefsName(),MODE_PRIVATE);

        helpers = new Helpers();
        /*textViewInternet = findViewById(R.id.TextViewInternetConnection);
        textViewInforep = findViewById(R.id.TextViewInforeporter);
        textViewServiceCode = findViewById(R.id.TextViewServiceCode);
        textViewDownload = findViewById(R.id.TextViewDownload);*/

        progressBarDownload = findViewById(R.id.ProgressBarDownload);
        progressBarInforep = findViewById(R.id.ProgressBarInforeporter);
        progressBarInternetConncection = findViewById(R.id.ProgressBarInternetConncection);
        progressServiceCode = findViewById(R.id.ProgressBarServiceCode);

        //PreloadSettings();
    }

    protected void PreloadSettings(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(init.this);

        alertDialog.setCancelable(false);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setTitle("Figyelem");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        ImageView myImage;

        if(helpers.ConnectionTesting(VH_CONSTANTS.getPetrolcardBaseUrl(),VH_CONSTANTS.getPetrolcardPort())){
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.yes);

            replaceView(progressBarInternetConncection,myImage);
            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();

            if(helpers.ConnectionTesting(VH_CONSTANTS.getCheckInternetVia(),80)) {
                myImage = new ImageView(this);
                myImage.setAlpha(0f);
                myImage.setImageResource(R.drawable.yes);

                replaceView(progressBarInforep,myImage);
                myImage.animate().alpha(1f).setDuration(DURATION);
                myImage.animate().start();
            }
            else{
                myImage = new ImageView(this);
                myImage.setAlpha(0f);
                myImage.setImageResource(R.drawable.no);

                replaceView(progressBarInforep,myImage);
                myImage.animate().alpha(1f).setDuration(DURATION);
                myImage.animate().start();
            }
        }
        else{
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.no);

            replaceView(progressBarInternetConncection,myImage);

            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();

            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.no);

            replaceView(progressBarInforep,myImage);

            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();
        }
        if(sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(),"0").equals("0")) {
            startActivity(new Intent(init.this, ServiceCode.class));
        }
        else{
            String PERSONAL_CODE = sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(),"0");

            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.yes);

            replaceView(progressServiceCode,myImage);
            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();

            if(sharedPreferences.getString("cookie","0").equals("0")){
                try{
                    getCookieAsync getCookieAsync = new getCookieAsync();
                    String cookie = getCookieAsync.execute().get();
                    //String cookie = getCookie();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("cookie",cookie);
                    editor.apply();
                    Log.v("cookie",cookie);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Log.v("else cookie",sharedPreferences.getString("cookie","0"));
                try {
                    getRealmAsync getRealmAsync = new getRealmAsync();
                    String realm = getRealmAsync.execute(sharedPreferences.getString("cookie", "0")).get();
                    if(realm == null){
                        getRealmAsync = new getRealmAsync();
                        realm = getRealmAsync.execute(sharedPreferences.getString("cookie", "0")).get();
                    }
                    CodeCalculator codeCalculator = new CodeCalculator(realm,PERSONAL_CODE);
                    Log.v("realm",realm);
                    Log.v("codeCalculator",realm+"+"+PERSONAL_CODE+"="+codeCalculator.Calc());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("onActivityReenter","true");
        PreloadSettings();
    }

    private String getCookie()throws Exception{
        getCookieAsync getCookie = new getCookieAsync();
        try{
           return getCookie.execute().get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}