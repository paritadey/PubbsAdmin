package admin.pubbs.in.pubbsadminnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class SignIn extends Fragment implements AsyncResponse {
    EditText userid, password;
    Button login;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView mobileTv, passwordTv;
    RelativeLayout layoutMobile, layoutPassword;

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
        Typeface type3 = Typeface.createFromAsset(getContext().getAssets(),"fonts/AvenirNextLTPro-Bold.otf");

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
                pd.setMessage("Loading...");
                pd.show();
                JSONObject jo = new JSONObject();
                try {
                    jo.put("method", "auth");
                    jo.put("login_id", userid.getText().toString().trim());
                    jo.put("password", password.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new SendRequest(getResources().getString(R.string.url), jo, SignIn.this, getActivity())
                        .executeJsonRequest();
            }
        });

        return rootView;
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("auth") && jsonObject.getBoolean("success")) {
                    editor.putBoolean("login", true);
                    editor.commit();
                    startActivity(new Intent(getActivity(), DashBoardActivity.class));
                } else {
                    AppConfig.alertMsg(getActivity(), getResources().getString(R.string.invalid_auth));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(), getResources().getString(R.string.server_error));
        pd.dismiss();
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

}
