package hu.vhcom.www.petrolcard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.OutputStreamWriter;

import Utils.VH_CONSTANTS;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

    protected void PreloadSettings() {
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

        if (helpers.ConnectionTesting(VH_CONSTANTS.getPetrolcardBaseUrl(), VH_CONSTANTS.getPetrolcardPort())) {
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.yes);

            replaceView(progressBarInternetConncection, myImage);
            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();

            if (helpers.ConnectionTesting(VH_CONSTANTS.getCheckInternetVia(), 80)) {
                myImage = new ImageView(this);
                myImage.setAlpha(0f);
                myImage.setImageResource(R.drawable.yes);

                replaceView(progressBarInforep, myImage);
                myImage.animate().alpha(1f).setDuration(DURATION);
                myImage.animate().start();
            } else {
                myImage = new ImageView(this);
                myImage.setAlpha(0f);
                myImage.setImageResource(R.drawable.no);

                replaceView(progressBarInforep, myImage);
                myImage.animate().alpha(1f).setDuration(DURATION);
                myImage.animate().start();
            }
        } else {
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.no);

            replaceView(progressBarInternetConncection, myImage);

            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();

            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.no);

            replaceView(progressBarInforep, myImage);

            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();
        }
        if (sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(), "0").equals("0")) {
            startActivity(new Intent(init.this, ServiceCode.class));
        } else {
            String PERSONAL_CODE = sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(), "0");

            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.yes);

            replaceView(progressServiceCode, myImage);
            myImage.animate().alpha(1f).setDuration(DURATION);
            myImage.animate().start();
                Authenticate(PERSONAL_CODE);
                try {
                    String cookie = sharedPreferences.getString("cookie", "0");
                    DownloadXmlAsync downloadXmlAsync = new DownloadXmlAsync();
                    String xml = downloadXmlAsync.execute(cookie).get();
                    Log.v("downloaded xml",xml);
                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("partnerlist.xml", Context.MODE_PRIVATE));
                        outputStreamWriter.write(xml);
                        outputStreamWriter.close();
                        startActivity(new Intent(init.this, PartnersList.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }

    public void Authenticate(String PERSONAL_CODE){
        try {
            GetCookieAsync getCookieAsync = new GetCookieAsync();
            String cookie = getCookieAsync.execute().get();
            //String cookie = getCookie();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookie);
            editor.apply();
            Log.v("Cookie setted", cookie);
            GetRealmAsync getRealmAsync = new GetRealmAsync();
            String realm = getRealmAsync.execute(cookie).get();
            if (realm == null) {
                getRealmAsync = new GetRealmAsync();
                realm = getRealmAsync.execute(cookie).get();
            }
            CodeCalculator codeCalculator = new CodeCalculator(realm, PERSONAL_CODE);
            String password = codeCalculator.Calc();
            Log.v("codeCalculator", realm + "+" + PERSONAL_CODE + "=" + password);
            AuthenticateAsync authenticateAsync = new AuthenticateAsync();
            Response response = authenticateAsync.execute(new String[]{password, cookie}).get();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                if (responseBody.string().startsWith("authentication")) {
                    Log.v("2nd attemp", "auth");
                    authenticateAsync = new AuthenticateAsync();
                    authenticateAsync.execute(new String[]{password, cookie}).get();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
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
}