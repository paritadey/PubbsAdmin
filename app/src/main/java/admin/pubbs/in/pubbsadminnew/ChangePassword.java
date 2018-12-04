package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    String adminmobile, admintype;
    SharedPreferences sharedPreferences;
    private String TAG = ChangePassword.class.getSimpleName();
    ImageView back;
    TextView change_pass, mobile_tv, password_tv;
    EditText user_id, password;
    String userPhone, userNewPassword;
    Button changePassword_btn;
    ProgressDialog progressDialog;
    String UserUrl = "http://pubbs.in/api/1.0/updatePassword.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminmobile = sharedPreferences.getString("adminmobile", null);
        admintype = sharedPreferences.getString("admin_type", "null");
        Log.d(TAG, "Admin Details:" + adminmobile + "-" + admintype);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        change_pass = findViewById(R.id.change_pass);
        change_pass.setTypeface(type1);
        mobile_tv = findViewById(R.id.mobile_tv);
        mobile_tv.setTypeface(type1);
        password_tv = findViewById(R.id.password_tv);
        password_tv.setTypeface(type1);
        user_id = findViewById(R.id.user_id);
        user_id.setTypeface(type1);
        password = findViewById(R.id.password);
        password.setTypeface(type1);
        changePassword_btn = findViewById(R.id.changePassword_btn);
        changePassword_btn.setOnClickListener(this);
        changePassword_btn.setTypeface(type3);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePassword.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(ChangePassword.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.changePassword_btn:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (user_id.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    if (user_id.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                        user_id.startAnimation(animShake);
                        password.startAnimation(animShake);
                    } else if (user_id.getText().toString().isEmpty()) {
                        user_id.startAnimation(animShake);
                    } else if (password.getText().toString().isEmpty()) {
                        password.startAnimation(animShake);
                    }
                } else {
                    userPhone = user_id.getText().toString();
                    userNewPassword = password.getText().toString();
                    if (userPhone.equals(adminmobile)) {
                        PasswordChangeFunction(userPhone, userNewPassword);
                    } else {
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter mobile number properly!";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void PasswordChangeFunction(final String userPhone, final String userNewPassword) {

        class PasswordChangeFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show
                        (ChangePassword.this, "Connecting to the server", "Updating Password...", true, true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Log.d("Password", ""+httpResponseMsg.toString());
               // Toast.makeText(getApplicationContext(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                if (httpResponseMsg.equalsIgnoreCase("Password updated successfully")) {
                    sharedPreferences.edit().clear().commit();
                    Intent intent = new Intent(ChangePassword.this, SignInUp.class);
                    startActivity(intent);
                } else {
                    Log.d("Change Password", "server result: " + httpResponseMsg);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("userPhone", params[0]);
                hashMap.put("userNewPassword", params[1]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }
        PasswordChangeFunctionClass passwordChangeFunctionClass = new PasswordChangeFunctionClass();
        passwordChangeFunctionClass.execute(userPhone, userNewPassword);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }
}
