package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SetAreaAgreement extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    String adminmobile, areaname, areaid;
    private String TAG = SetAreaAgreement.class.getSimpleName();
    ImageView back;
    TextView area_legal, stepOne, stepTwo, stepThree, stepFour;
    Button set_legal;
    EditText geofencing_conditions, wallet_condition, service_condition, area_condition;

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
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        set_legal = findViewById(R.id.set_legal);
        set_legal.setTypeface(type3);
        set_legal.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SetAreaAgreement.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(SetAreaAgreement.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.set_legal:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (geofencing_conditions.getText().toString().isEmpty() || wallet_condition.getText().toString().isEmpty()
                        || service_condition.getText().toString().isEmpty() || area_condition.getText().toString().isEmpty()) {
                    if (geofencing_conditions.getText().toString().isEmpty() && wallet_condition.getText().toString().isEmpty() &&
                            service_condition.getText().toString().isEmpty() && area_condition.getText().toString().isEmpty()) {
                        geofencing_conditions.startAnimation(animShake);
                        wallet_condition.startAnimation(animShake);
                        service_condition.startAnimation(animShake);
                        area_condition.startAnimation(animShake);
                    } else if (geofencing_conditions.getText().toString().isEmpty()) {
                        geofencing_conditions.startAnimation(animShake);
                    } else if (wallet_condition.getText().toString().isEmpty()) {
                        wallet_condition.startAnimation(animShake);
                    } else if (service_condition.getText().toString().isEmpty()) {
                        service_condition.startAnimation(animShake);
                    } else if (area_condition.getText().toString().isEmpty()) {
                        area_condition.startAnimation(animShake);
                    }
                } else {

                }
                break;
            default:
                break;
        }
    }
}
