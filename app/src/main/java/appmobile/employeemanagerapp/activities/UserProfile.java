package appmobile.employeemanagerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import appmobile.employeemanagerapp.R;

public class UserProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        TextView textViewWelcome = (TextView) findViewById(R.id.textView3);
        textViewWelcome.setText("Welcome to Employee Manager");

        TextView textViewDetails = (TextView) findViewById(R.id.detailsTextView);
        textViewDetails.setText(Utils.AppDetails);
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
        Intent intent = new Intent(UserProfile.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void viewPersons(View view) {
        Intent intent = new Intent(UserProfile.this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    public void addPerson(View view) {
        Intent intent = new Intent(UserProfile.this, AddPersonActivity.class);
        startActivity(intent);
        finish();
    }

    public void logOut(View view) {
        Utils.Logout();
        Intent intent = new Intent(UserProfile.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
