package kr.ac.kumoh.railroApplication;

import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.fragments.BaseFragment;
import kr.ac.kumoh.railroApplication.fragments.DigitalFootprintFragment;
import kr.ac.kumoh.railroApplication.fragments.MyTripListFragment;
import kr.ac.kumoh.railroApplication.fragments.HomeFragment;
import kr.ac.kumoh.railroApplication.fragments.FloatingActionButtonFragment;
import kr.ac.kumoh.railroApplication.fragments.TripInfoFragment;
import kr.ac.kumoh.railroApplication.fragments.tabs.PlanListTabFragment;
import kr.ac.kumoh.railroApplication.util.LogUtils;
import kr.ac.kumoh.railroApplication.util.Navigator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.navigation_view)
    NavigationView mNavigationView;

    private static Navigator mNavigator;
    private Toolbar mToolbar;
    private @IdRes
    int mCurrentMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupToolbar();
        setupNavDrawer();
        initNavigator();
       // mCurrentMenuItem = R.id.standard_app_bar_menu_item;
        mCurrentMenuItem = R.id.toolbar_flexible_space_with_image;
       // setNewRootFragment(dddndardAppBarFragment.newInstance());
        setNewRootFragment(HomeFragment.newInstance());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initNavigator() {
        if (mNavigator != null) return;
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.container);
    }

    private void setNewRootFragment(BaseFragment fragment) {
        if (fragment.hasCustomToolbar()) {
            hideActionBar();
        } else {
            showActionBar();
        }
        mNavigator.setRootFragment(fragment);
        mDrawerLayout.closeDrawers();
    }

    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar == null) {
            //  LOGD(this, "Didn't find a toolbar");
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.hide();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.show();
    }

    private void setupNavDrawer() {
        if (mDrawerLayout == null) {
            LogUtils.LOGE(this, "mDrawerLayout is null - Can not setup the NavDrawer! Have you set the android.support.v7.widget.DrawerLayout?");
            return;
        }
        mNavigationView.setNavigationItemSelectedListener(this);
        //LOGD(this, "setup setupNavDrawer");
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        @IdRes int id = menuItem.getItemId();
        if (id == mCurrentMenuItem) {
            mDrawerLayout.closeDrawers();
            return false;
        }
        switch (id) {
            case R.id.home_menu_item:
                //TODO : Change Homefragment and FloatingButton Fragment(SJ)
                setNewRootFragment(HomeFragment.newInstance());
                break;

            case R.id.my_trip_menu_item:
                setNewRootFragment(MyTripListFragment.newInstance());
                break;

            case R.id.plan_list_menu_item:
                setNewRootFragment(PlanListTabFragment.newInstance());
                break;

            case R.id.plan_info_menu_item:
                setNewRootFragment(TripInfoFragment.newInstance());
                break;

            case R.id.digital_footprint_menu_item:
                setNewRootFragment(DigitalFootprintFragment.newInstance());
                break;

            case R.id.share_menu_item:
                setNewRootFragment(FloatingActionButtonFragment.newInstance());
                break;
        }
        mCurrentMenuItem = id;
        menuItem.setChecked(true);
        return false;
    }

    @Override
    public void finish() {
        mNavigator = null;
        super.finish();
    }
}
