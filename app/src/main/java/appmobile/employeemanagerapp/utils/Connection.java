package appmobile.employeemanagerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Connection URLs & helpers.
 */

public class Connection {
    public static final String ServerURL = "ServerURL";
    public static final String InsertURL = ServerURL + "insert.php";
    public static final String EditURL = ServerURL + "edit.php";
    public static final String DeleteURL = ServerURL + "delete.php";
    public static final String LoginURL = ServerURL + "login.php";
    public static final String GetFilteredDataURL = ServerURL + "filter.php";
    public static final String GetDataURL = ServerURL + "fetch.php";

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void NoConnectionToast(Activity activity){
        Toast.makeText(activity.getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();
    }
}
