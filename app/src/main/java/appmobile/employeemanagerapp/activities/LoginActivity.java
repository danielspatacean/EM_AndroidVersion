package appmobile.employeemanagerapp.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.tasks.LoginAsync;
import appmobile.employeemanagerapp.utils.AppState;
import appmobile.employeemanagerapp.utils.Connection;
import appmobile.employeemanagerapp.utils.Constants;
import appmobile.employeemanagerapp.utils.Validator;

public class LoginActivity extends Activity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Validator validator;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.validator = new Validator();
        if (AppState.LOGGED){
            Intent intent = new Intent(LoginActivity.this, UserProfile.class);
            finish();
            startActivity(intent);

        }
        else {
            setContentView(R.layout.activity_login);
            editTextUserName = (EditText) findViewById(R.id.editTextUserName);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            editTextUserName.setHint(Constants.username);
            editTextPassword.setHint(Constants.password);
        }
    }
    @Override
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
        Log.d("CDA", "onBackPressed Called");
        finish();
    }

    public void invokeLogin(View view){
        if (Connection.isNetworkAvailable(this)) {
            username = editTextUserName.getText().toString();
            password = editTextPassword.getText().toString();

            switch (validator.ValidateUsernameAndPassword(username, password)) {
                case EMPTY_FIELD:
                    Toast.makeText(getApplicationContext(),Constants.EmptyLoginFields, Toast.LENGTH_LONG).show();
                    break;
                case OK:
                    login(username, password);
                    break;
                default:
            }
        }
        else{
            Connection.NoConnectionToast(this);
        }
    }

    private void login(final String username, String password) {
        LoginAsync la = new LoginAsync(this);
        la.execute(username, password);
    }

    public void exit(View view) {
        finish();
        super.onBackPressed();
    }
}
