package admin.pubbs.in.pubbsadminnew;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
/*created by Parita Dey*/

public class AreaSubscription extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AreaSubscription.class.getSimpleName();
    String areaId, areaName, adminmobile, subsName, subsTime, subsStartDate, subsEndDate, subsDesc, subsMoney;
    ImageView back;
    TextView subscriptionTv, subscription_tv;
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
    EditText subscriptionPlanName, timeLimit, descriptionPlan, money;
    TextView startDate, endDate;
    Button add_plan;
    private int mYear, mMonth, mDay;

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
        endDate.setOnClickListener(this);
        endDate.setTypeface(type1);
        money = findViewById(R.id.money);
        money.setTypeface(type1);
        descriptionPlan = findViewById(R.id.description_plan);
        descriptionPlan.setTypeface(type1);
        add_plan = findViewById(R.id.add_plan);
        add_plan.setTypeface(type2);
        add_plan.setOnClickListener(this);

        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        subscriptionTv = findViewById(R.id.subscription);
        subscriptionTv.setTypeface(type1);
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetRateChartTimeFragment().show(getSupportFragmentManager(), "dialog");

            }
        });
        subscription_tv = findViewById(R.id.subscription_tv);
        subscription_tv.setTypeface(type1);

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

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.end_date:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                endDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog1.show();
                break;
            case R.id.add_plan:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (subscriptionPlanName.getText().toString().isEmpty() || timeLimit.getText().toString().isEmpty()
                        || startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty() || money.getText().toString().isEmpty()
                        || descriptionPlan.getText().toString().isEmpty()) {
                    if (subscriptionPlanName.getText().toString().isEmpty() && timeLimit.getText().toString().isEmpty()
                            && startDate.getText().toString().isEmpty() && endDate.getText().toString().isEmpty() && money.getText().toString().isEmpty()
                            && descriptionPlan.getText().toString().isEmpty()) {
                        subscriptionPlanName.startAnimation(animShake);
                        timeLimit.startAnimation(animShake);
                        startDate.startAnimation(animShake);
                        endDate.startAnimation(animShake);
                        money.startAnimation(animShake);
                        descriptionPlan.startAnimation(animShake);
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
                    }
                }else{
                    subsName = subscriptionPlanName.getText().toString();
                    subsTime = timeLimit.getText().toString();
                    subsStartDate = startDate.getText().toString();
                    subsEndDate = endDate.getText().toString();
                    subsMoney = money.getText().toString();
                    subsDesc = descriptionPlan.getText().toString();
                    subscription_id = generateSubscriptionID();
                    addSubscriptionPlan(adminmobile, areaId, areaName, subscription_id, subsName, subsTime, subsStartDate, subsEndDate, subsMoney, subsDesc);
                }
                break;
            default:
                break;
        }

    }

    public void addSubscriptionPlan(String adminmobile, String areaId, String areaName, String subscription_id, String subsName, String subsTime,
                                    String subsStartDate, String subsEndDate, String subsMoney, String subsDesc) {
    }
}
