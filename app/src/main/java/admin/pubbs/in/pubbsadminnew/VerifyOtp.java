package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyOtp extends AppCompatActivity implements View.OnClickListener {

    String userMobile;
    TextView otpMessage;
    private String TAG = VerifyOtp.class.getSimpleName();
    TextView editNumber, resendOtp;
    EditText otp;
    Button submitOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent mobile = getIntent();
        userMobile = mobile.getStringExtra("mobile");
        Log.d(TAG, "User phone number: " + userMobile);
        otpMessage = findViewById(R.id.otp_message);
        otpMessage.setText(getResources().getString(R.string.otp_message) + "-" + userMobile);
        otp = findViewById(R.id.otp);
        editNumber = findViewById(R.id.edit_number);
        resendOtp = findViewById(R.id.resend_otp);
        submitOtp = findViewById(R.id.submit_otp);

        editNumber.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        submitOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_number:
                Intent edit = new Intent(VerifyOtp.this, OtpActivity.class);
                edit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(edit);
                break;
            case R.id.resend_otp:
                //code to be added for resend otp
                break;
            case R.id.submit_otp:
                otp.setError(null);
                if (otp.getText().toString().trim().isEmpty()) {
                    otp.setError("Enter the OTP");
                } else {
                    startActivity(new Intent(VerifyOtp.this, DashBoardActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
