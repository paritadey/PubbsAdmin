package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;
/*created by Parita Dey*/

public class SetAreaAgreement extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    SharedPreferences sharedPreferences;
    String adminmobile, areaname, areaid;
    private String TAG = SetAreaAgreement.class.getSimpleName();
    ImageView back;
    TextView area_legal_tv, area_legal, stepOne, stepTwo, stepThree, stepFour, stepFive;
    Button set_legal;
    EditText geofencing_conditions, wallet_condition, service_condition, area_condition, area_subscription_condition;
    String date_time, geofencingCondition, walletCondition, serviceCondition, areaCondition, areaSubscriptionCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_area_agreement);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Admin Mobile" + adminmobile);
        Intent intent = getIntent();
        areaname = intent.getStringExtra("areaname");
        areaid = intent.getStringExtra("areaid");
        Log.d(TAG, "Area details:" + areaname + "-" + areaid);
        area_legal_tv = findViewById(R.id.area_legal_tv);
        area_legal_tv.setTypeface(type2);
        area_legal = findViewById(R.id.area_legal);
        area_legal.setTypeface(type1);
        geofencing_conditions = findViewById(R.id.geofencing_conditions);
        geofencing_conditions.setTypeface(type1);
        wallet_condition = findViewById(R.id.wallet_condition);
        wallet_condition.setTypeface(type1);
        service_condition = findViewById(R.id.service_condition);
        service_condition.setTypeface(type1);
        area_condition = findViewById(R.id.area_condition);
        area_condition.setTypeface(type1);
        stepOne = findViewById(R.id.step_one);
        stepOne.setTypeface(type2);
        stepTwo = findViewById(R.id.step_two);
        stepTwo.setTypeface(type2);
        stepThree = findViewById(R.id.step_three);
        stepThree.setTypeface(type2);
        stepFour = findViewById(R.id.step_four);
        stepFour.setTypeface(type2);
        area_subscription_condition = findViewById(R.id.area_subscription_condition);
        area_subscription_condition.setTypeface(type1);
        stepFive = findViewById(R.id.step_five);
        stepFive.setTypeface(type2);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        set_legal = findViewById(R.id.set_legal);
        set_legal.setTypeface(type3);
        set_legal.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SetAreaAgreement.this, AreaLegal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(SetAreaAgreement.this, AreaLegal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.set_legal:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (geofencing_conditions.getText().toString().isEmpty() || wallet_condition.getText().toString().isEmpty()
                        || service_condition.getText().toString().isEmpty() || area_condition.getText().toString().isEmpty()
                        || area_subscription_condition.getText().toString().isEmpty()) {
                    if (geofencing_conditions.getText().toString().isEmpty() && wallet_condition.getText().toString().isEmpty() &&
                            service_condition.getText().toString().isEmpty() && area_condition.getText().toString().isEmpty()
                            && area_subscription_condition.getText().toString().isEmpty()) {
                        geofencing_conditions.startAnimation(animShake);
                        wallet_condition.startAnimation(animShake);
                        service_condition.startAnimation(animShake);
                        area_condition.startAnimation(animShake);
                        area_subscription_condition.startAnimation(animShake);
                    } else if (geofencing_conditions.getText().toString().isEmpty()) {
                        geofencing_conditions.startAnimation(animShake);
                    } else if (wallet_condition.getText().toString().isEmpty()) {
                        wallet_condition.startAnimation(animShake);
                    } else if (service_condition.getText().toString().isEmpty()) {
                        service_condition.startAnimation(animShake);
                    } else if (area_condition.getText().toString().isEmpty()) {
                        area_condition.startAnimation(animShake);
                    } else if (area_subscription_condition.getText().toString().isEmpty()) {
                        area_subscription_condition.startAnimation(animShake);
                    }
                } else {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
                    date_time = sdf.format(date);
                    geofencingCondition = geofencing_conditions.getText().toString();
                    walletCondition = wallet_condition.getText().toString();
                    serviceCondition = service_condition.getText().toString();
                    areaCondition = area_condition.getText().toString();
                    areaSubscriptionCondition = area_subscription_condition.getText().toString();
                    setLegal(adminmobile, areaid, areaname, geofencingCondition, walletCondition,
                            serviceCondition, areaCondition, areaSubscriptionCondition, date_time);
                }
                break;
            default:
                break;
        }
    }

    public void setLegal(String adminmobile, String areaname, String areaid, String geofencingCondition, String walletCondition
            , String serviceCondition, String areaCondition, String areaSubscriptionCondition, String date_time) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "set_area_legal");
            jo.put("adminmobile", adminmobile);
            jo.put("areaname", areaname);
            jo.put("areaid", areaid);
            jo.put("geofencingCondition", geofencingCondition);
            jo.put("walletCondition", walletCondition);
            jo.put("serviceCondition", serviceCondition);
            jo.put("areaCondition", areaCondition);
            jo.put("areaSubscriptionCondition", areaSubscriptionCondition);
            jo.put("date_time", date_time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, SetAreaAgreement.this, getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("set_area_legal") && jsonObject.getBoolean("success")) {
                    showMessageDialog("Area Legal is updated");
                } else {
                    Toast.makeText(getApplicationContext(), "couldn't save try again later", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showMessageDialog("Server Error !");
    }
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
                Intent intent = new Intent(SetAreaAgreement.this, AreaLegal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }


}
