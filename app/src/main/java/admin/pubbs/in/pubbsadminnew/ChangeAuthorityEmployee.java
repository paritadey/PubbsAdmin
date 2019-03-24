package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChangeAuthorityEmployee extends AppCompatActivity {
    String full_name, admin_mobile, admin_type;
    private String TAG = ChangeAuthorityEmployee.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_authority_employee);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        full_name = intent.getStringExtra("full_name");
        admin_mobile = intent.getStringExtra("admin_mobile");
        admin_type = intent.getStringExtra("admin_type");
        Log.d(TAG, "Employee details:" + full_name + "-" + admin_mobile + "-" + admin_type);
    }
}
