package mfuentes.labartolaoficial.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import mfuentes.labartolaoficial.Controller.NewsController;
import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.saxrssreader.RssItem;

public class NewsAdapter extends BaseAdapter{

    private Context context;

    public NewsAdapter(Context c){
        this.context = c;
    }

    @Override
    public int getCount() {
        return getNews().size();
    }

    @Override
    public Object getItem(int i) {
        return getNews().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout view = (FrameLayout) inflater.inflate(context.getResources().getLayout(R.layout.feed_layout), null);

        TextView title = (TextView) view.findViewById(R.id.feed_title);
        TextView description = (TextView) view.findViewById(R.id.feed_description);

        title.setText(NewsController.getInstance().getNews().get(i).getTitle());
        description.setText(Html.fromHtml(NewsController.getInstance().getNews().get(i).getDescription()));

        return view;
    }


    public List<RssItem> getNews(){
        return NewsController.getInstance().getNews();
    }
}
