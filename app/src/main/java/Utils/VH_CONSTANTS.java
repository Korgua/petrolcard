package Utils;

import java.util.regex.Pattern;

public class VH_CONSTANTS {

    private static final int PETROLCARD_PORT = 7621;
    private static final String PETROLCARD_BASE_URL = "www.petrolcard.hu";
    private static final String PETROLCARD_URL = "http://"+PETROLCARD_BASE_URL+":"+PETROLCARD_PORT+"/";
    private static final String PETROLCARD_NEW = PETROLCARD_URL+"new/";

    private static final String PETROLCARD_HTTP_REFERER = PETROLCARD_NEW+"index.php";
    private static final String PETROLCARD_AUTH = PETROLCARD_NEW+"authme.php";
    private static final String PETROLCARD_DATA = PETROLCARD_NEW + "data.php?sortby=company&ver=1.41&hash=";
    private static final String PETROLCARD_USERNAME = "vhcom";
    private static final String SERVICE_CODE_VALIDATOR = "^[0-9]{4}[a-zA-Z]{1}$";

    private static final String PREFS_NAME = "PrefsFile";
    private static final String SERVICE_CODE_KEY = "code";
    private static final String CHECK_INTERNET_VIA = "www.google.com";

    private static final int ANIMATION_DURATION = 1000;

    private static final String PARTNERLIST_FILE_NAME = "partnerlist.xml";

    public static String getPetrolcardHttpReferer(){return PETROLCARD_HTTP_REFERER;}
    public static String getPetrolcardBaseUrl()    {return PETROLCARD_BASE_URL;}
    public static String getPetrolcardUrl()        {return PETROLCARD_URL;}
    public static String getPetrolcardNew()        {return PETROLCARD_NEW;}
    public static String getPetrolcardAuth()       {return PETROLCARD_AUTH;}
    public static String getPetrolcardData()       {return PETROLCARD_DATA;}
    public static String getPetrolcardUsername()   {return PETROLCARD_USERNAME;}
    public static String getServiceCodeValidator() {return SERVICE_CODE_VALIDATOR;}
    public static String getServiceCodeKey()       {return SERVICE_CODE_KEY;}
    public static String getPrefsName()            {return PREFS_NAME;}
    public static int    getPetrolcardPort()       {return PETROLCARD_PORT;}
    public static String getCheckInternetVia()     {return CHECK_INTERNET_VIA;}
    public static int    getAnimationDuration()    {return ANIMATION_DURATION;}
    public static String getPartnerlistFileName()  {return PARTNERLIST_FILE_NAME;}

}
