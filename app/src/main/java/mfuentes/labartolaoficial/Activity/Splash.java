package mfuentes.labartolaoficial.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.Service.ImageService;
import mfuentes.labartolaoficial.Service.YoutubeService;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        ImageService facebook = new ImageService();
        facebook.execute();
        YoutubeService youtube = new YoutubeService();
        youtube.execute();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this,Home.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
