package mfuentes.labartolaoficial.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.List;

import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.YoutubeVideo;

public class YoutubeVideosAdapter extends BaseAdapter {

    private List<YoutubeVideo> videos = new ArrayList<YoutubeVideo>();
    private Context context;
    public static String YOUTUBE_API_KEY = "AIzaSyDIAtyq-wtY1CiCv-tquxQb0fM2qbxZbIs";

    public YoutubeVideosAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int i) {
        return videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout view = (RelativeLayout) inflater.inflate(context.getResources().getLayout(R.layout.video_layout), null);
        YouTubeThumbnailView youtubeView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_view);
        final TextView text = (TextView) view.findViewById(R.id.videoTitle);
        youtubeView.initialize(YOUTUBE_API_KEY,new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(videos.get(i).getToken());
                text.setText(videos.get(i).getDescription());
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
        return view;
    }
}
