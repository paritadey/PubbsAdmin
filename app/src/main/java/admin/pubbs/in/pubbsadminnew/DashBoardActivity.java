package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    SharedPreferences sharedPreferences;
    NavigationView navigationView;
    ImageView close;
    TextView allBicycleTv;
    EditText inputSearch;
    private String TAG = DashBoardActivity.class.getSimpleName();
    TextView phone_number, admin_type;
    String uphone, uadmin;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        allBicycleTv = findViewById(R.id.all_bicycle_tv);
        allBicycleTv.setTypeface(type);
        inputSearch = findViewById(R.id.input_search);
        inputSearch.setTypeface(type);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        close = hView.findViewById(R.id.close_drawer);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
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
                    .add(R.id.myFrame, new DashboardFragment())
                    .commitAllowingStateLoss();
            phone_number = hView.findViewById(R.id.phone_number);
            uphone = sharedPreferences.getString("adminmobile", "null");
            phone_number.setText(uphone);
            phone_number.setTypeface(type);
            admin_type = hView.findViewById(R.id.admin_type);
            uadmin = sharedPreferences.getString("admin_type", "null");
            admin_type.setTypeface(type);
            admin_type.setText(uadmin);
            if (uadmin.equals("Super Admin")) {
                check = true;
                navigationView.getMenu().findItem(R.id.manage_area).setTitle("Manage Admin/Employee");
                // navigationView.getMenu().setGroupVisible(R.id.gr_1, false);
                navigationView.getMenu().findItem(R.id.add_area).setTitle("Add New Admin");
                navigationView.getMenu().findItem(R.id.add_station).setTitle("Show all Admin");
                navigationView.getMenu().findItem(R.id.add_new_bicycle).setTitle("Edit Admin");
                navigationView.getMenu().findItem(R.id.edit_station).setTitle("Delete Admin");
                navigationView.getMenu().findItem(R.id.delete_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.lists).setTitle("View Panel");
                navigationView.getMenu().findItem(R.id.redistribution).setVisible(false);
                navigationView.getMenu().findItem(R.id.repair).setTitle("Show all Areas");
                navigationView.getMenu().findItem(R.id.recharge_battery).setTitle("Show all Stations");
                navigationView.getMenu().findItem(R.id.remove_bicycle).setVisible(false);
                navigationView.getMenu().findItem(R.id.manage_users).setTitle("Feedback History");
                navigationView.getMenu().findItem(R.id.my_users).setTitle("Feedback");
                navigationView.getMenu().findItem(R.id.support_user).setVisible(false);
                navigationView.getMenu().findItem(R.id.admin).setTitle("Subscription History");
                navigationView.getMenu().findItem(R.id.service).setTitle("Subscriptions");
                navigationView.getMenu().findItem(R.id.manage_operator).setVisible(false);
                navigationView.getMenu().findItem(R.id.contact_super_admin).setVisible(false);
            } else if (uadmin.equals("Employee")) {
                check = false;
                navigationView.getMenu().findItem(R.id.add_area).setVisible(false);
                navigationView.getMenu().findItem(R.id.edit_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.delete_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.admin).setTitle("Contact");
                navigationView.getMenu().findItem(R.id.service).setVisible(false);
                navigationView.getMenu().findItem(R.id.manage_operator).setVisible(false);
                navigationView.getMenu().findItem(R.id.contact_super_admin).setTitle("Contact Admin");
            } else if (uadmin.equals("Sub Admin")) {
                check = false;
            }

        } else {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            startActivity(new Intent(DashBoardActivity.this, SignInUp.class));
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
        switch (id) {
            case R.id.redistribution:
                if (check == true) {
                    // startActivity(new Intent(DashBoardActivity.this, ShowAllAreas.class));
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, Redistribution.class));
                    break;
                }
            case R.id.repair:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, ShowAllAreas.class));
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, Repair.class));
                    break;
                }
            case R.id.recharge_battery:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, ShowAllStations.class));
                    break;
                } else {
                    setMenuCounter(R.id.recharge_battery, 3);
                    startActivity(new Intent(DashBoardActivity.this, RechargeBattery.class));
                    break;
                }
            case R.id.add_new_bicycle:
                if (check == true) {
                    //edit Admin/Employee not yet done
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, AddNewBicycle.class));
                    break;
                }
            case R.id.remove_bicycle:
                startActivity(new Intent(DashBoardActivity.this, RemoveBicycle.class));
                break;
            case R.id.my_users:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, FeedBack.class));
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, MyUsers.class));
                    break;
                }
            case R.id.support_user:
                break;
            case R.id.add_area:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, AddOperator.class));
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, AddNewArea.class));
                    break;
                }
            case R.id.add_station:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, SuperAdminShowAdmins.class));
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, AddNewStation.class));
                    break;
                }
            case R.id.edit_station:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, DeleteOperatorArea.class));
                    break;
                } else {
                    //still not developed
                    break;
                }
            case R.id.delete_station:
                startActivity(new Intent(DashBoardActivity.this, DeleteStation.class));
                break;
            case R.id.service:
                if (check == true) {
                    startActivity(new Intent(DashBoardActivity.this, Subscription.class));
                    break;
                } else {
                    startActivity(new Intent(DashBoardActivity.this, StartStopService.class));
                    break;
                }
            case R.id.manage_operator:
                startActivity(new Intent(DashBoardActivity.this, ManageOperator.class));
                break;
            case R.id.contact_super_admin:
               /* Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "paritasampa95@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Prolem / Query about Pubbs Admin");
                intent.putExtra(Intent.EXTRA_TEXT, "Admin/Employee ID is: "+""+uphone+""+"of Admin type :"+""+uadmin);
                startActivity(intent);*/
               Intent intent = new Intent(DashBoardActivity.this, ContactSuperAdmin.class);
               intent.putExtra("uphone", uphone);
               intent.putExtra("uadmin", uadmin);
               startActivity(intent);
                break;
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void setMenuCounter(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

}
