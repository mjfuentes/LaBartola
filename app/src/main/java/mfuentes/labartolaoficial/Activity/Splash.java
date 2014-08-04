package mfuentes.labartolaoficial.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.ExecutionException;

import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.Service.ImageService;
import mfuentes.labartolaoficial.Service.NewsService;
import mfuentes.labartolaoficial.Service.YoutubeService;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    private int activeTasks = 0;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        ImageService facebook = new ImageService();
        facebook.execute(Splash.this); activeTasks++;
        YoutubeService youtube = new YoutubeService();
        youtube.execute(Splash.this);activeTasks++;
        NewsService news = new NewsService();
        news.execute(Splash.this);activeTasks++;
    }

    public void finishedTask(){
        activeTasks--;
        if (activeTasks==0){
            Intent mainIntent = new Intent(Splash.this,Home.class);
            Splash.this.startActivity(mainIntent);
            Splash.this.finish();
        }
    }
}
