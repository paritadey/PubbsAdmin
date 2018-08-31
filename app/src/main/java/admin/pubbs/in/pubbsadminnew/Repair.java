package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/*created by Parita Dey*/

public class Repair extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RepairAdapter repairAdapter;
    private List<RepairList> repairList = new ArrayList<>();
    ImageView scanQr, back;
    private TextView bicycleTv, drawRedistribution, scanQrTv;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        back = findViewById(R.id.back_button);
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
        repairAdapter = new RepairAdapter(repairList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(repairAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RepairList lists = repairList.get(position);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Repair.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        prepareBicycleData();
    }
    private void prepareBicycleData() {
        RepairList bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);

        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);
        bicycle = new RepairList("WB213426G");
        repairList.add(bicycle);

        repairAdapter.notifyDataSetChanged();
    }

}
