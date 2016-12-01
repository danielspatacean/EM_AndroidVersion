package appmobile.employeemanagerapp.utils;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import appmobile.employeemanagerapp.activities.ListActivity;

public class GetDataJSON extends AsyncTask<String, Void, String> {
    public String myJSON;
    public ListActivity la;

    public GetDataJSON(ListActivity listActivity) {
        this.la = listActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("http://192.168.1.4/android/fetch.php");

        // Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
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
        setJsonResult(result);
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
