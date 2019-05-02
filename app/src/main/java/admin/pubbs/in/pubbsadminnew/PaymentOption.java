package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PaymentOption extends AppCompatActivity implements View.OnClickListener {
    String adminmobile, admintype;
    SharedPreferences sharedPreferences;
    private String TAG = PaymentOption.class.getSimpleName();
    TextView add_bank_account_tv, add_bank_tv;
    ImageView back;
    ProgressBar circularProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admintype = sharedPreferences.getString("admin_type", "null");
        Log.d(TAG, "Admin Details:" + adminmobile + "-" + admintype);
        add_bank_account_tv = findViewById(R.id.add_bank_account_tv);
        add_bank_account_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        add_bank_tv = findViewById(R.id.add_bank_tv);
        add_bank_tv.setTypeface(type2);
        add_bank_tv.setOnClickListener(this);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();

    }

    @Override
    public void onBackPressed() {
        //back press will return to the setting page
        Intent intent = new Intent(PaymentOption.this, Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                //back press will return to the setting page
                Intent intent = new Intent(PaymentOption.this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.add_bank_tv:
                new BottomSheetAddBank().show(getSupportFragmentManager(), "dialog");
                break;
            default:
                break;
        }
    }
}
