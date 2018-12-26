package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/*created by Parita Dey*/
public class ManageSystem extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    private TextView bottomSheetTv, manageSytem, allSetTv, manageYourSystem, moreEfficient, maxRideTv, maxHoldtv, minWalletTv;
    private Button proceed;
    ImageView upArrow, back;
    private final String TAG = ManageSystem.class.getSimpleName();
    CoordinatorLayout manageSystem;
    private EditText maxRide, maxHold, minWallet;
    private TextView openingHrTv, closingHrTv, geofencingFineTv, rupeesTv;
    private ImageView openingTimer, closingTimer;
    private TextView openingHour, closingHour;
    private int currentHour, currentMinute;
    private EditText geofencingFine;
    private String amPm;
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    public JsonArray markers;
    private String areaNumber, area_Name, adminMobile;
    private String openHr, closeHr, geofenceFine, max_ride, max_hold, min_wallet;
    RelativeLayout layoutGeofenceFine, layoutClosingHr, layoutOpeningHr;
    private String rupee1, rupee2, rupee3, rupee4, rupee5;
    private String numberPicker1, numberPicker2, numberPicker3, numberPicker4, numberPicker5;
    String markerArray;
    private String basicPlanAmount, basicPlanMonth, basicPlanMins, standardPlanAmount, standardPlanMonth, standardPlanMins;
    private String sweetPlanAmount, sweetPlanMonth, sweetPlanMins, premiumPlanAmount, premiumPlanMonth, premiumPlanMins;
    String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
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
        /*basicPlanAmount = intent.getStringExtra("basicPlanAmount");
        basicPlanMonth = intent.getStringExtra("basicPlanMonth");
        basicPlanMins = intent.getStringExtra("basicPlanMins");
        standardPlanAmount = intent.getStringExtra("standardPlanAmount");
        standardPlanMonth = intent.getStringExtra("standardPlanMonth");
        standardPlanMins = intent.getStringExtra("standardPlanMins");
        sweetPlanAmount = intent.getStringExtra("sweetPlanAmount");
        sweetPlanMonth = intent.getStringExtra("sweetPlanMonth");
        sweetPlanMins = intent.getStringExtra("sweetPlanMins");
        premiumPlanAmount = intent.getStringExtra("premiumPlanAmount");
        premiumPlanMonth = intent.getStringExtra("premiumPlanMonth");
        premiumPlanMins = intent.getStringExtra("premiumPlanMins");*/

        Log.d(TAG, "Data from Rate Chart:" + markerList + "\t" + areaNumber + "\t" + area_Name + "\t" + adminMobile);
        Log.d(TAG, "Data from Number picker and rupees:" + numberPicker1 + ":" + rupee1 + "\t" + numberPicker2 +
                ":" + rupee2 + "\t" + numberPicker3 + ":" + rupee3 + "\t" + numberPicker4 + ":" + rupee4 + "\t" + numberPicker5 + ":" + rupee5);
      /*  Log.d(TAG, "Subscription:"+basicPlanAmount+"//"+basicPlanMonth+"//"+basicPlanMins+"\t"+standardPlanAmount+"//"+standardPlanMonth+
        "//"+standardPlanMins+"\t"+sweetPlanAmount+"//"+sweetPlanMonth+"//"+sweetPlanMins+"\t"+premiumPlanAmount+"//"+premiumPlanMonth+"//"+premiumPlanMins);*/

        Gson gson = new Gson();
        json=gson.toJson(markerList);
        Log.d(TAG, "Marker String:"+json);

        showinJsonArray(markerList);
        manageSystem = findViewById(R.id.manage_system);
        back = findViewById(R.id.back_button);
        upArrow = findViewById(R.id.up_arrow);
        bottomSheetTv = findViewById(R.id.bottomsheet_text);
        bottomSheetTv.setTypeface(type1);
        manageSytem = findViewById(R.id.manage_area_tv);
        manageSytem.setTypeface(type1);
        allSetTv = findViewById(R.id.all_set_tv);
        allSetTv.setTypeface(type2);
        manageYourSystem = findViewById(R.id.manage_ur_system_tv);
        manageYourSystem.setTypeface(type2);
        moreEfficient = findViewById(R.id.more_efficient_tv);
        moreEfficient.setTypeface(type2);
        maxRideTv = findViewById(R.id.maximum_ride_tv);
        maxRideTv.setTypeface(type2);
        maxRide = findViewById(R.id.max_time);
        maxRide.setTypeface(type2);
        maxHoldtv = findViewById(R.id.maximum_hold_tv);
        maxHoldtv.setTypeface(type2);
        maxHold = findViewById(R.id.max_hold);
        maxHold.setTypeface(type2);
        minWalletTv = findViewById(R.id.min_wallet_tv);
        minWalletTv.setTypeface(type2);
        minWallet = findViewById(R.id.min_wallet);
        minWallet.setTypeface(type2);
        proceed = findViewById(R.id.proceed_btn);
        proceed.setTypeface(type3);
        upArrow.setOnClickListener(this);
        proceed.setOnClickListener(this);
        back.setOnClickListener(this);

       // manageSystem = findViewById(R.id.manage_area_layout);
        proceed = findViewById(R.id.proceed_btn);
        layoutGeofenceFine = findViewById(R.id.layout_geofencing_fine);
        layoutOpeningHr = findViewById(R.id.layout_opening_hour);
        layoutClosingHr = findViewById(R.id.layout_closing_hour);
        openingHrTv = findViewById(R.id.opening_hour_tv);
        openingTimer = findViewById(R.id.opening_timer);
        openingHour = findViewById(R.id.opening_hour);

        closingHrTv = findViewById(R.id.closing_hour_tv);
        closingTimer = findViewById(R.id.closing_timer);
        closingHour = findViewById(R.id.closing_hour);

        geofencingFineTv = findViewById(R.id.geofencing_fine_tv);
        geofencingFine = findViewById(R.id.geofencing_fine);
        rupeesTv = findViewById(R.id.rupees_tv);

        geofencingFineTv.setTypeface(type2);
        geofencingFine.setTypeface(type2);
        rupeesTv.setTypeface(type1);

        closingHrTv.setTypeface(type2);
        openingHrTv.setTypeface(type2);
        openingHour.setTypeface(type2);
        closingHour.setTypeface(type2);

        openingTimer.setOnClickListener(this);
        openingHour.setOnClickListener(this);

        closingTimer.setOnClickListener(this);
        closingHour.setOnClickListener(this);
        proceed.setOnClickListener(this);

    }

    private void showinJsonArray(ArrayList<LatLng> markerLists) {
       /* Gson gson = new GsonBuilder().create();
        markers = gson.toJsonTree(markerList).getAsJsonArray();
        for(int i=0; i<markerList.size(); i++){
            Log.d(TAG, "Lat/Lon:"+markers.get(i).getAsJsonObject());
        }*/
        markerArray = "";
        for (LatLng s : markerLists) {
            markerArray += s + ";";
        }
        Log.d(TAG, "Large Marker String:" + markerArray);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_arrow:
                new BottomSheetManageSystemFragment().show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(ManageSystem.this, AddNewArea.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.opening_timer:
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageSystem.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        openingHour.setText(String.format("%02d:%02d", hourOfDay, minutes) + "\t" + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
                break;
            case R.id.opening_hour:
                Calendar calendar_one = Calendar.getInstance();
                currentHour = calendar_one.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar_one.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog_one = new TimePickerDialog(ManageSystem.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        openingHour.setText(String.format("%02d:%02d", hourOfDay, minutes) + "\t" + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog_one.show();

                break;
            case R.id.closing_timer:
                Calendar calendar_two = Calendar.getInstance();
                currentHour = calendar_two.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar_two.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog_two = new TimePickerDialog(ManageSystem.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        closingHour.setText(String.format("%02d:%02d", hourOfDay, minutes) + "\t" + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog_two.show();
                break;
            case R.id.closing_hour:
                Calendar calendar_three = Calendar.getInstance();
                currentHour = calendar_three.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar_three.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog_three = new TimePickerDialog(ManageSystem.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        closingHour.setText(String.format("%02d:%02d", hourOfDay, minutes) + "\t" + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog_three.show();
                break;
            case R.id.proceed_btn:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (openingHour.getText().toString().isEmpty() || closingHour.getText().toString().isEmpty() || geofencingFine.getText().toString().isEmpty()) {
                    if (openingHour.getText().toString().isEmpty() && closingHour.getText().toString().isEmpty() && geofencingFine.getText().toString().isEmpty()) {
                        layoutOpeningHr.startAnimation(animShake);
                        layoutClosingHr.startAnimation(animShake);
                        layoutGeofenceFine.startAnimation(animShake);
                    } else if (openingHour.getText().toString().isEmpty()) {
                        layoutOpeningHr.startAnimation(animShake);
                    } else if (closingHour.getText().toString().isEmpty()) {
                        layoutClosingHr.startAnimation(animShake);
                    } else if (geofencingFine.getText().toString().isEmpty()) {
                        layoutGeofenceFine.startAnimation(animShake);
                    }
                } else {
                    max_ride = maxRide.getText().toString();
                    max_hold = maxHold.getText().toString();
                    min_wallet = minWallet.getText().toString();
                    openHr = openingHour.getText().toString();
                    closeHr = closingHour.getText().toString();
                    geofenceFine = geofencingFine.getText().toString();

                    sendData(areaNumber, area_Name, json, numberPicker1, rupee1, numberPicker2, rupee2, numberPicker3, rupee3,
                            numberPicker4, rupee4, numberPicker5, rupee5, openHr, closeHr, max_ride, max_hold, min_wallet, geofenceFine, adminMobile);
                          /*  basicPlanAmount, basicPlanMonth, basicPlanMins, standardPlanAmount, standardPlanMonth, standardPlanMins, sweetPlanAmount,
                            sweetPlanMonth, sweetPlanMins, premiumPlanAmount, premiumPlanMonth, premiumPlanMins);*/
                }
                break;
            default:
                break;
        }
    }

    public void sendData(String areaNumber, String area_Name, String markerArray, String numberPicker1, String rupee1,
                         String numberPicker2, String rupee2, String numberPicker3, String rupee3, String numberPicker4,
                         String rupee4, String numberPicker5, String rupee5, String openHr, String closeHr, String max_ride,
                         String max_hold, String min_wallet, String geofenceFine, String adminMobile)/*, String basic_plan_amount, String basic_plan_month,
    String basic_plan_mins, String standard_plan_amount, String standard_plan_month, String standard_plan_mins, String sweet_plan_amount,
                         String sweet_plan_month, String sweet_plan_mins, String premium_plan_amount, String premium_plan_month, String premium_plan_mins)*/ {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "addnewarea");
            jo.put("area_id", areaNumber);
            jo.put("area_name", area_Name);
            jo.put("area_lat_lon", markerArray);
            jo.put("rate_chart_one", numberPicker1);
            jo.put("rate_chart_price_one", rupee1);
            jo.put("rate_chart_two", numberPicker2);
            jo.put("rate_chart_price_two", rupee2);
            jo.put("rate_chart_three", numberPicker3);
            jo.put("rate_chart_price_three", rupee3);
            jo.put("rate_chart_four", numberPicker4);
            jo.put("rate_chart_price_four", rupee4);
            jo.put("rate_chart_five", numberPicker5);
            jo.put("rate_chart_price_five", rupee5);
            jo.put("opening_hour", openHr);
            jo.put("closing_hour", closeHr);
            jo.put("max_ride_time", max_ride);
            jo.put("max_hold_time", max_hold);
            jo.put("min_wallet_amnt", min_wallet);
            jo.put("geofencing_fine", geofenceFine);
            jo.put("adminmobile", adminMobile);
          /*  jo.put("basic_plan_amount", basic_plan_amount);
            jo.put("basic_plan_month", basic_plan_month);
            jo.put("basic_plan_mins", basic_plan_mins);
            jo.put("standard_plan_amount", standard_plan_amount);
            jo.put("standard_plan_month", standard_plan_month);
            jo.put("standard_plan_mins", standard_plan_mins);
            jo.put("sweet_plan_amount", sweet_plan_amount);
            jo.put("sweet_plan_month", sweet_plan_month);
            jo.put("sweet_plan_mins", sweet_plan_mins);
            jo.put("premium_plan_amount",premium_plan_amount);
            jo.put("premium_plan_month", premium_plan_month);
            jo.put("premium_plan_mins",premium_plan_mins);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, ManageSystem.this, getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManageSystem.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showAreaAddedDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.area_added_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        final Button ok = (Button)dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        areaAdd.setTypeface(type1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(ManageSystem.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("addnewarea") && jsonObject.getBoolean("success")) {
                    showAreaAddedDialog();
                } else {
                    Toast.makeText(getApplicationContext(), "couldn't save try again later", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                Intent intent = new Intent(ManageSystem.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
