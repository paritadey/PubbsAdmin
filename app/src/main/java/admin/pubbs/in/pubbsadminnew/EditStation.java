package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import admin.pubbs.in.pubbsadminnew.Adapter.EditStationAdpater;
import admin.pubbs.in.pubbsadminnew.BottomSheet.CustomDivider;
import admin.pubbs.in.pubbsadminnew.BottomSheet.RecyclerTouchListener;
import admin.pubbs.in.pubbsadminnew.List.EditStationList;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.HttpParse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

public class EditStation extends AppCompatActivity implements AsyncResponse {
    //xml based variables
    private RecyclerView recyclerView;
    ImageView back;
    private TextView bicycleTv;
    EditText inputSearch;
    ProgressBar circularProgressbar;
    //java based variables
    String area_name, area_id, zone_id, admin_mobile, admin_type;
    //private modifier-- accessible in the inner class only, where the variable is declared
    private EditStationAdpater editStationAdpater;
    private List<EditStationList> editStationLists = new ArrayList<>();
    private String TAG = EditStation.class.getSimpleName();
    SharedPreferences sharedPreferences;
    int primary, secondary;
    String finalResult;
    String UserUrl = "http://pubbs.in/api/1.0/addStationType.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_station);
        initView();
    }

    public void initView() {
        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //getting area_id, area_name, areaLatLon data as intent data from AddNewBicycle class
        Intent intent = getIntent();
        area_id = intent.getStringExtra("area_name");
        area_name = intent.getStringExtra("area_id");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        admin_mobile = sharedPreferences.getString("adminmobile", "null");
        admin_type = sharedPreferences.getString("admin_type", "null");
        zone_id = sharedPreferences.getString("zone_id", "null");
        Log.d(TAG, "Area details: " + area_id + "\t" + area_name + "\t" + zone_id + "\t" + admin_mobile + "\t" + admin_type);
        back = findViewById(R.id.back_button);
        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type1);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        //RecyclerView will show the objects
        recyclerView = findViewById(R.id.recycler_view);
        editStationAdpater = new EditStationAdpater(editStationLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(editStationAdpater);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EditStationList lists = editStationLists.get(position);
                String station_id = lists.getStation_id();
                String station_name = lists.getStation_name();
                String area_name = lists.getArea_name();
                String area_id = lists.getArea_id();
                Log.d(TAG, "Station Details:" + station_id + "\t" + station_name + "\t" + area_name + "\t" + area_id);
                changeStationType(admin_mobile, admin_type, zone_id, area_id, area_name, station_id, station_name);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        //on clicking the back button redirects back to Dashboard
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStation.this, EditAreaStation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditStation.this, EditAreaStation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //loadData() will fetch the result set from the server
    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getAreaStationType");
            jo.put("area_id", area_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, EditStation.this, getApplicationContext()).executeJsonRequest();

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        editStationLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getAreaStationType") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            EditStationList user = new EditStationList(jo.getString("station_name"),
                                    jo.getString("station_id"), jo.getString("area_name"), jo.getString("area_id"));
                            editStationLists.add(user);
                        }
                    }
                } else {
                    showMessageDialog("No Station Present for adding");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editStationAdpater.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(VolleyError error) {
        showMessageDialog("Server Error !");
    }

    //if any error occurred or success msg will show via a dialog box
    private void showMessageDialog(String message) {
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
                if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }
                Intent intent = new Intent(EditStation.this, EditAreaStation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    private void changeStationType(String admin_mobile, String admin_type, String zone_id, String area_id, String area_name, String station_id, String station_name) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(EditStation.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_station, null);
        final TextView station_type_tv = (TextView) dialogView.findViewById(R.id.station_type_tv);
        station_type_tv.setTypeface(type1);
        final RadioGroup station_type = dialogView.findViewById(R.id.station_type);
        final RadioButton radioPrimaryStation = dialogView.findViewById(R.id.radioPrimaryStation);
        radioPrimaryStation.setTypeface(type1);
        final RadioButton radioSecondaryStation = dialogView.findViewById(R.id.radioSecondaryStation);
        radioSecondaryStation.setTypeface(type1);
        final Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setTypeface(type2);
        station_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioPrimaryStation) {
                    Log.d(TAG, "Primary Station checked");
                    primary = 1;
                    secondary = 0;
                    Log.d(TAG, "Values:" + primary + "\t" + secondary);
                } else if (checkedId == R.id.radioSecondaryStation) {
                    Log.d(TAG, "Secondary Station checked");
                    primary = 0;
                    secondary = 1;
                    Log.d(TAG, "Station Type change:" + primary + "\t" + secondary);
                }
            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(admin_mobile, admin_type, zone_id, area_id, area_name, station_id, station_name, String.valueOf(primary), String.valueOf(secondary));
                dialogBuilder.dismiss();

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(true);
    }

    public void sendData(String admin_mobile, String admin_type, String zone_id, String area_id, String area_name, String station_id, String station_name, String primary, String secondary) {
        class sendDataClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Log.d(TAG, httpResponseMsg.toString());
                showMessageDialog(httpResponseMsg.toString());
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("admin_mobile", params[0]);
                hashMap.put("admin_type", params[1]);
                hashMap.put("zone_id", params[2]);
                hashMap.put("area_id", params[3]);
                hashMap.put("area_name", params[4]);
                hashMap.put("station_id", params[5]);
                hashMap.put("station_name", params[6]);
                hashMap.put("primary", params[7]);
                hashMap.put("secondary", params[8]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }
        sendDataClass sendDataClass = new sendDataClass();
        sendDataClass.execute(admin_mobile, admin_type, zone_id,area_id,area_name,station_id,station_name,primary,secondary);
    }
}
