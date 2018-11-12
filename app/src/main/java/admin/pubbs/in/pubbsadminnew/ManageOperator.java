package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManageOperator extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    TextView manageOperatorTv;
    ImageView back;
    TextView addOperator, editOperator, deleteOperator;
    CardView addOperatorCard, editOperatorCard, deleteOperatorCard;
    private String TAG = ManageOperator.class.getSimpleName();
    SharedPreferences sharedPreferences;
    String admin_mobile, admin_type, admin_area_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_operator);
        initView();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        admin_mobile = sharedPreferences.getString("adminmobile", null);
        //admin_type = sharedPreferences.getString("admin_type", null);
        Log.d(TAG, "Admin Details: " + admin_mobile);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManageOperator.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        addOperatorCard = findViewById(R.id.addOperator_card);
        editOperatorCard = findViewById(R.id.editOperator_card);
        //deleteOperatorCard = findViewById(R.id.deleteOperator_card);
        manageOperatorTv = findViewById(R.id.manage_operator_tv);
        manageOperatorTv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        addOperator = findViewById(R.id.add_operator);
        addOperator.setTypeface(type1);
        editOperator = findViewById(R.id.edit_operator);
        editOperator.setTypeface(type1);
        //deleteOperator = findViewById(R.id.delete_operator);
        //deleteOperator.setTypeface(type1);
        addOperatorCard.setOnClickListener(this);
        editOperatorCard.setOnClickListener(this);
      //  deleteOperatorCard.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.addOperator_card:
                startActivity(new Intent(ManageOperator.this, AddOperator.class));
                break;
            case R.id.editOperator_card:
                if (admin_area_id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Some network issue occured. Give some moment.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent delete = new Intent(ManageOperator.this, EditOperator.class);
                    delete.putExtra("admin_area_id", admin_area_id);
                    startActivity(delete);
                }
                break;
            /*case R.id.deleteOperator_card:
                break;*/
            case R.id.back_button:
                Intent intent = new Intent(ManageOperator.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getoperatorarea");
            jo.put("admin_mobile", admin_mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, ManageOperator.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getoperatorarea") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            admin_area_id = jo.getString("area_id");
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        Log.d(TAG, "Data is not recieved" + admin_area_id);
    }
}
