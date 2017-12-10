package hu.vhcom.www.petrolcard;

/*

public class Country {

    String name;
    String capital;
    String id;


    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


 */

public class Partners {
    private static String site;
    private static String client;
    public String id;
    public String company;
    public String guid;

    public String getId() {
        return id;
    }
    /*
    public static void setId(String id) {
        Partners.id = id;
    }*/



    public static String getSite() {
        return site;
    }

    public static void setSite(String site) {
        Partners.site = site;
    }

    public static String getClient() {
        return client;
    }

    public static void setClient(String client) {
        Partners.client = client;
    }


}
