package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import Utils.VH_CONSTANTS;

public class init extends AppCompatActivity {

    private TextView textViewInternet,textViewInforep,textViewServiceCode,textViewDownload;
    private ImageView imageViewInforep,imageViewServiceCode,imageViewDownload;
    DrawerLayout imageViewInternet;
    private Helpers helpers;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        sharedPreferences = getSharedPreferences(VH_CONSTANTS.getPrefsName(),MODE_PRIVATE);

        helpers = new Helpers();
        helpers.setContext(init.this);
        textViewInternet = findViewById(R.id.TextViewInternetConnection);
        textViewInforep = findViewById(R.id.TextViewInforeporter);
        textViewServiceCode = findViewById(R.id.TextViewServiceCode);
        textViewDownload = findViewById(R.id.TextViewDownload);

        imageViewDownload = findViewById(R.id.ImageViewDownload);
        imageViewInforep = findViewById(R.id.ImageViewInforeporter);
        imageViewInternet = findViewById(R.id.ImageViewInternetConncection);
        imageViewServiceCode = findViewById(R.id.ImageViewServiceCode);

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

        if(!helpers.isOnline()){
            /*alertDialog.setMessage("A telefon jelenleg nem csatlakozik az Internetre!");
            alertDialog.show();*/
            imageViewInternet.setBackgroundResource(R.drawable.no);
        }
        else{
            Drawable drawable[] = new Drawable[2];
            drawable[0] = ContextCompat.getDrawable(init.this,R.drawable.icon);
            drawable[1] = ContextCompat.getDrawable(init.this,R.drawable.yes);
            TransitionDrawable transitionDrawable = new TransitionDrawable(drawable);
            imageViewInternet.setBackground(transitionDrawable);

            transitionDrawable.startTransition(3000);
            //imageViewInternet.setBackgroundResource(R.drawable.yes);
        }
        if(!helpers.CheckPetrolcard()){
            /*alertDialog.setMessage("Az InfoReporter ("+ VH_CONSTANTS.getPetrolcardUrl()+") jelenleg nem elérhető!");
            alertDialog.show();*/
            imageViewInforep.setBackgroundResource(R.drawable.no);
        }
        else{
            imageViewInforep.setBackgroundResource(R.drawable.yes);
        }
        if(sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(),"0").equals("0")) {
            startActivity(new Intent(init.this, ServiceCode.class));
        }
        else{
            imageViewServiceCode.setBackgroundResource(R.drawable.yes);
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        //super.onActivityReenter(resultCode, data);
        init();
    }
}
