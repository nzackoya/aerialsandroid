package com.aerials.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.aerials.adapter.SideAdapter;
import com.aerials.fragments.WaveListFragment;

public class Aerials extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aerials);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.aerial));

        getSupportActionBar().setIcon(R.drawable.ic_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.abc_ic_ab_back_holo_dark,R.drawable.ic_drawer,R.drawable.ic_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        SideAdapter sideAdapter = new SideAdapter(this, R.layout.side_list_item);
        sideAdapter.add(WaveListFragment.class);

        mDrawerList.setAdapter(sideAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        setActionBarArrowDependingOnFragmentsBackStack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aerials, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    private void selectItem(int position) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) ((Class) mDrawerList.getAdapter().getItem(position)).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment).addToBackStack(null)
                .commit();

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm= getSupportFragmentManager();
                if(fm.getBackStackEntryCount()>0){
                    fm.popBackStackImmediate();
                } else if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                break;
            default:
                break;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void setActionBarArrowDependingOnFragmentsBackStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        boolean shouldEnableDrawerIndicator = backStackEntryCount == 0;
        mDrawerToggle.setDrawerIndicatorEnabled(shouldEnableDrawerIndicator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(!shouldEnableDrawerIndicator);
    }
}
