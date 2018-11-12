package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
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

public class ContactSuperAdmin extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    ImageView back;
    TextView contactTv;
    String adminmobile, admin_type, msg_subject, msg_body, date_time;
    EditText subject, message;
    Button sendEmail;

    private String TAG = ContactSuperAdmin.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_super_admin);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        Intent intent = getIntent();
        adminmobile = intent.getStringExtra("uphone");
        admin_type = intent.getStringExtra("uadmin");
        Log.d(TAG, "Admin details: " + adminmobile + "-" + admin_type);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        contactTv = findViewById(R.id.contact);
        contactTv.setTypeface(type1);
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
                Intent intent = new Intent(ContactSuperAdmin.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.send_email:
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
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
                    date_time = sdf.format(date);
                    msg_subject = subject.getText().toString().trim();
                    msg_body = message.getText().toString().trim();
                    sendQuery(adminmobile, admin_type, msg_subject, msg_body, date_time);
                    Intent email = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "paritadey@gmail.com"));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Pubbs Admin : "+""+msg_subject);
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

    public void sendQuery(String adminmobile, String admin_type, String subject, String message, String date_time){
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactSuperAdmin.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showQueryAddedDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.area_added_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        final Button ok = (Button)dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        areaAdd.setTypeface(type1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(ContactSuperAdmin.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
