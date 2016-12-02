package appmobile.employeemanagerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.utils.AppState;
import appmobile.employeemanagerapp.utils.Connection;
import appmobile.employeemanagerapp.utils.Constants;

public class UserProfile extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        TextView textViewWelcome = (TextView) findViewById(R.id.textView3);
        textViewWelcome.setText(Constants.WelcomeMessage);

        TextView textViewDetails = (TextView) findViewById(R.id.detailsTextView);
        textViewDetails.setText(Constants.AppDetails);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),Constants.LogOutMessage, Toast.LENGTH_LONG).show();
    }

    public void viewPersons(View view) {
        if (Connection.isNetworkAvailable(this)) {
            Intent intent = new Intent(UserProfile.this, ListActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Connection.NoConnectionToast(this);
        }
    }

    public void addPerson(View view) {
        if (Connection.isNetworkAvailable(this)) {
            Intent intent = new Intent(UserProfile.this, AddPersonActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Connection.NoConnectionToast(this);
        }
    }

    public void logOut(View view) {
        AppState.Logout();
        Intent intent = new Intent(UserProfile.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
