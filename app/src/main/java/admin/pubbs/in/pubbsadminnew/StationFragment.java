package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by LORD on 8/29/2017.
 */

public class StationFragment extends Fragment implements AsyncResponse{
    TextView name;
    Button virtual,add_cycles,save;
    String stn_name;
    int stn_id;
    boolean isVirtual=false;
    EditText openHour,closeHour,gr,gf;
    Spinner holdtime;
    ProgressDialog pd;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stn_id=getArguments().getInt("id");
        stn_name=getArguments().getString("name");
        isVirtual=getArguments().getBoolean("virtual");
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Station Details");
        View v=inflater.inflate(R.layout.fragment_station,container,false);
        name= (TextView) v.findViewById(R.id.name);
        virtual= (Button) v.findViewById(R.id.virtual);
        add_cycles= (Button) v.findViewById(R.id.add_cycle);
        save=(Button) v.findViewById(R.id.button2);
        openHour= (EditText) v.findViewById(R.id.oh);
        closeHour= (EditText) v.findViewById(R.id.ch);
        gr= (EditText) v.findViewById(R.id.gr);
        gf= (EditText) v.findViewById(R.id.gf);
        holdtime=v.findViewById(R.id.holdtime);
        if(!isVirtual)virtual.setVisibility(View.VISIBLE);
        openHour.setInputType(InputType.TYPE_NULL);
        closeHour.setInputType(InputType.TYPE_NULL);
        name.setText(getArguments().getString("name"));
        pd.setMessage("Loading...");
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","station_details");
            jo.put("sid",stn_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getString(R.string.url),jo,this,getActivity()).executeJsonRequest();

        virtual.setOnClickListener(view -> {
            Bundle args=new Bundle();
            args.putString("name",stn_name);
            args.putInt("id",stn_id);
            args.putDouble("lat",getArguments().getDouble("lat"));
            args.putDouble("lng",getArguments().getDouble("lng"));
            VirtualStationListFragment vsf=new VirtualStationListFragment();
            vsf.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.myFrame,vsf)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        });

        add_cycles.setOnClickListener(view -> {
            Bundle args=new Bundle();
            args.putString("name",stn_name);
            args.putInt("id",stn_id);
            CycleListFragment cf=new CycleListFragment();
            cf.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.myFrame,cf)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        });
        openHour.setOnFocusChangeListener((v1, hasFocus) -> {
            if (hasFocus) {
                Calendar c=Calendar.getInstance();
                if(!((EditText)v1).getText().toString().trim().isEmpty()){
                    try {
                        c.setTime(new SimpleDateFormat("HH:mm:ss").parse(((EditText)v1).getText().toString().trim()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                timePicker(v1,c);
            }
        });

        closeHour.setOnFocusChangeListener((v1, hasFocus) -> {
            if (hasFocus) {
                Calendar c=Calendar.getInstance();
                if(!((EditText)v1).getText().toString().trim().isEmpty()){
                    try {
                        c.setTime(new SimpleDateFormat("HH:mm:ss").parse(((EditText)v1).getText().toString().trim()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                timePicker(v1,c);
            }
        });

        openHour.setOnClickListener(view->{
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
        closeHour.setOnClickListener(view->{
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

        save.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle(getString(R.string.app_name));
            alertDialog.setMessage("Sure to update?");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Yes", (dialog, which) -> {
                pd.setMessage("Updating...");
                pd.show();
                JSONObject jo1 = new JSONObject();
                try {
                    jo1.put("method", "updatestation");
                    jo1.put("open", openHour.getText().toString().trim());
                    jo1.put("close", closeHour.getText().toString().trim());
                    jo1.put("sid", stn_id);
                    jo1.put("geofine",gf.getText().toString().trim());
                    jo1.put("radius",gr.getText().toString().trim());
                    jo1.put("hold",holdtime.getSelectedItem().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new SendRequest(getString(R.string.url), jo1, StationFragment.this, getActivity()).executeJsonRequest();
            });
            alertDialog.setNegativeButton("No", null);
            // Showing Alert Message
            alertDialog.show();
        });
        return v;
    }

    private void timePicker(final View v,Calendar c){
        NumberFormat nf3 = new DecimalFormat("#00");
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute) -> ((EditText)v).setText(nf3.format(hourOfDay) + ":" + nf3.format(minute)+":00"), mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if (jsonObject.has("method")){
            try{
                if (jsonObject.getBoolean("success")){
                    if (jsonObject.getString("method").equals("station_details")){
                        openHour.setText(jsonObject.getString("open"));
                        closeHour.setText(jsonObject.getString("close"));
                        gf.setText(jsonObject.getString("fine"));
                        gr.setText(jsonObject.getString("radius"));
                    }
                    if (jsonObject.getString("method").equals("updatestation")){
                        Toast.makeText(getActivity(), "Updated success fully", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        pd.dismiss();
    }
}
