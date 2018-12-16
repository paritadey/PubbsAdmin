package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
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

import java.util.HashMap;

public class DeleteSingleOperator extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    TextView delete_operator_tv, all_set_tv, delete_operatorTv, fullname, adminMobile, area_name, admin_type;
    Button delete;
    String full_name, admin_mobile, areaName, adminType;
    private String TAG = DeleteSingleOperator.class.getSimpleName();
    ImageView back;
    String finalResult;
    ProgressDialog progressDialog;
    String UserUrl = "http://pubbs.in/api/1.0/DeleteOperator.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_single_operator);
        initView();
    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        full_name = intent.getStringExtra("full_name");
        admin_mobile = intent.getStringExtra("admin_mobile");
        areaName = intent.getStringExtra("area_name");
        adminType = intent.getStringExtra("admin_type");
        Log.d(TAG, "Details of operator:" + full_name + "--" + admin_mobile + "--" + areaName + "--" + adminType);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        delete_operator_tv = findViewById(R.id.delete_operator_tv);
        delete_operator_tv.setTypeface(type1);
        all_set_tv = findViewById(R.id.all_set_tv);
        all_set_tv.setTypeface(type1);
        delete_operatorTv = findViewById(R.id.delete_operatorTv);
        delete_operatorTv.setTypeface(type1);
        fullname = findViewById(R.id.fullname);
        fullname.setTypeface(type1);
        fullname.setText(full_name);
        adminMobile = findViewById(R.id.adminmobile);
        adminMobile.setText(admin_mobile);
        adminMobile.setTypeface(type1);
        area_name = findViewById(R.id.area_name);
        area_name.setTypeface(type1);
        area_name.setText(areaName);
        admin_type = findViewById(R.id.admin_type);
        admin_type.setText(adminType);
        admin_type.setTypeface(type1);
        delete = findViewById(R.id.delete_operator);
        delete.setTypeface(type3);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(DeleteSingleOperator.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.delete_operator:
               // DeleteOperator(admin_mobile);
                deleteOperator(admin_mobile);
                break;
        }
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
                Intent intent = new Intent(DeleteSingleOperator.this, DashBoardActivity.class);
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
                Intent intent = new Intent(DeleteSingleOperator.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);

    }

    public void DeleteOperator(final String admin_mobile) {
        class DeleteOperatorClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show
                        (DeleteSingleOperator.this, "Connecting to the server", "Feteching Data...", true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(DeleteSingleOperator.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("admin_mobile", params[0]);

                finalResult = httpParse.postRequest(hashMap, UserUrl);

                return finalResult;
            }
        }

        DeleteOperatorClass deleteOperatorClass = new DeleteOperatorClass();

        deleteOperatorClass.execute(admin_mobile);
    }

    private void deleteOperator(String admin_mobile) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "delete_operator");
            jo.put("admin_mobile", admin_mobile);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, DeleteSingleOperator.this,
                getApplicationContext()).executeJsonRequest();

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("delete_operator") && jsonObject.getBoolean("success")) {
                    showDeletedDialog("Operator is deleted.");
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
}
