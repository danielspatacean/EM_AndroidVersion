package appmobile.employeemanagerapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.tasks.DeleteAsync;
import appmobile.employeemanagerapp.tasks.EditAsync;
import appmobile.employeemanagerapp.tasks.GetDataJSON;
import appmobile.employeemanagerapp.tasks.GetFilterJSON;
import appmobile.employeemanagerapp.utils.Connection;
import appmobile.employeemanagerapp.utils.Constants;
import appmobile.employeemanagerapp.utils.Local;
import appmobile.employeemanagerapp.utils.Validator;

public class ListActivity extends ActionBarActivity {
    private String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_ADDRESS = "address";

    private Validator validator;
    private EditText editTextSearch;

    private AdapterView.OnItemClickListener myClickListener;
    private AdapterView.OnItemLongClickListener myLongClickListener;

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        validator = new Validator();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        list = (ListView) findViewById(R.id.listView);

        editTextSearch = (EditText) findViewById(R.id.searchEditText);
        editTextSearch.setHint(Constants.SearchHint);

        editTextSearch.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);

        if (Connection.isNetworkAvailable(this)){
            Local.UpdateFromFileForEdit(this);
            Local.UpdateFileForDelete(this);
            Local.ClearFile();
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if (Connection.isNetworkAvailable(ListActivity.this)) {
                    GetFilterJSON g = new GetFilterJSON(ListActivity.this);
                    g.setText(cs);
                    try {
                        g.execute().get();
                        myJSON = g.getJSON();
                        Local.list = myJSON;
                        personList.clear();
                        list.setAdapter(null);
                        showList();
                    } catch (InterruptedException | JSONException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Connection.NoConnectionToast(ListActivity.this);
                    myJSON = Local.list;
                    personList.clear();
                    list.setAdapter(null);
                    try {
                        showList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}
        });

        myClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout ll = (LinearLayout) view;

                String name = ((TextView) ll.findViewById(R.id.name)).getText().toString();
                String phone = ((TextView) ll.findViewById(R.id.phone)).getText().toString();
                String address = ((TextView) ll.findViewById(R.id.address)).getText().toString();

                EditDialog(name, phone, address);
            }
        };

        myLongClickListener = new AdapterView.OnItemLongClickListener(){


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout ll = (LinearLayout) view;

                String name = ((TextView) ll.findViewById(R.id.name)).getText().toString();
                String phone = ((TextView) ll.findViewById(R.id.phone)).getText().toString();
                String address = ((TextView) ll.findViewById(R.id.address)).getText().toString();

                DeleteDialog(name, phone, address);
                return true;
            }
        };

        list.setClickable(true);
        list.setOnItemClickListener(myClickListener);
        list.setOnItemLongClickListener(myLongClickListener);

        personList = new ArrayList<>();
        getDataAndShowList();
    }

    private void EditDialog(String nameS, String phoneS, String addressS) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(Constants.EmployeeInfoLabel);
        final List<EditText> dialogWidgets = new ArrayList<>();

        final String oldName = nameS;
        final String oldPhone = phoneS;

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameBox = new EditText(this);
        nameBox.setText(nameS);
        layout.addView(nameBox);
        dialogWidgets.add(nameBox);

        final EditText phoneBox = new EditText(this);
        phoneBox.setText(phoneS);
        layout.addView(phoneBox);
        dialogWidgets.add(phoneBox);

        final EditText addressBox = new EditText(this);
        addressBox.setText(addressS);
        layout.addView(addressBox);
        dialogWidgets.add(addressBox);
        alert.setView(layout);

        alert.setPositiveButton(Constants.EditLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String newName = nameBox.getText().toString();
                String newPhone = phoneBox.getText().toString();
                String newAddress = addressBox.getText().toString();

                switch (validator.ValidateNewEmployee(newName, newPhone, newAddress)){
                    case EMPTY_FIELD:
                        Toast.makeText(getApplicationContext(), Constants.EmptyFields, Toast.LENGTH_LONG).show();
                        break;
                    case INVALID_PHONE:
                        Toast.makeText(getApplicationContext(), Constants.IncorrectPhoneNr, Toast.LENGTH_LONG).show();
                        break;
                    case OK:
                        if (Connection.isNetworkAvailable(ListActivity.this)) {
                            EditUser(oldName, oldPhone, newName, newPhone, newAddress);
                        }
                        else{
                            Connection.NoConnectionToast(ListActivity.this);
                            Local.WriteToFileForEdit("EDIT", oldName, oldPhone, newName, newPhone, newAddress);
                        }
                        break;
                }

            }
        });

        alert.setNegativeButton(Constants.CancelLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();

            }
        });

        alert.setNeutralButton(Constants.CallEmployeeLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + oldPhone));
                startActivity(intent);
            }
        });

        alert.show();
    }

    private void EditUser(String oldName, String oldPhone, String newName, String newPhone, String newAddress) {
        EditAsync la = new EditAsync(this);
        try {
            la.execute(oldName, oldPhone, newName, newPhone, newAddress).get();
            refreshList();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void refreshList() throws JSONException {
        personList.clear();
        list.setAdapter(null);
        getDataAndShowList();
    }

    private void DeleteDialog(String name, String phone, String address) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(Constants.DeleteEmployeeLabel);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameBox = new EditText(this);
        nameBox.setText(name);
        nameBox.setKeyListener(null);
        nameBox.setClickable(false);
        layout.addView(nameBox);

        final EditText phoneBox = new EditText(this);
        phoneBox.setText(phone);
        phoneBox.setKeyListener(null);
        phoneBox.setClickable(false);
        layout.addView(phoneBox);

        final EditText addressBox = new EditText(this);
        addressBox.setText(address);
        addressBox.setKeyListener(null);
        addressBox.setClickable(false);
        layout.addView(addressBox);

        alert.setView(layout);

        alert.setPositiveButton(Constants.DeleteLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = nameBox.getText().toString();
                String phone = phoneBox.getText().toString();
                String address = addressBox.getText().toString();

                if (Connection.isNetworkAvailable(ListActivity.this)) {
                    DeleteUser(name, phone, address);
                }
                else{
                    Connection.NoConnectionToast(ListActivity.this);
                    Local.WriteToFile("DELETE", name, phone, address);
                }
            }
        });

        alert.setNegativeButton(Constants.CancelLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    private void DeleteUser(String name, String phone, String address) {
        DeleteAsync da = new DeleteAsync(this);
        try {
            da.execute(name, phone, address).get();
            refreshList();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void getDataAndShowList() {

        GetDataJSON g = new GetDataJSON(this);

        if (Connection.isNetworkAvailable(this)){
            try {
                g.execute().get();
                myJSON = g.getJSON();
                Local.SetList(myJSON);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try {
                showList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                myJSON = Local.list;
                showList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }

    private void showList() throws JSONException {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_NAME);
                String name = c.getString(TAG_PHONE);
                String address = c.getString(TAG_ADDRESS);

                HashMap<String, String> persons = new HashMap<>();

                persons.put(TAG_NAME, id);
                persons.put(TAG_PHONE, name);
                persons.put(TAG_ADDRESS, address);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ListActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_NAME,TAG_PHONE, TAG_ADDRESS},
                    new int[]{R.id.name, R.id.phone, R.id.address}
            );
            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        goBack(view);
    }

    public void goBack(View view) {
        Intent intent = new Intent(ListActivity.this, UserProfile.class);
        startActivity(intent);
        finish();
    }
}
