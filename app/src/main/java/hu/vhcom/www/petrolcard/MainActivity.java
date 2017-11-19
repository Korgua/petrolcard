package hu.vhcom.www.petrolcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helpers = new Helpers(MainActivity.this);
        if(helpers.isOnline()){
            Toast.makeText(MainActivity.this, "There's an internet connection",Toast.LENGTH_SHORT).show();
        }
        if(helpers.CheckPetrolcard())
            Toast.makeText(MainActivity.this, "URL is alive",Toast.LENGTH_SHORT).show();

    }
}
