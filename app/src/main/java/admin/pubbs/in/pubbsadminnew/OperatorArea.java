package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*created by Parita Dey*/

public class OperatorArea extends AppCompatActivity implements AsyncResponse {
    private RecyclerView recyclerView;
    private OperatorAdapter operatorAdapter;
    private List<OperatorList> operatorLists = new ArrayList<>();
    ImageView back;
    private TextView operatorTv;
    private String TAG = OperatorArea.class.getSimpleName();
    ProgressBar circularProgressbar;
    String area_id;
    SharedPreferences sharedPreferences;
    String admin_mobile;
    int rollOver;
    String finalResult;
    String UserUrl = "http://pubbs.in/api/1.0/updateOperatorStatus.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_area);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        operatorTv = findViewById(R.id.delete_operator_tv);
        operatorTv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        recyclerView = findViewById(R.id.recyclerview);
        operatorAdapter = new OperatorAdapter(operatorLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(operatorAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                OperatorList lists = operatorLists.get(position);
                String fullname = lists.getFullname();
                String adminmobile = lists.getAdminmobile();
                String areaname = lists.getArea_name();
                String admintype = lists.getAdmin_type();
                int active = lists.getActive();
                showRollOverDialog(fullname, adminmobile, areaname, admintype, active);
            }

            @Override
            public void onLongClick(View view, int position) {
                OperatorList lists = operatorLists.get(position);

            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OperatorArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void showRollOverDialog(String fullname, String admin_mobile, String area_name, String admin_type, int active) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(OperatorArea.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_operator_dialog, null);
        final TextView roll_over_tv = (TextView) dialogView.findViewById(R.id.roll_over_tv);
        roll_over_tv.setTypeface(type1);
        final RadioGroup roll_over_type = (RadioGroup) dialogView.findViewById(R.id.roll_over_type);
        final RadioButton radioYes = (RadioButton) dialogView.findViewById(R.id.radioYes);
        final RadioButton radioNo = (RadioButton) dialogView.findViewById(R.id.radioNo);
        radioYes.setTypeface(type1);
        radioNo.setTypeface(type1);
        final Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);

        if (active == 0) {
            roll_over_tv.setText(R.string.roll_in);
            roll_over_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (R.id.radioYes == checkedId) {
                        rollOver = 1;
                        Log.d(TAG, "Yes clicked" + "--" + rollOver + "--" + admin_mobile);
                    } else if (R.id.radioNo == checkedId) {
                        rollOver = 0;
                        Log.d(TAG, "No clicked" + "--" + rollOver + "--" + admin_mobile);
                    }
                }
            });

        } else {
            roll_over_tv.setText(R.string.roll_over);
            roll_over_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (R.id.radioYes == checkedId) {
                        rollOver = 0;
                        Log.d(TAG, "Yes clicked" + "--" + rollOver + "--" + admin_mobile);
                    } else if (R.id.radioNo == checkedId) {
                        rollOver = 1;
                        Log.d(TAG, "No clicked" + "--" + rollOver + "--" + admin_mobile);
                    }
                }
            });
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOperatorStatusFunction(admin_mobile, String.valueOf(rollOver));
                dialogBuilder.dismiss();
                loadData();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);

    }

    public void updateOperatorStatusFunction(final String admin_mobile, final String active) {

        class updateOperatorStatusClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Log.d(TAG, httpResponseMsg.toString());
              //  Toast.makeText(getApplicationContext(), "Authority is taken off !",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("admin_mobile", params[0]);
                hashMap.put("active", params[1]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }
        updateOperatorStatusClass updateOperatorStatusClass = new updateOperatorStatusClass();
        updateOperatorStatusClass.execute(admin_mobile, active);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getoperator");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, OperatorArea.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        operatorLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getoperator") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            OperatorList user = new OperatorList(jo.getString("full_name"),
                                    jo.getString("admin_mobile"), jo.getString("area_name"),
                                    jo.getString("admin_type"), jo.getInt("active"));
                            operatorLists.add(user);
                        }
                    }
                } else {
                    Log.d(TAG, "No operator is present.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        operatorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Problem !");
    }

    private void showDialog(String message) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(getApplicationContext()).create();
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
                if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }
                Intent intent = new Intent(OperatorArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
