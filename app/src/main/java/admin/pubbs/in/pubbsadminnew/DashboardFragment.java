package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    MapView mapView;
    GoogleMap gmap;
    Handler handler = new Handler();
    final int delay = 5000; //milliseconds
    TextView cycleId, cycleUsername, cycleUserPhone, call, locate, accident;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Dashboard");
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        /*cycleId = v.findViewById(R.id.cycle_id);
        cycleId.setTypeface(type1);
        cycleUsername = v.findViewById(R.id.cycle_user_name);
        cycleUsername.setTypeface(type1);
        cycleUserPhone = v.findViewById(R.id.cycle_user_phone);
        cycleUserPhone.setTypeface(type1);
        call = v.findViewById(R.id.call);
        call.setTypeface(type1);
        call.setOnClickListener(this);
        locate = v.findViewById(R.id.locate);
        locate.setTypeface(type1);
        locate.setOnClickListener(this);
        accident = v.findViewById(R.id.accident);
        accident.setTypeface(type2);*/
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

   /* @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            gmap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            try {
                if (jsonObject.getString("method").equals("trackrides")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            LatLng ll = new LatLng(jo.getDouble("lat"), jo.getDouble("lng"));
                            gmap.addMarker(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_virtual_stn)).title(jo.getString("booking_id")));
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

    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //trackRide.run();
                loadData();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
      //  trackRide.run();
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
      //  handler.removeCallbacks(trackRide);
        loadData();
    }

    public void loadData(){
        JSONObject jo = new JSONObject();
        try {
            //  jo.put("method", "trackrides");
            jo.put("method", "get_all_stations");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getString(R.string.url), jo, DashboardFragment.this, getActivity()).executeJsonRequest();

    }
  /*  private Runnable trackRide = new Runnable() {
        @Override
        public void run() {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "trackrides");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getString(R.string.url), jo, DashboardFragment.this, getActivity()).executeJsonRequest();
            handler.postDelayed(trackRide, delay);
        }
    };*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.call:
                break;
            case R.id.locate:
                break;*/
            default:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
           // gmap.clear();
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
                            double longitude =Double.parseDouble(lon);
                            LatLng ll = new LatLng(latitude,longitude);
                            gmap.addMarker(new MarkerOptions().position(ll)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.station))
                                    .title("Station Name:"+jo.getString("station_name")));
                            builder.include(ll);
                        }
                        LatLngBounds bounds = builder.build();
                     //   int padding = 50; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 15);
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
