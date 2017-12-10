package hu.vhcom.www.petrolcard;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class CodeCalculator {
    private static String INPUT_CODE;

    private static void setInputCode(String inputCode) {INPUT_CODE = inputCode;}

    private static void setPersonalCode(String personalCode) {PERSONAL_CODE = personalCode;}

    private static String PERSONAL_CODE;

    CodeCalculator(String inputCode, String personalCode) {
        setInputCode(inputCode);
        setPersonalCode(personalCode);
    }


    String Calc(){
        Date now = new Date();
        Log.v("date",now.toString());

        int temp;
        int firstNum,lastNum;
        int dayOfWeek,dayOfMonth,month;
        int  persCode_1st_char = PERSONAL_CODE.charAt(0)-'0',
                persCode_2nd_char = PERSONAL_CODE.charAt(1)-'0',
                persCode_3rd_char = PERSONAL_CODE.charAt(2)-'0',
                persCode_4th_char = PERSONAL_CODE.charAt(3)-'0';
        char persCode_5th_char = PERSONAL_CODE.charAt(4);
        firstNum = INPUT_CODE.charAt(0)-'0';
        lastNum = INPUT_CODE.charAt(4)-'0';



        dayOfWeek = Integer.parseInt(new SimpleDateFormat("u",Locale.getDefault()).format(now));
        dayOfMonth = Integer.parseInt(new SimpleDateFormat("d",Locale.getDefault()).format(now));
        month = Integer.parseInt(new SimpleDateFormat("MM",Locale.getDefault()).format(now))%10;

        StringBuilder sb = new StringBuilder();

        temp = (firstNum * lastNum + persCode_1st_char + dayOfWeek)%10;
        sb.append(Integer.toString(temp));
        temp += (month + persCode_2nd_char)%10;
        temp %= 10;
        sb.append(Integer.toString(temp));
        temp += (Math.floor(dayOfMonth/10) + persCode_3rd_char)%10;
        temp %= 10;
        sb.append(Integer.toString(temp));
        temp += (dayOfMonth%10 + persCode_4th_char)%10;
        temp %= 10;
        sb.append(Integer.toString(temp));
        sb.append(Character.toString(persCode_5th_char).toLowerCase());

        /*Log.v("dayOfWeek",Integer.toString(dayOfWeek));
        Log.v("dayOfMonth",Integer.toString(dayOfMonth));
        Log.v("month",Integer.toString(month));
        Log.v("OUTPUT_CODE",sb.toString());*/

        return sb.toString();
    }

}