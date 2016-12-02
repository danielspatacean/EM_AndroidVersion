package appmobile.employeemanagerapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.tasks.AddEmployeeAsync;
import appmobile.employeemanagerapp.utils.Connection;
import appmobile.employeemanagerapp.utils.Constants;
import appmobile.employeemanagerapp.utils.Validator;

public class AddPersonActivity extends ActionBarActivity {

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTExtAddress;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validator = new Validator();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTExtAddress = (EditText) findViewById(R.id.editTextAddress);
    }

    public void insert(View view){
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTExtAddress.getText().toString();

        switch (validator.ValidateNewEmployee(name, phone, address)){
            case EMPTY_FIELD:
                Toast.makeText(getApplicationContext(), Constants.EmptyFields, Toast.LENGTH_LONG).show();
                break;
            case INVALID_PHONE:
                Toast.makeText(getApplicationContext(), Constants.IncorrectPhoneNr, Toast.LENGTH_LONG).show();
                break;
            case OK:
                if (Connection.isNetworkAvailable(this)) {
                    insertToDatabase(name, phone, address);
                }
                else{
                    Connection.NoConnectionToast(AddPersonActivity.this);
                }
                break;
            default:
        }
    }

    public void insertToDatabase(String name, String phone, String address){
        AddEmployeeAsync sendPostReqAsyncTask = new AddEmployeeAsync(this);
        sendPostReqAsyncTask.execute(name, phone, address);
    }

    public void viewPersons(View view) {
        if (Connection.isNetworkAvailable(this)) {
            Intent intent = new Intent(AddPersonActivity.this, ListActivity.class);
            startActivity(intent);
        }
        else{
            Connection.NoConnectionToast(this);
        }
    }

    public void goBackToUserPofile(View view) {
        Intent intent = new Intent(AddPersonActivity.this, UserProfile.class);
        startActivity(intent);
        finish();
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
        View view = findViewById(R.id.listView);
        goBackToUserPofile(view);
    }
}
