package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LORD on 8/27/2017.
 */

public class AddAreaFragment extends Fragment implements AsyncResponse{

    EditText name;
    Button submit;
    ProgressDialog pd;
    String lat,lng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        lat=getArguments().getString("lat");
        lng=getArguments().getString("lng");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.area_details_fragment,container,false);
        name= (EditText) v.findViewById(R.id.name);
        submit= (Button) v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().trim().equals("")){
                    pd.setMessage("Saving...");
                    pd.show();
                    JSONObject jo=new JSONObject();
                    try {
                        jo.put("method","addarea");
                        jo.put("name",name.getText().toString().trim());
                        jo.put("lat",lat);
                        jo.put("lng",lng);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new SendRequest(getResources().getString(R.string.url),jo,AddAreaFragment.this,getActivity()).executeJsonRequest();
                }
            }
        });
        return v;
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if (jsonObject.has("method")){
            try {
                if (jsonObject.getString("method").equals("addarea") && jsonObject.getBoolean("success")){
                    Toast.makeText(getActivity(), "Area added successfully", Toast.LENGTH_LONG).show();
                    getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                    getActivity().getFragmentManager().popBackStackImmediate();
                }else {
                    Toast.makeText(getActivity(), "couldn't save try again later", Toast.LENGTH_SHORT).show();
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
