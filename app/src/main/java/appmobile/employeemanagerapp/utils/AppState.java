package appmobile.employeemanagerapp.utils;

/**
 * The application state
 */

public class AppState {
    public static boolean LOGGED = false;
    public static void Login(){
        LOGGED = true;
    }
    public static void Logout(){
        LOGGED = false;
    }
}
