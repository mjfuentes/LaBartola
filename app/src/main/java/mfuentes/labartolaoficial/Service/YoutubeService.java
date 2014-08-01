package mfuentes.labartolaoficial.Service;


import android.app.Fragment;
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

import mfuentes.labartolaoficial.Activity.Home;
import mfuentes.labartolaoficial.Model.YoutubeVideo;

public class YoutubeService extends AsyncTask {
    private Fragment context;
    private String uri;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            context = (Fragment) objects[1];
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
                //String id = videos.getJSONObject(i).getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t");
                String description = videos.getJSONObject(i).getJSONObject("title").getString("$t");
                String link = videos.getJSONObject(i).getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t");
                String image = videos.getJSONObject(i).getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(1).getString("url");
                YoutubeVideo video = new YoutubeVideo(description,link,image);
                this.publishProgress(video);
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

    @Override
    protected void onProgressUpdate(Object[] values) {
        ((Home.VideosFragment)context).addVideo((YoutubeVideo) values[0]);
    }
}
