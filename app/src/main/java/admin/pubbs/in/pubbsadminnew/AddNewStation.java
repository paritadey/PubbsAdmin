package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddNewStation extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
         GoogleApiClient.OnConnectionFailedListener, LocationListener {

    ImageView backButton;
    public static final int REQUEST_CODE_FOR_PERMISSIONS = 1;
    GoogleApiClient mGoogleApiClient;
    LatLng mLatLng;
    GoogleMap mGoogleMap;
    Marker mCurrLocationMarker;
    private MapView mapView;
    LocationManager locationManager;
    boolean statusOfGPS;
    Location mLastLocation;
    private Dialog mDialogGPS;
    LocationRequest mLocationRequest;
    CoordinatorLayout selectArea;
    ImageView upArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_station);
        selectArea = findViewById(R.id.selectArea);
        setUpToolbar();
        initView();
        mapView = (MapView) findViewById(R.id.select_area_map);
        mapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);
        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(getApplicationContext(), "play services not available", Toast.LENGTH_SHORT).show();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetFragment().show(getSupportFragmentManager(), "dialog");

            }
        });

    }

    private void initView() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                Intent intent = new Intent(AddNewStation.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNewStation.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000); //5 seconds
        mLocationRequest.setFastestInterval(2000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
        // Zoom in the Google Map
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED &&
                    this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_FOR_PERMISSIONS);
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        //show dialog when click on location top-right side located on map.
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!statusOfGPS) {
                    Log.d("New Station", "no gps");
                } else {
                    getCurrentLocation(mLastLocation);
                }
                return false;
            }
        });
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_FOR_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED &&
                            this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    if (!(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                            && (!(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)))) {
                        Snackbar snackbar = Snackbar.make(selectArea, "Never asked"
                               , Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("Allow", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                    }
                }
                break;
        }
    }

    private void getCurrentLocation(Location location) {

        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//getting last location
            }
            if (mLastLocation != null) {
                if (mGoogleMap != null) {
                    Log.d("activity", "LOC by Network");
                    mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(mLatLng);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (mDialogGPS != null) {
            if (mDialogGPS.isShowing()) {
                mDialogGPS.dismiss();
            }
        }
        if (!statusOfGPS) {
            Log.d("FRAGMENT", "no gps");
        } else {
            getCurrentLocation(mLastLocation);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient = null;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
