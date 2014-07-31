package mfuentes.labartolaoficial;

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

public class FacebookService extends AsyncTask {

    private Fragment context;

    @Override
    protected void onPostExecute(Object o) {
        ((Home.FotosFragment)context).setImages((FBImage[]) o);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            ArrayList<FBImage> images = new ArrayList<FBImage>();
            context = (Home.FotosFragment)params[0];
            String uri = "https://graph.facebook.com/242629215867146/photos?limit=400";
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
                images.add(new FBImage(data.getJSONObject(i).getString("picture"), data.getJSONObject(i).getString("source")));
            }
            return images.toArray(new FBImage[images.size()]);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
