package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import admin.pubbs.in.pubbsadminnew.Adapter.SubAdminAdpater;
import admin.pubbs.in.pubbsadminnew.List.SubadminList;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;
/*created by Parita Dey*/

public class SubAdmin extends AppCompatActivity implements AsyncResponse {
    ImageView back;
    private TextView subadmin_tv;
    ProgressBar circularProgressbar;
    private RecyclerView recyclerView;
    private SubAdminAdpater subAdminAdpater;
    private List<SubadminList> subadminLists = new ArrayList<>();
    private String TAG = AddNewStation.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin);
        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    @Override
    public void onBackPressed() {
        //on back press move back to the main landing screen i.e Dashboard by clearing all the previous stack history
        Intent intent = new Intent(SubAdmin.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void initView() {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        subadmin_tv = findViewById(R.id.subadmin_tv);
        subadmin_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();

        //RecyclerView will show all the objects
        recyclerView = findViewById(R.id.recycler_view);
        subAdminAdpater = new SubAdminAdpater(subadminLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subAdminAdpater);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubadminList lists = subadminLists.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            //this will redirect back to the previous page Dashboard clearing the stack history
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubAdmin.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        //on tapping the menu item of 'Add Lock' in DashboardActivity fetch the result from the server
        super.onResume();
        loadData();
    }

    //loadData() will fetch the result set from the server
    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "get_sub_admin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, SubAdmin.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        subadminLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("get_sub_admin") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            SubadminList list = new SubadminList(jo.getString("full_name"),
                                    jo.getString("admin_mobile"), jo.getString("area_id"));
                            subadminLists.add(list);
                        }
                    }
                } else {
                    showDialog("No result is present");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        subAdminAdpater.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Error !");
    }

    //if any error occurred or success msg will show via a dialog box
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
                Intent intent = new Intent(SubAdmin.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }
}