package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by LORD on 4/8/2018.
 */

public class TrackRideMap extends Fragment implements OnMapReadyCallback,AsyncResponse{
    MapView mapView;
    GoogleMap gmap;
    Handler handler = new Handler();
    final int delay = 5000; //milliseconds

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_area, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                trackRide.run();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        trackRide.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(trackRide);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if(jsonObject.has("method")){
            gmap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            try {
                if (jsonObject.getString("method").equals("trackrides")){
                    JSONArray ja=jsonObject.getJSONArray("data");
                    if (ja.length()>0) {
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

    }

    private Runnable trackRide=new Runnable() {
        @Override
        public void run() {
            JSONObject jo=new JSONObject();
            try {
                jo.put("method","trackrides");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getString(R.string.url),jo,TrackRideMap.this,getActivity()).executeJsonRequest();
            handler.postDelayed(trackRide, delay);
        }
    };
}
