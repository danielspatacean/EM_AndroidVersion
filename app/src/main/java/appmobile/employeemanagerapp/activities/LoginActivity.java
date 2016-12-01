package appmobile.employeemanagerapp.activities;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.tasks.LoginAsync;
import appmobile.employeemanagerapp.utils.Validator;

public class LoginActivity extends Activity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Validator validator;

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.validator = new Validator();
        if (Utils.LOGGED){
            Intent intent = new Intent(LoginActivity.this, UserProfile.class);
            finish();
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_login);
            editTextUserName = (EditText) findViewById(R.id.editTextUserName);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            editTextUserName.setHint("username");
            editTextPassword.setHint("password");
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
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        TextView textViewResult;
        switch (validator.ValidateUsernameAndPassword(username, password)){
            case EMPTY_FIELD:
                textViewResult = (TextView) findViewById(R.id.textViewErrors);
                textViewResult.setText("Please fill username and password");
                textViewResult.setTextColor(Color.RED);
                break;
            case OK:
                textViewResult = (TextView) findViewById(R.id.textViewErrors);
                textViewResult.setText("");
                textViewResult.clearComposingText();
                login(username,password);
                break;
            default:
        }

    }

    private void login(final String username, String password) {
        LoginAsync la = new LoginAsync(this);
        la.execute(username, password);
    }

    public void exit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
