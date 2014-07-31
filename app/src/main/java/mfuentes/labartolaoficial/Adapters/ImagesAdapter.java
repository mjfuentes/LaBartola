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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import mfuentes.labartolaoficial.FBImage;
import mfuentes.labartolaoficial.R;

public class ImagesAdapter extends BaseAdapter{

    private FBImage[] images;
    private Context context;

    public ImagesAdapter(Context c){
        context = c;
    }

    @Override
    public int getCount() {
        if (images != null) {
            return images.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (images != null) {
            return images[i];
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        if (images != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout view = (RelativeLayout) inflater.inflate(context.getResources().getLayout(R.layout.image_layout), null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent viewImage = new Intent(context,ImageView.class);
                    viewImage.putExtra("currentImage",((FBImage)getItem(i)).getShareLink());
                    context.startActivity(viewImage);
                }



            });
            final ImageView imageView = (ImageView) view.findViewById(R.id.image);
            final ProgressBar bar = (ProgressBar) view.findViewById(R.id.progressBar);
            ImageLoader.getInstance().loadImage(images[i].getUri(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setImageBitmap(loadedImage);
                    bar.setVisibility(View.INVISIBLE);
                }
            });
            return view;
        }
        return null;
    }

    public void setImages(FBImage[] images){
        this.images = images;
    }
}
