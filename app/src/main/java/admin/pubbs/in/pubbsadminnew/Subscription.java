package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Subscription extends AppCompatActivity {
    private String TAG = Subscription.class.getSimpleName();
    String area_id, area_name, basic_plan_amount, basic_plan_month, basic_plan_mins;
    String standard_plan_amount, standard_plan_month, standard_plan_mins, sweet_plan_amount, sweet_plan_month, sweet_plan_mins;
    String premium_plan_amount, premium_plan_month, premium_plan_mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
    }

    public void initView() {
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
    }
}
