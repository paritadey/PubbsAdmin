package admin.pubbs.in.pubbsadminnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class SignIn extends Fragment implements AsyncResponse {
    EditText userid, password;
    Button login;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
        userid = rootView.findViewById(R.id.user_id);
        password = rootView.findViewById(R.id.password);
        login = rootView.findViewById(R.id.login_btn);
        login.setOnClickListener(view -> {
            userid.setError(null);
            password.setError(null);
            if (userid.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                if (userid.getText().toString().trim().isEmpty()) {
                    userid.setError("Enter login id");
                }
                if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Enter password");
                }
            } else {
                pd.setMessage("Loading...");
                pd.show();
                JSONObject jo = new JSONObject();
                try {
                    jo.put("method", "auth");
                    jo.put("login_id", login.getText().toString().trim());
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

}
