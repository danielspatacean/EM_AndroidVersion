package appmobile.employeemanagerapp.tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import appmobile.employeemanagerapp.activities.LoginActivity;
import appmobile.employeemanagerapp.activities.UserProfile;
import appmobile.employeemanagerapp.utils.AppState;
import appmobile.employeemanagerapp.utils.Constants;
import appmobile.employeemanagerapp.utils.Connection;

public class LoginAsync extends AsyncTask<String, Void, String> {

    private Dialog loadingDialog;
    private LoginActivity la;

    public LoginAsync(LoginActivity loginActivity) {
        this.la=loginActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(la, "Please wait", "Loading...");
    }

    @Override
    protected String doInBackground(String... params) {
        String uname = params[0];
        String pass = params[1];

        InputStream is = null;
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(Constants.username, uname));
        nameValuePairs.add(new BasicNameValuePair(Constants.password, pass));
        String result = null;

        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Connection.LoginURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        String s = result.trim();
        loadingDialog.dismiss();
        if(s.equalsIgnoreCase(Constants.success)){
            AppState.Login();
            Intent intent = new Intent(la, UserProfile.class);
            la.finish();
            la.startActivity(intent);
        }else {
            Toast.makeText(la.getApplicationContext(), Constants.InvalidCredentialsMessage, Toast.LENGTH_LONG).show();
        }
    }
}