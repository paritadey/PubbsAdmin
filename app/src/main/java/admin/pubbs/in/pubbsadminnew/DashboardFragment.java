package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import admin.pubbs.in.pubbsadminnew.List.StationModel;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

/**
 * Created by Parita Dey
 */

public class DashboardFragment extends Fragment implements OnMapReadyCallback, AsyncResponse, View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    //xml based variables' declaration
    MapView mapView;
    GoogleMap gmap;
    ImageView support;
    //java file based variables' declaration
    SharedPreferences sharedPreferences;
    //global variables
    String adminPhone, adminType, supportContactNo;
    private List<StationModel> stations = new ArrayList<>();
    private String TAG = DashboardFragment.class.getSimpleName();
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

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
        if (adminType.equals("Super Admin")) {
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
                loadData(adminPhone);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
         loadData(adminPhone);
    }

    @Override
    public void onPause() {
        super.onPause();
        loadData(adminPhone);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.support:
                //   @SuppressLint({"NewApi", "LocalSuppress"})
                Intent intent = new Intent(getActivity(), ContactSuperAdmin.class);
                intent.putExtra("adminPhone", adminPhone);
                intent.putExtra("adminType", adminType);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private void loadData(String adminPhone) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getareastations");
            jo.put("adminPhone", adminPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getString(R.string.url), jo, DashboardFragment.this, getActivity()).executeJsonRequest();
    }

    private Marker createMarker(LatLng latLng, String title, String snippet, int iconResID) {
        return gmap.addMarker(new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(vectorToBitmap(iconResID, getResources().getColor(R.color.blue_800))));
    }

    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        assert vectorDrawable != null;
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getareastations")) {
                    gmap.clear();
                    JSONObject areaObject = jsonObject.getJSONObject("area");
                    supportContactNo = areaObject.getString("emergency_contact");
                    List<LatLng> areaPoly = new Gson().fromJson(areaObject.getString("area_lat_lon"), new TypeToken<ArrayList<LatLng>>() {
                    }.getType());
                    JSONArray ja = areaObject.getJSONArray("stations");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jsonObject_ = ja.getJSONObject(i);
                        StationModel stationModel = new StationModel();
                        stationModel.setId(jsonObject_.getString("id"));
                        stationModel.setName(jsonObject_.getString("name"));
                        stationModel.setAvailable(jsonObject_.getInt("available"));
                        stationModel.setLocation(new LatLng(jsonObject_.getDouble("lat"), jsonObject_.getDouble("lon")));
                        stations.add(stationModel);
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng ll : areaPoly) {
                        builder.include(ll);
                    }
                    LatLngBounds bounds = builder.build();
                    for (StationModel sm : stations) {
                        Marker m = createMarker(sm.getLocation(), sm.getName(), "Available: " + sm.getAvailable(), R.drawable.station);
                    }
                    int padding = 30; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    gmap.moveCamera(cu);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        Log.d(TAG, "Error:" + error.toString());
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
