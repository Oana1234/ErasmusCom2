package com.oanabalaita.oana_maria.erasmuscom2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BunVenitActivity extends AppCompatActivity {


    //ADAPTOR

    public class MyViewPageAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPageAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    private ViewPager viewPager;
    private MyViewPageAdapter myViewPageAdapter;
    private LinearLayout puncteLayout;
    private TextView[] puncte;
    private int[] layouts;
    private Button butonSkip, butonNext;
    private PrimaLansareClass primaLansareClass;
    public static final int FROM_HTML_MODE_LEGACY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // verific daca e lansata pt prima data
        primaLansareClass = new PrimaLansareClass(this);
        if (!primaLansareClass.isFirstTimeLaunch()) {
            lanseazaHomeScreen();
            finish();
        }

        // bara de notificare transparenta
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_bun_venit);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        puncteLayout = (LinearLayout) findViewById(R.id.layoutDots);
        butonSkip = (Button) findViewById(R.id.btn_skip);
        butonNext = (Button) findViewById(R.id.btn_next);

        //  layout-urile slideurilor

        layouts = new int[]{
                R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3,
                R.layout.slide4,
                R.layout.slide5,
                R.layout.slide6
        };

        // adaugare puncte de jos
        addBottomDots(0);


        myViewPageAdapter = new MyViewPageAdapter();
        viewPager.setAdapter(myViewPageAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        butonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanseazaHomeScreen();
            }
        });

        butonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    lanseazaHomeScreen();

                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void addBottomDots(int currentPage) {
        puncte = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        puncteLayout.removeAllViews();
        for (int i = 0; i < puncte.length; i++) {
            puncte[i] = new TextView(this);

            if (android.os.Build.VERSION.SDK_INT >= 24) {
                puncte[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            } else {
                puncte[i].setText(Html.fromHtml("&#8226"));
            }

            puncte[i].setTextSize(35);
            puncte[i].setTextColor(colorsInactive[currentPage]);
            puncteLayout.addView(puncte[i]);

        }
        if (puncte.length > 0)
            puncte[currentPage].setTextColor(colorsActive[currentPage]);
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    private void lanseazaHomeScreen() {
        primaLansareClass.setFirstTimeLaunch(false);
        startActivity(new Intent(BunVenitActivity.this, PreMainActivity.class));
        finish();
    }

    // viewpage listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // schimabarea butonului next NEXT / GOT IT

            if (position == layouts.length - 1) {
                // ultima pagina face butonul text GOT IT
                butonNext.setText(getString(R.string.start));
                butonSkip.setVisibility(View.GONE);
            } else {
                // inca au ramas slideuri
                butonNext.setText(getString(R.string.start));
                butonSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }



    };
}
