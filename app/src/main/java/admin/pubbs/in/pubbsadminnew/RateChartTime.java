package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

import java.util.ArrayList;
/*created by Parita Dey*/

public class RateChartTime extends Fragment {
    private final String TAG = "RateChartTime";
    com.travijuu.numberpicker.library.NumberPicker numberPickerOne, numberPickerTwo, numberPickerThree, numberPickerFour, numberPickerFive;
    private TextView timeTv, priceTv;
    private Button proceed;
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    private String areaNumber, area_Name, adminMobile, numberPicker1, numberPicker2, numberPicker3, numberPicker4, numberPicker5;
    ConstraintLayout ratechartTime;
    private String rupee1, rupee2, rupee3, rupee4, rupee5;
    EditText rupees_one, rupees_two, rupees_three, rupees_four, rupees_five;
    RelativeLayout layout_price, layout_price_2, layout_price_3, layout_price_4, layout_price_5;
    String basicPlanAmount, basicPlanMonth, basicPlanMins;
    public RateChartTime() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rate_chart_time, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Bundle args = getArguments();
        markerList = args.getParcelableArrayList("markerList");
        areaNumber = args.getString("areaNumber");
        area_Name = args.getString("area_Name");
        adminMobile = args.getString("adminMobile");
        basicPlanAmount = args.getString("basicPlanAmount");
        basicPlanMonth = args.getString("basicPlanMonth");
        basicPlanMins = args.getString("basicPlanMins");
        Log.d(TAG, "Area Details:" + markerList + "\t" + areaNumber + "\t" + area_Name + "\t" + adminMobile);
        Log.d(TAG,"From Subscription: "+basicPlanAmount+"*"+basicPlanMonth+"*"+basicPlanMins);
        timeTv = rootView.findViewById(R.id.time_tv);
        timeTv.setTypeface(type1);
        priceTv = rootView.findViewById(R.id.price_tv);
        priceTv.setTypeface(type1);

        rupees_one = rootView.findViewById(R.id.rupees_1);
        rupees_one.setTypeface(type1);
        rupees_two = rootView.findViewById(R.id.rupees_2);
        rupees_two.setTypeface(type1);
        rupees_three = rootView.findViewById(R.id.rupees_3);
        rupees_three.setTypeface(type1);
        rupees_four = rootView.findViewById(R.id.rupees_4);
        rupees_four.setTypeface(type1);
        rupees_five = rootView.findViewById(R.id.rupees_5);
        rupees_five.setTypeface(type1);

        ratechartTime = rootView.findViewById(R.id.ratechartTime);
        proceed = rootView.findViewById(R.id.proceed_btn);
        proceed.setTypeface(type2);

        layout_price = rootView.findViewById(R.id.layout_price);
        layout_price_2 = rootView.findViewById(R.id.layout_price_2);
        layout_price_3 = rootView.findViewById(R.id.layout_price_3);
        layout_price_4 = rootView.findViewById(R.id.layout_price_4);
        layout_price_5 = rootView.findViewById(R.id.layout_price_5);

        numberPickerOne = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_1);
        numberPicker1 = String.valueOf(numberPickerOne.getValue());
        rupee1 = rupees_one.getText().toString();
        numberPickerTwo = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_2);
        numberPicker2 = String.valueOf(numberPickerTwo.getValue());
        rupee2 = rupees_two.getText().toString();
        numberPickerThree = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_3);
        numberPicker3 = String.valueOf(numberPickerThree.getValue());
        rupee3 = rupees_three.getText().toString();
        numberPickerFour = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_4);
        numberPicker4 = String.valueOf(numberPickerFour);
        rupee4 = rupees_four.getText().toString();
        numberPickerFive = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_5);
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
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
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

                    Intent manageSystem = new Intent(getActivity(), ManageSystem.class);
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
            }
        });

        return rootView;
    }
}

