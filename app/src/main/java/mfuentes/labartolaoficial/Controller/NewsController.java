package mfuentes.labartolaoficial.Controller;

import java.util.ArrayList;
import java.util.List;

import mfuentes.labartolaoficial.saxrssreader.RssItem;

public class NewsController {

    private static NewsController instance;
    private List<RssItem> news;

    public List<RssItem> getNews() {
        if (news == null){
            news = new ArrayList<RssItem>();
        }
        return news;
    }

    public void setNews(List<RssItem> news) {
        this.news = news;
    }

    public static NewsController getInstance(){
        if (instance == null){
            instance = new NewsController();
        }
        return instance;
    }
}
