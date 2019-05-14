package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

public class GenerateReport extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    String uphone, uadmin;
    TextView admin_type, choose_area_tv, report_tv, financial_report, usage_report;
    SharedPreferences sharedPreferences;
    Spinner choose_area;
    ImageView back;
    ArrayList<String> area_list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private String TAG = GenerateReport.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        initView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GenerateReport.this, ManageOperator.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void initView() {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        uphone = sharedPreferences.getString("adminmobile", "null"); //uphone is the user_phone to store the mobile number of the user
        uadmin = sharedPreferences.getString("admin_type", "null"); //uadmin is the admin type of the user who is using the app at the moment
        report_tv = findViewById(R.id.report_tv);
        report_tv.setTypeface(type1);
        financial_report = findViewById(R.id.financial_report);
        financial_report.setTypeface(type1);
        financial_report.setOnClickListener(this);
        usage_report = findViewById(R.id.usage_report);
        usage_report.setTypeface(type1);
        admin_type = findViewById(R.id.admin_type);
        admin_type.setTypeface(type1);
        admin_type.setText("My Admin Type:" + "\t" + uadmin);
        choose_area_tv = findViewById(R.id.choose_area_tv);
        choose_area_tv.setTypeface(type1);
        choose_area = findViewById(R.id.choose_area);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, area_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_area.setAdapter(adapter);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
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
            new SendRequest(getResources().getString(R.string.url), jo, GenerateReport.this,
                    getApplicationContext()).executeJsonRequest();
        } else if (uadmin.equals("Super Admin")) {
            JSONObject jo_one = new JSONObject();
            try {
                jo_one.put("method", "getsuperadminparea");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo_one, GenerateReport.this,
                    getApplicationContext()).executeJsonRequest();
        }
    }

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
                        Toast.makeText(getApplicationContext(), "No Area is present", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "No Area is present", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No Area is present.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        Log.d(TAG, "Network issue");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.financial_report:
                startActivity(new Intent(GenerateReport.this, FinancialReport.class));
                break;
            case R.id.back_button:
                Intent intent = new Intent(GenerateReport.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
