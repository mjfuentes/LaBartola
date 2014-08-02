package mfuentes.labartolaoficial.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeIntents;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mfuentes.labartolaoficial.Controller.YoutubeController;
import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.Model.YoutubeVideo;

public class YoutubeVideosAdapter extends BaseAdapter implements Observer {

    private Context context;
    public static String YOUTUBE_API_KEY = "AIzaSyDIAtyq-wtY1CiCv-tquxQb0fM2qbxZbIs";

    public YoutubeVideosAdapter(Context context){
        this.context = context;
        YoutubeController.getInstance().addObserver(this);
    }

    @Override
    public int getCount() {
        return getVideos().size();
    }

    @Override
    public Object getItem(int i) {
        return getVideos().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        final YoutubeVideo item = getVideos().get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final FrameLayout view = (FrameLayout) inflater.inflate(context.getResources().getLayout(R.layout.youtube_video_list_item), null);
        final ImageView image = (ImageView) view.findViewById(R.id.video_thumbnail);
        final TextView text = (TextView) view.findViewById(R.id.video_title);
        final ImageView share = (ImageView) view.findViewById(R.id.video_share);
        text.setText(item.getDescription());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = YouTubeIntents.createPlayVideoIntent(context,item.getLink());
                context.startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" + item.getDescription() + "\" on YouTube");
                sendIntent.putExtra(Intent.EXTRA_TEXT, item.getLink());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
        ImageLoader.getInstance().loadImage(item.getImage(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View v, Bitmap loadedImage) {
                image.setImageBitmap(loadedImage);

            }
        });
        return view;
    }

    public List<YoutubeVideo> getVideos() {
        return YoutubeController.getInstance().getVideos();
    }

    @Override
    public void update(Observable observable, Object o) {
        this.notifyDataSetChanged();
    }
}
