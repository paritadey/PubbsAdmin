package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/*created by Parita Dey*/

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backButton;
    EditText mobile;
    Button getOtp;
    TextView header;
    TextView mobileTv;
    RelativeLayout layoutMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type2);
        backButton = findViewById(R.id.back_button);
        mobile = findViewById(R.id.user_mobile);
        mobile.setTypeface(type2);
        getOtp = findViewById(R.id.get_otp);
        getOtp.setTypeface(type3);
        backButton.setOnClickListener(this);
        getOtp.setOnClickListener(this);
        header = findViewById(R.id.header);
        header.setTypeface(type3);
        layoutMobile = findViewById(R.id.layout_mobile);
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
                if (mobile.getText().toString().trim().isEmpty() || mobile.getText().toString().trim().length() < 10) {
                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    layoutMobile.startAnimation(animShake);
                    View view_layout = findViewById(R.id.otpActivity);
                    String message = "Enter Your Mobile Number";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(view_layout, message, duration);
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

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }
}
