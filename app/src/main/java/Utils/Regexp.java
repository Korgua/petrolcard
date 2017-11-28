package Utils;

import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexp {

    public boolean result;

    public  Regexp(String string, String regexp){
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(string);
        if(m.matches()){
            Log.v("Regexp - match",string+", "+regexp);
            this.result = true;
        }
        else{
            Log.v("Regexp - not match",string+", "+regexp);
            this.result = false;
        }
    }
}
