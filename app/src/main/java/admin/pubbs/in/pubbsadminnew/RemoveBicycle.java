package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import admin.pubbs.in.pubbsadminnew.Adapter.RemoveBicycleAdapter;
import admin.pubbs.in.pubbsadminnew.List.RemoveBicycleList;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;
/*created by Parita Dey*/

public class RemoveBicycle extends AppCompatActivity implements AsyncResponse {
    private RecyclerView recyclerView;
    private RemoveBicycleAdapter removeBicycleAdapter;
    private List<RemoveBicycleList> removeBicycleLists = new ArrayList<>();
    ImageView back;
    private TextView bicycleTv;
    EditText inputSearch;
    ProgressBar circularProgressbar;
    SharedPreferences sharedPreferences;
    String adminmobile, area_id, admin_type;
    private String TAG = RemoveBicycle.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_bicycle);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admin_type = sharedPreferences.getString("admin_type", null);
        area_id = sharedPreferences.getString("area_id", null);
        Log.d(TAG, "Area details:" + area_id + "\t" + adminmobile + "\t" + admin_type);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        back = findViewById(R.id.back_button);
        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type1);
        inputSearch = findViewById(R.id.input_search);
        inputSearch.setTypeface(type1);
        recyclerView = findViewById(R.id.recycler_view);
        removeBicycleAdapter = new RemoveBicycleAdapter(removeBicycleLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(removeBicycleAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RemoveBicycleList lists = removeBicycleLists.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoveBicycle.this, DashBoardActivity.class);
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

    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        if (admin_type.equals("Employee")) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "getemployeecycle");
                jo.put("area_id", area_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo, RemoveBicycle.this,
                    getApplicationContext()).executeJsonRequest();

        } else if (admin_type.equals("Sub Admin")) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "geteachcycle");
                jo.put("adminmobile", adminmobile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getResources().getString(R.string.url), jo, RemoveBicycle.this,
                    getApplicationContext()).executeJsonRequest();
        }
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
                if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }
                Intent intent = new Intent(RemoveBicycle.this, DashBoardActivity.class);
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
        removeBicycleLists.clear();
        if (admin_type.equals("Employee")) {
            if (jsonObject.has("method")) {
                try {
                    if (jsonObject.getString("method").equals("getemployeecycle") && jsonObject.getBoolean("success")) {
                        JSONArray ja = jsonObject.getJSONArray("data");
                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                RemoveBicycleList list = new RemoveBicycleList(jo.getString("address"));
                                removeBicycleLists.add(list);
                            }
                        }
                    } else {
                        showDialog("No Cycles is present under this");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (admin_type.equals("Sub Admin")) {
            if (jsonObject.has("method")) {
                try {
                    if (jsonObject.getString("method").equals("geteachcycle") && jsonObject.getBoolean("success")) {
                        JSONArray ja = jsonObject.getJSONArray("data");
                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                RemoveBicycleList list = new RemoveBicycleList(jo.getString("address"));
                                removeBicycleLists.add(list);
                            }
                        }
                    } else {
                        showDialog("No Cycles is present under this");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        removeBicycleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Error !");
    }
}
