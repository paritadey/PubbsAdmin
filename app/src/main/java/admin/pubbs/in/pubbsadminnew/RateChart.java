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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

import java.util.ArrayList;
/*created by Parita Dey*/

public class RateChart extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    TextView rateTv;
    ImageView upArrow;
    TextView bottomsheetText;
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    String areaNumber, area_Name, adminMobile;
    private String TAG = RateChart.class.getSimpleName();
    com.travijuu.numberpicker.library.NumberPicker numberPickerOne, numberPickerTwo, numberPickerThree, numberPickerFour, numberPickerFive;
    private TextView timeTv, priceTv;
    private Button proceed;
    private String numberPicker1, numberPicker2, numberPicker3, numberPicker4, numberPicker5;
    private String rupee1, rupee2, rupee3, rupee4, rupee5;
    EditText rupees_one, rupees_two, rupees_three, rupees_four, rupees_five;
    RelativeLayout layout_price, layout_price_2, layout_price_3, layout_price_4, layout_price_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_chart);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        markerList = (ArrayList<LatLng>) getIntent().getSerializableExtra("markerList");
        areaNumber = intent.getStringExtra("areaNumber");
        area_Name = intent.getStringExtra("area_Name");
        adminMobile = intent.getStringExtra("adminMobile");
        Log.d(TAG, "Area Values:" + markerList + "\t" + area_Name + "\t" + areaNumber + "\t" + adminMobile);

        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        rateTv = findViewById(R.id.rate_chart_tv);
        rateTv.setTypeface(type1);
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetRateChartTimeFragment().show(getSupportFragmentManager(), "dialog");

            }
        });
        timeTv = findViewById(R.id.time_tv);
        timeTv.setTypeface(type1);
        priceTv = findViewById(R.id.price_tv);
        priceTv.setTypeface(type1);

        rupees_one = findViewById(R.id.rupees_1);
        rupees_one.setTypeface(type1);
        rupees_two = findViewById(R.id.rupees_2);
        rupees_two.setTypeface(type1);
        rupees_three = findViewById(R.id.rupees_3);
        rupees_three.setTypeface(type1);
        rupees_four = findViewById(R.id.rupees_4);
        rupees_four.setTypeface(type1);
        rupees_five = findViewById(R.id.rupees_5);
        rupees_five.setTypeface(type1);

        proceed = findViewById(R.id.proceed_btn);
        proceed.setTypeface(type2);

        layout_price = findViewById(R.id.layout_price);
        layout_price_2 = findViewById(R.id.layout_price_2);
        layout_price_3 = findViewById(R.id.layout_price_3);
        layout_price_4 = findViewById(R.id.layout_price_4);
        layout_price_5 = findViewById(R.id.layout_price_5);

        numberPickerOne = (com.travijuu.numberpicker.library.NumberPicker) findViewById(R.id.number_picker_1);
        numberPicker1 = String.valueOf(numberPickerOne.getValue());
        rupee1 = rupees_one.getText().toString();
        numberPickerTwo = (com.travijuu.numberpicker.library.NumberPicker) findViewById(R.id.number_picker_2);
        numberPicker2 = String.valueOf(numberPickerTwo.getValue());
        rupee2 = rupees_two.getText().toString();
        numberPickerThree = (com.travijuu.numberpicker.library.NumberPicker) findViewById(R.id.number_picker_3);
        numberPicker3 = String.valueOf(numberPickerThree.getValue());
        rupee3 = rupees_three.getText().toString();
        numberPickerFour = (com.travijuu.numberpicker.library.NumberPicker) findViewById(R.id.number_picker_4);
        numberPicker4 = String.valueOf(numberPickerFour);
        rupee4 = rupees_four.getText().toString();
        numberPickerFive = (com.travijuu.numberpicker.library.NumberPicker) findViewById(R.id.number_picker_5);
        numberPicker5 = String.valueOf(numberPickerFive);
        rupee5 = rupees_five.getText().toString();

        numberPickerOne.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                // String actionText = action == ActionEnum.MANUAL ? "manually set" : (action == ActionEnum.INCREMENT ? "incremented" : "decremented");
                int message = value;// String.format("NumberPicker is %s to %d", actionText, value);
                numberPicker1 = String.valueOf(message);
                Log.d(TAG, "Number Picker 1 value: " + message + rupee1);
            }
        });
        numberPickerTwo.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPicker2 = String.valueOf(message);
                Log.d(TAG, "Number Picker 2 value: " + message + rupee2);
            }
        });
        numberPickerThree.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPicker3 = String.valueOf(message);
                Log.d(TAG, "Number Picker 3 value: " + message + rupee3);
            }
        });
        numberPickerFour.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPicker4 = String.valueOf(message);
                Log.d(TAG, "Number Picker 4 value: " + message + rupee4);
            }
        });
        numberPickerFive.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                numberPicker5 = String.valueOf(message);
                Log.d(TAG, "Number Picker 5 value: " + message + rupee5);
            }
        });
        proceed.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RateChart.this, AddNewArea.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(RateChart.this, AddNewArea.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.proceed_btn:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (rupees_one.getText().toString().isEmpty() || rupees_two.getText().toString().isEmpty() || rupees_three.getText().toString().isEmpty()
                        || rupees_four.getText().toString().isEmpty() || rupees_five.getText().toString().isEmpty()) {
                    if (rupees_one.getText().toString().isEmpty() && rupees_two.getText().toString().isEmpty() && rupees_three.getText().toString().isEmpty()
                            && rupees_four.getText().toString().isEmpty() && rupees_five.getText().toString().isEmpty()) {
                        layout_price.startAnimation(animShake);
                        layout_price_2.startAnimation(animShake);
                        layout_price_3.startAnimation(animShake);
                        layout_price_4.startAnimation(animShake);
                        layout_price_5.startAnimation(animShake);
                    } else if (rupees_one.getText().toString().isEmpty()) {
                        layout_price.startAnimation(animShake);
                    } else if (rupees_two.getText().toString().isEmpty()) {
                        layout_price_2.startAnimation(animShake);
                    } else if (rupees_three.getText().toString().isEmpty()) {
                        layout_price_3.startAnimation(animShake);
                    } else if (rupees_four.getText().toString().isEmpty()) {
                        layout_price_4.startAnimation(animShake);
                    } else if (rupees_five.getText().toString().isEmpty()) {
                        layout_price_5.startAnimation(animShake);
                    }
                } else {

                    Intent manageSystem = new Intent(RateChart.this, ManageSystem.class);
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
                    startActivity(manageSystem);
                }
                break;
            default:
                break;
        }

    }
}
