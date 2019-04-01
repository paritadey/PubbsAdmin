package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/*created by Parita Dey*/
public class SupportChatReply extends AppCompatActivity implements View.OnClickListener {
    private String sender, sender_date_time, sender_message, sender_email, area_id, admin_mobile;
    private String TAG = SupportChatReply.class.getSimpleName();
    SharedPreferences sharedPreferences;
    TextView reply_tv, user_mobile, to_me_tv, reply_to_tv, reply_to, date_tv, date, user_message;
    ImageView reply;
    CardView sender_details;
    EditText admin_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_chat_reply);
        initView();
    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        sender_date_time = intent.getStringExtra("date_time");
        sender_message = intent.getStringExtra("user_message");
        sender_email = intent.getStringExtra("user_email");
        area_id = intent.getStringExtra("area_id");
        Log.d(TAG, "Sender details:" + sender + "-" + sender_date_time + "-" + sender_message + "-" + sender_email + "-" + area_id);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        admin_mobile = sharedPreferences.getString("adminmobile", "null");
        Log.d(TAG, "Admin phone: " + admin_mobile);
        sender_details = findViewById(R.id.sender_details);
        reply_tv = findViewById(R.id.reply_tv);
        reply_tv.setTypeface(type1);
        user_mobile = findViewById(R.id.user_mobile);
        user_mobile.setTypeface(type1);
        user_mobile.setText(sender);
        to_me_tv = findViewById(R.id.to_me_tv);
        to_me_tv.setTypeface(type1);
        reply = findViewById(R.id.reply);
        reply.setOnClickListener(this);
        reply_to_tv = findViewById(R.id.reply_to_tv);
        reply_to_tv.setTypeface(type1);
        reply_to = findViewById(R.id.reply_to);
        reply_to.setTypeface(type1);
        reply_to.setText(sender_email);
        date_tv = findViewById(R.id.date_tv);
        date_tv.setTypeface(type1);
        date = findViewById(R.id.date);
        date.setTypeface(type1);
        date.setText(sender_date_time);
        user_message = findViewById(R.id.user_message);
        user_message.setText(sender_message);
        user_message.setTypeface(type1);
        admin_reply = findViewById(R.id.admin_reply);
        admin_reply.setTypeface(type1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                if (admin_reply.getVisibility() == View.VISIBLE) {
                    admin_reply.setVisibility(View.GONE);
                } else {
                    admin_reply.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
