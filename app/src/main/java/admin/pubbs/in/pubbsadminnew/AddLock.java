package admin.pubbs.in.pubbsadminnew;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddLock extends AppCompatActivity implements AsyncResponse {
    private String fullname, adminmobile, areaid;
    private String TAG = AddLock.class.getSimpleName();
    TextView lock_type_tv;
    RelativeLayout lockLayout;
    CardView addLockdetails;
    ArrayList<String> lockIDList = new ArrayList<String>();
    ArrayList<String> bleAddress = new ArrayList<String>();
    ArrayList<String> choosenLockType = new ArrayList<String>();
    ArrayList<String> simNoList = new ArrayList<String>();
    Typeface type1, type2, type3;
    EditText lockid, ble_address, simNumber;
    String lock_id, ble_id, sim_number, locktype, date_time;
    Button add, addLocks;
    ImageView back, cart;
    RadioGroup locktypeGroup;
    String orderNumber;
    RadioButton radioAT_BLE_1, radioQT_BLE_2, radioQT_GSM_GPS, radioQT_SMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lock);
        type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        initView();
    }

    public void initView() {
        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullname");
        adminmobile = intent.getStringExtra("adminmobile");
        areaid = intent.getStringExtra("areaid");
        Log.d(TAG, "Sub Admin: " + fullname + "-" + adminmobile + "-" + areaid);
        addLockdetails = findViewById(R.id.addLockdetails);
        lockLayout = findViewById(R.id.lock_layout);
        back = findViewById(R.id.back_button);
        lockid = findViewById(R.id.lock_id);
        lockid.setTypeface(type1);
        ble_address = findViewById(R.id.ble_address);
        add = findViewById(R.id.add);
        simNumber = findViewById(R.id.sim_number);
        lock_type_tv = findViewById(R.id.lock_type_tv);
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lockIDList.isEmpty() && !bleAddress.isEmpty() && !simNoList.isEmpty() && !choosenLockType.isEmpty()) {
                    Intent lockdetails = new Intent(AddLock.this, LockCart.class);
                    lockdetails.putExtra("orderNumber", orderNumber);
                    lockdetails.putExtra("fullname", fullname);
                    lockdetails.putExtra("adminmobile", adminmobile);
                    lockdetails.putExtra("areaid", areaid);
                    lockdetails.putExtra("lockIDList", lockIDList);
                    lockdetails.putExtra("bleAddress", bleAddress);
                    lockdetails.putExtra("simNoList", simNoList);
                    lockdetails.putExtra("choosenLockType", choosenLockType);
                    lockdetails.putExtra("date_time", date_time);
                    startActivity(lockdetails);
                } else {
                    View view_layout = findViewById(R.id.manage_locks);
                    String message = "Nothing is added to cart";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(view_layout, message, duration);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLock.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderNumber = generateOrderID();
                Log.d(TAG, "User order details:" + fullname + "/" + adminmobile + "/" + areaid + "/" + orderNumber);
                for (int i = 0; i < lockIDList.size(); i++) {
                    Log.d(TAG, "Lock List: " + lockIDList.get(i));
                }
                for (int j = 0; j < bleAddress.size(); j++) {
                    Log.d(TAG, "Ble Address:" + bleAddress.get(j));
                }
                for (int k = 0; k < simNoList.size(); k++) {
                    Log.d(TAG, "sim List:" + simNoList.get(k));
                }
                for (int l = 0; l < choosenLockType.size(); l++) {
                    Log.d(TAG, "Lock type:" + choosenLockType.get(l));
                }
                View view_layout = findViewById(R.id.manage_locks);
                String message = "Lock is added to the cart.";
                int duration = Snackbar.LENGTH_SHORT;
                showSnackbar(view_layout, message, duration);
                clearFields();
                Log.d(TAG, "Added to Cart");
            }
        });
    }

    public String generateOrderID() {
        String order = "LOCK_OD";
        String orderNo;
        int max = 9999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        orderNo = order + randomNum;
        Log.d(TAG, "Area Number: " + orderNo);
        return orderNo;

    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public void clearFields() {
        lockid.setText("");
        ble_address.setText("");
        simNumber.setText("");
        locktypeGroup.clearCheck();
    }

    public void onRadioButtonClicked(View v) {
        locktypeGroup = findViewById(R.id.locktype);
        radioAT_BLE_1 = findViewById(R.id.radioAT_BLE_1);
        radioAT_BLE_1.setTypeface(type1);
        radioQT_BLE_2 = findViewById(R.id.radioQT_BLE_2);
        radioQT_BLE_2.setTypeface(type1);
        radioQT_GSM_GPS = findViewById(R.id.radioQT_GSM_GPS);
        radioQT_GSM_GPS.setTypeface(type1);
        radioQT_SMS = findViewById(R.id.radioQT_SMS);
        radioQT_SMS.setTypeface(type1);
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.radioAT_BLE_1:
                if (checked) {
                    lock_id = lockid.getText().toString();
                    ble_id = ble_address.getText().toString();
                    locktype = "AT_BLE_1";
                    if (!lock_id.isEmpty() && !ble_id.isEmpty()) {
                        sendAddLockDetails(fullname, adminmobile, areaid, lock_id, ble_id, "NA", locktype);
                    } else {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        lockid.startAnimation(animShake);
                        ble_address.startAnimation(animShake);
                    }
                }
                break;
            case R.id.radioQT_BLE_2:
                if (checked) {
                    lock_id = lockid.getText().toString();
                    ble_id = ble_address.getText().toString();
                    locktype = "QT_BLE_2";
                    if (!lock_id.isEmpty() && !ble_id.isEmpty()) {
                        sendAddLockDetails(fullname, adminmobile, areaid, lock_id, ble_id, "NA", locktype);
                    } else {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        lockid.startAnimation(animShake);
                        ble_address.startAnimation(animShake);
                    }
                }
                break;
            case R.id.radioQT_GSM_GPS:
                if (checked) {
                    lock_id = lockid.getText().toString();
                    ble_id = ble_address.getText().toString();
                    locktype = "QT_GSM_GPS";
                    if (!lock_id.isEmpty() && !ble_id.isEmpty()) {
                        sendAddLockDetails(fullname, adminmobile, areaid, lock_id, ble_id, "NA", locktype);
                    } else {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        lockid.startAnimation(animShake);
                        ble_address.startAnimation(animShake);
                    }
                }
                break;
            case R.id.radioQT_SMS:
                if (checked) {
                    lock_id = lockid.getText().toString();
                    sim_number = simNumber.getText().toString();
                    locktype = "QT_SMS";
                    if (!lock_id.isEmpty() && !ble_id.isEmpty()) {
                        sendAddLockDetails(fullname, adminmobile, areaid, lock_id, "NA", sim_number, locktype);
                    } else {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        lockid.startAnimation(animShake);
                        simNumber.startAnimation(animShake);
                    }
                }
                break;
        }

    }

    public void sendAddLockDetails(String fullname, String adminmobile,
                                   String areaid, String lock_id, String ble_id, String sim_number, String locktype) {
        Log.d(TAG, "User details:" + fullname + "---" + adminmobile + "----" + areaid);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
        date_time = sdf.format(date);

        lockIDList.add(lock_id);
        bleAddress.add(ble_id);
        simNoList.add(sim_number);
        choosenLockType.add(locktype);
        for (int i = 0; i < lockIDList.size(); i++) {
            Log.d(TAG, "Lock List: " + lockIDList.get(i));
        }
        for (int j = 0; j < bleAddress.size(); j++) {
            Log.d(TAG, "Ble Address:" + bleAddress.get(j));
        }
        for (int k = 0; k < simNoList.size(); k++) {
            Log.d(TAG, "sim List:" + simNoList.get(k));
        }
        for (int l = 0; l < choosenLockType.size(); l++) {
            Log.d(TAG, "Lock type:" + choosenLockType.get(l));
        }
        /*JSONObject jo = new JSONObject();

        try {
            jo.put("method", "add_lock_details");
            jo.put("fullname", fullname);
            jo.put("adminmobile", adminmobile);
            jo.put("areaid", areaid);
            jo.put("lock_id", lock_id);
            jo.put("ble_id", ble_id);
            jo.put("sim_number", sim_number);
            jo.put("locktype", locktype);
            jo.put("date_time", date_time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AddLock.this, getApplicationContext()).executeJsonRequest();*/

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("add_lock_details") && jsonObject.getBoolean("success")) {
                    showDialog("Lock is added to the Operator account");
                } else {
                    showDialog("couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Error !");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddLock.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                Intent intent = new Intent(AddLock.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}