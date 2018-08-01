package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    SharedPreferences sharedPreferences;
    NavigationView navigationView;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        close = hView.findViewById(R.id.close_drawer);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        getFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment f = getFragmentManager().findFragmentById(R.id.myFrame);
            if (f instanceof DashboardFragment) {
                setTitle(getString(R.string.app_name));
                toggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.syncState();
            } else {
                toggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toggle.setToolbarNavigationClickListener(v -> onBackPressed());
            }
        });
        if (sharedPreferences.contains("login")) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .add(R.id.myFrame, new DashboardFragment())
                    .commitAllowingStateLoss();
        } else {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .add(R.id.myFrame, new LoginFragment())
                    .commitAllowingStateLoss();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getFragmentManager().findFragmentById(R.id.myFrame);
            if (f instanceof DashboardFragment) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.redistribution) {

        } else if (id == R.id.repair) {

        } else if (id == R.id.recharge_battery) {
            setMenuCounter(R.id.recharge_battery, 3);
        } else if (id == R.id.add_new_bicycle) {
        } else if (id == R.id.remove_bicycle) {

        } else if (id == R.id.my_users) {

        } else if (id == R.id.support_user) {
            setMenuCounter(R.id.support_user, 3);
        } else if (id == R.id.add_station) {
            startActivity(new Intent(DashBoardActivity.this, AddNewStation.class));

        } else if (id == R.id.edit_station) {

        } else if (id == R.id.delete_station) {

        } else if (id == R.id.service) {

        } else if (id == R.id.manage_operator) {

        } else if (id == R.id.contact_super_admin) {

        }

       /* if (id == R.id.item1) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .replace(R.id.myFrame, new AreaFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else if (id == R.id.item2) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .replace(R.id.myFrame, new RateFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else if (id == R.id.item3) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .replace(R.id.myFrame, new TrackRideMap())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else if (id == R.id.item4) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .replace(R.id.myFrame, new ReportFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else if (id == R.id.item5) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .replace(R.id.myFrame, new TCFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setMenuCounter(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

}
