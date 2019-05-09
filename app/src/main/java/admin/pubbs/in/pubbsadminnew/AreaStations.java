package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import admin.pubbs.in.pubbsadminnew.Adapter.AddNewBicycleAdapter;
import admin.pubbs.in.pubbsadminnew.List.DeleteStationList;
/*created by Parita Dey*/

public class AreaStations extends AppCompatActivity implements AsyncResponse {
    //xml based variables
    private RecyclerView recyclerView;
    ImageView back;
    private TextView bicycleTv;
    EditText inputSearch;
    ProgressBar circularProgressbar;
    //java based variables
    String area_name, area_id, areaLatLon;
    //private modifier-- accessible in the inner class only, where the variable is declared
    private AddNewBicycleAdapter addNewBicycleAdapter;
    private List<DeleteStationList> deleteStationLists = new ArrayList<>();
    private String TAG = AreaStations.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_stations);
        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //getting area_id, area_name, areaLatLon data as intent data from AddNewBicycle class
        Intent intent = getIntent();
        area_id = intent.getStringExtra("area_name");
        area_name = intent.getStringExtra("area_id");
        areaLatLon = intent.getStringExtra("latlon");
        Log.d(TAG, "Area details: " + area_id + "\t" + area_name + "\t" + areaLatLon);
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
        addNewBicycleAdapter = new AddNewBicycleAdapter(deleteStationLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addNewBicycleAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DeleteStationList lists = deleteStationLists.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        //on clicking the back button redirects back to Dashboard
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AreaStations.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

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
            jo.put("method", "getAreaStation");
            jo.put("area_id", area_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AreaStations.this, getApplicationContext()).executeJsonRequest();

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
                Intent intent = new Intent(AreaStations.this, AddNewBicycle.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        deleteStationLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getAreaStation") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            DeleteStationList user = new DeleteStationList(jo.getString("station_name"),
                                    jo.getString("station_id"), jo.getString("area_name"), jo.getString("area_id"));
                            deleteStationLists.add(user);
                        }
                    }
                } else {
                    showMessageDialog("No Station has created.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        addNewBicycleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(VolleyError error) {
        showMessageDialog("Server Error !");
    }

}
