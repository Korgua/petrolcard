package hu.vhcom.www.petrolcard;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import Utils.VH_CONSTANTS;

import static Utils.ViewReplacer.replaceView;

public class init extends AppCompatActivity {

    private TextView textViewInternet,textViewInforep,textViewServiceCode,textViewDownload;
    // imageViewInternet;
    private ProgressBar progressBarInternetConncection,progressBarInforep,progressServiceCode,progressBarDownload;
    private Helpers helpers;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        sharedPreferences = getSharedPreferences(VH_CONSTANTS.getPrefsName(),MODE_PRIVATE);

        helpers = new Helpers();
        textViewInternet = findViewById(R.id.TextViewInternetConnection);
        textViewInforep = findViewById(R.id.TextViewInforeporter);
        textViewServiceCode = findViewById(R.id.TextViewServiceCode);
        textViewDownload = findViewById(R.id.TextViewDownload);

        progressBarDownload = findViewById(R.id.ProgressBarDownload);
        progressBarInforep = findViewById(R.id.ProgressBarInforeporter);
        progressBarInternetConncection = findViewById(R.id.ProgressBarInternetConncection);
        progressServiceCode = findViewById(R.id.ProgressBarServiceCode);

        init();
    }

    protected void init(){
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


        View C = findViewById(R.id.ProgressBarInternetConncection);

        //replaceView(C,myImage);




        if(helpers.isOnline()){
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.yes);

            replaceView(progressBarInternetConncection,myImage);
            myImage.animate().alpha(1f).setDuration(5000);
            myImage.animate().start();

            if(helpers.CheckPetrolcard()) {
                myImage = new ImageView(this);
                myImage.setAlpha(0f);
                myImage.setImageResource(R.drawable.yes);

                replaceView(progressBarInforep,myImage);
                myImage.animate().alpha(1f).setDuration(5000);
                myImage.animate().start();
            }
            else{
                myImage = new ImageView(this);
                myImage.setAlpha(0f);
                myImage.setImageResource(R.drawable.no);

                replaceView(progressBarInforep,myImage);
                myImage.animate().alpha(1f).setDuration(5000);
                myImage.animate().start();
            }
        }
        else{
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.no);

            replaceView(progressBarInternetConncection,myImage);

            myImage.animate().alpha(1f).setDuration(5000);
            myImage.animate().start();

            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.no);

            replaceView(progressBarInforep,myImage);

            myImage.animate().alpha(1f).setDuration(5000);
            myImage.animate().start();
        }
        if(sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(),"0").equals("0")) {
            startActivity(new Intent(init.this, ServiceCode.class));
        }
        else{
            myImage = new ImageView(this);
            myImage.setAlpha(0f);
            myImage.setImageResource(R.drawable.yes);

            replaceView(progressServiceCode,myImage);
            myImage.animate().alpha(1f).setDuration(5000);
            myImage.animate().start();
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        //super.onActivityReenter(resultCode, data);
        init();
    }

    private static class CrossFade implements Animator.AnimatorListener{

        private ImageView iv;
        private Drawable dr;
        private boolean complete = false;
        private int duration = 500;

        private CrossFade(ImageView iv, Drawable dr){
            Log.v("CrossFade","constructor");
            this.iv = iv;
            this.dr = dr;
        }

        @Override
        public void onAnimationStart(Animator animator) {
            Log.v("CrossFade","onAnimationStart");
            //imageViewInternet.animate().start();

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            Log.v("CrossFade", "onAnimationEnd");
            if (!complete) {
                complete = true;
                iv.setBackground(dr);
                iv.animate().alpha(1f).setDuration(duration);
            }
        }
        @Override
        public void onAnimationCancel(Animator animator) {
            Log.v("CrossFade","onAnimationCancel");
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            Log.v("CrossFade","onAnimationRepeat");
        }
    }
}
