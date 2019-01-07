package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AreaRateChart extends AppCompatActivity {

    String areaname, areaid, adminmobile;
    private String TAG = AreaRateChart.class.getSimpleName();
    SharedPreferences sharedPreferences;
    Spinner choice;
    private static final String[] rateTypes = {"Select Rate ", "Time", "Distance"};
    String rate_type;
    ConstraintLayout rateChartTime,rateChartDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_rate_chart);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        areaname = intent.getStringExtra("areaname");
        areaid = intent.getStringExtra("areaid");
        Log.d(TAG, "Area details: " + areaname + "\t" + areaid);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Admin Mobile" + adminmobile);

        rateChartTime = findViewById(R.id.rateChartTime);
        rateChartDistance = findViewById(R.id.rateChartDistance);

        choice = findViewById(R.id.choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, rateTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice.setAdapter(adapter);
        choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        choice.startAnimation(animShake);
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view, "Choose Your Option", duration);
                        break;
                    case 1:
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartTime.setVisibility(View.VISIBLE);
                        rateChartDistance.setVisibility(View.GONE);
                        break;
                    case 2:
                        rate_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:" + rate_type);
                        rateChartDistance.setVisibility(View.VISIBLE);
                        rateChartTime.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

}
