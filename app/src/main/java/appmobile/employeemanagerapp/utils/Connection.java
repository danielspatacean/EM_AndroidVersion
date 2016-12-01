package appmobile.employeemanagerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Connection URLs & helpers.
 */

public class Connection {
    public static final String InsertURL = "http://192.168.1.4/android/insert.php";
    public static final String EditURL = "http://192.168.1.4/android/edit.php";
    public static final String DeleteURL = "http://192.168.1.4/android/delete.php";

    public static final String LoginURL = "http://192.168.1.4/android/login.php";
    public static final String GetFilteredDataURL = "http://192.168.1.4/android/filter.php";
    public static final String GetDataURL ="http://192.168.1.4/android/fetch.php";

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void NoConnectionToast(Activity activity){
        Toast.makeText(activity.getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();
    }
}
