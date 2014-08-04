package mfuentes.labartolaoficial.Activity;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import mfuentes.labartolaoficial.Adapters.ImagesAdapter;
import mfuentes.labartolaoficial.Adapters.NewsAdapter;
import mfuentes.labartolaoficial.Adapters.YoutubeVideosAdapter;
import mfuentes.labartolaoficial.Controller.YoutubeController;
import mfuentes.labartolaoficial.Model.FBImage;
import mfuentes.labartolaoficial.Service.ImageService;
import mfuentes.labartolaoficial.R;
import mfuentes.labartolaoficial.Model.YoutubeVideo;
import mfuentes.labartolaoficial.Service.YoutubeService;


public class Home extends Activity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);


    }

    public void goTwitter(MenuItem item){
        try
        {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.twitter.android", "com.twitter.android.ProfileActivity");
            intent.putExtra("user_id", 977331320L);
            startActivity(intent);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/labartolaLB")));
        }
    }

    public void goFacebook(MenuItem item){
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/235834106546657"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/LaBartolaOficial")));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NoticiasFragment.newInstance();
                case 1:
                    return FotosFragment.newInstance();
                case 2:
                    return VideosFragment.newInstance();
                case 3:
                    return InfoFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.Noticias).toUpperCase(l);
                case 1:
                    return getString(R.string.Fotos).toUpperCase(l);
                case 2:
                    return getString(R.string.Videos).toUpperCase(l);
                case 3:
                    return "INFO";
            }
            return null;
        }
    }


    public static class NoticiasFragment extends Fragment {
        private NewsAdapter adapter;
        public static NoticiasFragment newInstance() {
            NoticiasFragment fragment = new NoticiasFragment();

            return fragment;
        }

        public NoticiasFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_news, container, false);
            adapter = new NewsAdapter(this.getActivity());
            ListView list = (ListView) rootView.findViewById(R.id.newsList);
            list.setAdapter(adapter);
            return rootView;
        }
    }


    public static class FotosFragment extends Fragment {

        private ImagesAdapter adapter;

        public static FotosFragment newInstance() {
            FotosFragment fragment = new FotosFragment();
            return fragment;
        }

        public FotosFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_images, container, false);
            adapter = new ImagesAdapter(getActivity());
            ((GridView)rootView.findViewById(R.id.gridview)).setAdapter(adapter);
            return rootView;

        }
    }



    public static class VideosFragment extends Fragment implements Observer {
        private YoutubeVideosAdapter adapter;
        public static VideosFragment newInstance() {
            VideosFragment fragment = new VideosFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public VideosFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
            adapter = new YoutubeVideosAdapter(this.getActivity());
            ((ListView) rootView.findViewById(R.id.videosList)).setAdapter(adapter);
            return rootView;
        }

        @Override
        public void update(Observable observable, Object o) {
            adapter.notifyDataSetChanged();
        }
    }

    public static class InfoFragment extends Fragment {

        public static InfoFragment newInstance() {
            InfoFragment fragment = new InfoFragment();
            return fragment;
        }

        public InfoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info, container, false);
            return rootView;
        }


    }


}
