package admin.pubbs.in.pubbsadminnew;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PaymentOption extends AppCompatActivity {
    String adminmobile, admintype;
    SharedPreferences sharedPreferences;
    private String TAG = PaymentOption.class.getSimpleName();
    TextView payment_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        initView();
    }
    public void initView(){
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admintype = sharedPreferences.getString("admin_type", "null");
        Log.d(TAG, "Admin Details:" + adminmobile + "-" + admintype);
        payment_tv = findViewById(R.id.payment_tv);
        payment_tv.setTypeface(type1);
    }
}
