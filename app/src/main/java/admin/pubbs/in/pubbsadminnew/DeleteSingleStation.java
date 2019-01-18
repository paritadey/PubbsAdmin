package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
/*created by Parita Dey*/

public class DeleteSingleStation extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    TextView delete_station_tv, all_set_tv, delete_stationTv, stationName, stationId, areaName, areaId;
    Button delete;
    String station_name, station_id, area_name, area_id;
    private String TAG = DeleteSingleStation.class.getSimpleName();
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_single_station);
        initView();
    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        station_name = intent.getStringExtra("station_name");
        station_id = intent.getStringExtra("station_id");
        area_name = intent.getStringExtra("area_name");
        area_id = intent.getStringExtra("area_id");
        Log.d(TAG, "Details of station:" + station_name + "--" + station_id + "--" + area_name + "--" + area_id);
        back = findViewById(R.id.back_button);
        delete_station_tv = findViewById(R.id.delete_station_tv);
        all_set_tv = findViewById(R.id.all_set_tv);
        delete_stationTv = findViewById(R.id.delete_stationTv);
        delete_station_tv.setTypeface(type1);
        all_set_tv.setTypeface(type2);
        delete_station_tv.setTypeface(type2);
        stationName = findViewById(R.id.station_name);
        stationName.setTypeface(type1);
        stationName.setText(station_name);
        stationId = findViewById(R.id.station_id);
        stationId.setText(station_id);
        stationId.setTypeface(type1);
        areaName = findViewById(R.id.area_name);
        areaName.setTypeface(type1);
        areaName.setText(area_name);
        areaId = findViewById(R.id.area_id);
        areaId.setText(area_id);
        areaId.setTypeface(type1);
        delete = findViewById(R.id.delete_station);
        delete.setTypeface(type3);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(DeleteSingleStation.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.delete_station:
                deleteStation(station_name);
                break;
        }
    }

    private void deleteStation(String station_name) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "deleteStation");
            jo.put("station_name", station_name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, DeleteSingleStation.this,
                getApplicationContext()).executeJsonRequest();

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("deleteStation") && jsonObject.getBoolean("success")) {
                    showDeletedDialog("Station is deleted.");
                } else {
                    Toast.makeText(getApplicationContext(), "couldn't delete try again later", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(DeleteSingleStation.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    public void showDeletedDialog(String msg) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.deletestation_layout, null);
        final TextView deleteMsg = (TextView) dialogView.findViewById(R.id.delete_msg);
        final ImageView delete = dialogView.findViewById(R.id.delete_img);
        final Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        deleteMsg.setTypeface(type1);
        deleteMsg.setText(msg);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(DeleteSingleStation.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);

    }

}

