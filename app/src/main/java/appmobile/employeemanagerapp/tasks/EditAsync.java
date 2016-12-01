package appmobile.employeemanagerapp.tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import appmobile.employeemanagerapp.activities.ListActivity;
import appmobile.employeemanagerapp.utils.Utils;
import appmobile.employeemanagerapp.utils.Connection;

public class EditAsync extends AsyncTask<String, Void, String> {

    private Dialog loadingDialog;
    private ListActivity la;

    public EditAsync(ListActivity listActivity) {
        this.la = listActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(la, "Please wait", "Loading...");
    }

    @Override
    protected String doInBackground(String... params) {
        String oldName = params[0];
        String oldPhone = params[1];
        String newName = params[2];
        String newPhone = params[3];
        String newAddress = params[4];


        InputStream is;
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(Utils.oldName, oldName));
        nameValuePairs.add(new BasicNameValuePair(Utils.oldPhone, oldPhone));
        nameValuePairs.add(new BasicNameValuePair(Utils.newName, newName));
        nameValuePairs.add(new BasicNameValuePair(Utils.newPhone, newPhone));
        nameValuePairs.add(new BasicNameValuePair(Utils.newAddress, newAddress));
        String result;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Connection.EditURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (ClientProtocolException e) {
            return e.getMessage().toString();
        } catch (UnsupportedEncodingException e) {
            return e.getMessage().toString();
        } catch (IOException e) {
            return e.getMessage().toString();
        }
        return Utils.success;
    }

    @Override
    protected void onPostExecute(String result) {
        String s = result.trim();
        loadingDialog.dismiss();
        if(s.equalsIgnoreCase(Utils.success)){
            Toast.makeText(la.getApplicationContext(), Utils.EditedMessage, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(la.getApplicationContext(), Utils.WrongErrorMessage, Toast.LENGTH_LONG).show();
        }
    }
}