package utils;

/**
 * Created by RVerma on 30-03-2017.
 */

public  class StaticDrugCode {

    private static String Drugcode;


    public static void set(String drugcode)
    {
        Drugcode=drugcode;

    }
    public static String get()
    {
        return Drugcode;

    }


}
