package com.yordy.ecoresi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.interfaces.HomeInterface;

public class NavBarFragment extends Fragment implements OnCheckedChangeListener {
    @InjectView(R.id.camera)
    ToggleButton camera;
    @InjectView(R.id.events)
    ToggleButton events;
    @InjectView(R.id.home)
    ToggleButton home;
    private HomeInterface listener;
    @InjectView(R.id.profile)
    ToggleButton profile;
    @InjectView(R.id.reports)
    ToggleButton reports;

    public static NavBarFragment newInstance() {
        return new NavBarFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navbar, container, false);
        ButterKnife.inject((Object) this, rootView);
        this.home.setOnCheckedChangeListener(this);
        this.reports.setOnCheckedChangeListener(this);
        this.camera.setOnCheckedChangeListener(this);
        this.events.setOnCheckedChangeListener(this);
        this.profile.setOnCheckedChangeListener(this);
        return rootView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (HomeInterface) activity;
    }

    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (compoundButton.getId() != this.camera.getId()) {
                disableOther(compoundButton);
            }
            switch (compoundButton.getId()) {
                case R.id.home:
                    this.listener.setFragmentContet(PostsFragment.newInstance());
                    return;
                case R.id.reports:
                    this.listener.setFragmentContet(ReportsFragment.newInstance());
                    return;
                case R.id.camera:
                    return;
                case R.id.events:
                    this.listener.setFragmentContet(EventsFragment.newInstance());
                    return;
                case R.id.profile:
                    this.listener.setFragmentContet(ProfileFragment.newInstance());
                    return;
                default:
                    return;
            }
        }
        check((ToggleButton) compoundButton);
    }

    private void disableOther(CompoundButton compoundButton) {
        if (this.home.getId() != compoundButton.getId()) {
            uncheck(this.home);
        }
        if (this.reports.getId() != compoundButton.getId()) {
            uncheck(this.reports);
        }
        if (this.camera.getId() != compoundButton.getId()) {
            uncheck(this.camera);
        }
        if (this.events.getId() != compoundButton.getId()) {
            uncheck(this.events);
        }
        if (this.profile.getId() != compoundButton.getId()) {
            uncheck(this.profile);
        }
    }

    private void uncheck(ToggleButton b) {
        b.setOnCheckedChangeListener(null);
        b.setChecked(false);
        b.setOnCheckedChangeListener(this);
    }

    private void check(ToggleButton b) {
        b.setOnCheckedChangeListener(null);
        b.setChecked(true);
        b.setOnCheckedChangeListener(this);
    }
}
