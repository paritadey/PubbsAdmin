package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*created by Parita Dey*/
public class AddNewArea extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    ImageView backButton;
    CoordinatorLayout selectArea;
    ImageView upArrow;
    ImageView mapGps;
    ImageView search;
    private static final String TAG = AddNewArea.class.getSimpleName();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    EditText inputSearch;
    Context mContext;
    View v;
    TextView selectAreaTv, bottomsheetText;
    boolean polygonCreation = false;
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    public ArrayList<LatLng> stationList = new ArrayList<LatLng>();
    Button procced;
    String stationName;
    String area_name, station_name;
    String areaNumber;
    String area_Name;
    SharedPreferences sharedPreferences;
    String adminMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_area);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        //getting the mobile number of the admin/superadmin using the app from sharedpreference
        adminMobile = sharedPreferences.getString("adminmobile",null);
        Log.d(TAG, "Admin Mobile:"+adminMobile);
        selectArea = findViewById(R.id.selectArea);
        getLocationPermission();
        setUpToolbar();
        initView();

        upArrow = findViewById(R.id.up_arrow);
        //onclicking of the upArrow button in the xml a bottom sheet fragment will show in the screen
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetAreaFragment().show(getSupportFragmentManager(), "dialog");

            }
        });

    }

    private void initView() {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        backButton = findViewById(R.id.back_button);
        mapGps = findViewById(R.id.map_gps);
        backButton.setOnClickListener(this);
        mapGps.setOnClickListener(this);
        selectAreaTv = findViewById(R.id.select_area_tv);
        selectAreaTv.setTypeface(type);
        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type);
        procced = findViewById(R.id.proceed_btn);
        procced.setTypeface(type2);
        procced.setOnClickListener(this);
    }

    @SuppressLint("ResourceType")
    private void init() {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        inputSearch = findViewById(R.id.input_search);
        inputSearch.setTypeface(type);
        search = findViewById(R.id.ic_magnify);
        search.setOnClickListener(new View.OnClickListener() {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

            @Override
            public void onClick(View v) {
                //geoLocate();
                hideSoftKeyboard(mContext, v);
                Log.d(TAG, "geoLocate: geolocating");
                String searchString = inputSearch.getText().toString();
                List<Address> addressList = null;
                if (searchString != null || !searchString.equals("")) {
                    Geocoder geocoder = new Geocoder(AddNewArea.this);
                    try {
                        addressList = geocoder.getFromLocationName(searchString, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = null;
                    if (addressList != null) {
                        address = addressList.get(0);
                    }
                    LatLng latLng = new LatLng(address != null ? address.getLatitude() : 0, address != null ? address.getLongitude() : 0);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(searchString).icon(icon));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });
    }
//setting the toolbar of the xml
    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(AddNewArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.map_gps:
                getDeviceLocation();
                break;
            case R.id.proceed_btn:
                showArrayList(markerList, areaNumber, adminMobile);
                Log.d(TAG, "Area name:"+area_Name);
                Intent intent_rate = new Intent(AddNewArea.this, ManageSystem.class);
                intent_rate.putExtra("markerList",markerList);
                intent_rate.putExtra("areaNumber", areaNumber);
                intent_rate.putExtra("area_Name", area_Name);
                intent_rate.putExtra("adminMobile", adminMobile);
                startActivity(intent_rate);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNewArea.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { //placeing a dynamic marker
                @Override
                public void onMapClick(LatLng latLng) {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.circumference);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                    markerOptions.icon(icon);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.addMarker(markerOptions);
                    Log.d(TAG, "Area added");
                    markerList.add(latLng);
                    drawPolygon(markerList);
                    //showArrayList(markerList, areaNumber, adminMobile);
                }
            });

            init();
        }

    }

    public void showArrayList(ArrayList<LatLng> list, String areaNumber, String adminMobile) {
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, "markers: " + list.get(i));
        }
        Log.d(TAG, "Selected area details:" + areaNumber);
        Log.d(TAG, "Admin mobile:" + adminMobile);
        showDistance(markerList);
    }

    //distance is in mile
    public void showDistance(ArrayList<LatLng> list){
        for(int i=0; i<list.size(); i++){
            for (int j=i+1; j<list.size(); j++){
                LatLng A = list.get(i);
                LatLng B = list.get(j);
                double toLat=A.latitude;
                double toLng=A.longitude;
                double fromLat=B.latitude;
                double fromLng=B.longitude;;
                /*int R = 6371; // km
                double x = (toLng - fromLng) * Math.cos((toLat + fromLat) / 2);
                double y = (toLat - fromLat);
                double distance = Math.sqrt(x * x + y * y) * R;*/
                double theta =fromLng-toLng; //lon1 - lon2;
                double dist = Math.sin(deg2rad(fromLat))
                        * Math.sin(deg2rad(toLat))
                        + Math.cos(deg2rad(fromLat))
                        * Math.cos(deg2rad(toLat))
                        * Math.cos(deg2rad(theta));
                dist = Math.acos(dist);
                dist = rad2deg(dist);
                dist = dist * 60 * 1.1515;

                Log.d(TAG, "Distance of A and B:"+dist+"---------cord_one:"+fromLat+"/"+fromLng+"------------cord_two:"+toLat+"/"+toLng);
                Log.d(TAG, "\t");
            }
        }
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void drawPolygon(ArrayList<LatLng> myLatLng) {
        Log.d(TAG, "Drawing polygon");
        if (myLatLng.size() >= 12){//6) {
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(myLatLng);
            polygonOptions.strokeColor(getResources().getColor(R.color.blue_300));
            polygonOptions.strokeWidth(5);
            polygonOptions.fillColor(getResources().getColor(R.color.blue_100));
            Polygon polygon = mMap.addPolygon(polygonOptions);

            selectAreaDialog();
            Log.d(TAG, "Polygon created");
            areaNumber = generateArea();
            Log.d(TAG, "Area Number: " + areaNumber);
            procced.setVisibility(View.VISIBLE);
            // polygonCreation = true;
        }
        //  drawStation(polygonCreation);
    }

    private String selectAreaDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_add_area_layout, null);

        final TextView areaHeader = (TextView) dialogView.findViewById(R.id.area_header);
        areaHeader.setTypeface(type1);
        final EditText areaName = (EditText) dialogView.findViewById(R.id.area_name);
        areaName.setTypeface(type1);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (areaName.getText().toString().isEmpty()) {
                    areaName.startAnimation(animShake);
                } else {
                    area_Name = areaName.getText().toString().trim();
                    Log.d(TAG, "Area Name : " + area_Name);
                    dialogBuilder.dismiss();
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
        return area_Name;
    }

    public void selectStationDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_add_station_layout, null);

        final TextView stationHeader = (TextView) dialogView.findViewById(R.id.station_header);
        stationHeader.setTypeface(type1);
        final EditText stationName = (EditText) dialogView.findViewById(R.id.station_name);
        stationName.setTypeface(type1);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                if (stationName.getText().toString().isEmpty()) {
                    stationName.startAnimation(animShake);
                } else {
                    station_name = stationName.getText().toString().trim();
                    Log.d(TAG, "Station Name : " + station_name);
                    dialogBuilder.dismiss();
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    public void drawStation(boolean drawStation) {
        if (drawStation == true) {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    selectStationDialog();
                    String stationName = generateStation();
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.station);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                    markerOptions.icon(icon);
                    markerOptions.snippet(stationName);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.addMarker(markerOptions);
                    Log.d(TAG, "Station added");
                    stationList.add(latLng);
                    //showArrayList(stationList);
                    //procced.setVisibility(View.VISIBLE);

                }
            });
        }
    }

    public String generateStation() {
        String stationNumber = "station_";
        String station;
        int max = 999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        station = stationNumber + randomNum;
        Log.d(TAG, "Station Number: " + station);
        return station;

    }

    public String generateArea() {
        String areaNumber = "area_";
        String area;
        int max = 999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        area = areaNumber + randomNum;
        Log.d(TAG, "Area Number: " + area);
        return area;

    }

    public String showStationLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View inflatedLayout = inflater.inflate(R.layout.custom_marker_title, null, false);
        EditText markerTitle = (EditText) inflatedLayout.findViewById(R.id.marker_title);
        Button addStation = (Button) inflatedLayout.findViewById(R.id.add_station);
        RelativeLayout stationLayout = (RelativeLayout) inflatedLayout.findViewById(R.id.stationLayout);
        addStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationName = markerTitle.getText().toString();
                stationLayout.setVisibility(View.GONE);
            }
        });
        return stationName;
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(AddNewArea.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(AddNewArea.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = inputSearch.getText().toString();

        Geocoder geocoder = new Geocoder(AddNewArea.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }

    }

    private void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
