package com.oanabalaita.oana_maria.erasmuscom2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oanabalaita.oana_maria.erasmuscom2.core.logout.LogoutContract;
import com.oanabalaita.oana_maria.erasmuscom2.core.logout.LogoutPresenter;
import com.oanabalaita.oana_maria.erasmuscom2.fragment.InfoFragment;
import com.oanabalaita.oana_maria.erasmuscom2.fragment.PostFragment;
import com.oanabalaita.oana_maria.erasmuscom2.fragment.SocialFragment;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.User;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.CommunityNewsActivity;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.PersonalProfileActivity;


public class MainActivity extends AppCompatActivity implements LogoutContract.View {


    private static final String LOG = "MainActivity";

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private static SharedPreferences pref;
    private LogoutPresenter mLogoutPresenter;
    static User user = new User();

    private static final String urlNavHeaderBg = "https://firebasestorage.googleapis.com/v0/b/erasmusapp-30e7b.appspot.com/o/pics%2Fada.png?alt=media&token=f65a99b1-52ee-43f1-b920-9f6cd3ac7ada";

    public static int navItemIndex = 0;
    private static final String TAG_INFO = "info";
    private static final String TAG_SOCIAL = "social";
    private static final String TAG_POST = "post";
    public static String CURRENT_TAG = TAG_INFO;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "YOU ARE SO CURIOUS", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_INFO;
            loadHomeFragment();
        }
        pref = this.getPreferences(0);

        mLogoutPresenter = new LogoutPresenter(this);

    }


    private void loadNavHeader() {

        txtName.setText("Erasmus Community");

        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments

                Fragment fragment = getInfoFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);

                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getInfoFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                InfoFragment infoFragment = new InfoFragment();
                return infoFragment;
            case 1:

                SocialFragment socialFragment = new SocialFragment();
                return socialFragment;
            case 2:

                PostFragment postFragment = new PostFragment();
                return postFragment;
            default:
                return new InfoFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_info:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_INFO;
                        break;
                    case R.id.nav_social:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_SOCIAL;
                        break;
                    case R.id.nav_post:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_POST;
                        break;

                    case R.id.nav_news:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, CommunityNewsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_ratings:
                        // launch new intent instead of loading fragment

                        Toast.makeText(MainActivity.this, "AVAILABLE IN VERSION 2.0. COMMING SOON!",Toast.LENGTH_LONG).show();
//
//                        startActivity(new Intent(MainActivity.this, RatingsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_tops:
                        // launch new intent instead of loading fragment

                        Toast.makeText(MainActivity.this, "AVAILABLE IN VERSION 2.0. COMMING SOON!",Toast.LENGTH_LONG).show();

//                        startActivity(new Intent(MainActivity.this, TopsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_INFO;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0 || navItemIndex == 1 || navItemIndex == 2) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_logout:
                logout2();

                return true;
            case R.id.action_help:
//                Intent helpActivity = new Intent(MainActivity.this, HelpActivity.class);
//                startActivity(helpActivity);

                Toast.makeText(MainActivity.this, "AVAILABLE IN VERSION 2.0. COMMING SOON!", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_privacy:
                Intent privacyActivity = new Intent(MainActivity.this, PrivacyActivity.class);
                startActivity(privacyActivity);
                return true;
            case R.id.action_settings:
                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                return true;
            case R.id.action_myprofile:
                Intent personalProfileActivity = new Intent(MainActivity.this, PersonalProfileActivity.class);
                startActivity(personalProfileActivity);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void logout2() {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putBoolean(Variabile.getIsLoggedIn(), false);
//                        editor.putString(Variabile.getEMAIL(), " ");
//                        editor.putString(Variabile.getSTATUS(), " ");
////                      editor.putString(Variabile.STATUS,"STUDENT");
//                        editor.putString(Variabile.getUniqueId(), "");
//                        editor.apply();

                        mLogoutPresenter.logout();
                   //     Log.w(LOG, " after logging out from firebasee");
//                        Intent i = new Intent(MainActivity.this, PreMainActivity.class);
//                        startActivity(i);

//                        Log.w(LOG, " after kill main Activity");
//                        MainActivity.this.finish();


                        dialog.dismiss();

                     //   Log.w(LOG, " after start premain Activity");
                      //  PreMainActivity.startIntent(getApplicationContext(), Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        alertDialog.show();


    }

    @Override
    public void onLogoutSuccess(String message) {
       // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();




        Intent intent = new Intent(getApplicationContext(), PreMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

       // Toast.makeText(MainActivity.this, "User logged out", Toast.LENGTH_SHORT).show();

        MainActivity.this.finish();
        //Toast.makeText(MainActivity.this, "User logged out", Toast.LENGTH_SHORT).show();

       // MainActivity.this.finish();
    }

    @Override
    public void onLogoutFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }


}
