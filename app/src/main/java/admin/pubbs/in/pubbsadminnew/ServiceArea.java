package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class ServiceArea extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    SharedPreferences sharedPreferences;
    String adminmobile;
    private String TAG = ServiceArea.class.getSimpleName();
    String area_id, area_name, date_time;
    ImageView back;
    TextView serviceArea, all_set_tv, launchTv, areaname, areaid;
    Button start_service, stop_service;
    ProgressBar circularProgressbar;
    SharedPreferences.Editor editor;
    String service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_area);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        area_id = intent.getStringExtra("area_name");
        area_name = intent.getStringExtra("area_id");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Area details: " + adminmobile + "-" + area_name + "-" + area_id);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
        date_time = sdf.format(date);

        circularProgressbar = findViewById(R.id.circularProgressbar);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        serviceArea = findViewById(R.id.service_area);
        serviceArea.setTypeface(type1);
        all_set_tv = findViewById(R.id.all_set_tv);
        all_set_tv.setTypeface(type2);
        launchTv = findViewById(R.id.launchTv);
        launchTv.setTypeface(type2);
        areaname = findViewById(R.id.area_name);
        areaname.setTypeface(type1);
        areaname.setText(area_name);
        areaid = findViewById(R.id.area_id);
        areaid.setTypeface(type1);
        areaid.setText(area_id);
        start_service = findViewById(R.id.start_service);
        start_service.setTypeface(type3);
        start_service.setOnClickListener(this);
        stop_service = findViewById(R.id.stop_service);
        stop_service.setTypeface(type3);
        stop_service.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(ServiceArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.start_service:
                stop_service.setEnabled(false);
                sendServiceArea(adminmobile, area_name, area_id, date_time, "start");
                break;
            case R.id.stop_service:
                //getServiceStatus(area_id);
                start_service.setEnabled(false);
                //updateService(area_name, date_time, "stop");
                break;
            default:
                break;
        }
    }

    public void updateService(String area_name, String date_time, String status) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method_service", "update_service_area");
            jo.put("area_name", area_name);
            jo.put("date_time", date_time);
            jo.put("status", status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, ServiceArea.this,
                getApplicationContext()).executeJsonRequest();

    }

    public void sendServiceArea(String adminmobile, String area_name, String area_id, String date_time, String status) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "service_area");
            jo.put("adminmobile", adminmobile);
            jo.put("area_name", area_id);
            jo.put("area_id", area_name);
            jo.put("date_time", date_time);
            jo.put("status", status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, ServiceArea.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("service_area") && jsonObject.getBoolean("success")) {
                    showDialog("Area service is launched !");
                    Log.d(TAG, "Suceess");
                } else {
                    Log.d(TAG, "couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Problem !");
    }

    private void showDialog(String message) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        final TextView serverProblem = (TextView) dialogView.findViewById(R.id.server_problem);
        final TextView extraLine = (TextView) dialogView.findViewById(R.id.extra_line);
        extraLine.setTypeface(type1);
        serverProblem.setTypeface(type1);
        serverProblem.setText(message);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(ServiceArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
