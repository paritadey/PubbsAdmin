package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetEmployeeAuthority extends AppCompatActivity implements AsyncResponse {
    ImageView back;
    private TextView operatorTv;
    String area_id, admin_mobile;
    private RecyclerView recyclerView;
    private EditOperatorAdapter editOperatorAdapter;
    private List<EditOperatorList> editOperatorLists = new ArrayList<>();
    ProgressBar circularProgressbar;
    private String TAG = SetEmployeeAuthority.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_employee_authority);
        initView();
    }

    private void initView() {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get the values of area_id, admin_mobile via intent from ManageOperator
        Intent intent = getIntent();
        area_id = intent.getStringExtra("admin_area_id");
        admin_mobile = intent.getStringExtra("admin_mobile");
        Log.d(TAG, "Area id/Sub-Admin:" + area_id + "--" + admin_mobile);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        back = findViewById(R.id.back_button);
        operatorTv = findViewById(R.id.delete_operator_tv);
        operatorTv.setTypeface(type1);
        //Recyclerview will show the objects
        recyclerView = findViewById(R.id.recyclerview);
        editOperatorAdapter = new EditOperatorAdapter(editOperatorLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(editOperatorAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        EditOperatorList lists = editOperatorLists.get(position);

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        EditOperatorList lists = editOperatorLists.get(position);

                    }
                }));
        back.setOnClickListener(new View.OnClickListener() {
            //this will redirect back to the previous page ManageOperator clearing the stack history
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetEmployeeAuthority.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //this will redirect back to the previous page ManageOperator clearing the stack history
        Intent intent = new Intent(SetEmployeeAuthority.this, ManageOperator.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        //on tapping the menu item of 'Manage Operator' in DashboardActivity will move to Edit Operator
        // class that will fetch the result from the server
        super.onResume();
        loadData();
    }

    //loadData() will fetch the result set from the server
    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getAreaOperator");
            jo.put("area_id", area_id);
            jo.put("admin_mobile", admin_mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, SetEmployeeAuthority.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        editOperatorLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getAreaOperator") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            EditOperatorList user = new EditOperatorList(jo.getString("admin_mobile"),
                                    jo.getString("full_name"), jo.getString("admin_type"), jo.getInt("active"));
                            editOperatorLists.add(user);
                        }
                    }
                } else {
                    Log.d(TAG, "No operator is present.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editOperatorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Problem !");
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
                Intent intent = new Intent(SetEmployeeAuthority.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }


}
