package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
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

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
/*created by Parita Dey*/

public class FeedbackDetails extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    private String admin_Mobile, admin_Type, admin_Subject, admin_Message, admin_Datetime, admin_Email;
    private String reply_dateTime, reply_msg;
    private String TAG = FeedbackDetails.class.getSimpleName();
    private TextView admin_mobile, admin_type, date_time, admin_message, manage_feedback_tv;
    private EditText admin_reply;
    private Button send_reply;
    private ImageView message_img, reply, back_button;
    SharedPreferences sharedPreferences;
    String super_admin_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_details);
        initView();
    }
    //This method initializes all the variables, Typeface, intent data
    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //sharedpreference stores the subadmin's details like phone number of the super admin
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        super_admin_phone = sharedPreferences.getString("adminmobile", "null");
        Log.d(TAG, "Super admin phone: " + super_admin_phone);
        Intent intent = getIntent();
        admin_Mobile = intent.getStringExtra("adminmobile");
        admin_Type = intent.getStringExtra("admin_type");
        admin_Subject = intent.getStringExtra("subject");
        admin_Message = intent.getStringExtra("message");
        admin_Datetime = intent.getStringExtra("date_time");
        admin_Email = intent.getStringExtra("email");
        Log.d(TAG, "Feedback details: " + admin_Mobile + "-" + admin_Type + "-" + admin_Subject + "-" + admin_Message + "-" + admin_Datetime +
                "-" + admin_Email);
        //defining the textviews in the xml
        admin_mobile = findViewById(R.id.admin_mobile);
        admin_mobile.setTypeface(type2);
        admin_mobile.setText(admin_Mobile);

        admin_type = findViewById(R.id.admin_type);
        admin_type.setTypeface(type1);
        admin_type.setText(admin_Type);

        date_time = findViewById(R.id.date_time);
        date_time.setText(admin_Datetime);
        date_time.setTypeface(type1);

        admin_message = findViewById(R.id.admin_message);
        admin_message.setTypeface(type1);
        admin_message.setText(admin_Message);

        manage_feedback_tv = findViewById(R.id.manage_feedback_tv);
        manage_feedback_tv.setTypeface(type1);

        admin_reply = findViewById(R.id.admin_reply);
        admin_reply.setTypeface(type1);

        send_reply = findViewById(R.id.send_reply);
        send_reply.setTypeface(type3);
        send_reply.setOnClickListener(this);

        message_img = findViewById(R.id.message_img);

        reply = findViewById(R.id.reply);
        reply.setOnClickListener(this);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                admin_reply.setVisibility(View.VISIBLE);
                send_reply.setVisibility(View.VISIBLE);
                break;
            case R.id.back_button:
                Log.d(TAG, "Back button pressed");
                Intent intent = new Intent(FeedbackDetails.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.send_reply:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (admin_reply.getText().toString().isEmpty()) {
                    admin_reply.startAnimation(animShake);
                } else {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
                    reply_dateTime = sdf.format(date);
                    reply_msg = admin_reply.getText().toString();
                    Log.d(TAG, "Date and message from super admin: " + reply_dateTime + "***" + reply_msg);
                    sendQuery(admin_Mobile, admin_Type, admin_Email, admin_Datetime, admin_Message, admin_Subject,
                            reply_msg, super_admin_phone, reply_dateTime);
                    //this super admin's reply will go back to the user through email
                    Intent email = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + admin_Email));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Reply from Super Admin of Subject: " + "" + admin_Subject);
                    email.putExtra(Intent.EXTRA_TEXT, reply_msg);
                    startActivity(email);
                    finish();
                }
                break;
            default:
                break;
        }

    }
    //onback press go to the previous screen
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FeedbackDetails.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //this method will store super admin's reply with all other details in database
    public void sendQuery(String adminmobile, String admin_type, String email, String message_date_time,
                          String message, String subject, String reply, String super_admin, String reply_date_time) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "send_query_ans");
            jo.put("adminmobile", adminmobile);
            jo.put("admin_type", admin_type);
            jo.put("email", email);
            jo.put("message_date_time", message_date_time);
            jo.put("message", message);
            jo.put("subject", subject);
            jo.put("reply", reply);
            jo.put("super_admin", super_admin);
            jo.put("reply_date_time", reply_date_time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, FeedbackDetails.this, getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("send_query_ans") && jsonObject.getBoolean("success")) {
                    //showQueryAddedDialog();
                    Log.d(TAG, "Suceess");
                } else {
                    Log.d(TAG, "couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        Log.d(TAG, "msg: "+error.toString());
    }
}
