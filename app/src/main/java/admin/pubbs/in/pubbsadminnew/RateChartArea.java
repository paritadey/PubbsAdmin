package admin.pubbs.in.pubbsadminnew;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class RateChartArea extends Fragment implements View.OnClickListener {
    private String TAG = RateChartArea.class.getSimpleName();
    private TextView openingHrTv, closingHrTv, geofencingFineTv, rupeesTv;
    private ImageView openingTimer, closingTimer;
    private TextView openingHour, closingHour;
    private int currentHour, currentMinute;
    private EditText geofencingFine;
    ConstraintLayout manageSystem;
    private String amPm;

    public RateChartArea() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rate_chart_area, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        manageSystem = rootView.findViewById(R.id.manage_area_layout);

        openingHrTv = rootView.findViewById(R.id.opening_hour_tv);
        openingTimer = rootView.findViewById(R.id.opening_timer);
        openingHour = rootView.findViewById(R.id.opening_hour);

        closingHrTv = rootView.findViewById(R.id.closing_hour_tv);
        closingTimer = rootView.findViewById(R.id.closing_timer);
        closingHour = rootView.findViewById(R.id.closing_hour);

        geofencingFineTv = rootView.findViewById(R.id.geofencing_fine_tv);
        geofencingFine = rootView.findViewById(R.id.geofencing_fine);
        rupeesTv = rootView.findViewById(R.id.rupees_tv);

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
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opening_timer:
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog timePickerDialog_one = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog timePickerDialog_two = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

                TimePickerDialog timePickerDialog_three = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
            default:
                break;
        }
    }
}
