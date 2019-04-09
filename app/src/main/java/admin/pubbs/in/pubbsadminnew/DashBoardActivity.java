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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*created by Parita Dey*/

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    SharedPreferences sharedPreferences;
    NavigationView navigationView;
    ImageView close;
    TextView allBicycleTv;
    private String TAG = DashBoardActivity.class.getSimpleName();
    TextView phone_number, admin_type;
    String uphone, uadmin, area_id;
    int check;
    int manager, service, driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        allBicycleTv = findViewById(R.id.all_bicycle_tv);
        allBicycleTv.setTypeface(type);
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
            //getting the employee rank details and mobile_number, admin_type from sharedpreference
            area_id = sharedPreferences.getString("area_id", null);
            manager = sharedPreferences.getInt("manager", 0);
            service = sharedPreferences.getInt("service", 0);
            driver = sharedPreferences.getInt("driver", 0);
            Log.d(TAG, "Employee Authority:" + area_id + "\t" + manager + "\t" +  "\t" + service + "\t" + driver);

            phone_number = hView.findViewById(R.id.phone_number);
            uphone = sharedPreferences.getString("adminmobile", "null"); //uphone is the user_phone to store the mobile number of the user
            phone_number.setText(uphone);
            phone_number.setTypeface(type);
            admin_type = hView.findViewById(R.id.admin_type);
            uadmin = sharedPreferences.getString("admin_type", "null"); //uadmin is the admin type of the user who is using the app at the moment
            Log.d(TAG, "Admin Phone number and type:" + uphone + "\t" + uadmin);
            admin_type.setTypeface(type);
            admin_type.setText(uadmin);
            if (uadmin.equals("Super Admin")) { //if the admin_type of the user using the app is "Super Admin" then following options will be shown in the app
                //check = true;
                check = 1;
                navigationView.getMenu().findItem(R.id.manage_area).setTitle("Manage Admin/Employee");
                // navigationView.getMenu().findItem(R.id.add_area).setTitle("Add New Admin");
                navigationView.getMenu().findItem(R.id.area_subscription).setVisible(false);
                navigationView.getMenu().findItem(R.id.rate_chart).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_station).setTitle("Show all Sub-Admins");
                navigationView.getMenu().findItem(R.id.add_new_bicycle).setTitle("Edit Admin");
                navigationView.getMenu().findItem(R.id.edit_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.delete_station).setTitle("Add Lock to database");
                navigationView.getMenu().findItem(R.id.lists).setTitle("View Panel");
                navigationView.getMenu().findItem(R.id.redistribution).setVisible(false);
                navigationView.getMenu().findItem(R.id.repair).setTitle("Show all Areas");
                navigationView.getMenu().findItem(R.id.recharge_battery).setTitle("Show all Stations");
                navigationView.getMenu().findItem(R.id.remove_bicycle).setTitle("Show Report");
                navigationView.getMenu().findItem(R.id.manage_users).setTitle("Feedback History");
                navigationView.getMenu().findItem(R.id.my_users).setTitle("Feedback");
                navigationView.getMenu().findItem(R.id.support_user).setVisible(false);
                navigationView.getMenu().findItem(R.id.admin).setTitle("Subscription History");
                navigationView.getMenu().findItem(R.id.service).setTitle("Subscriptions");
                navigationView.getMenu().findItem(R.id.manage_operator).setVisible(false);
                navigationView.getMenu().findItem(R.id.contact_super_admin).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_legal).setTitle("Add New Admin");
                navigationView.getMenu().findItem(R.id.profile).setTitle("Profile");
                navigationView.getMenu().findItem(R.id.log_out).setTitle("Log Out");
            } else if (uadmin.equals("Employee") && manager == 1) {
                check = 2;
                navigationView.getMenu().findItem(R.id.rate_chart).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_subscription).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_legal).setVisible(false);
                navigationView.getMenu().findItem(R.id.lists).setVisible(true);
                navigationView.getMenu().findItem(R.id.add_area).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.edit_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.delete_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.admin).setTitle("Contact");
                navigationView.getMenu().findItem(R.id.service).setVisible(false);
                navigationView.getMenu().findItem(R.id.manage_operator).setVisible(false);
                navigationView.getMenu().findItem(R.id.contact_super_admin).setTitle("Contact Admin");
                navigationView.getMenu().findItem(R.id.profile).setTitle("Profile");
                navigationView.getMenu().findItem(R.id.log_out).setTitle("Log Out");
            } else if (uadmin.equals("Employee") && service == 1) {
                //if the admin_type of the user using the app is "Employee" then following options will be shown in the app
                // check = false;
                check = 2;
                //      Log.d(TAG, "Authority:" + manager + "\t"  + "\t" + service + "\t" + driver);
                navigationView.getMenu().findItem(R.id.manage_area).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_new_bicycle).setVisible(false);
                navigationView.getMenu().findItem(R.id.rate_chart).setVisible(false);
                navigationView.getMenu().findItem(R.id.live_track).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_subscription).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_legal).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_area).setVisible(false);
                navigationView.getMenu().findItem(R.id.recharge_battery).setVisible(false);
                navigationView.getMenu().findItem(R.id.remove_bicycle).setVisible(false);
                navigationView.getMenu().findItem(R.id.manage_users).setVisible(false);
                navigationView.getMenu().findItem(R.id.my_users).setVisible(false);
                navigationView.getMenu().findItem(R.id.support_user).setVisible(false);
                navigationView.getMenu().findItem(R.id.edit_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.delete_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.admin).setTitle("Contact");
                navigationView.getMenu().findItem(R.id.service).setVisible(false);
                navigationView.getMenu().findItem(R.id.manage_operator).setVisible(false);
                navigationView.getMenu().findItem(R.id.redistribution).setVisible(true);
                navigationView.getMenu().findItem(R.id.repair).setVisible(true);
                navigationView.getMenu().findItem(R.id.support_user).setVisible(true);
                navigationView.getMenu().findItem(R.id.contact_super_admin).setTitle("Contact Admin");
                navigationView.getMenu().findItem(R.id.logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.profile).setTitle("Profile");
                navigationView.getMenu().findItem(R.id.log_out).setTitle("Log Out");

            } else if (uadmin.equals("Employee") && driver == 1) {
                check = 2;
                navigationView.getMenu().findItem(R.id.manage_area).setVisible(true);
                navigationView.getMenu().findItem(R.id.add_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_new_bicycle).setVisible(false);
                navigationView.getMenu().findItem(R.id.rate_chart).setVisible(false);
                navigationView.getMenu().findItem(R.id.my_users).setVisible(false);
                navigationView.getMenu().findItem(R.id.edit_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.delete_station).setVisible(false);
                navigationView.getMenu().findItem(R.id.add_area).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_legal).setVisible(false);
                navigationView.getMenu().findItem(R.id.area_subscription).setVisible(false);
                navigationView.getMenu().findItem(R.id.manage_operator).setVisible(false);
                navigationView.getMenu().findItem(R.id.service).setVisible(false);
                navigationView.getMenu().findItem(R.id.repair).setVisible(false);
                navigationView.getMenu().findItem(R.id.redistribution).setVisible(false);
                navigationView.getMenu().findItem(R.id.live_track).setVisible(true);
                navigationView.getMenu().findItem(R.id.recharge_battery).setVisible(true);
                navigationView.getMenu().findItem(R.id.remove_bicycle).setVisible(true);
                navigationView.getMenu().findItem(R.id.support_user).setVisible(true);
                navigationView.getMenu().findItem(R.id.contact_super_admin).setTitle("Contact Admin");
                navigationView.getMenu().findItem(R.id.logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.profile).setTitle("Profile");
                navigationView.getMenu().findItem(R.id.log_out).setTitle("Log Out");

            } else if (uadmin.equals("Sub Admin")) { //if the admin_type of the user using the app is "Sub Admin" then all options
                // will be shown in the app which are present in the drawer menu
                // check = false;
                check = 3;
                navigationView.getMenu().findItem(R.id.edit_station).setVisible(false);
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
            /*Fragment f = getFragmentManager().findFragmentById(R.id.myFrame);
            if (f instanceof DashboardFragment) {
                 finish();
            } else {*/
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            //   super.onBackPressed();
               /* this.finish();
                System.exit(0);*/
        }
    }
    //}

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.area_subscription:
                if (check == 1) {
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, AllAreaSubscriptions.class));
                }
                break;
            case R.id.rate_chart:
                if (check == 1) {
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, AllAreaRateChart.class));
                }
                break;
            case R.id.profile:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, Settings.class));
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, Settings.class));
                }
                break;
            case R.id.area_legal:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, SuperAdminAddOperator.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, AreaLegal.class));
                    break;
                }
            case R.id.redistribution:
                if (check == 1) {
                    // startActivity(new Intent(DashBoardActivity.this, ShowAllAreas.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, Redistribution.class));
                    break;
                }
            case R.id.repair:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, ShowAllAreas.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, Repair.class));
                    break;
                }
            case R.id.recharge_battery:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, ShowAreaStations.class));
                    break;
                } else if (check == 2 || check == 3) {
                    setMenuCounter(R.id.recharge_battery, 3);
                    startActivity(new Intent(DashBoardActivity.this, RechargeBattery.class));
                    break;
                }
            case R.id.add_new_bicycle:
                if (check == 1) {
                    //edit Admin/Employee not yet done
                    startActivity(new Intent(DashBoardActivity.this, OperatorArea.class));
                    break;
                } else if (check == 2) {
                    startActivity(new Intent(DashBoardActivity.this, EmployeeAddNewBicycle.class));
                    break;
                } else if (check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, AddNewBicycle.class));
                    break;
                }
            case R.id.remove_bicycle:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, FinancialReport.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, RemoveBicycle.class));
                    break;
                }
            case R.id.my_users:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, FeedBack.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, MyUsers.class));
                    break;
                }
            case R.id.support_user:
                startActivity(new Intent(DashBoardActivity.this, SupportUsers.class));
                break;
            case R.id.add_area:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, AddNewArea.class));
                    //      startActivity(new Intent(DashBoardActivity.this, SuperAdminAddOperator.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, AddNewArea.class));
                    break;
                }
            case R.id.add_station:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, SuperAdminShowAdmins.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, AddNewStation.class));
                    break;
                }
            case R.id.edit_station:
                if (check == 1) {
                    //   startActivity(new Intent(DashBoardActivity.this, OperatorArea.class));
                    break;
                } else if (check == 2 || check == 3) {
                    //still not developed
                    break;
                }
            case R.id.delete_station:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, SubAdmin.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, DeleteStation.class));
                    break;
                }
            case R.id.service:
                if (check == 1) {
                    startActivity(new Intent(DashBoardActivity.this, AdminSubscription.class));
                    break;
                } else if (check == 2 || check == 3) {
                    startActivity(new Intent(DashBoardActivity.this, StartStopService.class));
                    break;
                }
            case R.id.manage_operator:
                startActivity(new Intent(DashBoardActivity.this, ManageOperator.class));
                break;
            case R.id.contact_super_admin:
                Intent intent = new Intent(DashBoardActivity.this, ContactSuperAdmin.class);
                intent.putExtra("uphone", uphone);
                intent.putExtra("uadmin", uadmin);
                startActivity(intent);
                break;
            case R.id.live_track:
                startActivity(new Intent(DashBoardActivity.this, LiveTrack.class));
                break;
            case R.id.log_out:
                sharedPreferences.edit().clear().commit();
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish();

                // this.finish();
                // System.exit(0);
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
/*Employee can see the screens according to the rank.Sub Admin will set the authority rank.
 * Manager : 1
 * Finance : 2
 * Service : 3
 * Driver : 4
 * Manager can see all the screens like- 1.Add New Station, 2.Add New Bicycle, 3.Live Track, 4.Redistribution, 5.Repair, 6.Recharge Battery, 7.Remove Bicycle, 8.My Users, 9.Support to Users, 10.Contact Admin, 11.Profile
 * Finance Employee can see only screens like- 1.Add Area Legal, 2. Add Area Subscription, 3.Rate Chart, 4.Contact Admin, 5.Profile
 * Driver Employee can see only screens like- 1.Live Track, 2.Recharge Battery, 3.Remove Bicycle, 4.Support Users, 5.Contact Admin, 6.Profile
 * Service Employee can see only screens like- 1.Redistribution, 2.Repair, 3.Contact Admin, 4.Profile*/