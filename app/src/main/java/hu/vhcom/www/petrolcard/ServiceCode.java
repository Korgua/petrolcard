package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Utils.Messages;
import Utils.Regexp;
import Utils.VH_CONSTANTS;

public class ServiceCode extends AppCompatActivity {

    private Messages messages = new Messages();
    private AlertDialog.Builder alertBuilder = null;
    private static SharedPreferences sharedPreferences;

    private EditText sCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_code);

        sharedPreferences = getSharedPreferences(VH_CONSTANTS.getPrefsName(),MODE_PRIVATE);
        String sharedPreferencesServiceCode = sharedPreferences.getString(VH_CONSTANTS.getServiceCodeKey(),"0");
        if(!sharedPreferencesServiceCode.equals("0")){
            finish();
        }
        else {
            alertBuilder = new AlertDialog.Builder(ServiceCode.this);
            alertBuilder.setPositiveButton(messages.getMES_BTN_OK(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            createAlert(messages.getMES_TITLE_ATTENTION(), messages.getMES_DISCLAIMER());
            Button sCodeButton = findViewById(R.id.ServiceCodeButton);
            sCodeEditText = findViewById(R.id.ServiceCodeEditText);
            sCodeEditText.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
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
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("onBackPressed","Back pressed");
    }

    private void validation(String sCodeStr){
        Regexp regexp = new Regexp(sCodeStr, VH_CONSTANTS.getServiceCodeValidator());
        if(!regexp.result){
            alertBuilder = new AlertDialog.Builder(ServiceCode.this);
            alertBuilder.setPositiveButton(messages.getMES_BTN_WRONG_INPUT(), new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {}
            });
            createAlert(messages.getMES_TITLE_WRONG_INPUT(),messages.getMES_WRONG_INPUT());
        }
        else{
            alertBuilder = new AlertDialog.Builder(ServiceCode.this);
            alertBuilder.setPositiveButton(messages.getMES_BTN_WHATEVER(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //startActivity(new Intent(ServiceCode.this,init.class));
                    finish();
                }
            });
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            try{
                sharedPreferencesEditor.remove(VH_CONSTANTS.getServiceCodeKey());
                sharedPreferencesEditor.putString(VH_CONSTANTS.getServiceCodeKey(),sCodeStr);
                sharedPreferencesEditor.apply();
                createAlert(messages.getMES_TITLE_FUNFACT(),"Kim Jong-un is ez ta kódot használja rakétaindításhoz");

            }catch (Exception e){
                Toast.makeText(ServiceCode.this,messages.getTOAST_ERROR_SERVICE_CODE(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createAlert(String title, String message){
        alertBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertBuilder.setTitle(title);
        alertBuilder.setCancelable(false);
        alertBuilder.setMessage(message);
        alertBuilder.show();
    }
}


