package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
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
/*created by Parita Dey*/

public class EditOperator extends AppCompatActivity implements AsyncResponse {
    ImageView back;
    private TextView operatorTv;
    EditText inputSearch;
    String area_id, admin_mobile;
    private String TAG = EditOperator.class.getSimpleName();
    private RecyclerView recyclerView;
    private EditOperatorAdapter editOperatorAdapter;
    private List<EditOperatorList> editOperatorLists = new ArrayList<>();
    int rollOver;
    String finalResult;
    String UserUrl = "http://pubbs.in/api/1.0/updateOperatorStatus.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    ProgressBar circularProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_operator);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        area_id = intent.getStringExtra("admin_area_id");
        admin_mobile = intent.getStringExtra("admin_mobile");
        Log.d(TAG, "Area id/Sub-Admin:" + area_id + "--" + admin_mobile);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        back = findViewById(R.id.back_button);
        operatorTv = findViewById(R.id.delete_operator_tv);
        operatorTv.setTypeface(type1);
        inputSearch = findViewById(R.id.input_search);
        inputSearch.setTypeface(type1);
        recyclerView = findViewById(R.id.recyclerview);
        editOperatorAdapter = new EditOperatorAdapter(editOperatorLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(editOperatorAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EditOperatorList lists = editOperatorLists.get(position);
                String adminmobile = lists.getAdminmobile();
                String fullname = lists.getFullname();
                String admintype = lists.getAdmin_type();
                int active = lists.getActive();
                showRollOverDialog(fullname, adminmobile, admintype, active);
            }

            @Override
            public void onLongClick(View view, int position) {
                EditOperatorList lists = editOperatorLists.get(position);

            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditOperator.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void showRollOverDialog(String fullname, String admin_mobile, String admin_type, int active) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(EditOperator.this).create();
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
                Intent intent = new Intent(EditOperator.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
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
            jo.put("method", "getAreaOperator");
            jo.put("area_id", area_id);
            jo.put("admin_mobile", admin_mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, EditOperator.this,
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
}
