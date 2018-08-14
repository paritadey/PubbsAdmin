package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

public class RateChartTime extends Fragment {
    private final String TAG = "RateChartTime";
    com.travijuu.numberpicker.library.NumberPicker numberPickerOne, numberPickerTwo, numberPickerThree, numberPickerFour, numberPickerFive, numberPickerSix;
    private TextView timeTv, priceTv;
    com.travijuu.numberpicker.library.NumberPicker numberPickerSeven, numberPickerEight, numberPickerNine, numberPickerTen, numberPickerEleven;
    private EditText rupees;
    private Button proceed;

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
        proceed = rootView.findViewById(R.id.proceed_btn);
        numberPickerOne = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_1);
        numberPickerTwo = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_2);
        numberPickerThree = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_3);
        numberPickerFour = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_4);
        numberPickerFive = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_5);
        numberPickerSix = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_6);
        numberPickerSeven = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_7);
        numberPickerEight = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_8);
        numberPickerNine = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_9);
        numberPickerTen = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_10);
        numberPickerEleven = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker_11);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageSystem = new Intent(getActivity(), ManageSystem.class);
                startActivity(manageSystem);
            }
        });
        numberPickerOne.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                // String actionText = action == ActionEnum.MANUAL ? "manually set" : (action == ActionEnum.INCREMENT ? "incremented" : "decremented");
                int message = value;// String.format("NumberPicker is %s to %d", actionText, value);
                Log.d(TAG, "Number Picker 1 value: " + message);
            }
        });
        numberPickerTwo.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 2 value: " + message);
            }
        });
        numberPickerThree.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 3 value: " + message);
            }
        });
        numberPickerFour.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 4 value: " + message);
            }
        });
        numberPickerFive.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 5 value: " + message);
            }
        });
        numberPickerSix.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 6 value: " + message);
            }
        });
        numberPickerSeven.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 7 value: " + message);
            }
        });
        numberPickerEight.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 8 value: " + message);
            }
        });
        numberPickerNine.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 9 value: " + message);
            }
        });
        numberPickerTen.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 10 value: " + message);
            }
        });
        numberPickerEleven.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int message = value;
                Log.d(TAG, "Number Picker 11 value: " + message);
            }
        });
        return rootView;
    }
}

