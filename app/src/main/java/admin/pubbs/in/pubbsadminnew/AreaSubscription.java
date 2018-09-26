package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/*created by Parita Dey*/

public class AreaSubscription extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AreaSubscription.class.getSimpleName();
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    String areaNumber, area_Name, adminMobile;
    TextView subscription_tv, subscription_basic_tv, basic_plan_month_tv, basic_plan_mins_tv;
    EditText basic_plan_amount, basic_plan_month, basic_plan_mins;
    TextView subscription_standard_tv, standard_plan_month_tv, standard_plan_mins_tv;
    EditText standard_plan_amount, standard_plan_month, standard_plan_mins;
    TextView subscription_sweet_tv, sweet_plan_month_tv, sweet_plan_mins_tv;
    EditText sweet_plan_amount, sweet_plan_month, sweet_plan_mins;
    TextView subscription_premium_tv, premium_plan_month_tv, premium_plan_mins_tv;
    EditText premium_plan_amount, premium_plan_month, premium_plan_mins;
    String basicPlanAmount, basicPlanMonth, basicPlanMins, standardPlanAmount, standardPlanMonth, standardPlanMins;
    String sweetPlanAmount, sweetPlanMonth, sweetPlanMins, premiumPlanAmount, premiumPlanMonth, premiumPlanMins;
    private String rupee1, rupee2, rupee3, rupee4, rupee5;
    private String numberPicker1, numberPicker2, numberPicker3, numberPicker4, numberPicker5;
    ImageView back;
    TextView subscriptionTv;
    ImageView upArrow;
    TextView bottomsheetText;
    Button proceed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_subscription);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Intent intent = getIntent();
        markerList = intent.getParcelableArrayListExtra("markerList");
        areaNumber = intent.getStringExtra("areaNumber");
        area_Name = intent.getStringExtra("area_Name");
        adminMobile = intent.getStringExtra("adminMobile");
        numberPicker1 = intent.getStringExtra("numberPicker1");
        rupee1 = intent.getStringExtra("rupee1");
        numberPicker2 = intent.getStringExtra("numberPicker2");
        rupee2 = intent.getStringExtra("rupee2");
        numberPicker3 = intent.getStringExtra("numberPicker3");
        rupee3 = intent.getStringExtra("rupee3");
        numberPicker4 = intent.getStringExtra("numberPicker4");
        rupee4 = intent.getStringExtra("rupee4");
        numberPicker5 = intent.getStringExtra("numberPicker5");
        rupee5 = intent.getStringExtra("rupee5");
        Log.d(TAG, "Data from Rate Chart:" + markerList + "\t" + areaNumber + "\t" + area_Name + "\t" + adminMobile);
        Log.d(TAG, "Data from Number picker and rupees:" + numberPicker1 + ":" + rupee1 + "\t" + numberPicker2 +
                ":" + rupee2 + "\t" + numberPicker3 + ":" + rupee3 + "\t" + numberPicker4 + ":" + rupee4 + "\t" + numberPicker5 + ":" + rupee5);

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
        subscription_basic_tv = findViewById(R.id.subscription_basic_tv);
        subscription_basic_tv.setTypeface(type1);

        basic_plan_amount = findViewById(R.id.basic_plan_amount);
        basic_plan_amount.setTypeface(type1);
        basic_plan_month_tv = findViewById(R.id.basic_plan_month_tv);
        basic_plan_month_tv.setTypeface(type1);
        basic_plan_month = findViewById(R.id.basic_plan_month);
        basic_plan_month.setTypeface(type1);
        basic_plan_mins_tv = findViewById(R.id.basic_plan_mins_tv);
        basic_plan_mins_tv.setTypeface(type1);
        basic_plan_mins = findViewById(R.id.basic_plan_mins);
        basic_plan_mins.setTypeface(type1);

        subscription_standard_tv = findViewById(R.id.subscription_standard_tv);
        subscription_standard_tv.setTypeface(type1);
        standard_plan_amount = findViewById(R.id.standard_plan_amount);
        standard_plan_amount.setTypeface(type1);
        standard_plan_month_tv = findViewById(R.id.standard_plan_month_tv);
        standard_plan_month_tv.setTypeface(type1);
        standard_plan_month = findViewById(R.id.standard_plan_month);
        standard_plan_month.setTypeface(type1);
        standard_plan_mins_tv = findViewById(R.id.standard_plan_mins_tv);
        standard_plan_mins_tv.setTypeface(type1);
        standard_plan_mins = findViewById(R.id.standard_plan_mins);
        standard_plan_mins.setTypeface(type1);

        subscription_sweet_tv = findViewById(R.id.subscription_sweet_tv);
        subscription_sweet_tv.setTypeface(type1);
        sweet_plan_amount = findViewById(R.id.sweet_plan_amount);
        sweet_plan_amount.setTypeface(type1);
        sweet_plan_month_tv = findViewById(R.id.sweet_plan_month_tv);
        sweet_plan_month_tv.setTypeface(type1);
        sweet_plan_month = findViewById(R.id.sweet_plan_month);
        sweet_plan_month.setTypeface(type1);
        sweet_plan_mins_tv = findViewById(R.id.sweet_plan_mins_tv);
        sweet_plan_mins_tv.setTypeface(type1);
        sweet_plan_mins = findViewById(R.id.sweet_plan_mins);
        sweet_plan_mins.setTypeface(type1);

        subscription_premium_tv = findViewById(R.id.subscription_premium_tv);
        subscription_premium_tv.setTypeface(type1);
        premium_plan_amount = findViewById(R.id.premium_plan_amount);
        premium_plan_amount.setTypeface(type1);
        premium_plan_month_tv = findViewById(R.id.premium_plan_month_tv);
        premium_plan_month_tv.setTypeface(type1);
        premium_plan_month = findViewById(R.id.premium_plan_month);
        premium_plan_month.setTypeface(type1);
        premium_plan_mins_tv = findViewById(R.id.premium_plan_mins_tv);
        premium_plan_mins_tv.setTypeface(type1);
        premium_plan_mins = findViewById(R.id.premium_plan_mins);
        premium_plan_mins.setTypeface(type1);

        proceed = findViewById(R.id.next);
        proceed.setTypeface(type2);
        proceed.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AreaSubscription.this, AddNewArea.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(AreaSubscription.this, AddNewArea.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.next:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (basic_plan_amount.getText().toString().isEmpty() || basic_plan_month.getText().toString().isEmpty() || basic_plan_mins.getText().toString().isEmpty()
                        || standard_plan_amount.getText().toString().isEmpty() || standard_plan_month.getText().toString().isEmpty() || standard_plan_mins.getText().toString().isEmpty()
                        || standard_plan_amount.getText().toString().isEmpty() || sweet_plan_month.getText().toString().isEmpty() || sweet_plan_mins.getText().toString().isEmpty()
                        || premium_plan_amount.getText().toString().isEmpty() || premium_plan_month.getText().toString().isEmpty() || premium_plan_mins.getText().toString().isEmpty()) {
                    if (basic_plan_amount.getText().toString().isEmpty() && basic_plan_month.getText().toString().isEmpty() && basic_plan_mins.getText().toString().isEmpty()
                            && standard_plan_amount.getText().toString().isEmpty() && standard_plan_month.getText().toString().isEmpty() && standard_plan_mins.getText().toString().isEmpty()
                            && standard_plan_amount.getText().toString().isEmpty() && sweet_plan_month.getText().toString().isEmpty() && sweet_plan_mins.getText().toString().isEmpty()
                            && premium_plan_amount.getText().toString().isEmpty() && premium_plan_month.getText().toString().isEmpty() && premium_plan_mins.getText().toString().isEmpty()) {

                        basic_plan_amount.startAnimation(animShake);
                        basic_plan_month.startAnimation(animShake);
                        basic_plan_mins.startAnimation(animShake);
                        standard_plan_amount.startAnimation(animShake);
                        standard_plan_month.startAnimation(animShake);
                        standard_plan_mins.startAnimation(animShake);
                        sweet_plan_amount.startAnimation(animShake);
                        sweet_plan_month.startAnimation(animShake);
                        sweet_plan_mins.startAnimation(animShake);
                        premium_plan_amount.startAnimation(animShake);
                        premium_plan_month.startAnimation(animShake);
                        premium_plan_mins.startAnimation(animShake);
                    } else if (basic_plan_amount.getText().toString().isEmpty()) {
                        basic_plan_amount.startAnimation(animShake);
                    } else if (basic_plan_month.getText().toString().isEmpty()) {
                        basic_plan_month.startAnimation(animShake);
                    } else if (basic_plan_mins.getText().toString().isEmpty()) {
                        basic_plan_mins.startAnimation(animShake);
                    } else if (standard_plan_amount.getText().toString().isEmpty()) {
                        standard_plan_amount.startAnimation(animShake);
                    } else if (standard_plan_month.getText().toString().isEmpty()) {
                        standard_plan_month.startAnimation(animShake);
                    } else if (standard_plan_mins.getText().toString().isEmpty()) {
                        standard_plan_mins.startAnimation(animShake);
                    } else if (sweet_plan_amount.getText().toString().isEmpty()) {
                        sweet_plan_amount.startAnimation(animShake);
                    } else if (sweet_plan_month.getText().toString().isEmpty()) {
                        sweet_plan_month.startAnimation(animShake);
                    } else if (sweet_plan_mins.getText().toString().isEmpty()) {
                        sweet_plan_mins.startAnimation(animShake);
                    } else if (premium_plan_amount.getText().toString().isEmpty()) {
                        premium_plan_amount.startAnimation(animShake);
                    } else if (premium_plan_month.getText().toString().isEmpty()) {
                        premium_plan_month.startAnimation(animShake);
                    } else if (premium_plan_mins.getText().toString().isEmpty()) {
                        premium_plan_mins.startAnimation(animShake);
                    }
                } else {
                    basicPlanAmount = basic_plan_amount.getText().toString();
                    basicPlanMonth = basic_plan_month.getText().toString();
                    basicPlanMins = basic_plan_mins.getText().toString();
                    standardPlanAmount = standard_plan_amount.getText().toString();
                    standardPlanMonth = standard_plan_month.getText().toString();
                    standardPlanMins = standard_plan_mins.getText().toString();
                    sweetPlanAmount = sweet_plan_amount.getText().toString();
                    sweetPlanMonth = sweet_plan_month.getText().toString();
                    sweetPlanMins = sweet_plan_mins.getText().toString();
                    premiumPlanAmount = premium_plan_amount.getText().toString();
                    premiumPlanMonth = premium_plan_month.getText().toString();
                    premiumPlanMins = premium_plan_mins.getText().toString();

                    Log.d(TAG, "Subsceiption Plans:" + basicPlanAmount + ";" + basicPlanMonth + ";" + basicPlanMins + "\n" + standardPlanAmount + ";"
                            + standardPlanMonth + ";" + standardPlanMins + "\n" +
                            sweetPlanAmount + ";" + sweetPlanMonth + ";" + sweetPlanMins + "\n" + premiumPlanAmount + ";" + premiumPlanMonth + ";" + premiumPlanMins);

                    Intent manageSystem = new Intent(AreaSubscription.this, ManageSystem.class);
                    manageSystem.putParcelableArrayListExtra("markerList", markerList);
                    manageSystem.putExtra("areaNumber", areaNumber);
                    manageSystem.putExtra("area_Name", area_Name);
                    manageSystem.putExtra("adminMobile", adminMobile);
                    manageSystem.putExtra("numberPicker1", numberPicker1);
                    manageSystem.putExtra("rupee1", rupee1);
                    manageSystem.putExtra("numberPicker2", numberPicker2);
                    manageSystem.putExtra("rupee2", rupee2);
                    manageSystem.putExtra("numberPicker3", numberPicker3);
                    manageSystem.putExtra("rupee3", rupee3);
                    manageSystem.putExtra("numberPicker4", numberPicker4);
                    manageSystem.putExtra("rupee4", rupee4);
                    manageSystem.putExtra("numberPicker5", numberPicker5);
                    manageSystem.putExtra("rupee5", rupee5);
                    manageSystem.putExtra("basicPlanAmount", basicPlanAmount);
                    manageSystem.putExtra("basicPlanMonth", basicPlanMonth);
                    manageSystem.putExtra("basicPlanMins", basicPlanMins);
                    manageSystem.putExtra("standardPlanAmount", standardPlanAmount);
                    manageSystem.putExtra("standardPlanMonth", standardPlanMonth);
                    manageSystem.putExtra("standardPlanMins", standardPlanMins);
                    manageSystem.putExtra("sweetPlanAmount", sweetPlanAmount);
                    manageSystem.putExtra("sweetPlanMonth", sweetPlanMonth);
                    manageSystem.putExtra("sweetPlanMins", sweetPlanMins);
                    manageSystem.putExtra("premiumPlanAmount", premiumPlanAmount);
                    manageSystem.putExtra("premiumPlanMonth", premiumPlanMonth);
                    manageSystem.putExtra("premiumPlanMins", premiumPlanMins);
                    startActivity(manageSystem);
                }

                break;
            default:
                break;
        }

    }
}
