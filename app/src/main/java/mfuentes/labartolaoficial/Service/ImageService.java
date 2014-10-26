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
            String uri = "https://graph.facebook.com/242629215867146/photos?limit=400&access_token=CAAEMOKPkIl0BAHUxt0bm5eiZCUbVWFZC1OWr9D4U6Amn5oyUueCCqjcC9z2ZB6YliS0uI0Ijp5Q9uEGSCzalDOqGuVaUHw8EjxQFIfzUWSThr1nA45lHefVo4kDbRcB3UzEoodfrdOZBRGlEFRr4PoWTaIEoDsDUNSVjYokRw0ZAJmZCtrQmV8RXxyr2YDjDsBQRYos52g99RnVUGE4dly&expires=5183988";
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
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder content = new StringBuilder();
        String line;
        while (null != (line = rd.readLine())) {
            content.append(line);
        }
        JSONObject obj = new JSONObject(content.toString());
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
}
