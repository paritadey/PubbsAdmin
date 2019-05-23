package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;
/*created by Parita Dey*/

public class ContactSuperAdmin extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    //declaring the variables
    ImageView back;
    TextView contactTv, contact_tv, disclaimer;
    String adminmobile, admin_type, msg_subject, msg_body, date_time;
    EditText subject, message;
    Button sendEmail;
    SharedPreferences sharedPreferences;
    private String TAG = ContactSuperAdmin.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_super_admin);
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //get the adminmobile and admin_type from SharedPrefernce
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", "null");
        admin_type = sharedPreferences.getString("admin_type", "null");
        Log.d(TAG, "Admin details: " + adminmobile + "-" + admin_type);

        disclaimer = findViewById(R.id.disclaimer);
        disclaimer.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        contact_tv = findViewById(R.id.contact_tv);
        contact_tv.setTypeface(type1);
        contactTv = findViewById(R.id.contact);
        contactTv.setTypeface(type1);
        if(admin_type.equals("Sub Admin")){
            contactTv.setText("Contact Super Admin");
            contact_tv.setText("Write message to Super-Admin");
        }else if(admin_type.equals("Employee")){
            contactTv.setText("Contact Sub Admin");
            contact_tv.setText("Write message to Sub-Admin");
        }
        subject = findViewById(R.id.subject);
        subject.setTypeface(type1);
        message = findViewById(R.id.message);
        message.setTypeface(type1);
        sendEmail = findViewById(R.id.send_email);
        sendEmail.setTypeface(type2);
        sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                //on back press move back to the main landing screen i.e Dashboard by clearing all the previous stack history
                Intent intent = new Intent(ContactSuperAdmin.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.send_email:
                //checks if the subject, message are not emptied then send the msg as email by
                // opening the email app in the user's phone otherwise animate subject and message textviews
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (subject.getText().toString().isEmpty() || message.getText().toString().isEmpty()) {
                    if (subject.getText().toString().isEmpty() && message.getText().toString().isEmpty()) {
                        subject.startAnimation(animShake);
                        message.startAnimation(animShake);
                    } else if (subject.getText().toString().isEmpty()) {
                        subject.startAnimation(animShake);
                    } else if (message.getText().toString().isEmpty()) {
                        message.startAnimation(animShake);
                    }
                } else {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
                    date_time = sdf.format(date);
                    msg_subject = subject.getText().toString().trim();
                    msg_body = message.getText().toString().trim();
                    sendQuery(adminmobile, admin_type, msg_subject, msg_body, date_time);
                    Intent email = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "paritadey@gmail.com"));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Pubbs Admin : " + "" + msg_subject);
                    email.putExtra(Intent.EXTRA_TEXT, "Admin/Employee ID is: " + "" + adminmobile + "" + "of admin type :" + "" + admin_type + "\n"
                            + msg_body);
                    startActivity(email);
                    finish();
                }
                break;
            default:
                break;
        }

    }

    //this function will send the query to the server with all details like adminmobile, admin_Type, subject, message and date_time
    public void sendQuery(String adminmobile, String admin_type, String subject, String message, String date_time) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "add_query");
            jo.put("adminmobile", adminmobile);
            jo.put("admin_type", admin_type);
            jo.put("subject", subject);
            jo.put("message", message);
            jo.put("date_time", date_time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, ContactSuperAdmin.this, getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("add_query") && jsonObject.getBoolean("success")) {
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
        Log.d(TAG, "msg: " + error.toString());
    }

    @Override
    public void onBackPressed() {
        //on back press move back to the main landing screen i.e Dashboard by clearing all the previous stack history
        Intent intent = new Intent(ContactSuperAdmin.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}