package admin.pubbs.in.pubbsadminnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;

/*created by Parita Dey*/

public class SignIn extends Fragment {//implements AsyncResponse {
    EditText userid, password;
    Button login;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView mobileTv, passwordTv;
    RelativeLayout layoutMobile, layoutPassword;
    String finalResult;
    String HttpURL = "http://pubbs.in/api/1.0/admin_login.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String operator_type;
    Spinner choice;
    private String TAG =SignIn.class.getSimpleName();
    private static final String[] operator = {"Select Operator", "Super Admin", "Sub Admin", "Employee"};

    public SignIn() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        choice = rootView.findViewById(R.id.choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, operator);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice.setAdapter(adapter);
        choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    case 3:
                        operator_type = choice.getSelectedItem().toString();
                        Log.d(TAG, "Option:"+operator_type);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mobileTv = rootView.findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type1);
        userid = rootView.findViewById(R.id.user_id);
        userid.setTypeface(type1);
        passwordTv = rootView.findViewById(R.id.password_tv);
        passwordTv.setTypeface(type2);
        password = rootView.findViewById(R.id.password);
        password.setTypeface(type2);
        login = rootView.findViewById(R.id.login_btn);
        login.setTypeface(type3);
        layoutMobile = rootView.findViewById(R.id.layout_mobile);
        layoutPassword = rootView.findViewById(R.id.layout_password);
        login.setOnClickListener(view -> {
            if (userid.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                if (userid.getText().toString().trim().isEmpty() && password.getText().toString().trim().isEmpty()) {
                    final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    layoutMobile.startAnimation(animShake);
                    layoutPassword.startAnimation(animShake);
                    View view_layout = rootView.findViewById(R.id.singin);
                    String message = "Enter Your Mobile Number and Password";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(view_layout, message, duration);
                } else if (userid.getText().toString().trim().isEmpty()) {
                    final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    layoutMobile.startAnimation(animShake);
                    View view_layout = rootView.findViewById(R.id.singin);
                    String message = "Enter Your Mobile Number";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(view_layout, message, duration);
                } else if (password.getText().toString().trim().isEmpty()) {
                    final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    layoutPassword.startAnimation(animShake);
                    View view_layout = rootView.findViewById(R.id.singin);
                    String message = "Enter Your Password";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(view_layout, message, duration);
                }
            } else {
                String adminmobile = userid.getText().toString();
                String admin_password = password.getText().toString();
                String admin_type = operator_type;
                UserLoginFunction(adminmobile, admin_password, admin_type);
            }
        });

        return rootView;
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public void UserLoginFunction(final String adminmobile, final String adminpassword, final String admin_type) {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(getContext(), "Connecting to the server", "Fetching data...", true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if (httpResponseMsg.equalsIgnoreCase("Login Successful")) {

                    editor.putString("adminmobile", adminmobile);
                    editor.putString("password", adminpassword);
                    editor.putString("admin_type", admin_type);
                    editor.putBoolean("login",true);
                    editor.commit();
                    Log.d("SignIn.java", "SharedPreference stored the value");
                    Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                    startActivity(intent);

                } else {

                    Log.d("Signin", "server result: " + httpResponseMsg);
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("adminmobile", params[0]);

                hashMap.put("password", params[1]);

                hashMap.put("admin_type", params[2]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(adminmobile, adminpassword, admin_type);
    }

}
