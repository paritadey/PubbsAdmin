package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AreaRateChart extends AppCompatActivity {

    String areaname, areaid, adminmobile;
    private String TAG = AreaRateChart.class.getSimpleName();
    SharedPreferences sharedPreferences;

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
        Log.d(TAG, "Area details: "+areaname+"\t"+areaid);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Admin Mobile" + adminmobile);

    }
}
