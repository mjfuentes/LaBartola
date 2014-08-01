package mfuentes.labartolaoficial.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeIntents;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.Model.YoutubeVideo;

public class YoutubeVideosAdapter extends BaseAdapter {

    private List<YoutubeVideo> videos = new ArrayList<YoutubeVideo>();
    private Context context;
    public static String YOUTUBE_API_KEY = "AIzaSyDIAtyq-wtY1CiCv-tquxQb0fM2qbxZbIs";

    public YoutubeVideosAdapter(Context context){
        this.context = context;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout view = (RelativeLayout) inflater.inflate(context.getResources().getLayout(R.layout.video_layout), null);
        final ImageView image = (ImageView) view.findViewById(R.id.video_thumbnail);
        final TextView text = (TextView) view.findViewById(R.id.video_title);
        ImageLoader.getInstance().loadImage(videos.get(i).getImage(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View v, Bitmap loadedImage) {
                image.setImageBitmap(loadedImage);
                text.setText(videos.get(i).getDescription());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = YouTubeIntents.createPlayVideoIntent(context,videos.get(i).getLink());
                        context.startActivity(intent);
                    }
                });
            }
        });
        return view;
    }

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<YoutubeVideo> videos) {
        this.videos = videos;
    }
}
