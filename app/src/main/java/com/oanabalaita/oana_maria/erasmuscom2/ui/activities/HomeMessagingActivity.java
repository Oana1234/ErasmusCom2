package com.oanabalaita.oana_maria.erasmuscom2.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.oanabalaita.oana_maria.erasmuscom2.MainActivity;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.ui.fragments.ConversationsFragment;
import com.oanabalaita.oana_maria.erasmuscom2.ui.fragments.NotificationsFragment;
import com.oanabalaita.oana_maria.erasmuscom2.ui.fragments.PersonalProfileFragment;
import com.oanabalaita.oana_maria.erasmuscom2.ui.fragments.UsersFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeMessagingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;

    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_messaging,
            R.drawable.ic_error_24dp,
            R.drawable.friends

    };
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeMessagingActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, HomeMessagingActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_messaging);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ConversationsFragment(), "Conversations");
        adapter.addFrag(new NotificationsFragment(), "Notifications");
        adapter.addFrag(new UsersFragment(), "Users");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return mFragmentTitleList.get(position);

            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_listing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_gotomain:

                Intent intent = new Intent( HomeMessagingActivity.this, MainActivity.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startPersonalProfileActivity() {
        Intent personalProfileActivity=new Intent(HomeMessagingActivity.this,PersonalProfileFragment.class);
        startActivity(personalProfileActivity);
    }



}
