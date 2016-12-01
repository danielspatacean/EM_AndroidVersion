package appmobile.employeemanagerapp.tasks;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import appmobile.employeemanagerapp.activities.AddPersonActivity;
import appmobile.employeemanagerapp.R;
import appmobile.employeemanagerapp.utils.Connection;

public class AddEmployeeAsync extends AsyncTask<String, Void, String> {
    private AddPersonActivity apa;

    public AddEmployeeAsync(AddPersonActivity addPersonActivity) {
        this.apa = addPersonActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String paramUsername = params[0];
        String paramPhone = params[1];
        String paramAddress = params[2];

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("name", paramUsername));
        nameValuePairs.add(new BasicNameValuePair("phone", paramPhone));
        nameValuePairs.add(new BasicNameValuePair("address", paramAddress));

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Connection.InsertURL);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            System.out.println(entity.getContentType());

        } catch (ClientProtocolException e) {
            return e.getMessage();
        }
        catch (IOException e) {
            return e.getMessage();
        }
        return "success";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Toast.makeText(apa.getApplicationContext(), result, Toast.LENGTH_LONG).show();

        TextView textViewResult = (TextView) apa.findViewById(R.id.textViewResult);
        textViewResult.setText("User inserted!");
        textViewResult.setTextColor(Color.BLUE);
    }
}