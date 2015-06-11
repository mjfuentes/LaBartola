package mfuentes.labartolaoficial.Service;


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

import mfuentes.labartolaoficial.Activity.Splash;
import mfuentes.labartolaoficial.Controller.YoutubeController;
import mfuentes.labartolaoficial.Model.YoutubeVideo;

public class YoutubeService extends AsyncTask {
    private String uri;
    private Splash activity;

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            activity = (Splash) objects[0];
            uri = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=PLedPwWBpjt7xX3LXAXq7gSWmveyqZwLWz&key=AIzaSyD0CZnavoTPmO1KTUvWK42ik6O0zNA9RLo";
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
            JSONArray videos = obj.getJSONArray("items");
            for (int i=0;i<videos.length();i++){
                //String id = videos.getJSONObject(i).getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t");
                String description = videos.getJSONObject(i).getJSONObject("snippet").getString("title");
                String link = videos.getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                String image = videos.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
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
        YoutubeController.getInstance().addVideo((YoutubeVideo) values[0]);
    }


    @Override
    protected void onPostExecute(Object o) {
        activity.finishedTask();
    }

}
