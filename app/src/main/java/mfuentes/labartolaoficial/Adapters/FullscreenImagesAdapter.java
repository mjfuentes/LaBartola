package mfuentes.labartolaoficial.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mfuentes.labartolaoficial.Controller.ImageController;
import mfuentes.labartolaoficial.Model.FBImage;
import mfuentes.labartolaoficial.R;

public class FullscreenImagesAdapter extends PagerAdapter implements Observer {

    private Activity context;
    private LayoutInflater inflater;

    public FullscreenImagesAdapter(Activity context) {
        this.context = context;
        ImageController.getInstance().addObserver(this);
    }

    @Override
    public int getCount() {
        return getImages().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imgDisplay;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.fullscreen_image, container,
                false);
        final ProgressBar loading = (ProgressBar) viewLayout.findViewById(R.id.progressBar);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.currentImage);
        ImageLoader.getInstance().loadImage(getImages().get(position).getSource(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imgDisplay.setImageBitmap(loadedImage);
                loading.setVisibility(View.GONE);
            }
        });
        container.addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

    private List<FBImage> getImages(){
        return ImageController.getInstance().getImages();
    }

    @Override
    public void update(Observable observable, Object o) {
        this.notifyDataSetChanged();
    }
}
