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
import android.widget.ImageView;
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

/**
 * Created by LORD on 9/17/2017.
 */

public class DashboardFragment extends Fragment implements OnMapReadyCallback, AsyncResponse, View.OnClickListener {

    //xml based variables' declaration
    MapView mapView;
    GoogleMap gmap;
    ImageView support;
    //java file based variables' declaration
    SharedPreferences sharedPreferences;
    //global variables
    String adminPhone, adminType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Dashboard");
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //declare and initialize typeface
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //get the admin mobile number and admin type from sharedprefernce
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        adminPhone = sharedPreferences.getString("adminmobile", "null"); //adminPhone is the user_phone to store the mobile number of the user
        adminType = sharedPreferences.getString("admin_type", "null"); //adminType is the admin type of the user who is using the app at the moment
        support = v.findViewById(R.id.support);
        support.setOnClickListener(this);
        if(adminType.equals("Super Admin")){
            support.setVisibility(View.GONE);
        }
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                loadData();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
         loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
         loadData();
    }

     public void loadData() {
         JSONObject jo = new JSONObject();
         try {
             jo.put("method", "get_all_stations");
         } catch (JSONException e) {
             e.printStackTrace();
         }
         new SendRequest(getString(R.string.url), jo, DashboardFragment.this, getActivity()).executeJsonRequest();

     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.support:
                Intent intent = new Intent(getContext(), ContactSuperAdmin.class);
                intent.putExtra("adminPhone", adminPhone);
                intent.putExtra("adminType", adminType);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            try {
                if (jsonObject.getString("method").equals("get_all_stations")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            String lat = jo.getString("station_latitude");
                            String lon = jo.getString("station_longitude");
                            double latitude = Double.parseDouble(lat);
                            double longitude = Double.parseDouble(lon);
                            LatLng ll = new LatLng(latitude, longitude);
                            gmap.addMarker(new MarkerOptions().position(ll)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.station))
                                    .title("Station Name:" + jo.getString("station_name")));
                            builder.include(ll);
                        }
                        LatLngBounds bounds = builder.build();
                           int padding = 50; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        gmap.moveCamera(cu);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onResponseError(VolleyError error) {

    }
}