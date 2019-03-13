package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LORD on 9/17/2017.
 */

public class DashboardFragment extends Fragment implements AsyncResponse{

    String uphone, uadmin;
    SharedPreferences sharedPreferences;
    Spinner choose_area;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Dashboard");
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        uphone = sharedPreferences.getString("adminmobile", "null"); //uphone is the user_phone to store the mobile number of the user
        uadmin = sharedPreferences.getString("admin_type", "null"); //uadmin is the admin type of the user who is using the app at the moment
        choose_area = v.findViewById(R.id.choose_area);
        return v;
    }
   /* private void loadData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getallmaparea");
            jo.put("adminmobile", uphone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, DashBoardActivity.class(),
                getActivity()).executeJsonRequest();
    }*/

    @Override
    public void onResponse(JSONObject jsonObject) {

    }

    @Override
    public void onResponseError(VolleyError error) {

    }
}
