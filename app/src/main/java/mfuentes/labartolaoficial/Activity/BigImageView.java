package mfuentes.labartolaoficial.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import mfuentes.labartolaoficial.Adapters.FullscreenImagesAdapter;
import mfuentes.labartolaoficial.R;

public class BigImageView extends Activity {

    private int currentImage;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        currentImage = getIntent().getIntExtra("currentImage", 0);

        FullscreenImagesAdapter adapter = new FullscreenImagesAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentImage);
    }
}
