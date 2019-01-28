package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
/*created by Parita Dey*/

public class LockCart extends AppCompatActivity implements AsyncResponse {
    private String fullname, adminmobile, areaid, orderNumber;
    private String TAG = LockCart.class.getSimpleName();
    ArrayList<String> lockIDList = new ArrayList<String>();
    ArrayList<String> bleAddress = new ArrayList<String>();
    ArrayList<String> choosenLockType = new ArrayList<String>();
    ArrayList<String> simNoList = new ArrayList<String>();
    int frequency, totalPrice = 0, totalQuantity = 0;
    int freqATBT, freqQTBT, freqQTGSMGPS, freqQTSMS;
    LinearLayout checkout_layout;
    Typeface type1, type2, type3;
    Button calculate, pay;
    String atBt01, qtBt02, qtGsmGps, qtSms, paymentMethod;
    EditText lock_priceATBT, lock_priceQTBT, lock_priceQTGSMGPS, lock_priceQTSMS;
    int priceatBt01, priceqtBt02, priceqtGsmGps, priceqtSms;
    CardView orderDetails;
    TextView order_details_tv, quantity_tv, order_quantity, amount_tv, order_amount, payment_method_tv;
    RadioGroup payment_type;
    RadioButton radioCash, radioOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//disable screen capture
        setContentView(R.layout.activity_lock_cart);
        type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        initView();
    }

    public void initView() {
        calculate = findViewById(R.id.calculate);
        calculate.setTypeface(type2);
        calculate.setText("Get Amount");
        orderDetails = findViewById(R.id.orderDetails);
        orderDetails.setVisibility(View.GONE);
        order_details_tv = findViewById(R.id.order_details_tv);
        order_details_tv.setTypeface(type1);
        quantity_tv = findViewById(R.id.quantity_tv);
        quantity_tv.setTypeface(type1);
        order_quantity = findViewById(R.id.order_quantity);
        order_quantity.setTypeface(type1);
        amount_tv = findViewById(R.id.amount_tv);
        amount_tv.setTypeface(type1);
        order_amount = findViewById(R.id.order_amount);
        order_amount.setTypeface(type1);
        payment_method_tv = findViewById(R.id.payment_method_tv);
        payment_method_tv.setTypeface(type1);
        pay = findViewById(R.id.pay);
        pay.setTypeface(type2);

        Intent intent = getIntent();
        orderNumber = intent.getStringExtra("orderNumber");
        fullname = intent.getStringExtra("fullname");
        adminmobile = intent.getStringExtra("adminmobile");
        areaid = intent.getStringExtra("areaid");
        Log.d(TAG, "Sub Admin: " + fullname + "-" + adminmobile + "-" + areaid);
        lockIDList = intent.getStringArrayListExtra("lockIDList");
        bleAddress = intent.getStringArrayListExtra("bleAddress");
        choosenLockType = intent.getStringArrayListExtra("choosenLockType");
        simNoList = intent.getStringArrayListExtra("simNoList");
        Log.d(TAG, "Order number:" + orderNumber);
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
        checkQuantityLock(choosenLockType);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freqATBT > 0 || freqQTBT > 0 || freqQTGSMGPS > 0 || freqQTSMS > 0) {
                    if (freqATBT > 0) {
                        atBt01 = lock_priceATBT.getText().toString();
                        priceatBt01 = Integer.parseInt(atBt01) * freqATBT;
                    }
                    if (freqQTBT > 0) {
                        qtBt02 = lock_priceQTBT.getText().toString();
                        priceqtBt02 = Integer.parseInt(qtBt02) * freqQTBT;
                    }
                    if (freqQTGSMGPS > 0) {
                        qtGsmGps = lock_priceQTGSMGPS.getText().toString();
                        priceqtGsmGps = Integer.parseInt(qtGsmGps) * freqQTGSMGPS;
                    }
                    if (freqQTSMS > 0) {
                        qtSms = lock_priceQTSMS.getText().toString();
                        priceqtSms = Integer.parseInt(qtSms) * freqQTSMS;
                    }
                    totalPrice = totalPrice + priceatBt01 + priceqtBt02 + priceqtGsmGps + priceqtSms;
                    totalQuantity = totalQuantity + freqATBT + freqQTBT + freqQTGSMGPS + freqQTSMS;
                    Log.d(TAG, "Total price:" + totalPrice + "\t" + totalQuantity);

                    orderDetails.setVisibility(View.VISIBLE);

                    order_quantity.setText(Integer.toString(totalQuantity));
                    order_amount.setText(Integer.toString(totalPrice));
                }
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Payment Method: " + paymentMethod);
                sendOrderDetails(orderNumber, fullname, adminmobile, areaid, lockIDList, bleAddress,
                        simNoList, choosenLockType, totalQuantity, totalPrice, paymentMethod);
            }
        });
    }

    private void sendOrderDetails(String orderNumber, String fullname,
                                  String adminmobile, String areaid, ArrayList<String> lock_id,
                                  ArrayList<String> ble_id, ArrayList<String> sim_number,
                                  ArrayList<String> locktype, int totalQuantity, int totalPrice, String paymentMethod) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("method", "add_lock_details");
            jo.put("orderNumber", orderNumber);
            jo.put("fullname", fullname);
            jo.put("adminmobile", adminmobile);
            jo.put("areaid", areaid);
            jo.put("lock_id", lock_id);
            jo.put("ble_id", ble_id);
            jo.put("sim_number", sim_number);
            jo.put("locktype", locktype);
            jo.put("totalQuantity", totalQuantity);
            jo.put("totalPrice", totalPrice);
            jo.put("paymentMethod", paymentMethod);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, LockCart.this,
                getApplicationContext()).executeJsonRequest();

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
        Intent intent = new Intent(LockCart.this, DashBoardActivity.class);
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
                Intent intent = new Intent(LockCart.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    public void SetPayment(View v) {
        payment_type = findViewById(R.id.payment_type);
        radioCash = findViewById(R.id.radioCash);
        radioCash.setTypeface(type1);
        radioOther = findViewById(R.id.radioOther);
        radioOther.setTypeface(type1);
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.radioCash:
                if (checked) {
                    paymentMethod = "CASH";
                } else {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    radioCash.startAnimation(animShake);
                }
                break;
            case R.id.radioOther:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "This mode is not added yet! Please choose cash option.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void checkQuantityLock(ArrayList<String> choosenLockType) {
        if (choosenLockType.contains("AT_BLE_1") || choosenLockType.contains("QT_BLE_2") || choosenLockType.contains("QT_GSM_GPS")
                || choosenLockType.contains("QT_SMS")) {
            if (choosenLockType.contains("AT_BLE_1")) {
                frequency = Collections.frequency(choosenLockType, "AT_BLE_1");
                Log.d(TAG, "Frequency of AT_BLE_1:" + frequency);
                if (frequency > 0) {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText(String.valueOf(frequency) + "*" + "1" + "\t" + "\t" + "AT_BLE_1");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                    freqATBT = frequency;

                    lock_priceATBT = new EditText(getApplicationContext());
                    lock_priceATBT.setLayoutParams(lp_1);
                    lock_priceATBT.setTypeface(type1);
                    lock_priceATBT.setTextSize(14f);
                    lock_priceATBT.setHint("Enter the price");
                    lock_priceATBT.setTextColor(getResources().getColor(R.color.black));
                    lock_priceATBT.setPadding(8, 8, 8, 8);
                    lock_priceATBT.setInputType(InputType.TYPE_CLASS_NUMBER);
                    lock_priceATBT.setBackgroundResource(R.drawable.edittext_bg);
                    checkout_layout.addView(lock_priceATBT, lp_1);

                } else {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText("No Lock is added to the cart");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);
                }
            }
            if (choosenLockType.contains("QT_BLE_2")) {
                frequency = Collections.frequency(choosenLockType, "QT_BLE_2");
                Log.d(TAG, "Frequency of QT_BLE_2:" + frequency);
                if (frequency > 0) {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText(String.valueOf(frequency) + "*" + "1" + "\t" + "\t" + "QT_BLE_2");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                    freqQTBT = frequency;

                    lock_priceQTBT = new EditText(getApplicationContext());
                    lock_priceQTBT.setLayoutParams(lp_1);
                    lock_priceQTBT.setTypeface(type1);
                    lock_priceQTBT.setTextSize(14f);
                    lock_priceQTBT.setHint("Enter the price");
                    lock_priceQTBT.setTextColor(getResources().getColor(R.color.black));
                    lock_priceQTBT.setPadding(8, 8, 8, 8);
                    lock_priceQTBT.setBackgroundResource(R.drawable.edittext_bg);
                    lock_priceQTBT.setInputType(InputType.TYPE_CLASS_NUMBER);
                    checkout_layout.addView(lock_priceQTBT, lp_1);

                } else {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText("No Lock is added to the cart");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                }
            }
            if (choosenLockType.contains("QT_GSM_GPS")) {
                frequency = Collections.frequency(choosenLockType, "QT_GSM_GPS");
                Log.d(TAG, "Frequency of QT_GSM_GPS:" + frequency);
                if (frequency > 0) {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText(String.valueOf(frequency) + "*" + "1" + "\t" + "\t" + "QT_GSM_GPS");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                    freqQTGSMGPS = frequency;

                    lock_priceQTGSMGPS = new EditText(getApplicationContext());
                    lock_priceQTGSMGPS.setLayoutParams(lp_1);
                    lock_priceQTGSMGPS.setTypeface(type1);
                    lock_priceQTGSMGPS.setTextSize(14f);
                    lock_priceQTGSMGPS.setHint("Enter the price");
                    lock_priceQTGSMGPS.setTextColor(getResources().getColor(R.color.black));
                    lock_priceQTGSMGPS.setPadding(8, 8, 8, 8);
                    lock_priceQTGSMGPS.setBackgroundResource(R.drawable.edittext_bg);
                    lock_priceQTGSMGPS.setInputType(InputType.TYPE_CLASS_NUMBER);
                    checkout_layout.addView(lock_priceQTGSMGPS, lp_1);

                } else {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText("No Lock is added to the cart");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                }

            }
            if (choosenLockType.contains("QT_SMS")) {
                frequency = Collections.frequency(choosenLockType, "QT_SMS");
                Log.d(TAG, "Frequency of QT_SMS:" + frequency);
                if (frequency > 0) {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText(String.valueOf(frequency) + "*" + "1" + "\t" + "\t" + "QT_SMS");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                    freqQTSMS = frequency;

                    lock_priceQTSMS = new EditText(getApplicationContext());
                    lock_priceQTSMS.setLayoutParams(lp_1);
                    lock_priceQTSMS.setTypeface(type1);
                    lock_priceQTSMS.setTextSize(14f);
                    lock_priceQTSMS.setHint("Enter the price");
                    lock_priceQTSMS.setTextColor(getResources().getColor(R.color.black));
                    lock_priceQTSMS.setPadding(8, 8, 8, 8);
                    lock_priceQTSMS.setBackgroundResource(R.drawable.edittext_bg);
                    lock_priceQTSMS.setInputType(InputType.TYPE_CLASS_NUMBER);
                    checkout_layout.addView(lock_priceQTSMS, lp_1);
                } else {
                    checkout_layout = findViewById(R.id.checkout_layout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = 24;
                    LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp_1.setMargins(8, 18, 8, 8);
                    TextView lock_quantity = new TextView(getApplicationContext());
                    lock_quantity.setLayoutParams(lp_1);
                    lock_quantity.setText("No Lock is added to the cart");
                    lock_quantity.setTypeface(type1);
                    lock_quantity.setTextSize(14f);
                    lock_quantity.setTextColor(getResources().getColor(R.color.black));
                    lock_quantity.setPadding(24, 4, 24, 4);
                    checkout_layout.addView(lock_quantity, lp_1);

                }
            }
        } else {
            Log.d(TAG, "Some problem occured");
        }
    }

}
