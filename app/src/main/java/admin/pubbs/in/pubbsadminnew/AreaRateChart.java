package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class AreaRateChart extends AppCompatActivity implements View.OnClickListener {

    String areaname, areaid, adminmobile;
    private String TAG = AreaRateChart.class.getSimpleName();
    SharedPreferences sharedPreferences;
    Spinner choice;
    private static final String[] rateTypes = {"Select Rate ", "Time", "Distance"};
    String rate_type, numberPickerMins, numberPickerHour, kmRate, timePrice, distancePrice;
    ConstraintLayout rateChartTime, rateChartDistance;
    TextView minute_tv, hour_tv, distance_tv, price_tv, distance_price_tv, rate_chart_heading, rupees_tv;
    com.travijuu.numberpicker.library.NumberPicker number_picker_mintues, number_picker_hour;
    com.warkiz.widget.IndicatorStayLayout km_indicator_layout;
    com.warkiz.widget.IndicatorSeekBar km_indicator;
    Button add_time_rate, add_distance_rate;
    RelativeLayout layout_price, layout_distance_price;
    EditText time_price, distance_price;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_rate_chart);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        areaname = intent.getStringExtra("areaname");
        areaid = intent.getStringExtra("areaid");
        Log.d(TAG, "Area details: " + areaname + "\t" + areaid);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Admin Mobile" + adminmobile);
        rupees_tv = findViewById(R.id.rupees_tv);
        rate_chart_heading = findViewById(R.id.rate_chart_heading);
        rate_chart_heading.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        rateChartTime = findViewById(R.id.rateChartTime);
        rateChartDistance = findViewById(R.id.rateChartDistance);
        price_tv = findViewById(R.id.price_tv);
        price_tv.setTypeface(type1);
        layout_price = findViewById(R.id.layout_price);
        time_price = findViewById(R.id.time_price);
        time_price.setTypeface(type1);
        distance_price_tv = findViewById(R.id.distance_price_tv);
        distance_price_tv.setTypeface(type1);
        layout_distance_price = findViewById(R.id.layout_distance_price);
        distance_price = findViewById(R.id.distance_price);
        distance_price.setTypeface(type1);
        number_picker_mintues = findViewById(R.id.number_picker_mintues);
        number_picker_mintues.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPickerMins = String.valueOf(message);
                Log.d(TAG, "Number Picker Minute value: " + message);
            }
        });
        number_picker_hour = findViewById(R.id.number_picker_hour);
        number_picker_hour.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPickerHour = String.valueOf(message);
                Log.d(TAG, "Number Picker Hour value: " + message);
            }
        });

        add_time_rate = findViewById(R.id.add_time_rate);
        add_time_rate.setOnClickListener(this);
        add_time_rate.setTypeface(type2);
        add_distance_rate = findViewById(R.id.add_distance_rate);
        add_distance_rate.setOnClickListener(this);
        add_distance_rate.setTypeface(type2);
        km_indicator_layout = findViewById(R.id.km_indicator_layout);
        km_indicator = findViewById(R.id.km_indicator);
        km_indicator.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                Log.d(TAG, "Seekbar Progress:" + seekBar.getProgress());
                kmRate = String.valueOf(seekBar.getProgress());

            }
        });


        choice = findViewById(R.id.choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, rateTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice.setAdapter(adapter);
        choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        choice.startAnimation(animShake);
                        rateChartTime.setVisibility(View.GONE);
                        rateChartDistance.setVisibility(View.GONE);
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view, "Choose Your Option", duration);
                        break;
                    case 1:
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartTime.setVisibility(View.VISIBLE);
                        rateChartDistance.setVisibility(View.GONE);
                        Log.d(TAG, "Number Picker values:" + numberPickerMins + "\t" + numberPickerHour);
                        break;
                    case 2:
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartDistance.setVisibility(View.VISIBLE);
                        rateChartTime.setVisibility(View.GONE);
                        Log.d(TAG, "Chosen Seekbar data:" + kmRate);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AreaRateChart.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(AreaRateChart.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.add_time_rate:
                if (!time_price.getText().toString().isEmpty()) {
                    timePrice = time_price.getText().toString();
                    distancePrice = "N/A";
                    Log.d(TAG, "Time based rate:" + rate_type + "\t" + numberPickerHour + "\t"
                            + numberPickerMins + "\t" + timePrice + "\t" + distancePrice);
                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layout_price.startAnimation(animShake);
                }
                break;
            case R.id.add_distance_rate:
                if (!distance_price.getText().toString().isEmpty()) {
                    distancePrice = distance_price.getText().toString();
                    timePrice = "N/A";
                    Log.d(TAG, "Distance based rate:" + rate_type + "\t" + kmRate + "\t" + distancePrice + "\t" + timePrice);
                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layout_distance_price.startAnimation(animShake);
                }
                break;
            default:
                break;
        }

    }
}
