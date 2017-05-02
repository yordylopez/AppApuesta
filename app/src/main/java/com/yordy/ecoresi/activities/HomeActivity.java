package com.yordy.ecoresi.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.yordy.ecoresi.R;
import com.yordy.ecoresi.fragments.NavBarFragment;
import com.yordy.ecoresi.fragments.ProfileFragment;
import com.yordy.ecoresi.interfaces.HomeInterface;

public class HomeActivity extends ActionBarActivity implements HomeInterface {
    private Toolbar mToolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Inicio");
        setSupportActionBar(mToolbar);
        initNavBar();
        setFragmentContet(ProfileFragment.newInstance());
    }

    private void initNavBar() {
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar, NavBarFragment.newInstance()).commit();
    }

    public void setFragmentContet(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
    }
}
