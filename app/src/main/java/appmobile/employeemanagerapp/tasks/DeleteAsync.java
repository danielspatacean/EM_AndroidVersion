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
import appmobile.employeemanagerapp.utils.Connection;
import appmobile.employeemanagerapp.utils.Constants;

public class DeleteAsync extends AsyncTask<String, Void, String> {
    private Dialog loadingDialog;
    private ListActivity la;

    public DeleteAsync(ListActivity listActivity) {
        this.la = listActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String phone = params[1];
        String address = params[2];

        InputStream is;
        String result = Constants.EMPTY;

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(Constants.name, name));
        nameValuePairs.add(new BasicNameValuePair(Constants.phone, phone));
        nameValuePairs.add(new BasicNameValuePair(Constants.address, address));

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Connection.DeleteURL);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (ClientProtocolException e) {
            return e.getMessage().toString();
        } catch (UnsupportedEncodingException e) {
            return e.getMessage().toString();
        } catch (IOException e) {
            return e.getMessage().toString();
        }
        return result;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(la, Constants.WaitMessage, Constants.LoadingMessage);
    }

    @Override
    protected void onPostExecute(String result) {
        String s = result.trim();
        loadingDialog.dismiss();
        if(s.equalsIgnoreCase(Constants.success)){
            Toast.makeText(la.getApplicationContext(), Constants.DeletedMessage, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(la.getApplicationContext(), Constants.WrongErrorMessage, Toast.LENGTH_LONG).show();
        }
    }
}