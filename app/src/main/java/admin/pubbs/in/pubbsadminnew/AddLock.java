package admin.pubbs.in.pubbsadminnew;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
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
import android.widget.LinearLayout;
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

public class AddLock extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AsyncResponse {
    private String fullname, adminmobile, areaid;
    private String TAG = AddLock.class.getSimpleName();
    TextView lock_type_tv;
    Spinner locktypeSpinner, quantitySpinner;
    String[] lockType = {"Choose lock type", "AT_BLE_1", "QT_BLE_2", "QT_GSM_GPS", "QT_SMS"};
    String choosenLockType, lockQuantity;
    RelativeLayout lockLayout;
    CardView addLockdetails;
    LinearLayout lock_address_layout;
    ArrayList<String> lockIDList = new ArrayList<String>();
    ArrayList<String> bleAddress = new ArrayList<String>();
    Typeface type1, type2, type3;
    EditText lockid, ble_address, simNumber;
    String lock_id, ble_id, sim_number, locktype, date_time;
    Button add, addLocks;

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
        lockid = findViewById(R.id.lock_id);
        ble_address = findViewById(R.id.ble_address);
        add = findViewById(R.id.add);
        simNumber = findViewById(R.id.sim_number);
        lock_type_tv = findViewById(R.id.lock_type_tv);
        locktypeSpinner = findViewById(R.id.locktypeSpinner);
        locktypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lockType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locktypeSpinner.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Log.d(TAG, "Please choose option");
            Toast.makeText(getApplicationContext(), "Please choose option", Toast.LENGTH_SHORT).show();
        } else {
            choosenLockType = lockType[position];
            Log.d(TAG, "LockType:" + choosenLockType);
            createView(choosenLockType);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void createView(String choosenLockType) {
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
        date_time = sdf.format(date);
        lock_id = lockid.getText().toString();
        ble_id = ble_address.getText().toString();
        Log.d(TAG, "Lock ID:" + lock_id + "-" + ble_id);
        if (choosenLockType.equals("AT_BLE_1")) {
            locktype = "AT_BLE_1";
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAddLockDetails(fullname, adminmobile, areaid, lock_id, ble_id, "NA", locktype, date_time);
                }
            });
        } else if (choosenLockType.equals("QT_BLE_2")) {
            locktype = "QT_BLE_2";
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAddLockDetails(fullname, adminmobile, areaid, lock_id, ble_id, "NA", locktype, date_time);
                }
            });
        } else if (choosenLockType.equals("QT_GSM_GPS")) {
            locktype = "QT_GSM_GPS";
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAddLockDetails(fullname, adminmobile, areaid, lock_id, ble_id, "NA", locktype, date_time);
                }
            });
        } else if (choosenLockType.equals("QT_SMS")) {
            sim_number = simNumber.getText().toString().trim();
            Log.d(TAG,"Sim Number:"+sim_number);
            ble_address.setVisibility(View.GONE);
            locktype = "QT_SMS";
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendAddLockDetails(fullname, adminmobile, areaid, lock_id, "NA", sim_number, locktype, date_time);
                }
            });
        } else {
            Log.d(TAG, "No option choosen");
        }
    }

    public void sendAddLockDetails(String fullname, String adminmobile,
                                   String areaid, String lock_id, String ble_id, String sim_number, String locktype, String date_time) {
        JSONObject jo = new JSONObject();

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
        new SendRequest(getResources().getString(R.string.url), jo, AddLock.this, getApplicationContext()).executeJsonRequest();

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


   /* public void createAddressView(String lockQuantity) {
        if (lockQuantity.equals("Choose lock quantity")) {
            final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            quantitySpinner.startAnimation(animShake);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params
            params.setMargins(8, 8, 8, 8);
            lock_address_layout.removeAllViews();//Remove all views from Layout before placing new view
            for (int i = 0; i < 10; i++) {
                lockId = new EditText(this);
                lockId.setLayoutParams(params);
                lockId.setBackgroundResource(R.drawable.edittext_bg);
                lockId.setLines(1);
                lockId.setMaxLines(1);
                lockId.setHint("Enter Lock ID");
                lockId.setPadding(8, 4, 4, 4);
                lockId.setCursorVisible(true);
                lockId.setTypeface(type1);
                lockId.setTextSize(18f);
                lockId.setCompoundDrawablePadding(4);
                lock_address_layout.addView(lockId);
                lockIDList.add(lockId.getText().toString().trim()); //this adds an element to the list.

                bleId = new EditText(this);
                bleId.setLayoutParams(params);
                bleId.setBackgroundResource(R.drawable.edittext_bg);
                bleId.setHint("Enter BLE Address");
                bleId.setTextSize(18f);
                bleId.setLines(1);
                bleId.setMaxLines(1);
                bleId.setPadding(8, 4, 4, 4);
                bleId.setCursorVisible(true);
                bleId.setTypeface(type1);
                lock_address_layout.addView(bleId);
                bleAddress.add(bleId.getText().toString().trim());
            }
            add = new Button(this);
            add.setLayoutParams(params);
            add.setBackgroundResource(R.drawable.button_bg);
            add.setText("Add Lock");
            add.setTextColor(Color.WHITE);
            add.setAllCaps(false);
            add.setTextSize(18f);
            add.setTypeface(type3);
            lock_address_layout.addView(add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showArrayList(lockIDList, bleAddress);
                }
            });
        }

    }*/
}

  /*  private void showArrayList(ArrayList<String> lockIDList, ArrayList<String> bleAddress) {
        for (int i = 0; i < lockIDList.size(); i++) {
            Log.d(TAG, "Lock ID:" + lockIDList.get(i));
        }
        for (int j = 0; j < bleAddress.size(); j++) {
            Log.d(TAG, "Ble Address: " + bleAddress.get(j));
        }
    }
        }
    }
}*/
