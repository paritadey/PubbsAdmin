package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LORD on 8/23/2017.
 */

public class LoginFragment extends Fragment implements AsyncResponse{

    EditText login,password;
    Button btn;
    FragmentManager fragmentManager;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Login");
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        fragmentManager=getFragmentManager();
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_login,container,false);
        login= v.findViewById(R.id.login);
        password= v.findViewById(R.id.password);
        btn= v.findViewById(R.id.login_btn);

        btn.setOnClickListener(view -> {
            login.setError(null);
            password.setError(null);
            if (login.getText().toString().trim().isEmpty() ||password.getText().toString().trim().isEmpty()){
                if (login.getText().toString().trim().isEmpty()){
                    login.setError("Enter login id");
                }
                if (password.getText().toString().trim().isEmpty()){
                    password.setError("Enter password");
                }
            }else{
                pd.setMessage("Loading...");
                pd.show();
                JSONObject jo=new JSONObject();
                try {
                    jo.put("method","auth");
                    jo.put("login_id",login.getText().toString().trim());
                    jo.put("password",password.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new SendRequest(getResources().getString(R.string.url),jo,LoginFragment.this,getActivity()).executeJsonRequest();
            }
        });
        return v;
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if (jsonObject.has("method")){
            try {
                if (jsonObject.getString("method").equals("auth") && jsonObject.getBoolean("success")){
                    editor.putBoolean("login",true);
                    editor.commit();
                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.animator.slide_left_enter,
                                    R.animator.slide_right_enter, R.animator.slide_right_exit,
                                    R.animator.slide_left_exit)
                            .replace(R.id.myFrame,new DashboardFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }else{
                    AppConfig.alertMsg(getActivity(),getResources().getString(R.string.invalid_auth));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(),getResources().getString(R.string.server_error));
        pd.dismiss();
    }
}
