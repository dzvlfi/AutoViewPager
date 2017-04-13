package com.ristek.autoviewpager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ristek.autoviewpager.Fragment.FragmentA;
import com.ristek.autoviewpager.Fragment.FragmentB;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    int currentPage = 0;
    Timer timer;
    private CircleIndicator indicator;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    int NUM_PAGES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        setupViewPager(viewPager);

        //final Handler handler = new Handler();
        final Runnable Update = new Runnable() {

            public void run() {
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        Log.i("testviewpager", "onPageScrolled: "+position);
                        currentPage=position;
                    }

                    @Override
                    public void onPageSelected(int position) {
//                Log.i("testviewpager", "onPageSelected: "+position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
//                Log.i("testviewpager", "onPageScrollStateChanged: "+state);
                    }
                });
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                viewPager.post(Update);

            }
        }, DELAY_MS, PERIOD_MS);

    }

    private void setupViewPager(ViewPager viewPager) {
        CustomPageAdapter adapter = new CustomPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentA(),"Tab A");
        adapter.addFragment(new FragmentB(),"Tab B");
        adapter.addFragment(new FragmentA(),"Tab c");
        adapter.addFragment(new FragmentB(),"Tab d");
        adapter.addFragment(new FragmentA(),"Tab e");
        NUM_PAGES=5;
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }
}
