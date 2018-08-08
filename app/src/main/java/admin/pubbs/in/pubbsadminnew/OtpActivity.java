package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backButton;
    EditText mobile;
    Button getOtp;
    TextView header;
    TextView mobileTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/AvenirLTStd-Book.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type);
        mobileTv.setLetterSpacing(0.1f);
        backButton = findViewById(R.id.back_button);
        mobile = findViewById(R.id.user_mobile);
        mobile.setTypeface(type);
        mobile.setLetterSpacing(0.1f);
        getOtp = findViewById(R.id.get_otp);
        getOtp.setTypeface(type);
        getOtp.setLetterSpacing(0.1f);
        backButton.setOnClickListener(this);
        getOtp.setOnClickListener(this);
        header = findViewById(R.id.header);
        header.setTypeface(type);
        header.setLetterSpacing(0.1f);
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
