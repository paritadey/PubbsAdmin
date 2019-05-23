package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

/**
 * Author        : Arpan Bose(arpanbose@hotmail.com)
 * Reviewed by   : Arpan Bose (arpanbose@hotmail.com)
 * Creation Date : 5/14/2018 3:58 PM
 * Modified on   : 5/14/2018 3:58 PM
 * Dependency    :
 * Description   :
 * Execute like  :
 **/
public class TCFragment extends Fragment implements AsyncResponse {
    EditText tc;
    Button submit;
    ProgressDialog pd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_tc_update,container,false);
        tc= v.findViewById(R.id.tc_text);
        submit=v.findViewById(R.id.update);
        pd.setMessage("Please Wait...");
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","getTC");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getString(R.string.url),jo,this,getActivity()).executeJsonRequest();

        submit.setOnClickListener(v1 -> {
            if(!tc.getText().toString().trim().isEmpty()) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(getString(R.string.app_name));
                alertDialog.setMessage("Sure to update?");
                alertDialog.setPositiveButton("Yes", (dialog, which) -> {
                    pd.setMessage("Updating...");
                    pd.show();
                    JSONObject jo1 = new JSONObject();
                    try {
                        jo1.put("method", "setTC");
                        jo1.put("tc", tc.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new SendRequest(getString(R.string.url), jo1, TCFragment.this, getActivity()).executeJsonRequest();
                });
                alertDialog.setNegativeButton("No", null);

                // Showing Alert Message
                alertDialog.show();
            }else{
                tc.setError("Enter term & condition");
            }
        });
        return v;
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if(jsonObject.has("method")){
            try{
                if(jsonObject.getBoolean("success")){
                    if(jsonObject.getString("method").equals("getTC")) {
                        tc.setText(jsonObject.getString("tc"));
                    }
                    if(jsonObject.getString("method").equals("setTC")) {
                        Toast.makeText(getActivity(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        pd.dismiss();
        Toast.makeText(getActivity(), "some error occurred", Toast.LENGTH_SHORT).show();
    }
}
