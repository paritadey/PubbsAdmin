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
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/*created by Parita Dey*/

public class SignIn extends Fragment implements AsyncResponse {
    //xml based variables' declaration
    EditText userid, password;
    Button login;
    ProgressDialog pd, progressDialog;
    Spinner choice;
    TextView mobileTv, passwordTv;
    RelativeLayout layoutMobile, layoutPassword;
    //java file based variables' declaration
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    private String TAG = SignIn.class.getSimpleName();
    String HttpURL = "http://pubbs.in/api/1.0/admin_login.php";
    private static final String[] operator = {"Select Operator", "Super Admin", "Sub Admin", "Employee"};
    //global variables
    int manager, service, driver;
    String adminmobile, admin_password, admin_type, area_id, operator_type, finalResult;

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
                        Log.d(TAG, "Option:" + operator_type);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mobileTv = rootView.findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type1);
        userid = rootView.findViewById(R.id.user_id);//userid is mobile number by which user will login
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
                adminmobile = userid.getText().toString();
                admin_password = password.getText().toString();
                admin_type = operator_type;
                if (admin_type.equals("Employee")) {
                    loadData(adminmobile);
                } else {
                    UserLoginFunction(adminmobile, admin_password, admin_type, area_id, manager, service, driver);
                }
            }
        });

        return rootView;
    }

    private void getEmployeeRank(String area_id, int manager, int service, int driver) {
        Log.d(TAG, "Authority:" + manager + "\t"  + "\t" + service + "\t" + driver);
        if (manager  > 0 || service > 0 || driver > 0) {
            UserLoginFunction(adminmobile, admin_password, admin_type, area_id, manager, service, driver);
        }
    }

    public void loadData(String adminmobile) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getEmployeeRank");
            jo.put("adminmobile", adminmobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getString(R.string.url), jo, SignIn.this, getActivity()).executeJsonRequest();

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getEmployeeRank") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            area_id = jo.getString("area_id");
                            manager = Integer.parseInt(jo.getString("manager"));
                            service = Integer.parseInt(jo.getString("service"));
                            driver = Integer.parseInt(jo.getString("driver"));
                        }
                        Log.d(TAG, "Authority List of Employee:" + area_id + "\t" + manager + "\t"  + "\t" + service + "\t" + driver);
                        getEmployeeRank(area_id, manager, service, driver);
                    }
                } else {
                    Log.d(TAG, "Not Employee move further");
                    // showDashboard(manager, service, driver);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onResponseError(VolleyError error) {
        Log.d(TAG, "Error:" + error.toString());
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public void UserLoginFunction(final String adminmobile, final String adminpassword, final String admin_type, final String area_id,
                                  final int manager, final int service, final int driver) {
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
                    editor.putString("adminmobile", adminmobile); //after inserting the subadmin/admin's mobile number, password and admin_type
                    // both will store in xml by using SharedPreference. It will also store a boolean variable "login" if the sharedPreference
                    // stores the values of mobile_number, password, admin_type
                    editor.putString("password", adminpassword);
                    editor.putString("admin_type", admin_type);
                    //sharedprefernce stores employee rank as well
                    editor.putString("area_id", area_id);
                    editor.putInt("manager", manager);
                    editor.putInt("service", service);
                    editor.putInt("driver", driver);

                    editor.putBoolean("login", true);
                    editor.commit();
                    Log.d("SignIn.java", "SharedPreference stored the value");
                    Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                    intent.putExtra("manager", manager);
                    intent.putExtra("service", service);
                    intent.putExtra("driver", driver);
                    startActivity(intent);
                } else {
                    Log.d("Signin", "server result: " + httpResponseMsg);
                    Toast.makeText(getContext(), "User is not active or Username/password is incorrect.", Toast.LENGTH_LONG).show();
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
