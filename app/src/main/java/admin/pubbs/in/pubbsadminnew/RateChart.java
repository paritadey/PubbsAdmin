package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
/*created by Parita Dey*/

public class RateChart extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView back;
    TextView rateTv;
    ImageView upArrow;
    TextView bottomsheetText;
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    String areaNumber, area_Name, adminMobile;
    private String TAG = RateChart.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_chart);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Intent intent = getIntent();
        markerList = (ArrayList<LatLng>) getIntent().getSerializableExtra("markerList");
        areaNumber = intent.getStringExtra("areaNumber");
        area_Name = intent.getStringExtra("area_Name");
        adminMobile = intent.getStringExtra("adminMobile");
        Log.d(TAG, "Area Values:" + markerList + "\t" + area_Name + "\t" + areaNumber + "\t" + adminMobile);

        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        rateTv = findViewById(R.id.rate_chart_tv);
        rateTv.setTypeface(type);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetFragment().show(getSupportFragmentManager(), "dialog");

            }
        });

    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Area");
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        tabOne.setTypeface(type);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.setSelected(true);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Time");
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        tabTwo.setTypeface(type1);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setBackgroundColor(getResources().getColor(R.color.grey_800));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.setBackgroundColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RateChart.this, AddNewArea.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RateChartArea(), "Area");
        adapter.addFragment(new RateChartTime(), "Time");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(RateChart.this, AddNewArea.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle data = new Bundle();
            data.putParcelableArrayList("markerList", markerList);
            data.putString("areaNumber", areaNumber);
            data.putString("area_Name", area_Name);
            data.putString("adminMobile", adminMobile);
            switch (position) {
                case 0:
                    RateChartArea frag_area = new RateChartArea();
                    frag_area.setArguments(data);
                    return frag_area;
                case 1:
                    RateChartTime frag_time = new RateChartTime();
                    frag_time.setArguments(data);
                    return frag_time;
                default:
                    return mFragmentList.get(position);
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
