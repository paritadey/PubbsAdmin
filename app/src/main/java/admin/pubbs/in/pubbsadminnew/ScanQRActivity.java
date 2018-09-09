package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
/*created by Parita Dey*/

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private ZXingScannerView mScannerView;
    ImageView upArrow, back;
    private final String TAG = ScanQRActivity.class.getSimpleName();
    TextView redistributionTv, scanQrTv, enterNumberTv, bicycleTv, bicycleNumberTv, bottomSheetTv;
    EditText bicycleId;
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        redistributionTv = findViewById(R.id.redistribution_tv);
        redistributionTv.setTypeface(type1);
        scanQrTv = findViewById(R.id.scan_qr_tv);
        scanQrTv.setTypeface(type2);
        enterNumberTv = findViewById(R.id.enter_number_tv);
        enterNumberTv.setTypeface(type2);
        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type2);
        bicycleNumberTv = findViewById(R.id.bicycle_number_tv);
        bicycleNumberTv.setTypeface(type2);
        bicycleId = findViewById(R.id.bicycle_id);
        bicycleId.setTypeface(type2);
        proceed = findViewById(R.id.proceed_btn);
        proceed.setTypeface(type3);
        bottomSheetTv = findViewById(R.id.bottomsheet_text);
        bottomSheetTv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        upArrow = findViewById(R.id.up_arrow);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        upArrow.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanQRActivity.this);
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_arrow:
                new BottomSheetAreaFragment().show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(ScanQRActivity.this, Redistribution.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

