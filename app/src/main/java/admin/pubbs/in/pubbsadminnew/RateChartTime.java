package admin.pubbs.in.pubbsadminnew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

public class RateChartTime extends Fragment {
    private final String TAG = "RateChartTime";
    com.travijuu.numberpicker.library.NumberPicker numberPicker;
    EditText numberPickerValue;

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
        numberPicker = (com.travijuu.numberpicker.library.NumberPicker) rootView.findViewById(R.id.number_picker);
        numberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                String actionText = action == ActionEnum.MANUAL ? "manually set" : (action == ActionEnum.INCREMENT ? "incremented" : "decremented");
                String message = String.format("NumberPicker is %s to %d", actionText, value);
                Log.d(TAG, "Number Picker value: " + message);
            }
        });
        return rootView;
    }
}

