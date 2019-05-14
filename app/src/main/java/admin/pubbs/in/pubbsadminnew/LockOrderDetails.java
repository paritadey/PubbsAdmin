package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;
/*created by Parita Dey*/

public class LockOrderDetails extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    String order_number, area_id;
    private String TAG = LockOrderDetails.class.getSimpleName();
    ImageView back_button;
    TextView lock_details_tv;
    String lock_id, ble_id, lock_type, sim_number, totalQuantity, totalPrice, paymentMethod, date_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_order_details);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        area_id = intent.getStringExtra("area_id");
        Log.d(TAG, "Order Details: " + order_number + "---" + area_id);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        lock_details_tv = findViewById(R.id.lock_details_tv);
        lock_details_tv.setTypeface(type1);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getlock");
            jo.put("order_number", order_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, LockOrderDetails.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getlock") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);

                           /* LockList list = new LockList(jo.getString("lock_id"),
                                    jo.getString("ble_id"), jo.getString("sim_number"), jo.getString("locktype"),
                                    jo.getString("totalQuantity"), jo.getString("totalPrice"), jo.getString("paymentMethod"),
                                    jo.getString("date_time"));*/
                            lock_id = jo.getString("lock_id"); ble_id = jo.getString("ble_id"); sim_number = jo.getString("sim_number");
                            lock_type = jo.getString("locktype"); totalQuantity = jo.getString("totalQuantity"); totalPrice = jo.getString("totalPrice");
                            paymentMethod = jo.getString("paymentMethod"); date_time = jo.getString("date_time");
                            Log.d(TAG, "Data is fetched:"+lock_id+"--"+ble_id+"--"+sim_number+
                                    "--"+lock_type+"--"+totalQuantity+"--"+totalPrice+"--"+paymentMethod+"--"+date_time);

                        }
                    }
                } else {
                    showDialog("No Lock is purchased.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Internet Problem");
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
                /*if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }*/
                Intent intent = new Intent(LockOrderDetails.this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(LockOrderDetails.this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
