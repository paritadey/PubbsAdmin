package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LORD on 9/17/2017.
 */

public class DashboardFragment extends Fragment implements AsyncResponse {

    String uphone, uadmin;
    TextView admin_type, choose_area_tv, report_tv, financial_report, usage_report, growth_report;
    SharedPreferences sharedPreferences;
    Spinner choose_area;
    ArrayList<String> area_list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private String TAG = DashboardFragment.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Dashboard");
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        uphone = sharedPreferences.getString("adminmobile", "null"); //uphone is the user_phone to store the mobile number of the user
        uadmin = sharedPreferences.getString("admin_type", "null"); //uadmin is the admin type of the user who is using the app at the moment
        report_tv = v.findViewById(R.id.report_tv);
        report_tv.setTypeface(type1);
        financial_report = v.findViewById(R.id.financial_report);
        financial_report.setTypeface(type1);
        usage_report = v.findViewById(R.id.usage_report);
        usage_report.setTypeface(type1);
        growth_report = v.findViewById(R.id.growth_report);
        growth_report.setTypeface(type1);
        admin_type = v.findViewById(R.id.admin_type);
        admin_type.setTypeface(type1);
        admin_type.setText("My Admin Type:" + "\t" + uadmin);
        choose_area_tv = v.findViewById(R.id.choose_area_tv);
        choose_area_tv.setTypeface(type1);
        choose_area = v.findViewById(R.id.choose_area);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, area_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_area.setAdapter(adapter);
        return v;
    }

    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        if (uadmin.equals("Sub Admin") || uadmin.equals("Employee")) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "getallmaparea");
                jo.put("adminmobile", uphone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo, DashboardFragment.this, getActivity()).executeJsonRequest();
        } else if (uadmin.equals("Super Admin")) {
            JSONObject jo_one = new JSONObject();
            try {
                jo_one.put("method", "getsuperadminparea");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo_one, DashboardFragment.this, getActivity()).executeJsonRequest();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(JSONObject jsonObject) {
        area_list.add("Area List");
        if (uadmin.equals("Sub Admin") || uadmin.equals("Employee")) {
            if (jsonObject.has("method")) {
                try {
                    if (jsonObject.getString("method").equals("getallmaparea") && jsonObject.getBoolean("success")) {
                        JSONArray ja = jsonObject.getJSONArray("data");
                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                area_list.add(jo.getString("area_name"));
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "No Area is present", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No Area is present.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
        } else if (uadmin.equals("Super Admin")) {
            if (jsonObject.has("method")) {
                try {
                    if (jsonObject.getString("method").equals("getsuperadminparea") && jsonObject.getBoolean("success")) {
                        JSONArray ja = jsonObject.getJSONArray("data");
                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                area_list.add(jo.getString("area_name"));
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "No Area is present", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No Area is present.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponseError(VolleyError error) {
        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Server Error");
    }

}
