package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    String adminmobile, admintype;
    SharedPreferences sharedPreferences;
    private String TAG = Settings.class.getSimpleName();
    ImageView back;
    TextView settings_tv, adminmobile_tv, admin_mobile, admintype_tv, admin_type, change_password, payments, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
        settings_tv = findViewById(R.id.settings_tv);
        settings_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        adminmobile_tv = findViewById(R.id.adminmobile_tv);
        adminmobile_tv.setTypeface(type1);
        admin_mobile = findViewById(R.id.admin_mobile);
        admin_mobile.setText(adminmobile);
        admin_mobile.setTypeface(type1);
        admintype_tv = findViewById(R.id.admintype_tv);
        admintype_tv.setTypeface(type1);
        admin_type = findViewById(R.id.admin_type);
        admin_type.setTypeface(type1);
        admin_type.setText(admintype);
        change_password = findViewById(R.id.change_password);
        change_password.setTypeface(type1);
        payments = findViewById(R.id.payments);
        payments.setTypeface(type1);
        about = findViewById(R.id.about);
        about.setTypeface(type1);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(Settings.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.change_password:
                startActivity(new Intent(Settings.this, ChangePassword.class));
                break;
            case R.id.payments:
                break;
            case R.id.about:
                break;
            default:
                break;
        }
    }
}
