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

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

/*created by Parita Dey*/
public class ManageSystem extends AppCompatActivity implements View.OnClickListener {

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
    String areaNumber, area_Name, adminMobile;
    private String openHr, closeHr, geofenceFine;
    RelativeLayout layoutGeofenceFine, layoutClosingHr, layoutOpeningHr;


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

        manageSystem = findViewById(R.id.manage_area_layout);
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

        geofencingFineTv.setTypeface(type1);
        geofencingFine.setTypeface(type1);
        rupeesTv.setTypeface(type1);

        closingHrTv.setTypeface(type1);
        openingHrTv.setTypeface(type1);
        openingHour.setTypeface(type1);
        closingHour.setTypeface(type1);

        openingTimer.setOnClickListener(this);
        openingHour.setOnClickListener(this);

        closingTimer.setOnClickListener(this);
        closingHour.setOnClickListener(this);
        proceed.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_arrow:
                new BottomSheetFragment().show(getSupportFragmentManager(), "dialog");
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
                    if (openingHour.getText().toString().isEmpty()) {
                        layoutOpeningHr.startAnimation(animShake);
                    } else if (closingHour.getText().toString().isEmpty()) {
                        layoutClosingHr.startAnimation(animShake);
                    } else if (geofencingFine.getText().toString().isEmpty()) {
                        layoutGeofenceFine.startAnimation(animShake);
                    } else if (openingHour.getText().toString().isEmpty() && closingHour.getText().toString().isEmpty() && geofencingFine.getText().toString().isEmpty()) {
                        layoutOpeningHr.startAnimation(animShake);
                        layoutClosingHr.startAnimation(animShake);
                        layoutGeofenceFine.startAnimation(animShake);
                    }
                } else {
                    openHr = openingHour.getText().toString();
                    closeHr = closingHour.getText().toString();
                    geofenceFine = geofencingFine.getText().toString();
                    showAreaAddedDialog();
                }
                break;
            default:
                break;
        }
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
        View dialogView = inflater.inflate(R.layout.cycle_added_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        areaAdd.setTypeface(type1);
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(true);
    }
}
