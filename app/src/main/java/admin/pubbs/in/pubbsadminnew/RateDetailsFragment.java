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
 * Created by LORD on 9/17/2017.
 */

public class RateDetailsFragment extends Fragment implements AsyncResponse{
    EditText duration,rate;
    Button save;
    ProgressDialog pd;
    boolean update=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait;");
        pd.setCancelable(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Add Rate");
        View v=inflater.inflate(R.layout.fragment_rate_details,container,false);
        duration= (EditText) v.findViewById(R.id.duration);
        rate= (EditText) v.findViewById(R.id.rate);
        save= (Button) v.findViewById(R.id.save);

        if (getArguments()!=null) {
            duration.setText(getArguments().getString("duration"));
            rate.setText(getArguments().getString("rate"));
            update=true;
            getActivity().setTitle("Update Rate");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duration.setError(null);
                rate.setError(null);
                if (duration.getText().toString().trim().isEmpty() || rate.getText().toString().trim().isEmpty()){
                    if (duration.getText().toString().trim().isEmpty()){
                        duration.setError("Enter hours");
                    }
                    if (rate.getText().toString().trim().isEmpty()){
                        rate.setError("Enter rate");
                    }
                }else {
                    if (!update) {
                        for (Rates rate : AppConfig.getRates()) {
                            if (rate.getDuration().equals(duration.getText().toString().trim())) {
                                duration.setError("Hour already exists.");
                                return;
                            }
                        }
                    }
                    pd.show();
                    JSONObject jsonObject=new JSONObject();
                    try {
                        if(update) {
                            jsonObject.put("method", "updaterate");
                            jsonObject.put("id",getArguments().getInt("id"));
                        }
                        else
                            jsonObject.put("method","addrate");
                        jsonObject.put("duration",duration.getText().toString().trim());
                        jsonObject.put("rate",rate.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new SendRequest(getResources().getString(R.string.url),
                            jsonObject,
                            RateDetailsFragment.this, getActivity()).executeJsonRequest();
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
                if (jsonObject.getString("method").equals("addrate") && jsonObject.getBoolean("success")){
                    getFragmentManager().popBackStackImmediate();
                    Toast.makeText(getActivity(), "Rate added successfully", Toast.LENGTH_SHORT).show();
                }

                if (jsonObject.getString("method").equals("updaterate") && jsonObject.getBoolean("success")){
                    getFragmentManager().popBackStackImmediate();
                    Toast.makeText(getActivity(), "Rate updated successfully", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        pd.dismiss();
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
    }
}
