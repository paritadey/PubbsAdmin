package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.android.volley.VolleyError;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class AreaRateChart extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    String areaname, areaid, adminmobile, rate_id, date_time;
    int rateByTime, rateByDistance, kmRate, kmRateTimeDistance, distancePrice, amount;
    private String TAG = AreaRateChart.class.getSimpleName();
    SharedPreferences sharedPreferences;
    Spinner choice;
    private static final String[] rateTypes = {"Select Rate ", "Time", "Distance", "Time and Distance"};
    String rate_type, numberPickerMins, numberPickerHour, numberPickerTimeDistanceMins, numberPickerTimeDistanceHour;
    ConstraintLayout rateChartTime, rateChartDistance, rateChartTimeDistance;
    TextView minute_tv, hour_tv, distance_tv, price_tv, distance_price_tv, rate_chart_heading, rupees_tv, rupees_distv, rupeesTv, distancePriceTv, time_minute_tv, distanceTv, time_hour_tv;
    com.travijuu.numberpicker.library.NumberPicker number_picker_mintues, number_picker_hour, number_picker_time_mintues, number_picker_time_hour;
    com.warkiz.widget.IndicatorStayLayout km_indicator_layout, kmIndicatorLayout;
    com.warkiz.widget.IndicatorSeekBar km_indicator, kmIndicator;
    Button add_time_rate, add_distance_rate, add_rate;
    RelativeLayout layout_price, layout_distance_price, layoutdistance_price;
    EditText time_price, distance_price, distance_Price;
    ImageView back;
    int hour, min;

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
        minute_tv = findViewById(R.id.minute_tv);
        minute_tv.setTypeface(type1);
        hour_tv = findViewById(R.id.hour_tv);
        hour_tv.setTypeface(type1);
        rupees_tv = findViewById(R.id.rupees_tv);
        rupees_tv.setTypeface(type1);
        distance_tv = findViewById(R.id.distance_tv);
        distance_tv.setTypeface(type1);
        rate_chart_heading = findViewById(R.id.rate_chart_heading);
        rate_chart_heading.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        rateChartTime = findViewById(R.id.rateChartTime);
        rateChartDistance = findViewById(R.id.rateChartDistance);
        rateChartTimeDistance = findViewById(R.id.rateChartTimeDistance);
        time_minute_tv = findViewById(R.id.time_minute_tv);
        time_minute_tv.setTypeface(type1);
        time_hour_tv = findViewById(R.id.time_hour_tv);
        time_hour_tv.setTypeface(type1);
        distanceTv = findViewById(R.id.distanceTv);
        distanceTv.setTypeface(type1);
        distancePriceTv = findViewById(R.id.distancePriceTv);
        distancePriceTv.setTypeface(type1);
        distance_Price = findViewById(R.id.distancePrice);
        distance_Price.setTypeface(type1);
        rupeesTv = findViewById(R.id.rupeesTv);
        rupeesTv.setTypeface(type1);
        price_tv = findViewById(R.id.price_tv);
        price_tv.setTypeface(type1);
        layout_price = findViewById(R.id.layout_price);
        time_price = findViewById(R.id.time_price);
        time_price.setTypeface(type1);
        distance_price_tv = findViewById(R.id.distance_price_tv);
        distance_price_tv.setTypeface(type1);
        rupees_distv = findViewById(R.id.rupees_distv);
        rupees_distv.setTypeface(type1);
        layout_distance_price = findViewById(R.id.layout_distance_price);
        layoutdistance_price = findViewById(R.id.layoutdistance_price);
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
        number_picker_time_mintues = findViewById(R.id.number_picker_time_mintues);
        number_picker_time_mintues.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPickerTimeDistanceMins = String.valueOf(message);
                Log.d(TAG, "Number Picker Mintues value for both: " + message);
            }
        });
        number_picker_time_hour = findViewById(R.id.number_picker_time_hour);
        number_picker_time_hour.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPickerTimeDistanceHour = String.valueOf(message);
                Log.d(TAG, "Number Picker Hour value for both: " + message);
            }
        });
        add_time_rate = findViewById(R.id.add_time_rate);
        add_time_rate.setOnClickListener(this);
        add_time_rate.setTypeface(type2);
        add_distance_rate = findViewById(R.id.add_distance_rate);
        add_distance_rate.setOnClickListener(this);
        add_distance_rate.setTypeface(type2);
        add_rate = findViewById(R.id.add_rate);
        add_rate.setTypeface(type2);
        add_rate.setOnClickListener(this);
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
                kmRate = seekBar.getProgress();

            }
        });
        kmIndicatorLayout = findViewById(R.id.kmIndicatorLayout);
        kmIndicator = findViewById(R.id.kmIndicator);
        kmIndicator.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                Log.d(TAG, "Seekbar Progress:" + seekBar.getProgress());
                kmRateTimeDistance = seekBar.getProgress();
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
                        rateChartTimeDistance.setVisibility(View.GONE);
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view, "Choose Your Option", duration);
                        break;
                    case 1:
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartTime.setVisibility(View.VISIBLE);
                        rateChartDistance.setVisibility(View.GONE);
                        rateChartTimeDistance.setVisibility(View.GONE);
                        Log.d(TAG, "Number Picker values:" + numberPickerMins + "\t" + numberPickerHour);
                        break;
                    case 2:
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartDistance.setVisibility(View.VISIBLE);
                        rateChartTime.setVisibility(View.GONE);
                        rateChartTimeDistance.setVisibility(View.GONE);
                        Log.d(TAG, "Chosen Seekbar data:" + kmRate);
                        break;
                    case 3:
                        //need to show both time and distance
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartTime.setVisibility(View.GONE);
                        rateChartDistance.setVisibility(View.GONE);
                        rateChartTimeDistance.setVisibility(View.VISIBLE);
                        Log.d(TAG, "Number Picker values:" + numberPickerTimeDistanceHour + "\t" + numberPickerTimeDistanceMins);
                        Log.d(TAG, "Chosen Seekbar data:" + kmRateTimeDistance);
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

    public String generateRateID() {
        String rate = "PUBBS_RATE_ID";
        String rateNo;
        int max = 9999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        rateNo = rate + randomNum;
        Log.d(TAG, "Rate ID: " + rateNo);
        return rateNo;
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
                    amount = Integer.parseInt(time_price.getText().toString());
                    kmRate = 0;
                    rate_id = generateRateID();
                    if (numberPickerMins == null || numberPickerHour == null) {
                        if (numberPickerMins == null && numberPickerHour == null) {
                            rateByTime = 0;
                            Log.d(TAG, "Min:" + rateByTime);
                        } else if (numberPickerHour == null) {
                            hour = 0;
                            min = Integer.parseInt(numberPickerMins);
                            rateByTime = min;
                            Log.d(TAG, "Min:" + rateByTime);
                        } else if (numberPickerMins == null) {
                            min = 0;
                            hour = Integer.parseInt(numberPickerHour) * 60;
                            rateByTime = hour;
                            Log.d(TAG, "Min:" + rateByTime);
                        }
                    } else {
                        hour = Integer.parseInt(numberPickerHour) * 60;
                        min = Integer.parseInt(numberPickerMins);
                        rateByTime = hour + min;//numberPickerHour + "hr" + numberPickerMins + "mins";
                        Log.d(TAG, "Min:" + rateByTime);
                    }
                    Log.d(TAG, "Time based rate:" + rate_type + "\t" + numberPickerHour + "\t"
                            + numberPickerMins + "\t" + amount + "\t" + kmRate + "\t"
                            + rate_id + "\t" + date_time);
                    addRateChart(rate_id, adminmobile, areaname, rate_type, rateByTime, rateByDistance, amount);

                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layout_price.startAnimation(animShake);
                }
                break;
            case R.id.add_distance_rate:
                if (!distance_price.getText().toString().isEmpty()) {
                    amount = Integer.parseInt(distance_price.getText().toString());
                    rateByDistance = kmRate;
                    rateByTime = 0;
                    rate_id = generateRateID();
                    Log.d(TAG, "Distance based rate:" + rate_type + "\t" + kmRate + "\t" + amount
                            + "\t" + numberPickerHour + "\t" + numberPickerMins + "\t" + rate_id + "\t" + date_time + rateByDistance);
                    addRateChart(rate_id, adminmobile, areaname, rate_type, rateByTime, rateByDistance, amount);
                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layout_distance_price.startAnimation(animShake);
                }
                break;
            case R.id.add_rate:
                if (!distance_Price.getText().toString().isEmpty()) {
                    amount = Integer.parseInt(distance_Price.getText().toString());
                    rateByDistance = kmRateTimeDistance;
                    rate_id = generateRateID();
                    if (numberPickerTimeDistanceMins == null || numberPickerTimeDistanceHour == null) {
                        if (numberPickerTimeDistanceMins == null && numberPickerTimeDistanceHour == null) {
                            rateByTime = 0;
                            Log.d(TAG, "Min:" + rateByTime);
                        } else if (numberPickerTimeDistanceHour == null) {
                            hour = 0;
                            min = Integer.parseInt(numberPickerTimeDistanceMins);
                            rateByTime = min;
                            Log.d(TAG, "Min:" + rateByTime);
                        } else if (numberPickerTimeDistanceMins == null) {
                            min = 0;
                            hour = Integer.parseInt(numberPickerTimeDistanceHour) * 60;
                            rateByTime = hour;
                            Log.d(TAG, "Min:" + rateByTime);
                        }
                    } else {
                        hour = Integer.parseInt(numberPickerHour) * 60;
                        min = Integer.parseInt(numberPickerMins);
                        rateByTime = hour + min;
                        Log.d(TAG, "Min:" + rateByTime);
                    }
                    Log.d(TAG, "Time and Distance based:" + rate_type + "\t" + amount + "\t" + rateByDistance + "\t" + rateByTime + "\t" + rate_id);
                    addRateChart(rate_id, adminmobile, areaname, rate_type, rateByTime, rateByDistance, amount);

                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layoutdistance_price.startAnimation(animShake);
                }
                break;
            default:
                break;
        }

    }

    public void addRateChart(String rate_id, String adminmobile, String area_id, String rate_type, int rateByTime,
                             int rateByDistance, int amount) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("method", "add_rate_chart");
            jo.put("rate_id", rate_id);
            jo.put("adminmobile", adminmobile);
            jo.put("area_id", area_id);
            jo.put("rate_type", rate_type);
            jo.put("rateByTime", rateByTime);
            jo.put("rateByDistance", rateByDistance);
            jo.put("amount", amount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AreaRateChart.this,
                getApplicationContext()).executeJsonRequest();

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("add_rate_chart") && jsonObject.getBoolean("success")) {
                    showDialog("Rate Chart Plan is done against an area");
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
        showDialog("Server Error!");
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

    public void clearFields() {
        choice.setSelection(0);
        number_picker_mintues.setValue(5);
        number_picker_hour.setValue(1);
        time_price.setText("");
        km_indicator.setMin(500);
        distance_price.setText("");
    }

}
