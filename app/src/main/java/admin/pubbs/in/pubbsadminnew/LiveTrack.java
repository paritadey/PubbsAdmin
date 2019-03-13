package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveTrack extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, AsyncResponse {
    TextView live_track_tv;
    GoogleMap gmap;
    Handler handler = new Handler();
    final int delay = 5000; //milliseconds
    ImageView support, back;
    String uphone, uadmin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_track);
        initView();
    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        live_track_tv = findViewById(R.id.live_track_tv);
        live_track_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        uphone = sharedPreferences.getString("adminmobile", "null"); //uphone is the user_phone to store the mobile number of the user
        uadmin = sharedPreferences.getString("admin_type", "null"); //uadmin is the admin type of the user who is using the app at the moment
        support = findViewById(R.id.support);
        support.setOnClickListener(this);
        if (uadmin.equals("Super Admin")) {
            support.setVisibility(View.GONE);
        }
        //inflating the map inside the fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(LiveTrack.this);


    }

    @Override
    public void onBackPressed() {
        Intent intent_back = new Intent(LiveTrack.this, DashBoardActivity.class);
        intent_back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent back = new Intent(LiveTrack.this, DashBoardActivity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                break;
            case R.id.support:
                Intent intent = new Intent(getApplicationContext(), ContactSuperAdmin.class);
                intent.putExtra("uphone", uphone);
                intent.putExtra("uadmin", uadmin);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
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
        trackRide.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(trackRide);
    }

    private Runnable trackRide = new Runnable() {
        @Override
        public void run() {
            JSONObject jo = new JSONObject();
            try {
                jo.put("method", "trackrides");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendRequest(getString(R.string.url), jo, LiveTrack.this, getApplicationContext()).executeJsonRequest();
            handler.postDelayed(trackRide, delay);
        }
    };

    @Override
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
                            gmap.addMarker(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle_rider)));//.title(jo.getString("booking_id")));
                            builder.include(ll);
                        }
                        LatLngBounds bounds = builder.build();
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        int padding = (int) (width * 0.40); // offset from edges of the map 40% of screen*/
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
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
