package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import admin.pubbs.in.pubbsadminnew.Adapter.AddOperatorAdpater;
import admin.pubbs.in.pubbsadminnew.List.AreaList;
/*created by Parita Dey*/

public class AddOperator extends AppCompatActivity implements AsyncResponse {
    ImageView back;
    private TextView addOperatorTv;
    ProgressBar circularProgressbar;
    private RecyclerView recyclerView;
    private AddOperatorAdpater addOperatorAdpater;
    private List<AreaList> areaLists = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String adminmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operator);
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //sharedpreference will store the admin mobile number who is using the app
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        back = findViewById(R.id.back_button);
        addOperatorTv = findViewById(R.id.add_operator_tv);
        addOperatorTv.setTypeface(type1);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        recyclerView = findViewById(R.id.recycler_view);
        addOperatorAdpater = new AddOperatorAdpater(areaLists);
        //RecyclerView will show all the objects
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addOperatorAdpater);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AreaList lists = areaLists.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            //this will redirect back to the previous page ManageOperator clearing the stack history
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddOperator.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        //on tapping the menu item of 'Manage Operator' in DashboardActivity will move to Add Operator
        // class that will fetch the result from the server
        super.onResume();
        loadData();
    }

    //loadData() will fetch the result set from the server
    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getallmaparea");
            jo.put("adminmobile", adminmobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AddOperator.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        areaLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getallmaparea") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            AreaList list = new AreaList(jo.getString("area_name"),
                                    jo.getString("area_id"), jo.getString("area_lat_lon"));
                            areaLists.add(list);
                        }
                    }
                } else {
                    showDialog("No Area is present");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        addOperatorAdpater.notifyDataSetChanged();
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
                Intent intent = new Intent(AddOperator.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        //this will redirect back to the previous page ManageOperator clearing the stack history
        Intent intent = new Intent(AddOperator.this, ManageOperator.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
