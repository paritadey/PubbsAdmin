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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String TAG = UserProfile.class.getSimpleName();
    String userName, userId, userPhone;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        userId = intent.getStringExtra("userid");
        userPhone = intent.getStringExtra("userphone");
        Log.d(TAG, "User name, id, phone: " + userName +"-"+ userId +"-"+ userPhone);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();

    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Wallet");
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.otf");

        tabOne.setTypeface(type);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.setSelected(true);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Trips");
        Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.otf");
        tabTwo.setTypeface(type1);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Plans");
        Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.otf");
        tabThree.setTypeface(type2);
        tabLayout.getTabAt(2).setCustomView(tabThree);

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
            return mFragmentList.get(position);
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
