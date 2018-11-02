package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class FeedbackDetails extends AppCompatActivity implements View.OnClickListener {
    private String admin_mobile, admin_type, admin_subject, admin_message, admin_datetime, admin_email;
    private String reply_dateTime, reply_msg;
    private String TAG = FeedbackDetails.class.getSimpleName();
    private TextView adminMobile, adminType, date_time, message, feedback_tv;
    private EditText admin_reply;
    private Button super_admin_reply;
    private ImageView message_img, reply, back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        admin_mobile = intent.getStringExtra("adminmobile");
        admin_type = intent.getStringExtra("admin_type");
        admin_subject = intent.getStringExtra("subject");
        admin_message = intent.getStringExtra("message");
        admin_datetime = intent.getStringExtra("date_time");
        admin_email = intent.getStringExtra("email");
        Log.d(TAG, "Feedback details: " + admin_mobile + "-" + admin_type + "-" + admin_subject + "-" + admin_message + "-" + admin_datetime +
                "-" + admin_email);
        adminMobile = findViewById(R.id.adminmobile);
        adminMobile.setTypeface(type1);
        adminMobile.setText(admin_mobile);
        adminType = findViewById(R.id.adminType);
        adminType.setTypeface(type1);
        adminType.setText(admin_type);
        date_time = findViewById(R.id.date_time);
        date_time.setTypeface(type1);
        date_time.setText(admin_datetime);
        message = findViewById(R.id.message);
        message.setTypeface(type2);
        message.setText(admin_message);
        admin_reply = findViewById(R.id.admin_reply);
        admin_reply.setTypeface(type1);
        super_admin_reply = findViewById(R.id.super_admin_reply);
        super_admin_reply.setTypeface(type3);
        message_img = findViewById(R.id.message_img);
        reply = findViewById(R.id.reply);
        feedback_tv = findViewById(R.id.feedback_tv);
        feedback_tv.setTypeface(type1);
        back_button = findViewById(R.id.back_button);
        reply.setOnClickListener(this);
        back_button.setOnClickListener(this);
        super_admin_reply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                admin_reply.setVisibility(View.VISIBLE);
                super_admin_reply.setVisibility(View.VISIBLE);
                break;
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(FeedbackDetails.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.super_admin_reply:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if(admin_reply.getText().toString().isEmpty()){
                    admin_reply.startAnimation(animShake);
                }else {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
                    reply_dateTime = sdf.format(date);
                    reply_msg = admin_reply.getText().toString();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FeedbackDetails.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
