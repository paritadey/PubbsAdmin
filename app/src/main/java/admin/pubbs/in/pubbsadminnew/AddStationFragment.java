package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by LORD on 8/29/2017.
 */

public class AddStationFragment extends Fragment implements AsyncResponse{

    EditText name,capacity;
    RadioButton hire,parking;
    Button save;
    ProgressDialog pd;
    int area;
    EditText openHour,closeHour,gr,gf;
    Spinner holdtime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        area=getArguments().getInt("area");
        getActivity().setTitle("Add Station");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_station_fragment,container,false);
        name= (EditText) v.findViewById(R.id.name);
        capacity= (EditText) v.findViewById(R.id.capacity);
        hire= (RadioButton) v.findViewById(R.id.hire_radio);
        parking= (RadioButton) v.findViewById(R.id.parking_radio);
        save= (Button) v.findViewById(R.id.save);
        openHour= (EditText) v.findViewById(R.id.oh);
        closeHour= (EditText) v.findViewById(R.id.ch);
        gr= (EditText) v.findViewById(R.id.gr);
        gf= (EditText) v.findViewById(R.id.gf);
        holdtime=v.findViewById(R.id.holdtime);
        save.setOnClickListener(view -> {
            name.setError(null);
            capacity.setError(null);
            if (name.getText().toString().trim().isEmpty() || capacity.getText().toString().trim().isEmpty()){
                if(name.getText().toString().trim().isEmpty()){
                    name.setError("Enter Name");
                }
                if (capacity.getText().toString().trim().isEmpty()){
                    capacity.setError("Enter Capacity");
                }
            }else {
                pd.setMessage("Saving...");
                pd.show();
                JSONObject jo=new JSONObject();
                try {
                    jo.put("method","addstation");
                    jo.put("area",area);
                    jo.put("main_stn",0);
                    jo.put("name",name.getText().toString().trim());
                    jo.put("capacity",capacity.getText().toString().trim());
                    jo.put("open", openHour.getText().toString().trim());
                    jo.put("close", closeHour.getText().toString().trim());
                    if (hire.isChecked()){
                        jo.put("rp",false);
                    }else{
                        jo.put("rp",true);
                    }
                    jo.put("virtual",false);
                    jo.put("lat",getArguments().getString("lat"));
                    jo.put("lng",getArguments().getString("lng"));
                    jo.put("geofine",gf.getText().toString().trim());
                    jo.put("radius",gr.getText().toString().trim());
                    jo.put("hold",holdtime.getSelectedItem().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new SendRequest(getResources().getString(R.string.url),jo,AddStationFragment.this,getActivity()).executeJsonRequest();
            }
        });

        openHour.setOnClickListener(view -> {
            Calendar c=Calendar.getInstance();
            if(!((EditText)view).getText().toString().trim().isEmpty()){
                try {
                    c.setTime(new SimpleDateFormat("HH:mm:ss").parse(((EditText)view).getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            timePicker(view,c);
        });
        closeHour.setOnClickListener(view -> {
            Calendar c=Calendar.getInstance();
            if(!((EditText)view).getText().toString().trim().isEmpty()){
                try {
                    Date date=new SimpleDateFormat("HH:mm:ss").parse(((EditText)view).getText().toString().trim());
                    c.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            timePicker(view,c);
        });

        return v;
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if (jsonObject.has("method")){
            try {
                if (jsonObject.getString("method").equals("addstation") && jsonObject.getBoolean("success")){
                    Toast.makeText(getActivity(), "Station added successfully", Toast.LENGTH_LONG).show();
                    getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                    getActivity().getFragmentManager().popBackStackImmediate();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        pd.dismiss();
        AppConfig.alertMsg(getActivity(),error.toString());
    }

    private void timePicker(View v,Calendar c){
        NumberFormat nf3 = new DecimalFormat("#00");
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute) -> ((EditText)v).setText(nf3.format(hourOfDay) + ":" + nf3.format(minute)+":00"), mHour, mMinute, false);
        timePickerDialog.show();
    }
}
