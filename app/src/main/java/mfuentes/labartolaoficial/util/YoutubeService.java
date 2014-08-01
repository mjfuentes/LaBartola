package mfuentes.labartolaoficial.util;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mfuentes.labartolaoficial.YoutubeVideo;

public class YoutubeService extends AsyncTask {
    private String uri;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            uri = (String) objects[0];
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
            JSONArray videos = obj.getJSONObject("feed").getJSONArray("entry");
            for (int i=0;i<videos.length();i++){
                String id = videos.getJSONObject(0).getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t");
                String title = videos.getJSONObject(0).getJSONObject("media$group").getJSONObject("media$title").getString("$t");
                YoutubeVideo video = new YoutubeVideo(id,title);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
