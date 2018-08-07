package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backButton;
    EditText mobile;
    Button getOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backButton = findViewById(R.id.back_button);
        mobile = findViewById(R.id.user_mobile);
        getOtp = findViewById(R.id.get_otp);
        backButton.setOnClickListener(this);
        getOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent back = new Intent(OtpActivity.this, SignInUp.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                break;
            case R.id.get_otp:
                mobile.setError(null);
                if (mobile.getText().toString().trim().isEmpty()) {
                    mobile.setError("Enter Mobile Number");
                } else {
                    Intent getOtp = new Intent(OtpActivity.this, VerifyOtp.class);
                    getOtp.putExtra("mobile", mobile.getText().toString().trim());
                    startActivity(getOtp);
                }
                break;
            default:
                break;
        }
    }
}
