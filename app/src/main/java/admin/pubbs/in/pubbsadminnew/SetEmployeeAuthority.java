package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetEmployeeAuthority extends AppCompatActivity implements AsyncResponse {
    ImageView back;
    private TextView operatorTv;
    String area_id, admin_mobile;
    private RecyclerView recyclerView;
    private SetEmployeeAuthorityAdapter setEmployeeAuthorityAdapter;
    private List<EditOperatorList> editOperatorLists = new ArrayList<>();
    ProgressBar circularProgressbar;
    private String TAG = SetEmployeeAuthority.class.getSimpleName();
    int rank, manager, finance, service, driver, total_rank;
    String finalResult;
    String UserUrl = "http://pubbs.in/api/1.0/setEmployeeAuthority.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


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
        setEmployeeAuthorityAdapter = new SetEmployeeAuthorityAdapter(editOperatorLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(setEmployeeAuthorityAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        EditOperatorList lists = editOperatorLists.get(position);
                        String adminmobile = lists.getAdminmobile();
                        String fullname = lists.getFullname();
                        String admintype = lists.getAdmin_type();
                        showAuthorityDialog(fullname, adminmobile, admintype);
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

    private void showAuthorityDialog(String fullname, String admin_mobile, String admin_type) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(SetEmployeeAuthority.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_employee_authority, null);
        final TextView authority_tv = (TextView) dialogView.findViewById(R.id.authority_tv);
        authority_tv.setTypeface(type1);
        final CheckBox managerCheck = dialogView.findViewById(R.id.managerCheck);
        managerCheck.setTypeface(type1);
        managerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (managerCheck.isChecked()) {
                    rank = 1; // rank 1 is manager
                    manager = 1;
                } else {
                    rank = 0;
                    manager = 0;
                }
                total_rank += rank;
                Log.d(TAG, "Total Rank:" + total_rank);
            }
        });
        final CheckBox financeCheck = dialogView.findViewById(R.id.financeCheck);
        financeCheck.setTypeface(type1);
        financeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (financeCheck.isChecked()) {
                    rank = 2; // rank 2 is finance
                    finance = 1;
                } else {
                    rank = 0;
                    finance = 0;
                }
                total_rank += rank;
                Log.d(TAG, "Total Rank:" + total_rank);
            }
        });
        final CheckBox serviceCheck = dialogView.findViewById(R.id.serviceCheck);
        serviceCheck.setTypeface(type1);
        serviceCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceCheck.isChecked()) {
                    rank = 3; //rank 3 is service
                    service = 1;
                } else {
                    rank = 0;
                    service = 0;
                }
                total_rank += rank;
                Log.d(TAG, "Total Rank:" + total_rank);
            }
        });
        final CheckBox driverCheck = dialogView.findViewById(R.id.driverCheck);
        driverCheck.setTypeface(type1);
        driverCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (driverCheck.isChecked()) {
                    rank = 4; // rank 4 is driver
                    driver = 1;
                } else {
                    rank = 0;
                    driver = 0;
                }
                total_rank += rank;
                Log.d(TAG, "Total Rank:" + total_rank);
            }
        });
        final Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setTypeface(type2);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthorityFunction(fullname, admin_mobile, admin_type, String.valueOf(total_rank), String.valueOf(manager),
                        String.valueOf(finance), String.valueOf(service), String.valueOf(driver));
                dialogBuilder.dismiss();

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);

    }

    public void addAuthorityFunction(String fullname, String admin_mobile, String admin_type, String rank, String manager,
                                     String finance, String service, String driver) {
        class addAuthorityFunctionClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                // Toast.makeText(getApplicationContext(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                showDialog(httpResponseMsg.toString());
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("fullname", params[0]);
                hashMap.put("admin_mobile", params[1]);
                hashMap.put("admin_type", params[2]);
                hashMap.put("rank", params[3]);
                hashMap.put("manager", params[4]);
                hashMap.put("finance", params[5]);
                hashMap.put("service", params[6]);
                hashMap.put("driver", params[7]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }

        addAuthorityFunctionClass addAuthorityFunctionClass = new addAuthorityFunctionClass();
        addAuthorityFunctionClass.execute(fullname, admin_mobile, admin_type, rank, manager, finance, service, driver);

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
        setEmployeeAuthorityAdapter.notifyDataSetChanged();

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
