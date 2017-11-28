package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import Utils.Regexp;
import Utils.VH_CONSTANTS;

public class ServiceCode extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_code);
        createAlert("Figyelem, figyelem!",getResources().getString(R.string.service_code_disclaimer),getResources().getString(R.string.service_code_accept));
        Button sCodeButton = findViewById(R.id.ServiceCodeButton);
        final EditText sCodeEditText = findViewById(R.id.ServiceCodeEditText);
        sCodeEditText.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.v("OnKeyListener",String.valueOf(keyCode));
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    validation(sCodeEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        sCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sCodeStr = sCodeEditText.getText().toString();
                validation(sCodeStr);
            }
        });
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("onBackPressed","Back pressed");
    }

    private void validation(String sCodeStr){
        Regexp regexp = new Regexp(sCodeStr, VH_CONSTANTS.getServiceCodeValidator());
        if(!regexp.result){
            createAlert("Helytelen a szervízkód!","Én könnyíteni akarok, és te meg ellene vagy?\nPróbáld meg mégegyszer!","Megpróbálom");
        }
        else{
            createAlert("Fun fact:","Kim Jong-un is ezt használja rakétaindításhoz","¯\\_(ツ)_/¯");
        }
    }

    private void createAlert(String title, String message, String ok){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ServiceCode.this);
        alertBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertBuilder.setTitle(title);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        alertBuilder.setMessage(message);
        alertBuilder.show();
    }
}


