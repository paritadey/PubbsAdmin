package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddBankDetails extends AppCompatActivity implements AsyncResponse {
    TextView add_bank_account_tv, description;
    TextView ifsc_layout, account_holder_bank_layout, account_holder_branch_layout, account_number_layout, confirm_account_number_layout, account_holder_layout, phone_number_layout;
    EditText ifsc, account_holder_bank, account_holder_branch, account_number, confirm_account_number, account_holder, phone_number;
    Button confirm;
    String user_ifsc, user_bank_name, user_branch_name, user_account_number, user_account_holder, user_phone_number, admin_mobile, admin_type, area_id, finalResult;
    SharedPreferences sharedPreferences;
    private String TAG = AddBankDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_details);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        admin_mobile = sharedPreferences.getString("adminmobile", null);
        admin_type = sharedPreferences.getString("admin_type", "null");
        Log.d(TAG, "Admin Details:" + admin_mobile + "-" + admin_type);
        add_bank_account_tv = findViewById(R.id.add_bank_account_tv);
        add_bank_account_tv.setTypeface(type1);
        description = findViewById(R.id.description);
        description.setTypeface(type1);
        ifsc_layout = findViewById(R.id.ifsc_layout);
        ifsc_layout.setTypeface(type1);
        ifsc = findViewById(R.id.ifsc);
        ifsc.setTypeface(type1);
        user_ifsc = ifsc.getText().toString();
        account_holder_bank_layout = findViewById(R.id.account_holder_bank_layout);
        account_holder_bank_layout.setTypeface(type1);
        account_holder_bank = findViewById(R.id.account_holder_bank);
        account_holder_bank.setTypeface(type1);
        user_bank_name = account_holder_bank.getText().toString();
        account_holder_branch_layout = findViewById(R.id.account_holder_branch_layout);
        account_holder_branch_layout.setTypeface(type1);
        account_holder_branch = findViewById(R.id.account_holder_branch);
        account_holder_branch.setTypeface(type1);
        user_branch_name = account_holder_branch.getText().toString();
        account_number_layout = findViewById(R.id.account_number_layout);
        account_number_layout.setTypeface(type1);
        account_number = findViewById(R.id.account_number);
        account_number.setTypeface(type1);
        user_account_number = account_number.getText().toString();
        confirm_account_number_layout = findViewById(R.id.confirm_account_number_layout);
        confirm_account_number_layout.setTypeface(type1);
        confirm_account_number = findViewById(R.id.confirm_account_number);
        confirm_account_number.setTypeface(type1);
        account_holder_layout = findViewById(R.id.account_holder_layout);
        account_holder_layout.setTypeface(type1);
        account_holder = findViewById(R.id.account_holder);
        account_holder.setTypeface(type1);
        user_account_holder = account_holder.getText().toString();
        phone_number_layout = findViewById(R.id.phone_number_layout);
        phone_number_layout.setTypeface(type1);
        phone_number = findViewById(R.id.phone_number);
        phone_number.setTypeface(type1);
        user_phone_number = phone_number.getText().toString();
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifsc.getText().toString().trim().isEmpty() || account_holder_bank.getText().toString().trim().isEmpty() ||
                        account_holder_branch.getText().toString().trim().isEmpty() || account_number.getText().toString().trim().isEmpty() ||
                        confirm_account_number.getText().toString().trim().isEmpty() ||
                        account_holder.getText().toString().trim().isEmpty() || phone_number.getText().toString().trim().isEmpty()) {
                    if (ifsc.getText().toString().trim().isEmpty() && account_holder_bank.getText().toString().trim().isEmpty() &&
                            account_holder_branch.getText().toString().trim().isEmpty() && account_number.getText().toString().trim().isEmpty() &&
                            confirm_account_number.getText().toString().trim().isEmpty() &&
                            account_holder.getText().toString().trim().isEmpty() && phone_number.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        ifsc.startAnimation(animShake);
                        account_holder_bank.startAnimation(animShake);
                        account_holder_branch.startAnimation(animShake);
                        account_number.startAnimation(animShake);
                        confirm_account_number.startAnimation(animShake);
                        account_holder.startAnimation(animShake);
                        phone_number.startAnimation(animShake);
                    } else if (ifsc.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        ifsc.startAnimation(animShake);
                    } else if (account_holder_bank.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        account_holder_bank.startAnimation(animShake);
                    } else if (account_holder_branch.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        account_holder_branch.startAnimation(animShake);
                    } else if (account_number.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        account_number.startAnimation(animShake);
                    } else if (confirm_account_number.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        confirm_account_number.startAnimation(animShake);
                    } else if (account_holder.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        account_holder.startAnimation(animShake);
                    } else if (phone_number.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        phone_number.startAnimation(animShake);
                    }
                } else {
                    if (account_number.getText().toString().trim().equals(confirm_account_number.getText().toString().trim())) {
                        user_ifsc = ifsc.getText().toString().trim();
                        user_bank_name = account_holder_bank.getText().toString().trim();
                        user_branch_name = account_holder_branch.getText().toString().trim();
                        user_account_number = account_number.getText().toString().trim();
                        user_account_holder = account_holder.getText().toString().trim();
                        user_phone_number = phone_number.getText().toString().trim();
                        JSONObject jo = new JSONObject();
                        try {
                            jo.put("method", "add_bank_details");
                            jo.put("admin_mobile", admin_mobile);
                            jo.put("admin_type", admin_type);
                            jo.put("user_bank_name", user_bank_name);
                            jo.put("user_branch_name", user_branch_name);
                            jo.put("user_ifsc", user_ifsc);
                            jo.put("user_account_number", user_account_number);
                            jo.put("user_account_holder", user_account_holder);
                            jo.put("user_phone_number", user_phone_number);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new SendRequest(getResources().getString(R.string.url), jo, AddBankDetails.this, getApplicationContext()
                        ).executeJsonRequest();

                    } else {
                        Log.d(TAG, "No data present" + user_ifsc + "-" + user_account_number + "-"
                                + user_account_holder + "-" + user_phone_number);
                    }
                }
            }
        });

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("add_bank_details") && jsonObject.getBoolean("success")) {
                    showBankDetailsDialog("Bank details is added");
                } else {
                    // Toast.makeText(getApplicationContext(), "couldn't save try again later", Toast.LENGTH_SHORT).show();
                    showBankDetailsDialog("Bank details already added/Couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showBankDetailsDialog(error.toString());
    }

    //if the cycle is successfully added to the server then show the success msg or if any error occured then also shows the error msg
    public void showBankDetailsDialog(String msg) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bank_details_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        areaAdd.setText(msg);
        final Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        areaAdd.setTypeface(type1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(AddBankDetails.this, Settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
