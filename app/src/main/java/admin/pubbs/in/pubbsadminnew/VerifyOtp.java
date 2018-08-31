package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/*created by Parita Dey*/

public class VerifyOtp extends AppCompatActivity implements View.OnClickListener {

    String userMobile;
    TextView otpMessage;
    private String TAG = VerifyOtp.class.getSimpleName();
    TextView editNumber, resendOtp;
    EditText otp;
    Button submitOtp;
    TextView verifyOtpTv, otpTv;
    RelativeLayout layoutOtp;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent mobile = getIntent();
        userMobile = mobile.getStringExtra("mobile");
        Log.d(TAG, "User phone number: " + userMobile);
        otpTv = findViewById(R.id.otp_tv);
        otpTv.setTypeface(type2);
        verifyOtpTv = findViewById(R.id.verify_otp_tv);
        verifyOtpTv.setTypeface(type3);
        otpMessage = findViewById(R.id.otp_message);
        otpMessage.setTypeface(type2);
        otpMessage.setText(getResources().getString(R.string.otp_message) + "--" + userMobile);
        otp = findViewById(R.id.otp);
        otp.setTypeface(type2);
        editNumber = findViewById(R.id.edit_number);
        editNumber.setTypeface(type2);
        resendOtp = findViewById(R.id.resend_otp);
        resendOtp.setTypeface(type2);
        submitOtp = findViewById(R.id.submit_otp);
        submitOtp.setTypeface(type3);
        layoutOtp = findViewById(R.id.layout_otp);
        backButton = findViewById(R.id.back_button);
        editNumber.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        submitOtp.setOnClickListener(this);
        backButton.setOnClickListener(this);
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
                if (otp.getText().toString().trim().isEmpty()) {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layoutOtp.startAnimation(animShake);
                    View view_layout = findViewById(R.id.verifyOtp);
                    String message = "Enter the OTP";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(view_layout, message, duration);
                } else {
                    startActivity(new Intent(VerifyOtp.this, DashBoardActivity.class));
                }
                break;
            case R.id.back_button:
                Intent back = new Intent(VerifyOtp.this, OtpActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                break;
            default:
                break;
        }
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

}
