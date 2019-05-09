package admin.pubbs.in.pubbsadminnew;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import admin.pubbs.in.pubbsadminnew.BottomSheet.BottomSheetAreaFragment;
/*created by Parita Dey*/

public class AddStationInMap extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private String areaName, areaId, areaLatLng;
    private String TAG = AddStationInMap.class.getSimpleName();
    ImageView backButton, search, upArrow;
    CoordinatorLayout selectArea;
    Button procced;
    SharedPreferences sharedPreferences;
    private static final float DEFAULT_ZOOM = 12f;
    private GoogleMap mMap;
    EditText inputSearch;
    Context mContext;
    View v;
    TextView selectStationTv, bottomsheetText;
    String station_name, stationid, stationLatitude, stationLongitude, finalResult, adminMobile;
    double station_latitude, station_longitude;
    String UserUrl = "http://pubbs.in/api/1.0/AdminStation.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    Gson gson;
    List<LatLng> polygon;
    LatLng cordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station_in_map);
        //getting the intent data like area_name, area_id, latlon from AddNewStation class
        Intent intent = getIntent();
        areaName = intent.getStringExtra("area_name");
        areaId = intent.getStringExtra("area_id");
        areaLatLng = intent.getStringExtra("latlon");
        Log.d(TAG, "Area Details:" + areaName + "--" + areaId + "--" + areaLatLng);
        gson = new Gson();
        //converting string to LatLng
        polygon = gson.fromJson(areaLatLng, new TypeToken<List<LatLng>>() {
        }.getType());
        Log.d(TAG, "Lat/Long:" + polygon);
        cordinate = polygon.get(0);
        Log.d(TAG, "First Cordinate:" + cordinate);
        //sharedpreference will store the admin mobile number who is using the app
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        adminMobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Admin Mobile" + adminMobile);
        selectArea = findViewById(R.id.selectArea);
        setUpToolbar();
        initView();
        //inflating the map inside the fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(AddStationInMap.this);
        //on clicking the up arrow it will show the bottomsheetfragment with details in it
        upArrow = findViewById(R.id.up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetAreaFragment().show(getSupportFragmentManager(), "dialog");

            }
        });

    }


    @Override
    public void onBackPressed() {
        //this will redirect back to the previous page AddNewStation clearing the stack history
        Intent intent = new Intent(AddStationInMap.this, AddNewStation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        drawAreaPolygon(polygon);
        init();
    }

    //drawing the Polygonal area in the map
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
        drawStation();
    }

    //setting the toolbar
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

    //alert dialog to set the name of the station
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

    //drawing the station on tapping in map
    public void drawStation() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //if the tapped point is inside the polygonal area then only station will be created
                // and that will be checked by Ray Casting algorith,
                boolean point= isPointInPolygon(latLng, polygon);
                Log.d(TAG, "Point value:"+point);
                if(point==true) {
                    selectStationDialog();
                    stationid = generateStationID(); //generate random number station id
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
                    stationLatitude = String.valueOf(station_latitude);
                    station_longitude = latLng.longitude;
                    stationLongitude = String.valueOf(station_longitude);

                    procced.setVisibility(View.VISIBLE);
                }else{
                    showStationIsOutside("Please create station inside the area.");
                }

            }
        });
    }

    //Ray Casting algorithm :One simple way of finding whether the point is inside or outside a simple polygon
    // is to test how many times a ray, starting from the point and going in any fixed direction, intersects the edges
    // of the polygon. If the point is on the outside of the polygon the ray will intersect its edge an even number of times.
    // If the point is on the inside of the polygon then it will intersect the edge an odd number of times. This method won't work
    // if the point is on the edge of the polygon.
    //Ray Casting algorithm : identifies point in polygon
    private boolean isPointInPolygon(LatLng tap, List<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }


    //if user search for a place then geocoder will find the place and locate it by a green pointer
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

    //generate the random id concatinate with 'station_'
    public String generateStationID() {
        String stationNumber = "station_";
        String station;
        int max = 999;
        int min = 1;
        int randomNum = (int) (Math.random() * (max - min)) + min;
        station = stationNumber + randomNum;
        Log.d(TAG, "Station Number: " + station);
        return station;

    }

    //if any error occurred or success msg will show via a dialog box
    private void showMessageDialog(String message) {
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
                Intent intent = new Intent(AddStationInMap.this, AddNewStation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }
    //if the tapped point is outside the polygonal area then show dialog showing to create the area inside the polygonal area
    private void showStationIsOutside(String message) {
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
                /*Intent intent = new Intent(AddStationInMap.this, AddNewStation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }


    //on clicking proceed button it will send all the data to the server
    public void addStationData(final String station_id, final String station_name, final String station_latitude, final String station_longitude,
                                final String adminmobile, final String area_name, final String area_id) {

        class AddNewStation extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                showMessageDialog(httpResponseMsg.toString());
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("station_id", params[0]);
                hashMap.put("station_name", params[1]);
                hashMap.put("station_latitude", params[2]);
                hashMap.put("station_longitude", params[3]);
                hashMap.put("adminmobile", params[4]);
                hashMap.put("area_name", params[5]);
                hashMap.put("area_id", params[6]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }
        AddNewStation addNewStation = new AddNewStation();
        addNewStation.execute(station_id, station_name, station_latitude, station_longitude, adminmobile, area_name, area_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                //this will redirect back to the previous page AddNewStation clearing the stack history
                Intent intent = new Intent(AddStationInMap.this, AddNewStation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.proceed_btn:
                addStationData(stationid, station_name, stationLatitude, stationLongitude, adminMobile, areaName, areaId);
                break;
            default:
                break;
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

}
