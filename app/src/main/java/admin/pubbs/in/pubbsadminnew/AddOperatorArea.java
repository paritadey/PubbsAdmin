
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

import admin.pubbs.in.pubbsadminnew.NetworkCall.HttpParse;
/*created by Parita Dey*/
//will work on Admin_v15

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
    String operator_type, full_name, admin_mobile, admin_email, admin_address, admin_password, finalResult, zone_id, operator_id;
    ProgressDialog progressDialog;
    String UserUrl = "http://pubbs.in/api/1.0/Operator.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operator_area);
        //get the areaName and areaId from AddOperator class as intent data
        Intent intent = getIntent();
        areaName = intent.getStringExtra("area_name");
        areaId = intent.getStringExtra("area_id");
        Log.d(TAG, "Area Details:" + areaName + "--" + areaId);
        initView();

    }

    private void initView() {
        //initializing the typeface/fonts for this particular screen
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
        Intent intent = new Intent(AddOperatorArea.this, SuperAdminAddOperator.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //generate a random number starting from 1 to 999. This random number will concatenate with the word 'Zone_'.
    //Every time when the super-admin/ sub-admin create a new operator/sub-admin/employee
    // with its name then this function will create an zone_id with this random number function
    public String generateZoneID() {
        String city = address.getText().toString();
        String ZoneNumber = "Zone_" + city.substring(0, Math.min(city.length(), 4)) + "_"; //get the first 4 characters from the string
        String zone;
        int max = 999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        zone = ZoneNumber + randomNum;
        Log.d(TAG, "Zone Number: " + zone);
        return zone;

    }

    //generate a random number starting from 1 to 9999. This random number will concatenate with the word 'pubbsADMIN_'.
    //Every time when the super-admin/ sub-admin create a new operator/sub-admin/employee
    // with its name then this function will create an admin_id with this random number function
    public String generateOperatorID() {
        String operator_name = fullname.getText().toString().replace(" ", "_");
        String userNumber = "pubbsADMIN_" + operator_name + "_";
        String admin_id;
        int max = 9999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        admin_id = userNumber + randomNum;
        Log.d(TAG, "Admin ID: " + admin_id);
        return admin_id;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_operator:
                //checks if all the textviews are filled up or not then send the data to the server otherwise sows animation with message
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
                    operator_id = generateOperatorID();
                    zone_id = generateZoneID();
                    Log.d(TAG, "Zone and Operator id : " + zone_id + "\t" + operator_id);
                    full_name = fullname.getText().toString().trim();
                    admin_mobile = phone.getText().toString().trim();
                    admin_address = address.getText().toString().trim();
                    admin_email = email.getText().toString().trim();
                    admin_password = password.getText().toString().trim();
                    Log.d(TAG, "Operator details:" + areaName + "-" + areaId + "-" + full_name + "-" + admin_mobile + "-" + admin_address + "-" + admin_email + "-" + admin_password + "-" + operator_type);
                    addOperator(operator_id, full_name, admin_email, admin_mobile, admin_address, admin_password, zone_id, operator_type, areaName, areaId);
                }
                break;
            case R.id.back_button:
                Intent intent = new Intent(AddOperatorArea.this, SuperAdminAddOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

    //sending the default password and the sub-admin's phone number as sms. To send sms this
    // app will redirect to the default message app of the user's phone
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

    //choose items from spinner and store the operator_type
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


    //send admin's operator_id, fullname, phone_number, email, address, password, admin_type, area_name, area_id to the server to store in db
    public void addOperator(final String operator_id, final String adminfullname, final String adminemail, final String adminmobile, final String adminaddress,
                            final String adminpassword, final String zone_id, final String admin_type, final String area_name, final String area_id) {

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
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("operator_id", params[0]);
                hashMap.put("adminfullname", params[1]);
                hashMap.put("adminemail", params[2]);
                hashMap.put("adminmobile", params[3]);
                hashMap.put("adminaddress", params[4]);
                hashMap.put("adminpassword", params[5]);
                hashMap.put("zone_id", params[6]);
                hashMap.put("admin_type", params[7]);
                hashMap.put("area_name", params[8]);
                hashMap.put("area_id", params[9]);

                finalResult = httpParse.postRequest(hashMap, UserUrl);

                return finalResult;
            }
        }

        OperatorClass operatorClass = new OperatorClass();

        operatorClass.execute(operator_id, adminfullname, adminemail, adminmobile, adminaddress, adminpassword, zone_id, admin_type, area_name, area_id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //show error msg in snackbar
    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    //if successfull in adding the operator to the server then show msg in dialog box and send
    // admin's default passowrd and phone number
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
