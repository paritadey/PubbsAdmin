package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class AdminSubscriptionHistory extends AppCompatActivity implements AsyncResponse{

    private String TAG = AdminSubscriptionHistory.class.getSimpleName();
    String adminmobile, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_subscription_history);
        Intent intent = getIntent();
        adminmobile = intent.getStringExtra("adminmobile");
        email = intent.getStringExtra("email");
        Log.d(TAG, "Admin Details:"+adminmobile+"--"+email);

    }

    @Override
    public void onResponse(JSONObject jsonObject) {

    }

    @Override
    public void onResponseError(VolleyError error) {

    }
}
