package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import org.json.JSONException;
import org.json.JSONObject;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

/*created by Parita Dey*/
public class SupportChatReply extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    private String sender, sender_date_time, sender_message, sender_email, area_id, admin_mobile;
    private String TAG = SupportChatReply.class.getSimpleName();
    SharedPreferences sharedPreferences;
    TextView reply_tv, user_mobile, to_me_tv, reply_to_tv, reply_to, date_tv, date, user_message;
    ImageView reply;
    CardView sender_details;
    EditText admin_reply;
    SwipeButton swipeButton;
    String ticket_number, reply_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_chat_reply);
        initView();
    }

    //This method initializes all the variables, Typeface, intent data
    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //initializes animation if the reply box is empty
        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        //intent data from SupportUserAdpater
        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        sender_date_time = intent.getStringExtra("date_time");
        sender_message = intent.getStringExtra("user_message");
        sender_email = intent.getStringExtra("user_email");
        area_id = intent.getStringExtra("area_id");
        Log.d(TAG, "Sender details:" + sender + "-" + sender_date_time + "-" + sender_message + "-" + sender_email + "-" + area_id);
        //sharedpreference stores the subadmin's details like phone number of the sub admin
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        admin_mobile = sharedPreferences.getString("adminmobile", "null");
        Log.d(TAG, "Admin phone: " + admin_mobile);
        //defining the textviews in the xml
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
        swipeButton = findViewById(R.id.send_reply_swipe);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Log.d(TAG, "Active:" + active);
                if (admin_reply.getText().toString().isEmpty()) {
                    admin_reply.startAnimation(animShake);
                } else {
                    ticket_number = generateTicketID();
                    reply_msg = admin_reply.getText().toString().trim();
                    Log.d(TAG, "Details:" + ticket_number + "\t" + sender_message + "\t" + sender + "\t" + sender_date_time + "\t" +
                            reply_msg + "\t" + admin_mobile + "\t" + area_id);
                    sendChatReply(ticket_number, sender, sender_message, sender_date_time, reply_msg, admin_mobile, area_id);
                    //this sub admin's reply will go back to the user through email
                    Intent email = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + sender_email));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Reply from Sub Admin with Ticket Number: " + "" + ticket_number);
                    email.putExtra(Intent.EXTRA_TEXT, reply_msg);
                    startActivity(email);
                    finish();
                }
            }
        });
    }

    //onback press go to the previous screen
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SupportChatReply.this, SupportUsers.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //this method will store sub admin's reply with all other details in database
    public void sendChatReply(String ticket_number, String sender, String sender_message, String sender_date_time,
                              String reply_msg, String admin_mobile, String area_id) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("method", "send_admin_reply_chat");
            jo.put("ticket_number", ticket_number);
            jo.put("sender", sender);
            jo.put("sender_message", sender_message);
            jo.put("sender_date_time", sender_date_time);
            jo.put("reply_msg", reply_msg);
            jo.put("admin_mobile", admin_mobile);
            jo.put("area_id", area_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, SupportChatReply.this,
                getApplicationContext()).executeJsonRequest();
    }
    //generate the random ticket number for the reply of the user's chat
    public String generateTicketID() {
        String ticket = "PUBBS_SupportChat_TCKT_";
        String ticketNo;
        int max = 9999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        ticketNo = ticket + randomNum;
        Log.d(TAG, "Ticket number: " + ticketNo);
        return ticketNo;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reply:
                if (admin_reply.getVisibility() == View.VISIBLE) {
                    admin_reply.setVisibility(View.GONE);
                    swipeButton.setVisibility(View.GONE);
                } else {
                    admin_reply.setVisibility(View.VISIBLE);
                    swipeButton.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("send_admin_reply_chat") && jsonObject.getBoolean("success")) {
                    Log.d(TAG, "Suceess");

                } else {
                    Log.d(TAG, "couldn't save try again later");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "Error message:" + e);
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        Log.d(TAG, "msg: " + error.toString());
    }

}
