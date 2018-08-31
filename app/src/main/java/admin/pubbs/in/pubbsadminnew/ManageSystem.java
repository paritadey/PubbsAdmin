package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageSystem extends AppCompatActivity implements View.OnClickListener {

    private TextView bottomSheetTv, manageSytem, allSetTv, manageYourSystem, moreEfficient, maxRideTv, maxHoldtv, minWalletTv;
    private Button proceed;
    ImageView upArrow, back;
    private final String TAG = ManageSystem.class.getSimpleName();
    CoordinatorLayout manageSystem;
    private EditText maxRide, maxHold, minWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        manageSystem = findViewById(R.id.manage_system);
        back = findViewById(R.id.back_button);
        upArrow = findViewById(R.id.up_arrow);
        bottomSheetTv = findViewById(R.id.bottomsheet_text);
        bottomSheetTv.setTypeface(type1);
        manageSytem = findViewById(R.id.manage_area_tv);
        manageSytem.setTypeface(type1);
        allSetTv = findViewById(R.id.all_set_tv);
        allSetTv.setTypeface(type2);
        manageYourSystem = findViewById(R.id.manage_ur_system_tv);
        manageYourSystem.setTypeface(type2);
        moreEfficient = findViewById(R.id.more_efficient_tv);
        moreEfficient.setTypeface(type2);
        maxRideTv = findViewById(R.id.maximum_ride_tv);
        maxRideTv.setTypeface(type2);
        maxRide = findViewById(R.id.max_time);
        maxRide.setTypeface(type2);
        maxHoldtv = findViewById(R.id.maximum_hold_tv);
        maxHoldtv.setTypeface(type2);
        maxHold = findViewById(R.id.max_hold);
        maxHold.setTypeface(type2);
        minWalletTv = findViewById(R.id.min_wallet_tv);
        minWalletTv.setTypeface(type2);
        minWallet = findViewById(R.id.min_wallet);
        minWallet.setTypeface(type2);
        proceed = findViewById(R.id.proceed_btn);
        proceed.setTypeface(type3);
        upArrow.setOnClickListener(this);
        proceed.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_arrow:
                new BottomSheetFragment().show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(ManageSystem.this, AddNewArea.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.proceed_btn:
                showAreaAddedDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManageSystem.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showAreaAddedDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cycle_added_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        areaAdd.setTypeface(type1);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
                dialogBuilder.setCancelable(false);
            }
        }, 3000);

    }
}
