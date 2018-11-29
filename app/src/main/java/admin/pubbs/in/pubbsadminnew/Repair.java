package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/*created by Parita Dey*/

public class Repair extends AppCompatActivity implements AsyncResponse {
    private RecyclerView recyclerView;
    private RepairAdapter repairAdapter;
    private List<RepairList> repairList = new ArrayList<>();
    ImageView scanQr, back;
    private TextView bicycleTv, drawRedistribution, scanQrTv;
    EditText inputSearch;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    SharedPreferences sharedPreferences;
    String adminmobile, admin_type;
    private String TAG = Repair.class.getSimpleName();
    ProgressBar circularProgressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admin_type = sharedPreferences.getString("admin_type", null);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        back = findViewById(R.id.back_button);
        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type1);
        //inputSearch = findViewById(R.id.input_search);
        //inputSearch.setTypeface(type1);
        drawRedistribution = findViewById(R.id.draw_redistribution);
        drawRedistribution.setTypeface(type2);
        scanQrTv = findViewById(R.id.scan_qr_tv);
        scanQrTv.setTypeface(type2);

        scanQr = findViewById(R.id.scan_qr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        repairAdapter = new RepairAdapter(repairList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(repairAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RepairList lists = repairList.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(RepairCycleScanQr.class);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Repair.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

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
            jo.put("method", "get_repair_details");
            jo.put("adminmobile", adminmobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, Repair.this, getApplicationContext()).executeJsonRequest();
    }
    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            intent.putExtra("adminmobile", adminmobile);
            intent.putExtra("admin_type", admin_type);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        repairList.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("get_repair_details") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            RepairList user = new RepairList(jo.getString("address"),
                                    jo.getString("date_time"));
                            repairList.add(user);
                        }
                    } else{
                        showDialog("No repair data is present.");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        repairAdapter.notifyDataSetChanged();

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
