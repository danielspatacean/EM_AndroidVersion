package appmobile.employeemanagerapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.tasks.AddEmployeeAsync;
import appmobile.employeemanagerapp.utils.Validator;

public class AddPersonActivity extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void insert(View view){
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTExtAddress.getText().toString();

        TextView textViewResult;

        switch (validator.ValidateNewEmployee(name, phone, address)){
            case EMPTY_FIELD:
                textViewResult = (TextView) findViewById(R.id.textViewResult);
                textViewResult.setText("Please fill all the fields");
                textViewResult.setTextColor(Color.RED);
                break;
            case INVALID_PHONE:
                textViewResult = (TextView) findViewById(R.id.textViewResult);
                textViewResult.setText("Incorrect phone number");
                textViewResult.setTextColor(Color.RED);
                break;
            case OK:
                insertToDatabase(name, phone, address);
                break;
            default:
        }
    }

    public void insertToDatabase(String name, String phone, String address){
        AddEmployeeAsync sendPostReqAsyncTask = new AddEmployeeAsync(this);
        sendPostReqAsyncTask.execute(name, phone, address);
    }

    public void viewPersons(View view) {
        Intent intent = new Intent(AddPersonActivity.this, ListActivity.class);
        startActivity(intent);
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
