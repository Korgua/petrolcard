package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Utils.VH_CONSTANTS;


public class MainActivity extends AppCompatActivity {

    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //helpers = new Helpers(MainActivity.this);
        helpers = new Helpers();
        helpers.setContext(MainActivity.this);
        init();
    }

    protected void init(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

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
            alertDialog.setMessage("A telefon jelenleg nem csatlakozik az Internetre!");
            alertDialog.show();
        }
        if(!helpers.CheckPetrolcard()){
            alertDialog.setMessage("Az InfoReporter ("+ VH_CONSTANTS.getPetrolcardUrl()+") jelenleg nem elérhető!");
            alertDialog.show();
        }
        startActivity(new Intent(MainActivity.this,ServiceCode.class));
    }
}
