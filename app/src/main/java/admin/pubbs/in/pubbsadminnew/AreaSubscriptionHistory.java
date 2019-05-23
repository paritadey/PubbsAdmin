package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import admin.pubbs.in.pubbsadminnew.Adapter.AreaSubscriptionHistoryAdapter;
import admin.pubbs.in.pubbsadminnew.BottomSheet.CustomDivider;
import admin.pubbs.in.pubbsadminnew.BottomSheet.RecyclerTouchListener;
import admin.pubbs.in.pubbsadminnew.List.AdminSubscriptionHistoryList;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;
/*created by Parita Dey*/

public class AreaSubscriptionHistory extends AppCompatActivity implements AsyncResponse {
    ImageView back;
    private TextView admin_subscription;
    ProgressBar circularProgressbar;
    private RecyclerView recyclerView;
    private AreaSubscriptionHistoryAdapter areaSubscriptionHistoryAdapter;
    private List<AdminSubscriptionHistoryList> adminSubscriptionHistoryLists = new ArrayList<>();

    String adminmobile, area_id;
    private String TAG = AreaSubscriptionHistory.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_subscription_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        adminmobile = intent.getStringExtra("adminmobile");
        area_id = intent.getStringExtra("area_id");
        Log.d(TAG, "Admin Details: " + adminmobile + "-" + area_id);

        back = findViewById(R.id.back_button);
        admin_subscription = findViewById(R.id.admin_subscription);
        admin_subscription.setTypeface(type1);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        recyclerView = findViewById(R.id.recycler_view);
        areaSubscriptionHistoryAdapter = new AreaSubscriptionHistoryAdapter(adminSubscriptionHistoryLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(areaSubscriptionHistoryAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AdminSubscriptionHistoryList lists = adminSubscriptionHistoryLists.get(position);
                String subscription_name = lists.getSubscriptionPlanName();
                String subscription_start_date = lists.getStartDate();
                String subscription_end_date = lists.getEndDate();
                int subscription_ride_number = lists.getRideNumber();
                int subscription_ride_time = lists.getRideTime();
                int subscription_money = lists.getMoney();
                String subscription_description = lists.getDescription();
                int subscription_carryforward = lists.getCarryForward();
                if (subscription_carryforward == 1) {
                    String roll_over = "carry forward to next month";
                    showSubscriptionDetails(subscription_name, subscription_start_date, subscription_end_date, subscription_ride_number, subscription_ride_time,
                            subscription_money, subscription_description, roll_over);
                } else {
                    String roll_over = "not carry forward to next month";
                    showSubscriptionDetails(subscription_name, subscription_start_date, subscription_end_date, subscription_ride_number, subscription_ride_time,
                            subscription_money, subscription_description, roll_over);
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                AdminSubscriptionHistoryList lists = adminSubscriptionHistoryLists.get(position);
                String subscription_name = lists.getSubscriptionPlanName();
                String subscription_start_date = lists.getStartDate();
                String subscription_end_date = lists.getEndDate();
                int subscription_ride_number = lists.getRideNumber();
                int subscription_ride_time = lists.getRideTime();
                int subscription_money = lists.getMoney();
                String subscription_description = lists.getDescription();
                int subscription_carryforward = lists.getCarryForward();
                if (subscription_carryforward == 1) {
                    String roll_over = "carry forward to next month";
                    showSubscriptionDetails(subscription_name, subscription_start_date, subscription_end_date, subscription_ride_number, subscription_ride_time,
                            subscription_money, subscription_description, roll_over);
                } else {
                    String roll_over = "not carry forward to next month";
                    showSubscriptionDetails(subscription_name, subscription_start_date, subscription_end_date, subscription_ride_number, subscription_ride_time,
                            subscription_money, subscription_description, roll_over);
                }

            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AreaSubscriptionHistory.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "get_admin_area_details");
            jo.put("area_id", area_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AreaSubscriptionHistory.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        adminSubscriptionHistoryLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("get_admin_area_details") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            AdminSubscriptionHistoryList list = new AdminSubscriptionHistoryList(jo.getString("subscription_plan_name"),
                                    jo.getString("start_date"), jo.getString("end_date"), jo.getInt("ride_number"),
                                    jo.getInt("ride_mintues"), jo.getInt("money"), jo.getString("description"),
                                    jo.getInt("carry_forward"));
                            adminSubscriptionHistoryLists.add(list);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        areaSubscriptionHistoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Error !");
    }

    private void showDialog(String message) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        final TextView serverProblem = (TextView) dialogView.findViewById(R.id.server_problem);
        final TextView extraLine = (TextView) dialogView.findViewById(R.id.extra_line);
        extraLine.setTypeface(type1);
        serverProblem.setTypeface(type1);
        serverProblem.setText(message);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }
                Intent intent = new Intent(AreaSubscriptionHistory.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    private void showSubscriptionDetails(String name, String startdate, String enddate, int rideNumber, int rideTime, int rideMoney,
                                         String description, String carry) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_subscription_dialog, null);

        final TextView subscription_name = (TextView) dialogView.findViewById(R.id.subscription_name);
        final TextView start_date = (TextView) dialogView.findViewById(R.id.start_date);
        final TextView end_date = (TextView) dialogView.findViewById(R.id.end_date);
        final TextView ride_number = (TextView) dialogView.findViewById(R.id.ride_number);
        final TextView subscription_description = (TextView) dialogView.findViewById(R.id.description);
        final TextView carryforward = (TextView) dialogView.findViewById(R.id.carryforward);
        final TextView ride_time = (TextView) dialogView.findViewById(R.id.ride_time);
        final TextView ride_money = (TextView) dialogView.findViewById(R.id.ride_money);

        subscription_name.setTypeface(type2);
        subscription_name.setText("Subscription Name: " + name);
        start_date.setTypeface(type1);
        start_date.setText("Start Date:" + "\t" + startdate);
        end_date.setTypeface(type1);
        end_date.setText("End Date:" + "\t" + enddate);
        ride_number.setTypeface(type1);
        ride_number.setText("Amount of Rides:" + "\t" + String.valueOf(rideNumber));
        ride_time.setTypeface(type1);
        ride_time.setText("Ride Time:" + "\t" + String.valueOf(rideTime) + "\t" + "minutes");
        ride_money.setTypeface(type1);
        ride_money.setText("Amount of money:" + "\t" + String.valueOf(rideMoney) + "/-");
        subscription_description.setTypeface(type1);
        subscription_description.setText(description);
        carryforward.setTypeface(type1);
        carryforward.setText("The plan will " + carry);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
