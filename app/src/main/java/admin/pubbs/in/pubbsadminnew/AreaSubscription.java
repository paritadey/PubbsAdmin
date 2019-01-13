package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/*created by Parita Dey*/

public class AreaSubscription extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    private String TAG = AreaSubscription.class.getSimpleName();
    String areaId, areaName, adminmobile, subsName, subsTime, subsStartDate, subsEndDate, subsDesc, subsMoney;
    int subsRideNo;
    ImageView back;
    TextView subscriptionTv;
    ImageView upArrow;
    TextView bottomsheetText;
    Button proceed;
    SharedPreferences sharedPreferences;
    ArrayList<String> subscription_plan_name = new ArrayList<String>();
    ArrayList<String> time_limit = new ArrayList<String>();
    ArrayList<String> launch_date = new ArrayList<String>();
    ArrayList<String> end_date = new ArrayList<String>();
    ArrayList<String> description_plan = new ArrayList<String>();
    ArrayList<String> amount_money = new ArrayList<String>();
    String subscription_id, launch_plan_date;
    EditText subscriptionPlanName, timeLimit, descriptionPlan, money, ride_number;
    TextView startDate, endDate;
    Button add_plan;
    private int mYear, mMonth, mDay;
    Date sdate, eNdDate;
    int limitDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_subscription);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        areaName = intent.getStringExtra("areaname");
        areaId = intent.getStringExtra("areaid");
        Log.d(TAG, "Area details:" + areaId + "--" + areaName);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Admin Mobile" + adminmobile);

        subscriptionPlanName = findViewById(R.id.subscription_plan_name);
        subscriptionPlanName.setTypeface(type1);
        timeLimit = findViewById(R.id.time_limit);
        timeLimit.setTypeface(type1);
        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(this);
        startDate.setTypeface(type1);
        endDate = findViewById(R.id.end_date);
        endDate.setTypeface(type1);
        money = findViewById(R.id.money);
        money.setTypeface(type1);
        descriptionPlan = findViewById(R.id.description_plan);
        descriptionPlan.setTypeface(type1);
        ride_number = findViewById(R.id.ride_number);
        ride_number.setTypeface(type1);
        add_plan = findViewById(R.id.add_plan);
        add_plan.setTypeface(type2);
        add_plan.setOnClickListener(this);

        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        subscriptionTv = findViewById(R.id.subscription);
        subscriptionTv.setTypeface(type1);
        // subscriptionTv.setOnClickListener(this);
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AreaSubscription.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public String generateSubscriptionID() {
        String subscription = "PUBBS_SUBSCRIPTION_ID";
        String subscriptionNo;
        int max = 9999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        subscriptionNo = subscription + randomNum;
        Log.d(TAG, "Subscription ID: " + subscriptionNo);
        return subscriptionNo;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(AreaSubscription.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.start_date:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                startDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
                                launch_plan_date = startDate.getText().toString();
                                limitDay = Integer.parseInt(timeLimit.getText().toString());
                                try {
                                    sdate = formatter2.parse(launch_plan_date);
                                    Log.d(TAG, "Terminated date in DATE format:" + sdate);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    eNdDate = cal.getTime();
                                    Log.d(TAG, "End Date :" + eNdDate);
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String strDate = dateFormat.format(eNdDate);
                                    Log.d(TAG, "String format of date:" + strDate);
                                    endDate.setText(strDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Error: " + e);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.up_arrow:
                new BottomSheetRateChartTimeFragment().show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.add_plan:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (subscriptionPlanName.getText().toString().isEmpty() || timeLimit.getText().toString().isEmpty()
                        || startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty() || money.getText().toString().isEmpty()
                        || descriptionPlan.getText().toString().isEmpty() || ride_number.getText().toString().isEmpty()) {
                    if (subscriptionPlanName.getText().toString().isEmpty() && timeLimit.getText().toString().isEmpty()
                            && startDate.getText().toString().isEmpty() && endDate.getText().toString().isEmpty() && money.getText().toString().isEmpty()
                            && descriptionPlan.getText().toString().isEmpty() && ride_number.getText().toString().isEmpty()) {
                        subscriptionPlanName.startAnimation(animShake);
                        timeLimit.startAnimation(animShake);
                        startDate.startAnimation(animShake);
                        endDate.startAnimation(animShake);
                        money.startAnimation(animShake);
                        descriptionPlan.startAnimation(animShake);
                        ride_number.startAnimation(animShake);
                    } else if (subscriptionPlanName.getText().toString().isEmpty()) {
                        subscriptionPlanName.startAnimation(animShake);
                    } else if (timeLimit.getText().toString().isEmpty()) {
                        timeLimit.startAnimation(animShake);
                    } else if (startDate.getText().toString().isEmpty()) {
                        startDate.startAnimation(animShake);
                    } else if (endDate.getText().toString().isEmpty()) {
                        endDate.startAnimation(animShake);
                    } else if (money.getText().toString().isEmpty()) {
                        money.startAnimation(animShake);
                    } else if (descriptionPlan.getText().toString().isEmpty()) {
                        descriptionPlan.startAnimation(animShake);
                    } else if (ride_number.getText().toString().isEmpty()) {
                        ride_number.startAnimation(animShake);
                    }
                } else {
                    subsName = subscriptionPlanName.getText().toString();
                    subsTime = timeLimit.getText().toString();
                    subsStartDate = startDate.getText().toString();
                    subsEndDate = endDate.getText().toString();
                    subsMoney = money.getText().toString();
                    subsDesc = descriptionPlan.getText().toString();
                    subsRideNo = Integer.parseInt(ride_number.getText().toString());
                    subscription_id = generateSubscriptionID();
                    //  addSubscriptionPlan(adminmobile, areaId, areaName, subscription_id, subsName, subsTime,
                    // subsStartDate, subsEndDate, subsMoney, subsDesc);
                    sendSubscriptionPlan(adminmobile, areaName, areaId, subsName, subsTime,
                            subsStartDate, subsEndDate, subsDesc, subsMoney, subscription_id, subsRideNo);

                }
                break;
           /* case R.id.subscription:
                sendSubscriptionPlan(adminmobile, areaName, areaId, subscription_plan_name, time_limit,
                        launch_date, end_date, description_plan, amount_money, subscription_id);
                break;*/
            default:
                break;
        }

    }

    public void sendSubscriptionPlan(String admin_mobile, String area_id, String area_name, String subscription_plan_name, String time_limit,
                                     String start_date, String end_date, String description, String money, String subscription_plan_id, int ride_number) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("method", "add_subscription_plan");
            jo.put("admin_mobile", admin_mobile);
            jo.put("area_id", area_id);
            jo.put("area_name", area_name);
            jo.put("subscription_plan_name", subscription_plan_name);
            jo.put("time_limit", time_limit);
            jo.put("start_date", start_date);
            jo.put("end_date", end_date);
            jo.put("description", description);
            jo.put("money", money);
            jo.put("subscription_plan_id", subscription_plan_id);
            jo.put("ride_number", ride_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AreaSubscription.this,
                getApplicationContext()).executeJsonRequest();

    }

    public void addSubscriptionPlan(String adminmobile, String areaId, String areaName, String subscription_id, String subsName, String subsTime,
                                    String subsStartDate, String subsEndDate, String subsMoney, String subsDesc) {
        Log.d(TAG, "Admin details for subscription:" + adminmobile + "\t" + areaId + "\t" + areaName + "\t" + subscription_id);
        subscription_plan_name.add(subsName);
        time_limit.add(subsTime);
        launch_date.add(subsStartDate);
        end_date.add(subsEndDate);
        amount_money.add(subsMoney);
        description_plan.add(subsDesc);
        for (int i = 0; i < subscription_plan_name.size(); i++) {
            Log.d(TAG, "Subscription Plan name: " + subscription_plan_name.get(i));
        }
        for (int i = 0; i < time_limit.size(); i++) {
            Log.d(TAG, "Subscription Time Limit: " + time_limit.get(i));
        }
        for (int i = 0; i < launch_date.size(); i++) {
            Log.d(TAG, "Subscription Launch Date: " + launch_date.get(i));
        }
        for (int i = 0; i < end_date.size(); i++) {
            Log.d(TAG, "Subscription End Date: " + end_date.get(i));
        }
        for (int i = 0; i < amount_money.size(); i++) {
            Log.d(TAG, "Subscription Money: " + amount_money.get(i));
        }
        for (int i = 0; i < description_plan.size(); i++) {
            Log.d(TAG, "Subscription Description: " + description_plan.get(i));
        }
        View view_layout = findViewById(R.id.manage_subscription_area);
        String message = "Subscription Plan is added.";
        int duration = Snackbar.LENGTH_SHORT;
        showSnackbar(view_layout, message, duration);
        clearFields();
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public void clearFields() {
        subscriptionPlanName.setText("");
        timeLimit.setText("");
        descriptionPlan.setText("");
        money.setText("");
        startDate.setText("");
        endDate.setText("");
        ride_number.setText("");
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("add_subscription_plan") && jsonObject.getBoolean("success")) {
                    showDialog("Subscriptional Plan is done against an area");
                } else {
                    showDialog("couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Problem !");
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
               /* Intent intent = new Intent(AreaSubscription.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
                clearFields();

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
