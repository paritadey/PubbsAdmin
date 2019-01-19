package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
/*created by Parita Dey*/

public class SignUp extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText fullname, phone, address, email, password, confirmPassword;
    Button getOtp;
    TextView fullnameTv, mobileTv, emailTv, addressTv, passwordTv, confirmPasswordTv;
    RelativeLayout layoutFullname, layoutMobile, layoutEmail, layoutAddress, layoutPassword, layoutConfirmpassword;
    String finalResult;
    ProgressDialog progressDialog;
    String UserUrl = "http://pubbs.in/api/1.0/admin_registration.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    private String adminFullName, adminPhoneNumber, adminEmail, adminAddress, adminPassword;
    private final String TAG = SignUp.class.getSimpleName();
    Spinner choice;
    private static final String[] operator = {"Select Operator", "Sub Admin", "Employee"};
    String operator_type;

    public SignUp() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        choice = rootView.findViewById(R.id.choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operator);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice.setAdapter(adapter);
        choice.setOnItemSelectedListener(this);

        layoutFullname = rootView.findViewById(R.id.layout_fullname);
        layoutMobile = rootView.findViewById(R.id.layout_mobile);
        layoutEmail = rootView.findViewById(R.id.layout_email);
        layoutAddress = rootView.findViewById(R.id.layout_address);
        layoutPassword = rootView.findViewById(R.id.layout_password);
        layoutConfirmpassword = rootView.findViewById(R.id.layout_confirm_password);

        fullnameTv = rootView.findViewById(R.id.fullname_tv);
        fullnameTv.setTypeface(type2);
        fullname = rootView.findViewById(R.id.fullname);
        fullname.setTypeface(type2);
        mobileTv = rootView.findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type2);
        phone = rootView.findViewById(R.id.mobile);
        phone.setTypeface(type2);
        addressTv = rootView.findViewById(R.id.address_tv);
        addressTv.setTypeface(type2);
        address = rootView.findViewById(R.id.address);
        address.setTypeface(type2);
        emailTv = rootView.findViewById(R.id.email_tv);
        emailTv.setTypeface(type2);
        email = rootView.findViewById(R.id.email);
        email.setTypeface(type2);
        passwordTv = rootView.findViewById(R.id.password_tv);
        passwordTv.setTypeface(type1);
        password = rootView.findViewById(R.id.password);
        password.setTypeface(type1);
        confirmPasswordTv = rootView.findViewById(R.id.confirm_password_tv);
        confirmPasswordTv.setTypeface(type1);
        confirmPassword = rootView.findViewById(R.id.confirm_password);
        confirmPassword.setTypeface(type1);

        getOtp = rootView.findViewById(R.id.otp_btn);
        getOtp.setTypeface(type3);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fullname.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty()
                        || address.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty()
                        || password.getText().toString().trim().isEmpty() || confirmPassword.getText().toString().trim().isEmpty()) {

                    if (fullname.getText().toString().trim().isEmpty() && phone.getText().toString().trim().isEmpty()
                            && address.getText().toString().trim().isEmpty() && email.getText().toString().trim().isEmpty()
                            && password.getText().toString().trim().isEmpty() && confirmPassword.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutFullname.startAnimation(animShake);
                        layoutMobile.startAnimation(animShake);
                        layoutAddress.startAnimation(animShake);
                        layoutEmail.startAnimation(animShake);
                        layoutPassword.startAnimation(animShake);
                        layoutConfirmpassword.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Your Details";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);

                    } else if (fullname.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutFullname.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Your Full Name";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (phone.getText().toString().trim().isEmpty() || phone.getText().toString().trim().length() < 10) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutMobile.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Your Mobile";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (address.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutAddress.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Your Address";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (email.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutEmail.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Your Email";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (password.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutPassword.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Your Password";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    } else if (confirmPassword.getText().toString().trim().isEmpty()) {
                        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        layoutConfirmpassword.startAnimation(animShake);
                        View view_layout = rootView.findViewById(R.id.constraintLayout);
                        String message = "Enter Confirm Password";
                        int duration = Snackbar.LENGTH_SHORT;
                        showSnackbar(view_layout, message, duration);
                    }
                } else {
                    if (confirmPassword.getText().toString().trim().equals(password.getText().toString().trim())) {
                        if (operator_type.equals("Select Operator") || operator_type.equals("Sub Admin") || operator_type.equals("Employee")) {
                            showDialog("You are not eligible to Sign Up !!! Please Sign In.");
                        } else {
                            View view_layout = rootView.findViewById(R.id.constraintLayout);
                            String message = "Password matches !!!";
                            int duration = Snackbar.LENGTH_SHORT;
                            showSnackbar(view_layout, message, duration);
                            adminFullName = fullname.getText().toString().trim();
                            adminPhoneNumber = phone.getText().toString().trim();
                            adminAddress = address.getText().toString().trim();
                            adminEmail = email.getText().toString().trim();
                            adminPassword = password.getText().toString().trim();

                            Log.d(TAG, "Admin details: " + adminFullName + "--" + adminEmail + "--" +
                                    adminPhoneNumber + "--" + adminAddress + "--" + adminPassword);
                            AdminRegisterFunction(adminFullName, adminEmail, adminPhoneNumber, adminAddress, adminPassword);
                        }
                    }
                }
            }
        });
        return rootView;
    }

    private void showDialog(String message) {
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
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
                Intent intent = new Intent(getContext(), DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public void AdminRegisterFunction(final String admin_fullname, final String admin_email, final String admin_mobile, final String admin_address,
                                      final String admin_password) {

        class AdminRegisterFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show
                        (getContext(), "Connecting to the server", "Registering User...", true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(getContext(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("adminfullname", params[0]);

                hashMap.put("adminemail", params[1]);

                hashMap.put("adminmobile", params[2]);

                hashMap.put("adminaddress", params[3]);

                hashMap.put("adminpassword", params[4]);

                finalResult = httpParse.postRequest(hashMap, UserUrl);

                return finalResult;
            }
        }

        AdminRegisterFunctionClass adminRegisterFunctionClass = new AdminRegisterFunctionClass();

        adminRegisterFunctionClass.execute(admin_fullname, admin_email, admin_mobile, admin_address, admin_password);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
