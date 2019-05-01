package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*created by Parita Dey*/
public class AddBicycle extends AppCompatActivity implements View.OnClickListener {
    //xml based variables
    private TextView addBicycleTv, bicycleTv, bottomsheetText;
    private Button addBicycle;
    private ImageView back, upArrow;
    //java based variables
    private static final int ZXING_CAMERA_PERMISSION = 1;//camera permission for scanning qr code
    private Class<?> mClss;
    String station_name, station_id, area_name, area_id;
    private String TAG = AddBicycle.class.getSimpleName();
    SharedPreferences sharedPreferences;
    String adminmobile, admin_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bicycle);
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //getting the mobile number and the type of the admin using the app from sharedpreference
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admin_type = sharedPreferences.getString("admin_type", null);
        //getting the station_name, station_id, area_name, area_id as intent data from AreaStations addapter AddNewBicycleAdapter
        Intent intent = getIntent();
        station_name = intent.getStringExtra("station_name");
        station_id = intent.getStringExtra("station_id");
        area_name = intent.getStringExtra("area_name");
        area_id = intent.getStringExtra("area_id");
        Log.d(TAG, "Station Details:" + station_name + "-" + station_id + "-" + area_name + "-" + area_id + "-" + adminmobile + "-" + admin_type);
        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type1);
        addBicycleTv = findViewById(R.id.add_bicycle_tv);
        addBicycleTv.setTypeface(type1);
        addBicycle = findViewById(R.id.add_bicycle);
        addBicycle.setTypeface(type3);
        bicycleTv = findViewById(R.id.bicycle_tv);
        bicycleTv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        upArrow = findViewById(R.id.up_arrow);
        back.setOnClickListener(this);
        upArrow.setOnClickListener(this);
        addBicycle.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddBicycle.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_arrow:
                //on clicking of up_arrow in the xml Bottom Sheet Fragment will be seen
                new BottomSheetAddNewBicycleFragment().show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.back_button:
                //back_button will go back to the previous page by clearing the stack history
                Intent intent = new Intent(AddBicycle.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.add_bicycle:
                //on clicking this button AddBicycleQRActivity will open to scan the qr code from the cycle
                launchActivity(AddBicycleQRActivity.class);
                break;
            default:
                break;
        }
    }

    //this class will move from one class to another class and contain all the values like station_name, station_id, area_name, area_id
    //adminmobile and admin_type from the recent AddBicycle class to AddBicycleQRActivity class as intent values
    public void launchActivity(Class<?> clss) {
        //checking the permission grant from user to use the camera
        // If the user opens the app for the first time app will ask for the permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            intent.putExtra("station_name", station_name);
            intent.putExtra("station_id", station_id);
            intent.putExtra("area_name", area_name);
            intent.putExtra("area_id", area_id);
            intent.putExtra("adminmobile", adminmobile);
            intent.putExtra("admin_type", admin_type);
            startActivity(intent);
        }
    }

    //if the user doesn't agree with the permission grant then it will ask user to agree
    // it otherwise open the class and start scanning the qr code
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
