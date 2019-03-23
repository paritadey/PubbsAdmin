package admin.pubbs.in.pubbsadminnew;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class FinancialReport extends AppCompatActivity implements View.OnClickListener {
    TextView financial_report_tv;
    ImageView back;
    Button daily_button, weekly_button, monthly_button, yearly_button;
    private String TAG = FinancialReport.class.getSimpleName();

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
        financial_report_tv = findViewById(R.id.financial_report_tv);
        financial_report_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        ProgressBar progress = (ProgressBar) findViewById(R.id.circle_progress_bar);
        progress.setProgress(33);
        daily_button = findViewById(R.id.daily_button);
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
        yearly_button.setOnClickListener(this);
    }

    //on back button press move back to ManageOperator class from Financial class
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FinancialReport.this, ManageOperator.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.daily_button:
                //getting the current date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.now();
                String daily_date = dtf.format(localDate);
                Log.d(TAG, "Daily button shows current date:" + daily_date);
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

                                String start_week = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Log.d(TAG, "Start Week: " + start_week);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                                int limitDay = 7;
                                try {
                                    Date sdate = formatter2.parse(start_week); //start week date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    Date eNdDate = cal.getTime();//end date
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

                                String start_week = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Log.d(TAG, "Start Week: " + start_week);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                                int limitDay = 30;
                                try {
                                    Date sdate = formatter2.parse(start_week); //start week date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    Date eNdDate = cal.getTime();//end date
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
                                SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                                int limitDay = 365;
                                try {
                                    Date sdate = formatter2.parse(start_week); //start week date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sdate);
                                    cal.add(Calendar.DATE, limitDay); // add corrosponding days
                                    Date eNdDate = cal.getTime();//end date
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String strDate = dateFormat.format(eNdDate);
                                    Log.d(TAG, "End Week Day:" + strDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Error: " + e);
                                }
                            }
                        }, year, month, day);
                datePickerDialog_year.show();
                break;
            case R.id.back_button:
                //on back button imageview press move back to ManageOperator class from Financial class
                Intent intent = new Intent(FinancialReport.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
