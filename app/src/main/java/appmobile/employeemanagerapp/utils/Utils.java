package appmobile.employeemanagerapp.utils;

/**
 * Created by DJ DANY on 12/1/2016.
 */

public class Utils {
    public static String result = "";
    public static boolean LOGGED = false;
    public static String AppDetails = ("You can view all the employees from your company \n\n"+
                                        "Add new employees \n\n"+
                                        "Edit existing employees \n\n"+
                                        "Delete employees\n\n"+
                                        "Search employees\n\n"
    );

    public static void Login(){
        LOGGED = true;
    }
    public static void Logout(){
        LOGGED = false;
    }
}
