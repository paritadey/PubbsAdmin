package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/*created by Parita Dey*/

public class RateChartSubscription extends Fragment {
    private String TAG = RateChartSubscription.class.getSimpleName();
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    String areaNumber, area_Name, adminMobile;
    TextView subscription_tv, subscription_basic_tv, basic_plan_month_tv, basic_plan_mins_tv;
    EditText basic_plan_amount,basic_plan_month,basic_plan_mins;
    TextView subscription_standard_tv,standard_plan_month_tv,standard_plan_mins_tv;
    EditText standard_plan_amount,standard_plan_month,standard_plan_mins;
    TextView subscription_sweet_tv,sweet_plan_month_tv,sweet_plan_mins_tv;
    EditText sweet_plan_amount,sweet_plan_month,sweet_plan_mins;
    TextView subscription_premium_tv,premium_plan_month_tv,premium_plan_mins_tv;
    EditText premium_plan_amount,premium_plan_month,premium_plan_mins;
    Button proceed;
    String basicPlanAmount, basicPlanMonth, basicPlanMins, standardPlanAmount,standardPlanMonth, standardPlanMins;
    String sweetPlanAmount, sweetPlanMonth, sweetPlanMins, premiumPlanAmount, premiumPlanMonth, premiumPlanMins;

    public RateChartSubscription() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rate_chart_subscription, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Bundle args = getArguments();
        markerList = args.getParcelableArrayList("markerList");
        areaNumber = args.getString("areaNumber");
        area_Name = args.getString("area_Name");
        adminMobile = args.getString("adminMobile");
        Log.d(TAG, "Area Details:"+markerList+"\t"+areaNumber+"\t"+area_Name+"\t"+adminMobile);
        proceed = rootView.findViewById(R.id.proceed_btn);
        proceed.setTypeface(type2);
        subscription_tv = rootView.findViewById(R.id.subscription_tv);
        subscription_tv.setTypeface(type1);
        subscription_basic_tv = rootView.findViewById(R.id.subscription_basic_tv);
        subscription_basic_tv.setTypeface(type1);
        basic_plan_amount = rootView.findViewById(R.id.basic_plan_amount);
        basic_plan_amount.setTypeface(type1);
        basicPlanAmount = basic_plan_amount.getText().toString();
        basic_plan_month_tv = rootView.findViewById(R.id.basic_plan_month_tv);
        basic_plan_month_tv.setTypeface(type1);
        basic_plan_month = rootView.findViewById(R.id.basic_plan_month);
        basic_plan_month.setTypeface(type1);
        basicPlanMonth = basic_plan_month.getText().toString();
        basic_plan_mins_tv = rootView.findViewById(R.id.basic_plan_mins_tv);
        basic_plan_mins_tv.setTypeface(type1);
        basic_plan_mins = rootView.findViewById(R.id.basic_plan_mins);
        basic_plan_mins.setTypeface(type1);
        basicPlanMins = basic_plan_mins.getText().toString();
        subscription_standard_tv = rootView.findViewById(R.id.subscription_standard_tv);
        subscription_standard_tv.setTypeface(type1);
        standard_plan_amount = rootView.findViewById(R.id.standard_plan_amount);
        standard_plan_amount.setTypeface(type1);
        standardPlanAmount = standard_plan_amount.getText().toString();
        standard_plan_month_tv = rootView.findViewById(R.id.standard_plan_month_tv);
        standard_plan_month_tv.setTypeface(type1);
        standard_plan_month = rootView.findViewById(R.id.standard_plan_month);
        standard_plan_month.setTypeface(type1);
        standardPlanMonth = standard_plan_month.getText().toString();
        standard_plan_mins_tv = rootView.findViewById(R.id.standard_plan_mins_tv);
        standard_plan_mins_tv.setTypeface(type1);
        standard_plan_mins = rootView.findViewById(R.id.standard_plan_mins);
        standard_plan_mins.setTypeface(type1);
        standardPlanMins = standard_plan_mins.getText().toString();
        subscription_sweet_tv = rootView.findViewById(R.id.subscription_sweet_tv);
        subscription_sweet_tv.setTypeface(type1);
        sweet_plan_amount = rootView.findViewById(R.id.sweet_plan_amount);
        sweet_plan_amount.setTypeface(type1);
        sweetPlanAmount = sweet_plan_amount.getText().toString();
        sweet_plan_month_tv = rootView.findViewById(R.id.sweet_plan_month_tv);
        sweet_plan_month_tv.setTypeface(type1);
        sweet_plan_month = rootView.findViewById(R.id.sweet_plan_month);
        sweet_plan_month.setTypeface(type1);
        sweetPlanMonth = sweet_plan_month.getText().toString();
        sweet_plan_mins_tv = rootView.findViewById(R.id.sweet_plan_mins_tv);
        sweet_plan_mins_tv.setTypeface(type1);
        sweet_plan_mins = rootView.findViewById(R.id.sweet_plan_mins);
        sweet_plan_mins.setTypeface(type1);
        sweetPlanMins = sweet_plan_mins.getText().toString();
        subscription_premium_tv = rootView.findViewById(R.id.subscription_premium_tv);
        subscription_premium_tv.setTypeface(type1);
        premium_plan_amount = rootView.findViewById(R.id.premium_plan_amount);
        premium_plan_amount.setTypeface(type1);
        premiumPlanAmount = premium_plan_amount.getText().toString();
        premium_plan_month_tv = rootView.findViewById(R.id.premium_plan_month_tv);
        premium_plan_month_tv.setTypeface(type1);
        premium_plan_month = rootView.findViewById(R.id.premium_plan_month);
        premium_plan_month.setTypeface(type1);
        premiumPlanMonth = premium_plan_month.getText().toString();
        premium_plan_mins_tv = rootView.findViewById(R.id.premium_plan_mins_tv);
        premium_plan_mins_tv.setTypeface(type1);
        premium_plan_mins = rootView.findViewById(R.id.premium_plan_mins);
        premium_plan_mins.setTypeface(type1);
        premiumPlanMins = premium_plan_mins.getText().toString();

       /* proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("basicPlanAmount", basicPlanAmount);
                data.putString("basicPlanMonth", basicPlanMonth);
                data.putString("basicPlanMins", basicPlanMins);

            }
        });*/
        return rootView;
    }


}
