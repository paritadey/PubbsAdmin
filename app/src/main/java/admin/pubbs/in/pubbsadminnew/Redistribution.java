package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Redistribution extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RedistributionAdapter redistributionAdapter;
    private List<RedistributionList> redistributionList = new ArrayList<>();
    ImageView scanQr, back;
    private TextView bicycleTv, drawRedistribution, scanQrTv;
    EditText inputSearch;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redistribution);
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
                launchActivity(ScanQRActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Redistribution.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

}
