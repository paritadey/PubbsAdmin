package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
/*created by Parita Dey*/

public class RepairCycleScanQr extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener, AsyncResponse {
    private ZXingScannerView mScannerView;
    ImageView upArrow, back;
    private final String TAG = RepairCycleScanQr.class.getSimpleName();
    TextView redistributionTv, scanQrTv, enterNumberTv, bicycleTv, bicycleNumberTv, bottomSheetTv;
    EditText bicycleId;
    ProgressDialog pd;
    String adminmobile, admin_type;
    String cycle_id, address, date_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_scan_qr);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        adminmobile = intent.getStringExtra("adminmobile");
        admin_type = intent.getStringExtra("admin_type");
        Log.d(TAG, "Station Details:" + adminmobile + "-" + admin_type);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
        date_time = sdf.format(date);

        redistributionTv = findViewById(R.id.redistribution_tv);
        redistributionTv.setTypeface(type1);
        scanQrTv = findViewById(R.id.scan_qr_tv);
        scanQrTv.setTypeface(type2);
        enterNumberTv = findViewById(R.id.enter_number_tv);
        enterNumberTv.setTypeface(type2);
        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type2);
        bicycleNumberTv = findViewById(R.id.bicycle_number_tv);
        bicycleNumberTv.setTypeface(type2);
        bicycleId = findViewById(R.id.bicycle_id);
        bicycleId.setTypeface(type2);
        bottomSheetTv = findViewById(R.id.bottomsheet_text);
        bottomSheetTv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        upArrow = findViewById(R.id.up_arrow);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        upArrow.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        cycle_id = rawResult.getText();
        Log.d(TAG, cycle_id);
        address = cycle_id.replaceAll(":", "");
        Log.d(TAG, "replacing colon:" + address);
        bicycleId.setText(address);
        if (bicycleId.getText().toString().isEmpty() == false) {
            addCnfirmationDialog("Do you want to add this cycle to repair pool?", cycle_id, address, adminmobile,
                    admin_type, date_time, getApplicationContext());
        } else {
            Log.d(TAG, "Problem occures");
        }
    }

    private void addCnfirmationDialog(String msg, String cycle_id, String address, String adminmobile, String admin_type, String date_time, Context applicationContext) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        JSONObject jo = new JSONObject();
                        try {
                            jo.put("method", "add_cycle_repair");
                            jo.put("cycle_id", cycle_id);
                            jo.put("address", address);
                            jo.put("adminmobile", adminmobile);
                            jo.put("admin_type", admin_type);
                            jo.put("date_time", date_time);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new SendRequest(getResources().getString(R.string.url), jo, RepairCycleScanQr.this, getApplicationContext()
                        ).executeJsonRequest();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_arrow:
                new BottomSheetRepairFragment().show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(RepairCycleScanQr.this, Repair.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("add_cycle_repair") && jsonObject.getBoolean("success")) {
                    showCycleAddedRepairPoolDialog("Cycle is added to the repair pool.");
                } else {
                    // Toast.makeText(getApplicationContext(), "couldn't save try again later", Toast.LENGTH_SHORT).show();
                    showCycleAddedRepairPoolDialog("couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Problem !");
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
                Intent intent = new Intent(RepairCycleScanQr.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    public void showCycleAddedRepairPoolDialog(String msg) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.area_added_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        areaAdd.setText(msg);
        final Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        areaAdd.setTypeface(type1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(RepairCycleScanQr.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }
}

