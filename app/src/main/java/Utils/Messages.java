package Utils;
public class Messages {


    //Alert - Messages
    private static final String MES_DISCLAIMER = "A szervízkód csak a telefonban kerül mentésre!\nA mellékhatások tekintetében keresse fel jódszerészét, kérdezze meg vajákosát";
    private static final String MES_WRONG_INPUT = "Én könnyíteni akarok, és te meg ellene vagy?\nPróbáld meg mégegyszer!";

    public String getMES_DISCLAIMER() {return MES_DISCLAIMER;}
    public String getMES_WRONG_INPUT() {return MES_WRONG_INPUT;}

    //Alert - Buttons - Positive
    private static final String MES_BTN_OK = "Tudomásul vettem, de nem érdekel";
    private static final String MES_BTN_WHATEVER = "¯\\_(ツ)_/¯ Nekem mindegy";
    private static final String MES_BTN_WRONG_INPUT = "Namégegyszer";

    public String getMES_BTN_OK() {return MES_BTN_OK;}
    public String getMES_BTN_WHATEVER() {return MES_BTN_WHATEVER;}
    public String getMES_BTN_WRONG_INPUT() {return MES_BTN_WRONG_INPUT;}

    //Alert - Title
    private static final String MES_TITLE_ATTENTION = "Figyelem!";
    private static final String MES_TITLE_FUNFACT = "Fun Fact:";
    private static final String MES_TITLE_WRONG_INPUT = "Hát ezt elcseszted...";

    public String getMES_TITLE_ATTENTION() {return MES_TITLE_ATTENTION;}
    public String getMES_TITLE_WRONG_INPUT() {return MES_TITLE_WRONG_INPUT;}
    public String getMES_TITLE_FUNFACT() {return MES_TITLE_FUNFACT;}

    //Toast - Error
    private static final String TOAST_ERROR_SERVICE_CODE = "Nagy a baj, a szervízkód mentése sikertelen";
    public String getTOAST_ERROR_SERVICE_CODE() {return TOAST_ERROR_SERVICE_CODE;}

    //Toast - Restriction
    private static final String TOAST_RES_BACK = "Ki itt belépsz, hagyj fel minden reménnyel";
    public static String getTOAST_RES_BACK() {return TOAST_RES_BACK;}
}
