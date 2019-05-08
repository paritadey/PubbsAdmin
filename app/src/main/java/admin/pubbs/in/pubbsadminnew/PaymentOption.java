package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentOption extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    String adminmobile, admintype;
    SharedPreferences sharedPreferences;
    private String TAG = PaymentOption.class.getSimpleName();
    TextView add_bank_account_tv, add_bank_tv;
    ImageView back;
    ProgressBar circularProgressbar;
    RelativeLayout add_bank_sheet;
    private RecyclerView recyclerView;
    private PaymentAdapter paymentAdapter;
    private List<BankList> bankLists = new ArrayList<>();
    String finalResult;
    String UserUrl = "http://pubbs.in/api/1.0/updateBank.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    int primary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admintype = sharedPreferences.getString("admin_type", "null");
        Log.d(TAG, "Admin Details:" + adminmobile + "-" + admintype);
        add_bank_sheet = findViewById(R.id.add_bank_sheet);
        add_bank_sheet.setOnClickListener(this);
        add_bank_account_tv = findViewById(R.id.add_bank_account_tv);
        add_bank_account_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        add_bank_tv = findViewById(R.id.add_bank_tv);
        add_bank_tv.setTypeface(type2);
        add_bank_tv.setOnClickListener(this);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        recyclerView = findViewById(R.id.recycler_view);
        paymentAdapter = new PaymentAdapter(bankLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(paymentAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                BankList lists = bankLists.get(position);
                String ifsc = lists.getIfsc();
                int active = lists.getActive();
                showActiveDialog(adminmobile, ifsc, active);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    private void showActiveDialog(String adminmobile, String ifsc, int active) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(PaymentOption.this).create();
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
            roll_over_tv.setText(R.string.primary);
            roll_over_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (R.id.radioYes == checkedId) {
                        primary = 1;
                        Log.d(TAG, "Yes clicked" + "--" + primary + "--" + ifsc);
                    } else if (R.id.radioNo == checkedId) {
                        primary = 0;
                        Log.d(TAG, "No clicked" + "--" + primary + "--" + ifsc);
                    }
                }
            });

        }/* else {
            roll_over_tv.setText(R.string.roll_over);
            roll_over_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (R.id.radioYes == checkedId) {
                        primary = 0;
                        Log.d(TAG, "Yes clicked" + "--" + primary + "--" + admin_mobile);
                    } else if (R.id.radioNo == checkedId) {
                        primary = 1;
                        Log.d(TAG, "No clicked" + "--" + primary + "--" + admin_mobile);
                    }
                }
            });
        }*/
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBankStatusFunction(adminmobile, ifsc);//, String.valueOf(primary));
                dialogBuilder.dismiss();
                loadData();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);

    }

    public void updateBankStatusFunction(final String adminmobile, final String ifsc) {

        class updateOperatorStatusClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Log.d(TAG, httpResponseMsg.toString());
                //  Toast.makeText(getApplicationContext(), "Authority is taken off !",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("adminmobile", params[0]);
                hashMap.put("ifsc", params[1]);
                //hashMap.put("active", params[2]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }
        updateOperatorStatusClass updateOperatorStatusClass = new updateOperatorStatusClass();
        updateOperatorStatusClass.execute(adminmobile, ifsc);
    }

    @Override
    public void onBackPressed() {
        //back press will return to the setting page
        Intent intent = new Intent(PaymentOption.this, Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                //back press will return to the setting page
                Intent intent = new Intent(PaymentOption.this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.add_bank_tv:
                startActivity(new Intent(PaymentOption.this, AddBankDetails.class));
                break;
            case R.id.add_bank_sheet:
                startActivity(new Intent(PaymentOption.this, AddBankDetails.class));
                break;
            default:
                break;
        }
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
            jo.put("method", "get_admin_bank_details");
            jo.put("adminmobile", adminmobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, PaymentOption.this, getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        bankLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("get_admin_bank_details") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            BankList user = new BankList(jo.getString("bank_name"), jo.getString("branch_name"),
                                    jo.getString("ifsc"), jo.getString("account_number"), jo.getInt("active_account"));
                            bankLists.add(user);
                        }
                    } else {
                        showMessageDialog("No data is present.");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        paymentAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResponseError(VolleyError error) {
        showMessageDialog("Server Problem !");
    }

    private void showMessageDialog(String message) {
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
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }
                dialogBuilder.dismiss();
            }
        });

    }


}
