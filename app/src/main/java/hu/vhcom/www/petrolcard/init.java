package hu.vhcom.www.petrolcard;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import Utils.VH_CONSTANTS;

public class init extends AppCompatActivity {

    private TextView textViewInternet,textViewInforep,textViewServiceCode,textViewDownload;
    private ImageView imageViewInforep,imageViewServiceCode,imageViewDownload;//,imageViewInternet;
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
        //imageViewInternet = findViewById(R.id.ImageViewInternetConncection);
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

        ImageView iv = new ImageView(init.this);
        iv.setMaxHeight(32);
        iv.setMaxWidth(32);
/*

//LinearLayOut Setup
LinearLayout linearLayout= new LinearLayout(this);
linearLayout.setOrientation(LinearLayout.VERTICAL);

linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
LayoutParams.MATCH_PARENT));

//ImageView Setup
ImageView imageView = new ImageView(this);

//setting image resource
imageView.setImageResource(R.drawable.play);

//setting image position
imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
LayoutParams.WRAP_CONTENT));

//adding view to layout
linearLayout.addView(imageView);
//make visible to program
setContentView(linearLayout);



*/
        /*
        <ImageView
                    android:id="@+id/ImageViewInternetConncection"
                    android:background="@android:drawable/ic_menu_search"
                    android:contentDescription="@string/init_internet"
                    android:layout_marginStart="20sp"
                    android:layout_marginEnd="20sp">
        */

        /*if(helpers.isOnline()){
            imageViewInternet.animate().setListener(new CrossFade(imageViewInternet,ContextCompat.getDrawable(init.this,R.drawable.yes)));
            imageViewInternet.animate().alpha(0f).setDuration(1000);
            imageViewInternet.animate().start();

        }
        else{
            imageViewInternet.setBackgroundResource(R.drawable.no);
        }*/
        if(helpers.CheckPetrolcard()){
            imageViewInforep.animate().setListener(new CrossFade(imageViewInforep,ContextCompat.getDrawable(init.this,R.drawable.yes)));
            imageViewInforep.animate().alpha(0f).setDuration(1000);
            imageViewInforep.animate().start();
        }
        else{
            imageViewInforep.setBackgroundResource(R.drawable.no);
        }
        if(sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(),"0").equals("0")) {
            startActivity(new Intent(init.this, ServiceCode.class));
        }
        else{
            imageViewServiceCode.animate().setListener(new CrossFade(imageViewServiceCode,ContextCompat.getDrawable(init.this,R.drawable.yes)));
            imageViewServiceCode.animate().alpha(0f).setDuration(1000);
            imageViewServiceCode.animate().start();
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
