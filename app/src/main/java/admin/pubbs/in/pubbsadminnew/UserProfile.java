package admin.pubbs.in.pubbsadminnew;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/*created by Parita Dey*/

public class UserProfile extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String TAG = UserProfile.class.getSimpleName();
    String userName, userId, userPhone, userImei;
    android.support.v7.widget.Toolbar toolbar;
    ImageView back;
    TextView userDetails, userID, userIMEI;
    Context context;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        userId = intent.getStringExtra("userid");
        userPhone = intent.getStringExtra("userphone");
        userImei = intent.getStringExtra("userimei");
        Log.d(TAG, "User name, id, phone: " + userName + "-" + userId + "-" + userPhone + "-" + userImei);
        userDetails = findViewById(R.id.user_details);
        userDetails.setText(userName + "\t" + "\t" + userPhone);
        userDetails.setTypeface(type2);
        userID = findViewById(R.id.user_id);
        userID.setText("ID : " + "\t" + userId);
        userID.setTypeface(type2);
        userIMEI = findViewById(R.id.user_ime);
        userIMEI.setTypeface(type2);
        userIMEI.setText("IMEI :" + "\t" + userImei);
        back = findViewById(R.id.back_button);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(UserProfile.this, MyUsers.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });
    }


    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Wallet");
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        tabOne.setTypeface(type);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.setSelected(true);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Trips");
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        tabTwo.setTypeface(type1);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Plans");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        tabThree.setTypeface(type2);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setBackgroundColor(getResources().getColor(R.color.black));
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserProfileWallet(), "Wallet");
        adapter.addFragment(new UserProfileTrips(), "Trips");
        adapter.addFragment(new UserProfilePlans(), "Plans");
        viewPager.setAdapter(adapter);
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
            data.putString("userPhone", userPhone);
            switch (position) {
                case 0:
                    UserProfileWallet frag_1 = new UserProfileWallet();
                    frag_1.setArguments(data);
                    return frag_1;
                case 1:
                    UserProfileTrips frag_2 = new UserProfileTrips();
                    frag_2.setArguments(data);
                    return frag_2;
                case 2:
                    UserProfilePlans frag_3 = new UserProfilePlans();
                    frag_3.setArguments(data);
                    return frag_3;

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
