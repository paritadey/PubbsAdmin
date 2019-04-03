package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/*created by Parita Dey*/

public class ShowAllAreas extends AppCompatActivity implements AsyncResponse, OnMapReadyCallback {
    ImageView back;
    private TextView show_area_tv;
    ProgressBar circularProgressbar;
    private RecyclerView recyclerView;
    private AllAreaAdpater allAreaAdpater;
    private List<AreaList> areaLists = new ArrayList<>();
    ArrayList<String> area_cordinates = new ArrayList<String>();
    ArrayList<String> area_id_ = new ArrayList<>();
    private String TAG = ShowAllAreas.class.getSimpleName();
    Gson gson;
    ArrayList<LatLng> polygon;
    private GoogleMap mMap;
    String cord, area_id, areaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_areas);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addOperatorTv = findViewById(R.id.add_operator_tv);
        addOperatorTv.setTypeface(type1);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        recyclerView = findViewById(R.id.recycler_view);
        allAreaAdpater = new AllAreaAdpater(areaLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(allAreaAdpater);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AreaList lists = areaLists.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));*/
        back = findViewById(R.id.back_button);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        show_area_tv = findViewById(R.id.show_area_tv);
        show_area_tv.setTypeface(type1);
        mapFragment.getMapAsync(ShowAllAreas.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAllAreas.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    public void drawAreaPolygon(List<LatLng> coordinates, String areaID) {
        Log.d(TAG, "Drawing polygon");
       /* for(int i =0 ; i<coordinates.size(); i++){
            Log.d(TAG, "Polygon:"+coordinates.get(i)+"\t"+areaID);
        }*/
        if (coordinates.size() >= 6) {
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(coordinates);
            polygonOptions.strokeColor(getResources().getColor(R.color.red_500));
            polygonOptions.strokeWidth(5);
            polygonOptions.fillColor(getResources().getColor(R.color.orange_300));
            Polygon polygon = mMap.addPolygon(polygonOptions);
            LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712),
                    new LatLng(28.20453, 97.34466));
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(BOUNDS_INDIA, 12));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        //  circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getsuperadminparea");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, ShowAllAreas.this,
                getApplicationContext()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        // circularProgressbar.setVisibility(View.GONE);
        areaLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getsuperadminparea") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            cord = jo.getString("area_lat_lon");
                            area_id = jo.getString("area_id");
                            area_cordinates.add(cord);
                            area_id_.add(area_id);
                            AreaList list = new AreaList(jo.getString("area_name"),
                                    jo.getString("area_id"), jo.getString("area_lat_lon"));
                            areaLists.add(list);
                        }
                        showCordinates(area_cordinates, area_id_);
                    }
                } else {
                    showDialog("No area is present against this Sub-Admin");
                    Log.d(TAG, "No area is present against this Sub-Admin");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // allAreaAdpater.notifyDataSetChanged();
    }

    private void showCordinates(ArrayList<String> area_cordinates, ArrayList<String> area_id_) {
        gson = new Gson();
        Log.d(TAG, "Showing data in arraylist:");
        for (int i = 0; i < area_cordinates.size(); i++) {
            Log.d(TAG, "area coordinates with index:" + area_cordinates.get(i) + i);
            polygon = gson.fromJson(area_cordinates.get(i), new TypeToken<List<LatLng>>() {
            }.getType());
            areaID = area_id_.get(i);
            Log.d(TAG, "Lat/Long:" + polygon);
            Log.d(TAG, "Area id:" + areaID);
            drawAreaPolygon(polygon, areaID);
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
               /* if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }*/
                Intent intent = new Intent(ShowAllAreas.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowAllAreas.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
       /* LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712),
                new LatLng(28.20453, 97.34466));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(BOUNDS_INDIA, 12));*/

    }
}
