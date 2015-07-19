package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
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
import android.widget.ScrollView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

/**
 * Created by Ramesh on 7/18/2015.
 */
public class HotelDetails extends ActionBarActivity {


    MaterialViewPager mViewPager;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    RecyclerView recyclerView;
    String image;
    ObservableScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        scrollView = (ObservableScrollView) findViewById(R.id.myScroll);
        Intent in = getIntent();
        image = in.getStringExtra(Map_1.IMAGE);
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
        MaterialViewPagerHelper.registerScrollView(HotelDetails.this,scrollView,null);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {


            @Override
            public HeaderDesign getHeaderDesign(int i) {
                switch (i) {
                    case 0:
//                        return HeaderDesign.fromColorAndDrawable(R.color.blue,first);

                        return HeaderDesign.fromColorAndUrl(R.color.colorPrimary, image);
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


}
