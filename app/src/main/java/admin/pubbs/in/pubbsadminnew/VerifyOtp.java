package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
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
    TextView verifyOtpTv, otpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent mobile = getIntent();
        userMobile = mobile.getStringExtra("mobile");
        Log.d(TAG, "User phone number: " + userMobile);
        otpTv = findViewById(R.id.otp_tv);
        otpTv.setTypeface(type);
        otpTv.setLetterSpacing(0.1f);
        verifyOtpTv = findViewById(R.id.verify_otp_tv);
        verifyOtpTv.setTypeface(type);
        verifyOtpTv.setLetterSpacing(0.1f);
        otpMessage = findViewById(R.id.otp_message);
        otpMessage.setTypeface(type);
        otpMessage.setLetterSpacing(0.1f);
        otpMessage.setText(getResources().getString(R.string.otp_message) + "--" + userMobile);
        otp = findViewById(R.id.otp);
        otp.setTypeface(type);
        editNumber = findViewById(R.id.edit_number);
        editNumber.setTypeface(type);
        editNumber.setLetterSpacing(0.1f);
        resendOtp = findViewById(R.id.resend_otp);
        resendOtp.setTypeface(type);
        resendOtp.setLetterSpacing(0.1f);
        submitOtp = findViewById(R.id.submit_otp);
        submitOtp.setTypeface(type);
        submitOtp.setLetterSpacing(0.1f);

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
