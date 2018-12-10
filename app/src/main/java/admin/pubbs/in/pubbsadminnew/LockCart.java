package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class LockCart extends AppCompatActivity {
    private String fullname, adminmobile, areaid, orderNumber, date_time;
    private String TAG = LockCart.class.getSimpleName();
    ArrayList<String> lockIDList = new ArrayList<String>();
    ArrayList<String> bleAddress = new ArrayList<String>();
    ArrayList<String> choosenLockType = new ArrayList<String>();
    ArrayList<String> simNoList = new ArrayList<String>();
    int frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_cart);
        initView();
    }

    public void initView() {
        Intent intent = getIntent();
        orderNumber = intent.getStringExtra("orderNumber");
        fullname = intent.getStringExtra("fullname");
        adminmobile = intent.getStringExtra("adminmobile");
        areaid = intent.getStringExtra("areaid");
        date_time = intent.getStringExtra("date_time");
        Log.d(TAG, "Sub Admin: " + fullname + "-" + adminmobile + "-" + areaid + "-" + date_time);
        lockIDList = intent.getStringArrayListExtra("lockIDList");
        bleAddress = intent.getStringArrayListExtra("bleAddress");
        choosenLockType = intent.getStringArrayListExtra("choosenLockType");
        simNoList = intent.getStringArrayListExtra("simNoList");
        Log.d(TAG, "Order number:" + orderNumber);
        for (int i = 0; i < lockIDList.size(); i++) {
            Log.d(TAG, "Lock List: " + lockIDList.get(i));
        }
        for (int j = 0; j < bleAddress.size(); j++) {
            Log.d(TAG, "Ble Address:" + bleAddress.get(j));
        }
        for (int k = 0; k < simNoList.size(); k++) {
            Log.d(TAG, "sim List:" + simNoList.get(k));
        }
        for (int l = 0; l < choosenLockType.size(); l++) {
            Log.d(TAG, "Lock type:" + choosenLockType.get(l));
        }
        checkQuantityLock(choosenLockType);
    }

    private void checkQuantityLock(ArrayList<String> choosenLockType) {
        if (choosenLockType.contains("AT_BLE_1") || choosenLockType.contains("QT_BLE_2") || choosenLockType.contains("QT_GSM_GPS")
                || choosenLockType.contains("QT_SMS")) {
            if (choosenLockType.contains("AT_BLE_1")) {
                frequency = Collections.frequency(choosenLockType, "AT_BLE_1");
                Log.d(TAG, "Frequency of AT_BLE_1:" + frequency);
            }
            if (choosenLockType.contains("QT_BLE_2")) {
                frequency = Collections.frequency(choosenLockType, "QT_BLE_2");
                Log.d(TAG, "Frequency of QT_BLE_2:" + frequency);
            }
            if (choosenLockType.contains("QT_GSM_GPS")) {
                frequency = Collections.frequency(choosenLockType, "QT_GSM_GPS");
                Log.d(TAG, "Frequency of QT_GSM_GPS:" + frequency);
            }
            if (choosenLockType.contains("QT_SMS")) {
                frequency = Collections.frequency(choosenLockType, "QT_SMS");
                Log.d(TAG, "Frequency of QT_SMS:" + frequency);
            }
        } else {
            Log.d(TAG, "Some problem occured");
        }
    }
}
