package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
/*created by Parita Dey*/

public class SuperAdminShowSpecificArea extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback{
    private String areaName, areaId, areaLatLng;
    private String TAG = SuperAdminShowSpecificArea.class.getSimpleName();
    private GoogleMap mMap;
    Gson gson;
    List<LatLng> polygon;
    TextView show_area_tv;
    ImageView backButton;
    LatLng cordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_show_specific_area);
        initView();
    }

    public void drawAreaPolygon(List<LatLng> coordinates) {
        Log.d(TAG, "Drawing polygon");
        if (coordinates.size() >= 6) {
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(coordinates);
            polygonOptions.strokeColor(getResources().getColor(R.color.blue_300));
            polygonOptions.strokeWidth(5);
            polygonOptions.fillColor(getResources().getColor(R.color.blue_100));
            Polygon polygon = mMap.addPolygon(polygonOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cordinate, 12f));

        }
    }

    private void initView() {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Intent intent = getIntent();
        areaName = intent.getStringExtra("area_name");
        areaId = intent.getStringExtra("area_id");
        areaLatLng = intent.getStringExtra("area_latlon");
        Log.d(TAG, "Area Details:" + areaName + "--" + areaId + "--" + areaLatLng);
        gson = new Gson();
        polygon = gson.fromJson(areaLatLng, new TypeToken<List<LatLng>>() {
        }.getType());
        Log.d(TAG, "Lat/Long:" + polygon);
        int size = polygon.size();
        Log.d(TAG, "Size of the Polygon:"+size);
        cordinate = polygon.get(0);
        Log.d(TAG, "First Cordinate:" + cordinate);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        show_area_tv = findViewById(R.id.show_area_tv);
        show_area_tv.setTypeface(type);
        show_area_tv.setText(areaName);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(SuperAdminShowSpecificArea.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        drawAreaPolygon(polygon);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SuperAdminShowSpecificArea.this, ShowAllAreas.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(SuperAdminShowSpecificArea.this, ShowAllAreas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
