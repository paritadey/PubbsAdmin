package admin.pubbs.in.pubbsadminnew;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

public class FinancialReport extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    TextView financial_report_tv, progress_indication, progress_tv, progress_two_tv, debit_progress_indication;
    TextView debit_progress_tv, credit_progress_indication, credit_progress_tv;
    ImageView back;
    // Button daily_button, weekly_button, monthly_button, yearly_button;
    private String TAG = FinancialReport.class.getSimpleName();
    RadioGroup transaction_type;
    RadioButton radioRide, radioWallet;
    String transactionType;
    ProgressBar progress_bar, debit_progress_bar, credit_progress_bar;
    CardView result_card, wallet_result_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_report);
        initView();

    }

    private void initView() {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        result_card = findViewById(R.id.result_card);
        wallet_result_card = findViewById(R.id.wallet_result_card);
        financial_report_tv = findViewById(R.id.financial_report_tv);
        financial_report_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        progress_bar = findViewById(R.id.circle_progress_bar);
        progress_indication = findViewById(R.id.progress_indication);
        progress_indication.setTypeface(type3);
        progress_tv = findViewById(R.id.progress_tv);
        progress_tv.setTypeface(type1);
        progress_two_tv = findViewById(R.id.progress_two_tv);
        progress_two_tv.setTypeface(type1);
        debit_progress_indication = findViewById(R.id.debit_progress_indication);
        debit_progress_indication.setTypeface(type3);
        debit_progress_tv = findViewById(R.id.debit_progress_tv);
        debit_progress_tv.setTypeface(type1);
        credit_progress_indication = findViewById(R.id.credit_progress_indication);
        credit_progress_indication.setTypeface(type3);
        credit_progress_tv = findViewById(R.id.credit_progress_tv);
        credit_progress_tv.setTypeface(type1);
        debit_progress_bar = findViewById(R.id.debit_progress_bar);
        credit_progress_bar = findViewById(R.id.credit_progress_bar);
    /* daily_button = findViewById(R.id.daily_button);
        daily_button.setTypeface(type3);
        daily_button.setOnClickListener(this);
        weekly_button = findViewById(R.id.weekly_button);
        weekly_button.setTypeface(type3);
        weekly_button.setOnClickListener(this);
        monthly_button = findViewById(R.id.monthly_button);
        monthly_button.setTypeface(type3);
        monthly_button.setOnClickListener(this);
        yearly_button = findViewById(R.id.yearly_button);
        yearly_button.setTypeface(type3);
        yearly_button.setOnClickListener(this);*/
    }


    //on back button press move back to ManageOperator class from Financial class
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FinancialReport.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.daily_button:
                //getting the current date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.now();
                String start_daily_date = dtf.format(localDate);
                Log.d(TAG, "Daily button shows current date:" + start_daily_date);
                String end_daily_date = start_daily_date;
                sendData(transactionType, start_daily_date, end_daily_date);
                break;
            case R.id.weekly_button:
                //on clicking weekly button shows a calendar dialog to choose the start day of a week
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String start_week = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                Log.d(TAG, "Start Week: " + start_week);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                int limitDay = 7;
                                try {
                                    Date sdate = formatter2.parse(start_week); //start week date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    Date eNdDate = cal.getTime();//end date
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String strDate = dateFormat.format(eNdDate);
                                    Log.d(TAG, "End Week Day:" + strDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Error: " + e);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                //disable the past dates
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                break;
            case R.id.monthly_button:
                //on clicking weekly button shows a calendar dialog to choose the start day of a month
                final Calendar calendar = Calendar.getInstance();
                int Year = calendar.get(Calendar.YEAR);
                int Month = calendar.get(Calendar.MONTH);
                int Day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog_month = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String start_week = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                Log.d(TAG, "Start Week: " + start_week);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                int limitDay = 30;
                                try {
                                    Date sdate = formatter2.parse(start_week); //start week date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    Date eNdDate = cal.getTime();//end date
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String strDate = dateFormat.format(eNdDate);
                                    Log.d(TAG, "End Week Day:" + strDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Error: " + e);
                                }
                            }
                        }, Year, Month, Day);
                datePickerDialog_month.show();
                break;
            case R.id.yearly_button:
                //on clicking weekly button shows a calendar dialog to choose the start day of a month
                final Calendar calendar_year = Calendar.getInstance();
                int year = calendar_year.get(Calendar.YEAR);
                int month = calendar_year.get(Calendar.MONTH);
                int day = calendar_year.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog_year = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String start_week = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Log.d(TAG, "Start Week: " + start_week);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                int limitDay = 365;
                                try {
                                    Date sdate = formatter2.parse(start_week); //start week date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    Date eNdDate = cal.getTime();//end date
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String strDate = dateFormat.format(eNdDate);
                                    Log.d(TAG, "End Week Day:" + strDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Error: " + e);
                                }
                            }
                        }, year, month, day);
                datePickerDialog_year.show();
                break;*/
            case R.id.back_button:
                //on back button imageview press move back to ManageOperator class from Financial class
                Intent intent = new Intent(FinancialReport.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    public void sendData(String transactionType) {
        if (transactionType.equals("Ride Transaction")) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "getridefinancialreport");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo, FinancialReport.this,
                    getApplicationContext()).executeJsonRequest();
        } else if (transactionType.equals("Wallet Transaction")) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "getwalletfinancialreport");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo, FinancialReport.this,
                    getApplicationContext()).executeJsonRequest();

        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (transactionType.equals("Ride Transaction")) {
            if (jsonObject.has("method")) {
                try {
                    if (jsonObject.getString("method").equals("getridefinancialreport") && jsonObject.getBoolean("success")) {
                        JSONArray ja = jsonObject.getJSONArray("data");
                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                String total_amount = jo.getString("total_amount");
                                result_card.setVisibility(View.VISIBLE);
                                wallet_result_card.setVisibility(View.GONE);
                                double amount = Double.parseDouble(total_amount);
                                int progress = (int) amount;
                                Log.d(TAG, "progress:" + progress);
                                progress_bar.setProgress(progress);
                                progress_indication.setText(progress + "" + "%");
                                progress_tv.setText("Ride Transaction");
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Network issue", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error:" + e);
                }
            }
        } else if (transactionType.equals("Wallet Transaction")) {
            if (jsonObject.has("method")) {
                try {
                    if (jsonObject.getString("method").equals("getwalletfinancialreport") && jsonObject.getBoolean("success")) {
                        JSONArray ja = jsonObject.getJSONArray("data");
                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                String debit = jo.getString("debit");
                                String credit = jo.getString("credit");
                                wallet_result_card.setVisibility(View.VISIBLE);
                                result_card.setVisibility(View.GONE);
                                double debit_amount = Double.parseDouble(debit);
                                double credit_amount = Double.parseDouble(credit);
                                int debit_progress = (int) debit_amount;
                                int credit_progress = (int) credit_amount;
                                Log.d(TAG, "progress:" + debit_progress + "\t" + credit_progress);
                                debit_progress_bar.setProgress(debit_progress);
                                credit_progress_bar.setProgress(credit_progress);
                                debit_progress_indication.setText(debit_progress + "" + "%");
                                credit_progress_indication.setText(credit_progress + "" + "%");
                                progress_two_tv.setText("Wallet Transaction");
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Network issue", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error:" + e);
                }
            }
        }

    }

    @Override
    public void onResponseError(VolleyError error) {

    }

    public void onRadioClicked(View view) {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        transaction_type = findViewById(R.id.transaction_type);
        radioRide = findViewById(R.id.radioRide);
        radioRide.setTypeface(type1);
        radioWallet = findViewById(R.id.radioWallet);
        radioWallet.setTypeface(type1);
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioRide:
                if (checked) {
                    transactionType = "Ride Transaction";
                    sendData(transactionType);
                }
                break;
            case R.id.radioWallet:
                if (checked) {
                    transactionType = "Wallet Transaction";
                    sendData(transactionType);
                }
                break;
        }

    }
}
