package mfuentes.labartolaoficial.Activity;

import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import mfuentes.labartolaoficial.Adapters.ImagesAdapter;
import mfuentes.labartolaoficial.FBImage;
import mfuentes.labartolaoficial.FacebookService;
import mfuentes.labartolaoficial.R;


public class Home extends Activity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

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
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
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
            }
            return null;
        }
    }


    public static class NoticiasFragment extends Fragment {
        public static NoticiasFragment newInstance() {
            NoticiasFragment fragment = new NoticiasFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public NoticiasFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }


    public static class FotosFragment extends Fragment {

        private ImagesAdapter adapter;

        public static FotosFragment newInstance() {
            FotosFragment fragment = new FotosFragment();
            return fragment;
        }

        public void setImages(FBImage[] images){
            adapter.setImages(images);
            adapter.notifyDataSetChanged();
        }

        public FotosFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_images, container, false);
            FacebookService service = new FacebookService();
            service.execute(FotosFragment.this);
            adapter = new ImagesAdapter(getActivity());
            ((GridView)rootView.findViewById(R.id.gridview)).setAdapter(adapter);
            return rootView;
        }
    }



    public static class VideosFragment extends Fragment {
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
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }


}
