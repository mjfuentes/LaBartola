package mfuentes.labartolaoficial.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        final FBImage image = getImages().get(position);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.fullscreen_image, container,
                false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.currentImage);
        TextView title = (TextView) viewLayout.findViewById(R.id.name);
        title.setText(image.getName());
        ImageView save = (ImageView) viewLayout.findViewById(R.id.save);
        ImageView share = (ImageView) viewLayout.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Link");
                share.putExtra(Intent.EXTRA_TEXT, image.getLink());
                context.startActivity(Intent.createChooser(share, "Share Image"));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveImage(((BitmapDrawable) imgDisplay.getDrawable()).getBitmap());
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoader.getInstance().displayImage(image.getSource(),imgDisplay,options);
        container.addView(viewLayout);
        return viewLayout;
    }

    void saveImage(Bitmap image) throws FileNotFoundException {
        image.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(Environment.getExternalStorageDirectory() + "NameOfFile.jpg"));
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
