package rohan.darshan.amritha.abhi.hackindia;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by Ramesh on 7/18/2015.
 */
public class HotelDetails extends ActionBarActivity {


    MaterialViewPager mViewPager;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    RecyclerView recyclerView;
    String img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerAdapter());

        Toolbar toolbar = mViewPager.getToolbar();
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);

        }


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int i) {
                switch (i) {
                    case 0:
//                        return HeaderDesign.fromColorAndDrawable(R.color.blue,first);
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.accent_material_light,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");

                }
                return null;
            }
        });

        ViewPager viewPager = mViewPager.getViewPager();
        viewPager.setAdapter(new adap(getSupportFragmentManager()));

        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    class adap extends FragmentPagerAdapter {

        public adap(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new Desc();
        }

        @Override
        public int getCount() {
            return 1;
        }
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

        private String mNavTitles[] = {"amusement_park", "art_gallery", "bowling_alley", "cafe", "car_rental", "casino", "clothing_store", "food", "movie_theater", "night_club", "place_of_worship", "shopping_mall", "spa", "zoo"};
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_row, parent, false);
                Holder vhItem = new Holder(v, viewType);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = recyclerView.getChildPosition(v);
                        switch (position) {
                            //Intent
                        }
                    }
                });
                return vhItem;

            } else if (viewType == TYPE_HEADER) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);
                Holder vhHeader = new Holder(v, viewType);
                return vhHeader;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

            if (holder.Holderid == 1) {
                holder.textView.setText(mNavTitles[position - 1]);
            } else {

                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheOnDisc(true).cacheInMemory(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new SimpleBitmapDisplayer()).build();

                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                        getApplicationContext())
                        .defaultDisplayImageOptions(defaultOptions)
                        .memoryCache(new WeakMemoryCache())
                        .discCacheSize(100 * 1024 * 1024).build();
                ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(img_url, holder.imageView, defaultOptions);
            }


        }

        @Override
        public int getItemCount() {
            return mNavTitles.length + 1;
        }

        class Holder extends RecyclerView.ViewHolder {

            int Holderid;
            TextView textView;
            ImageView imageView;

            public Holder(View itemView, int ViewType) {
                super(itemView);

                if (ViewType == TYPE_ITEM) {
                    textView = (TextView) itemView.findViewById(R.id.rowText);
                    Holderid = 1;
                } else {
                    imageView = (ImageView) itemView.findViewById(R.id.picture);
                    Holderid = 0;
                }

            }

        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }


    }


}
