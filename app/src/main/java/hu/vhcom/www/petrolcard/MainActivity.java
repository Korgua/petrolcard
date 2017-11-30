package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Utils.VH_CONSTANTS;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //helpers = new Helpers(MainActivity.this);
        startActivity(new Intent(MainActivity.this, init.class));
    }
}
