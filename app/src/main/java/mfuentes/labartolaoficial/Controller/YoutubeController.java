package mfuentes.labartolaoficial.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import mfuentes.labartolaoficial.Model.YoutubeVideo;

public class YoutubeController extends Observable {
    private List<YoutubeVideo> videos;
    private static YoutubeController instance;

    public List<YoutubeVideo> getVideos() {
        if (videos == null){
            this.videos = new ArrayList<YoutubeVideo>();
        }
        return videos;
    }

    public void addVideo(YoutubeVideo video){
        this.getVideos().add(video);
        setChanged();
        this.notifyObservers();
    }

    public static YoutubeController getInstance(){
        if (instance == null){
            instance = new YoutubeController();
        }
        return instance;
    }

}
