package mfuentes.labartolaoficial.Service;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import mfuentes.labartolaoficial.Activity.Splash;
import mfuentes.labartolaoficial.Controller.NewsController;
import mfuentes.labartolaoficial.saxrssreader.RssFeed;
import mfuentes.labartolaoficial.saxrssreader.RssItem;
import mfuentes.labartolaoficial.saxrssreader.RssReader;

public class NewsService extends AsyncTask {

    private Splash activity;

    @Override
    protected void onPostExecute(Object o) {
        activity.finishedTask();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        activity = (Splash) objects[0];
        try {
            URL url = new URL("http://labartolanoticias.blogspot.com.ar/feeds/posts/default?alt=rss");
            RssFeed feed = RssReader.read(url);
            ArrayList<RssItem> rssItems = feed.getRssItems();
            NewsController.getInstance().setNews(rssItems);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
