package appmobile.employeemanagerapp.tasks;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import appmobile.employeemanagerapp.activities.ListActivity;
import appmobile.employeemanagerapp.utils.Connection;


public class GetFilterJSON extends AsyncTask<String, Void, String> {
    public String myJSON;
    public ListActivity la;
    public String text;

    public GetFilterJSON(ListActivity listActivity) {
        this.la = listActivity;
    }

    public void setText(CharSequence text){
        this.text = text.toString();
    }

    @Override
    protected String doInBackground(String... params) {


        // Depends on your web service
        //httppost.setHeader("Content-type", "application/json");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("text", text));

        InputStream inputStream = null;
        String result = null;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPpost = new HttpPost(Connection.GetFilteredDataURL);

            httpPpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httpPpost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        myJSON = result;
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        setJsonResult(result);
    }
    public String getJSON(){
        return myJSON;
    }
    public void setJsonResult(String result){
        myJSON = result;
    }
}
