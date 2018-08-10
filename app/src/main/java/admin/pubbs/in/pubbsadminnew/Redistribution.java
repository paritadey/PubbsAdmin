package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Redistribution extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RedistributionAdapter redistributionAdapter;
    private List<RedistributionList> redistributionList = new ArrayList<>();
    ImageView scanQr;
    private TextView bicycleTv, drawRedistribution, scanQrTv;
    AutoCompleteTextView inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redistribution);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type1);
        inputSearch = findViewById(R.id.input_search);
        inputSearch.setTypeface(type1);
        drawRedistribution = findViewById(R.id.draw_redistribution);
        drawRedistribution.setTypeface(type2);
        scanQrTv = findViewById(R.id.scan_qr_tv);
        scanQrTv.setTypeface(type2);

        scanQr = findViewById(R.id.scan_qr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        redistributionAdapter = new RedistributionAdapter(redistributionList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(redistributionAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RedistributionList lists = redistributionList.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        prepareBicycleData();
    }

    private void prepareBicycleData() {
        RedistributionList bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);

        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);
        bicycle = new RedistributionList("WB213426G");
        redistributionList.add(bicycle);

        redistributionAdapter.notifyDataSetChanged();
    }
}
