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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddStationInMap extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, AsyncResponse {
    private String areaName, areaId, areaLatLng;
    private String TAG = AddStationInMap.class.getSimpleName();
    ImageView backButton;
    CoordinatorLayout selectArea;
    ImageView upArrow;
    ImageView mapGps;
    Button procced;
    String stationName;
    SharedPreferences sharedPreferences;
    String adminMobile;
    ImageView search;
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
    TextView selectStationTv, bottomsheetText;
    String station_name;
    String[] mlocationStringArray;
    List<String> matchList = new ArrayList<String>();
    ArrayList<LatLng> coordinates = new ArrayList<LatLng>();
    double lat_one, lng_one, lat_two, lng_two, lat_three, lng_three, lat_four, lng_four, lat_five, lng_five, lat_six, lng_six;
    String l1, l2, l3, l4, l5, l6;
    String stationid;
    double station_latitude, station_longitude;
    String stationLatitude, stationLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station_in_map);
        Intent intent = getIntent();
        areaName = intent.getStringExtra("area_name");
        areaId = intent.getStringExtra("area_id");
        areaLatLng = intent.getStringExtra("latlon");
        Log.d(TAG, "Area Details:" + areaName + "--" + areaId + "--" + areaLatLng);
        GetPolygonPoints(areaLatLng);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminMobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG,"Admin Mobile"+ adminMobile);
        selectArea = findViewById(R.id.selectArea);
        getLocationPermission();
        setUpToolbar();
        initView();

        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetAreaFragment().show(getSupportFragmentManager(), "dialog");

            }
        });

    }

    public void GetPolygonPoints(String areaLatLng) {
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        Matcher regexMatcher = regex.matcher(areaLatLng);

        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group(1));
        }
        for (int i = 0; i < matchList.size(); i++) {
            Log.d(TAG, "markers: " + matchList.get(i));
        }
        mlocationStringArray = new String[matchList.size()];
        mlocationStringArray = matchList.toArray(mlocationStringArray);

        for (int i = 0; i < mlocationStringArray.length; i++) {
            Log.d("string is", (String) mlocationStringArray[i]);
        }
        l1 = mlocationStringArray[0];
        String l11[] = l1.split(",");
        String l11_1 = l11[0];
        String l22_2 = l11[1];
        lat_one = Double.parseDouble(l11_1);
        lng_one = Double.parseDouble(l22_2);

        l2 = mlocationStringArray[1];
        String ll2[] = l2.split(",");
        String ll2_1 = ll2[0];
        String ll2_2 = ll2[1];
        lat_two = Double.parseDouble(ll2_1);
        lng_two = Double.parseDouble(ll2_2);

        l3 = mlocationStringArray[2];
        String ll3[] = l3.split(",");
        String ll3_1 = ll3[0];
        String ll3_2 = ll3[1];
        lat_three = Double.parseDouble(ll3_1);
        lng_three = Double.parseDouble(ll3_2);

        l4 = mlocationStringArray[3];
        String ll4[] = l4.split(",");
        String ll4_1 = ll4[0];
        String ll4_2 = ll4[1];
        lat_four = Double.parseDouble(ll4_1);
        lng_four = Double.parseDouble(ll4_2);

        l5 = mlocationStringArray[4];
        String ll5[] = l5.split(",");
        String ll5_1 = ll5[0];
        String ll5_2 = ll5[1];
        lat_five = Double.parseDouble(ll5_1);
        lng_five = Double.parseDouble(ll5_2);

        l6 = mlocationStringArray[5];
        String ll6[] = l6.split(",");
        String ll6_1 = ll6[0];
        String ll6_2 = ll6[1];
        lat_six = Double.parseDouble(ll6_1);
        lng_six = Double.parseDouble(ll6_2);

        Log.d(TAG, "PARITA:" + l1 + "\n" + l2 + "\n" + l3 + "\n" + l4 + "\n" + l5 + "\n" + l6);
        Log.d(TAG, "DEY:" + lat_one + "/" + lng_one + "\n" + lat_two + "/" + lng_two + "\n" + lat_three + "/" + lng_three + "\n" + lat_four + "/" + lng_four + "\n"
                + lat_five + "/" + lng_five + "\n" + lat_six + "/" + lng_six);
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
                    Geocoder geocoder = new Geocoder(AddStationInMap.this);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddStationInMap.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.circumference);
        MarkerOptions markerOption_one = new MarkerOptions()
                .position(new LatLng(lat_one, lng_one))
                .title(l1).icon(icon);
        mMap.addMarker(markerOption_one);
        LatLng latLng_one = new LatLng(lat_one, lng_one);
        coordinates.add(latLng_one);

        MarkerOptions markerOption_two = new MarkerOptions()
                .position(new LatLng(lat_two, lng_two))
                .title(l2).icon(icon);
        mMap.addMarker(markerOption_two);
        LatLng latLng_two = new LatLng(lat_two, lng_two);
        coordinates.add(latLng_two);

        MarkerOptions markerOption_three = new MarkerOptions()
                .position(new LatLng(lat_three, lng_three))
                .title(l3).icon(icon);
        mMap.addMarker(markerOption_three);
        LatLng latLng_three = new LatLng(lat_three, lng_three);
        coordinates.add(latLng_three);

        MarkerOptions markerOption_four = new MarkerOptions()
                .position(new LatLng(lat_four, lng_four))
                .title(l4).icon(icon);
        mMap.addMarker(markerOption_four);
        LatLng latLng_four = new LatLng(lat_four, lng_four);
        coordinates.add(latLng_four);

        MarkerOptions markerOption_five = new MarkerOptions()
                .position(new LatLng(lat_five, lng_five))
                .title(l5).icon(icon);
        mMap.addMarker(markerOption_five);
        LatLng latLng_five = new LatLng(lat_five, lng_five);
        coordinates.add(latLng_five);

        MarkerOptions markerOption_six = new MarkerOptions()
                .position(new LatLng(lat_six, lng_six))
                .title(l6).icon(icon);
        mMap.addMarker(markerOption_six);
        LatLng latLng_six = new LatLng(lat_six, lng_six);
        coordinates.add(latLng_six);

        for (int i = 0; i < coordinates.size(); i++) {
            Log.d(TAG, "Coordinates: " + coordinates.get(i));
        }
        drawAreaPolygon(coordinates);

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }

    }

    public void drawAreaPolygon(ArrayList<LatLng> coordinates) {
        Log.d(TAG, "Drawing polygon");
        if (coordinates.size() >= 6) {
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(coordinates);
            polygonOptions.strokeColor(getResources().getColor(R.color.blue_300));
            polygonOptions.strokeWidth(5);
            polygonOptions.fillColor(getResources().getColor(R.color.blue_100));
            Polygon polygon = mMap.addPolygon(polygonOptions);
        }
        drawStation();
    }

    public void drawStation() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectStationDialog();
                stationid = generateStation();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.station);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                markerOptions.icon(icon);
                markerOptions.snippet(stationid);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.addMarker(markerOptions);
                Log.d(TAG, "Station added");
                station_latitude = latLng.latitude;
                //stationLatitude = String.valueOf(station_latitude);
                station_longitude = latLng.longitude;
                //stationLongitude = String.valueOf(station_longitude);
                procced.setVisibility(View.VISIBLE);

            }
        });
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
                            Toast.makeText(AddStationInMap.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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

        mapFragment.getMapAsync(AddStationInMap.this);
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

        Geocoder geocoder = new Geocoder(AddStationInMap.this);
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

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void initView() {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        selectStationTv = findViewById(R.id.select_station_tv);
        selectStationTv.setTypeface(type);
        bottomsheetText = findViewById(R.id.bottomsheet_text);
        bottomsheetText.setTypeface(type);
        procced = findViewById(R.id.proceed_btn);
        procced.setTypeface(type2);
        procced.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(AddStationInMap.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.proceed_btn:
                sendData(stationid, station_name, station_latitude, station_longitude,adminMobile, areaName, areaId);
                break;
            default:
                break;
        }
    }

    private void sendData(String station_id, String station_name, double station_latitude, double station_longitude,
                          String adminmobile, String area_name, String area_id) {
        Log.d(TAG, "Station Details:"+station_id+"-"+station_name+"-"+station_latitude+
                "-"+station_longitude+"-"+adminmobile+"-"+area_name+"-"+area_id);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "station");
            jo.put("station_id", station_id);
            jo.put("station_name", station_name);
            jo.put("station_latitude", station_latitude);
            jo.put("station_longitude", station_longitude);
            jo.put("adminmobile", adminmobile);
            jo.put("area_name", area_name);
            jo.put("area_id", area_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, AddStationInMap.this, getApplicationContext()).executeJsonRequest();

    }
//php side is not configured yet

    public void showStationAddedDialog() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.station_added_dialog, null);
        final TextView areaAdd = (TextView) dialogView.findViewById(R.id.area_add_tv);
        final Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        areaAdd.setTypeface(type1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(AddStationInMap.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("station") && jsonObject.getBoolean("success")) {
                    showStationAddedDialog();
                } else {
                    Toast.makeText(getApplicationContext(), "couldn't save try again later", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Error !");
    }

    private void showDialog(String message) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        final TextView serverProblem = (TextView) dialogView.findViewById(R.id.server_problem);
        final TextView extraLine = (TextView) dialogView.findViewById(R.id.extra_line);
        extraLine.setTypeface(type1);
        serverProblem.setTypeface(type1);
        serverProblem.setText(message);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                Intent intent = new Intent(AddStationInMap.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
