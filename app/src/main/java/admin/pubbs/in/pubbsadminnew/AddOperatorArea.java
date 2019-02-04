
package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
/*created by Parita Dey*/

public class AddOperatorArea extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ImageView back;
    TextView addOperatorTv;
    String areaName, areaId;
    private String TAG = AddOperatorArea.class.getSimpleName();
    EditText fullname, phone, address, email, password;
    TextView fullnameTv, mobileTv, emailTv, addressTv, passwordTv, addOperator;
    RelativeLayout layoutFullname, layoutMobile, layoutEmail, layoutAddress, layoutPassword;
    Spinner choice;
    private static final String[] operator = {"Select Operator", "Sub Admin", "Employee"};
    String operator_type, full_name, admin_mobile, admin_email, admin_address, admin_password;
    String finalResult;
    ProgressDialog progressDialog;
    String UserUrl = "http://pubbs.in/api/1.0/Operator.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operator_area);
        Intent intent = getIntent();
        areaName = intent.getStringExtra("area_name");
        areaId = intent.getStringExtra("area_id");
        Log.d(TAG, "Area Details:" + areaName + "--" + areaId);
        initView();

    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        layoutFullname = findViewById(R.id.layout_fullname);
        layoutMobile = findViewById(R.id.layout_mobile);
        layoutEmail = findViewById(R.id.layout_email);
        layoutAddress = findViewById(R.id.layout_address);
        layoutPassword = findViewById(R.id.layout_password);

        fullnameTv = findViewById(R.id.fullname_tv);
        fullnameTv.setTypeface(type2);
        fullname = findViewById(R.id.fullname);
        fullname.setTypeface(type2);
        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type2);
        phone = findViewById(R.id.mobile);
        phone.setTypeface(type2);
        addressTv = findViewById(R.id.address_tv);
        addressTv.setTypeface(type2);
        address = findViewById(R.id.address);
        address.setTypeface(type2);
        emailTv = findViewById(R.id.email_tv);
        emailTv.setTypeface(type2);
        email = findViewById(R.id.email);
        email.setTypeface(type2);
        passwordTv = findViewById(R.id.password_tv);
        passwordTv.setTypeface(type1);
        password = findViewById(R.id.password);
        password.setTypeface(type1);

        addOperator = findViewById(R.id.add_operator);
        addOperator.setTypeface(type3);
        addOperator.setOnClickListener(this);
        back = findViewById(R.id.back_button);
        addOperatorTv = findViewById(R.id.add_operator_tv);
        addOperatorTv.setTypeface(type1);
        back.setOnClickListener(this);
        choice = findViewById(R.id.choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOperatorArea.this,
                android.R.layout.simple_spinner_item, operator);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice.setAdapter(adapter);
        choice.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddOperatorArea.this, AddOperator.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_operator:
                if (fullname.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty()
                        || address.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty()
                        || password.getText().toString().trim().isEmpty()) {
                    if (fullname.getText().toString().trim().isEmpty() && phone.getText().toString().trim().isEmpty()
                            && address.getText().toString().trim().isEmpty() && email.getText().toString().trim().isEmpty()
                            && password.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        layoutFullname.startAnimation(animShake);
                        layoutMobile.startAnimation(animShake);
                        layoutAddress.startAnimation(animShake);
                        layoutEmail.startAnimation(animShake);
                        layoutPassword.startAnimation(animShake);
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter Your Details";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (fullname.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        layoutFullname.startAnimation(animShake);
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter Your Full Name";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (phone.getText().toString().trim().isEmpty() || phone.getText().toString().trim().length() < 10) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        layoutMobile.startAnimation(animShake);
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter Your Mobile";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (address.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        layoutAddress.startAnimation(animShake);
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter Your Address";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (password.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        layoutPassword.startAnimation(animShake);
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter Your Password";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (email.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        layoutEmail.startAnimation(animShake);
                        View view_layout = findViewById(R.id.constraintLayout);
                        String message = "Enter Your Email ID";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    }
                } else {
                    full_name = fullname.getText().toString().trim();
                    admin_mobile = phone.getText().toString().trim();
                    admin_address = address.getText().toString().trim();
                    admin_email = email.getText().toString().trim();
                    admin_password = password.getText().toString().trim();
                    Log.d(TAG, "Operator details:" + areaName + "-" + areaId + "-" + full_name + "-" + admin_mobile + "-"
                            + admin_address + "-" + admin_email + "-" + admin_password + "-" + operator_type);
                    Operator(full_name, admin_email, admin_mobile, admin_address, admin_password, operator_type, areaName, areaId);
                }
                break;
            case R.id.back_button:
                Intent intent = new Intent(AddOperatorArea.this, AddOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

    private void showDialog(String message) {
        sendSms(admin_mobile, admin_password);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        final TextView serverProblem = (TextView) dialogView.findViewById(R.id.server_problem);
        final TextView extraLine = (TextView) dialogView.findViewById(R.id.extra_line);
        extraLine.setTypeface(type1);
        serverProblem.setTypeface(type1);
        serverProblem.setText(message);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(AddOperatorArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    public void sendSms(String adminmobile, String password) {
        String msg = "Your Login id for PubbsAdmin application :" + adminmobile + "\n" + "Password:" + password;
        Log.d(TAG, "Message: " + msg);
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String(adminmobile));
        smsIntent.putExtra("sms_body", msg);

        try {
            startActivity(smsIntent);
            finish();
            Log.d("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddOperatorArea.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                choice.startAnimation(animShake);
                int duration = Snackbar.LENGTH_SHORT;
                showSnackbar(view, "Choose Your Option", duration);
                break;
            case 1:
                operator_type = choice.getSelectedItem().toString();
                Log.d(TAG, "Option:" + operator_type);
                break;
            case 2:
                operator_type = choice.getSelectedItem().toString();
                Log.d(TAG, "Option:" + operator_type);
                break;
        }
    }


    public void Operator(final String adminfullname, final String adminemail, final String adminmobile, final String adminaddress,
                         final String adminpassword, final String admin_type, final String area_name, final String area_id) {

        class OperatorClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show
                        (AddOperatorArea.this, "Connecting to the server", "Adding Operator...", true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Log.d(TAG, "Asynctask msg:" + httpResponseMsg.toString());
                showOperatorDialog();
                /*if (httpResponseMsg.toString().equals("Operator is Successfully Added !!!")) {
                    showOperatorDialog();
                } else {
                    showDialog("Server Problem !");
                }*/
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("adminfullname", params[0]);
                hashMap.put("adminemail", params[1]);
                hashMap.put("adminmobile", params[2]);
                hashMap.put("adminaddress", params[3]);
                hashMap.put("adminpassword", params[4]);
                hashMap.put("admin_type", params[5]);
                hashMap.put("area_name", params[6]);
                hashMap.put("area_id", params[7]);

                finalResult = httpParse.postRequest(hashMap, UserUrl);

                return finalResult;
            }
        }

        OperatorClass operatorClass = new OperatorClass();

        operatorClass.execute(adminfullname, adminemail, adminmobile, adminaddress, adminpassword, admin_type, area_name, area_id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public void showOperatorDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.operator_add, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        final Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        areaAdd.setTypeface(type1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                sendSms(admin_mobile, admin_password);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
