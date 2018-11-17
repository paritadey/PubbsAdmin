package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Subscription extends AppCompatActivity {
    private String TAG = Subscription.class.getSimpleName();
    String area_id, area_name, basic_plan_amount, basic_plan_month, basic_plan_mins;
    String standard_plan_amount, standard_plan_month, standard_plan_mins, sweet_plan_amount, sweet_plan_month, sweet_plan_mins;
    String premium_plan_amount, premium_plan_month, premium_plan_mins;
    private TextView area_name_tv, area_id_tv, basic_plan_name, basic_plan_amount_tv, basic_plan_month_tv, basic_plan_mins_tv;
    private TextView standard_plan_name, standard_plan_amount_tv, standard_plan_month_tv, standard_plan_mins_tv;
    private TextView sweet_plan_name, sweet_plan_amount_tv, sweet_plan_month_tv, sweet_plan_mins_tv;
    private TextView premium_plan_name, premium_plan_amount_tv, premium_plan_month_tv, premium_plan_mins_tv;
    private TextView subscription_tv;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        area_id = intent.getStringExtra("area_id");
        area_name = intent.getStringExtra("area_name");
        basic_plan_amount = intent.getStringExtra("basic_plan_amount");
        basic_plan_month = intent.getStringExtra("basic_plan_month");
        basic_plan_mins = intent.getStringExtra("basic_plan_mins");
        standard_plan_amount = intent.getStringExtra("standard_plan_amount");
        standard_plan_month = intent.getStringExtra("standard_plan_month");
        standard_plan_mins = intent.getStringExtra("standard_plan_mins");
        sweet_plan_amount = intent.getStringExtra("sweet_plan_amount");
        sweet_plan_month = intent.getStringExtra("sweet_plan_month");
        sweet_plan_mins = intent.getStringExtra("sweet_plan_mins");
        premium_plan_amount = intent.getStringExtra("premium_plan_amount");
        premium_plan_month = intent.getStringExtra("premium_plan_month");
        premium_plan_mins = intent.getStringExtra("premium_plan_mins");
        Log.d(TAG, "Area Subscription:" + area_id + "-" + area_name + "-" + basic_plan_amount + "-" + basic_plan_month + "-" + basic_plan_mins
                + "-" + standard_plan_amount + "-" + standard_plan_month + "-" + standard_plan_mins + "-" + sweet_plan_amount + "-" + sweet_plan_month +
                "-" + sweet_plan_mins + "-" + premium_plan_amount + "-" + premium_plan_month + "-" + premium_plan_mins);
        area_name_tv = findViewById(R.id.area_name);
        area_name_tv.setText(area_name);
        area_id_tv = findViewById(R.id.area_id);
        area_id_tv.setText(area_id);
        basic_plan_name = findViewById(R.id.basic_plan_name);
        basic_plan_amount_tv = findViewById(R.id.basic_plan_amount);
        basic_plan_amount_tv.setText(basic_plan_amount);
        basic_plan_month_tv = findViewById(R.id.basic_plan_month);
        basic_plan_month_tv.setText(basic_plan_month);
        basic_plan_mins_tv = findViewById(R.id.basic_plan_mins);
        basic_plan_mins_tv.setText(basic_plan_mins);
        standard_plan_name = findViewById(R.id.standard_plan_name);
        standard_plan_amount_tv = findViewById(R.id.standard_plan_amount);
        standard_plan_amount_tv.setText(standard_plan_amount);
        standard_plan_month_tv = findViewById(R.id.standard_plan_month);
        standard_plan_month_tv.setText(standard_plan_month);
        standard_plan_mins_tv = findViewById(R.id.standard_plan_mins);
        standard_plan_mins_tv.setText(standard_plan_mins);
        sweet_plan_name = findViewById(R.id.sweet_plan_name);
        sweet_plan_amount_tv = findViewById(R.id.sweet_plan_amount);
        sweet_plan_amount_tv.setText(sweet_plan_amount);
        sweet_plan_month_tv = findViewById(R.id.sweet_plan_month);
        sweet_plan_month_tv.setText(sweet_plan_month);
        sweet_plan_mins_tv = findViewById(R.id.sweet_plan_mins);
        sweet_plan_mins_tv.setText(sweet_plan_mins);
        premium_plan_name = findViewById(R.id.premium_plan_name);
        premium_plan_amount_tv = findViewById(R.id.premium_plan_amount);
        premium_plan_amount_tv.setText(premium_plan_amount);
        premium_plan_month_tv = findViewById(R.id.premium_plan_month);
        premium_plan_month_tv.setText(premium_plan_month);
        premium_plan_mins_tv = findViewById(R.id.premium_plan_mins);
        premium_plan_mins_tv.setText(premium_plan_mins);
        subscription_tv = findViewById(R.id.subscription_tv);
        back = findViewById(R.id.back_button);

    }
}
