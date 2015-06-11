package mfuentes.labartolaoficial.Service;

import android.app.Fragment;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import mfuentes.labartolaoficial.Activity.Home;
import mfuentes.labartolaoficial.Activity.Splash;
import mfuentes.labartolaoficial.Controller.ImageController;
import mfuentes.labartolaoficial.Model.FBImage;

public class ImageService extends AsyncTask {

    private Splash activity;

    @Override
    protected void onPostExecute(Object o) {
        activity.finishedTask();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        ImageController.getInstance().addImage((FBImage) values[0]);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        activity = (Splash) params[0];
        try {
            String uri = "https://graph.facebook.com/242629215867146/photos?limit=400&access_token=";
            while (uri!= null){
                uri = this.getData(uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getData(String uri) throws IOException, JSONException {
        HttpClient client = new DefaultHttpClient();
        HttpGet authenticate = new HttpGet("https://graph.facebook.com/oauth/access_token?client_id=294912384049757&client_secret=848d1b24c0d2ebfcac902ccc58afac7b&grant_type=client_credentials");
        HttpResponse authenticationResponse = client.execute(authenticate);
        String token = responseParser(authenticationResponse).split("\\=")[1];
        token = URLEncoder.encode(token, "UTF-8");
        HttpClient client2 = new DefaultHttpClient();
        HttpGet get = new HttpGet(uri + token);
        HttpResponse response = client2.execute(get);
        JSONObject obj = new JSONObject(responseParser(response));
        JSONArray data = obj.getJSONArray("data");
        for (int i = 0;i<data.length();i++)
        {
            String name;
            try {
                name = data.getJSONObject(i).getString("name");
            }
            catch (JSONException e){
                name = " ";
             }
            FBImage image = new FBImage(data.getJSONObject(i).getString("picture"), data.getJSONObject(i).getString("source"),name,data.getJSONObject(i).getString("link"));
            this.publishProgress(image);
        }

        if ((obj.getJSONObject("paging")).getString("next") != null){
            return (obj.getJSONObject("paging")).getString("next");
        }
        return null;
    }

    private String responseParser(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder content = new StringBuilder();
        String line;
        while (null != (line = rd.readLine())) {
            content.append(line);
        }
        return content.toString();
    }
}
