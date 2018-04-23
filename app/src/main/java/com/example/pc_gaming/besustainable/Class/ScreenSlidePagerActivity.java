package com.example.pc_gaming.besustainable.Class;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment1;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment10;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment2;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment3;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment4;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment5;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment6;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment7;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment8;
import com.example.pc_gaming.besustainable.Fragments.ScreenSlidePageFragment9;
import com.example.pc_gaming.besustainable.R;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 10;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // Tree of Switch/Case
            // Generate a Fragment per Slide (10 slides)

            switch (position){
                case 0:
                    return new ScreenSlidePageFragment1();
                case 1:
                    return new ScreenSlidePageFragment2();
                case 2:
                    return new ScreenSlidePageFragment3();
                case 3:
                    return new ScreenSlidePageFragment4();
                case 4:
                    return new ScreenSlidePageFragment5();
                case 5:
                    return new ScreenSlidePageFragment6();
                case 6:
                    return new ScreenSlidePageFragment7();
                case 7:
                    return new ScreenSlidePageFragment8();
                case 8:
                    return new ScreenSlidePageFragment9();
                case 9:
                    return new ScreenSlidePageFragment10();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}