package hu.vhcom.www.petrolcard;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Utils.Messages;
import Utils.Regexp;
import Utils.VH_CONSTANTS;

public class ServiceCode extends AppCompatActivity {
    private Messages messages = new Messages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_code);
        createAlert(messages.getMES_TITLE_ATTENTION(),messages.getMES_DISCLAIMER(),messages.getMES_BTN_OK());
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
            createAlert(messages.getMES_TITLE_WRONG_INPUT(),messages.getMES_WRONG_INPUT(),messages.getMES_BTN_WRONG_INPUT());
        }
        else{
            createAlert(messages.getMES_TITLE_FUNFACT(),"Kim Jong-un is ez ta kódot használja rakétaindításhoz",messages.getMES_BTN_WHATEVER());
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


