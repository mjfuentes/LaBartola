package mfuentes.labartolaoficial.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mfuentes.labartolaoficial.Activity.BigImageView;
import mfuentes.labartolaoficial.Controller.ImageController;
import mfuentes.labartolaoficial.Model.FBImage;
import mfuentes.labartolaoficial.R;

public class ImagesAdapter extends BaseAdapter implements Observer {

    private Context context;

    public ImagesAdapter(Context c){
        context = c;
        ImageController.getInstance().addObserver(this);
    }

    @Override
    public int getCount() {
        return getImages().size();
    }

    @Override
    public Object getItem(int i) {
        return getImages().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout view = (RelativeLayout) inflater.inflate(context.getResources().getLayout(R.layout.image_layout), null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewImage = new Intent(context,BigImageView.class);
                viewImage.putExtra("currentImage",i);
                context.startActivity(viewImage);
            }



        });
        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_image_small).cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoader.getInstance().loadImage(getImages().get(i).getIcon(),options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
            }
        });
        return view;
    }

    public List<FBImage> getImages(){
        return ImageController.getInstance().getImages();
    }

    @Override
    public void update(Observable observable, Object o) {
        this.notifyDataSetChanged();
    }
}
