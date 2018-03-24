package com.ansen.drawerlayoutdemo;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.drawerlayoutdemo.R;

public class MainActivity extends AppCompatActivity {


    private SettingsFragment mSettingsFragment;
    private MainFragment mMainFragment;
    private AboutFragment mAboutFragment;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();

        mMainFragment = new MainFragment();
        mSettingsFragment = new SettingsFragment();
        mAboutFragment = new AboutFragment();
        switchFragment(mMainFragment, false);

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);
                if (f instanceof MainFragment) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    toggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setTitle("DrawerLayoutDemo");
                } else if (f instanceof SettingsFragment) {
                    toggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle("设置");
                } else if (f instanceof AboutFragment) {
                    toggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle("关于");
                }
            }
        }, false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_settings: {
                        switchFragment(mSettingsFragment, true);
                        break;
                    }

                    case R.id.nav_about: {
                        switchFragment(mAboutFragment, true);
                        break;
                    }
                }
                return false;
            }
        });

        // 获取headerLayout点击事件
        View view = navigation.getHeaderView(0);
        view.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "imageView", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchFragment(Fragment fragment, boolean backStack) {
        drawer.closeDrawers();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.container, fragment).commitAllowingStateLoss();
    }
}
