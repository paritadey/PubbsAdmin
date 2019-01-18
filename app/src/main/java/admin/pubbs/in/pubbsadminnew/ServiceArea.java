package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
/*created by Parita Dey*/

public class ServiceArea extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    String adminmobile;
    private String TAG = ServiceArea.class.getSimpleName();
    String area_id, area_name, date_time;
    ImageView back;
    TextView serviceArea, all_set_tv, launchTv, areaname, areaid;
    Button start_service, stop_service;
    ProgressBar circularProgressbar;
    SharedPreferences.Editor editor;
    String service;
    public static final int CONNECTION_TIMEOUT = 20000;
    public static final int READ_TIMEOUT = 20000;
    String startStatus, date;
    String finalResult;
    String url = "http://pubbs.in/api/1.0/launchArea.php";
    String UserUrl = "http://pubbs.in/api/1.0/setservice.php";
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_area);
        initView();
    }

    public void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        area_id = intent.getStringExtra("area_id");
        area_name = intent.getStringExtra("area_name");
        Log.d(TAG, area_id + "\n" + area_name);

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        adminmobile = sharedPreferences.getString("adminmobile", null);
        Log.d(TAG, "Area details: " + adminmobile + "-" + area_name + "-" + area_id);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
        date_time = sdf.format(date);
        new AsyncFetch(area_id).execute();
        circularProgressbar = findViewById(R.id.circularProgressbar);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        serviceArea = findViewById(R.id.service_area);
        serviceArea.setTypeface(type1);
        all_set_tv = findViewById(R.id.all_set_tv);
        all_set_tv.setTypeface(type2);
        launchTv = findViewById(R.id.launchTv);
        launchTv.setTypeface(type2);
        areaname = findViewById(R.id.area_name);
        areaname.setTypeface(type1);
        areaname.setText(area_name);
        areaid = findViewById(R.id.area_id);
        areaid.setTypeface(type1);
        areaid.setText(area_id);
        start_service = findViewById(R.id.start_service);
        start_service.setTypeface(type3);
        start_service.setOnClickListener(this);
        stop_service = findViewById(R.id.stop_service);
        stop_service.setTypeface(type3);
        stop_service.setOnClickListener(this);
    }

    public void setStatus(boolean stat) {
        Log.d(TAG, "Status:" + startStatus+ "--"+stat);
        if (stat == true) {
            start_service.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Area is already launched", Toast.LENGTH_SHORT).show();
        } else {
            stop_service.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Area has not launched yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent intent = new Intent(ServiceArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.start_service:
                sendServiceArea(adminmobile, area_id, area_name, date_time, "start");
                break;
            case R.id.stop_service:
                updateServiceFunction(area_id, date_time, "stop");
                break;
            default:
                break;
        }
    }

    public void updateServiceFunction(final String area_id, final String date_time,
                                      final String status) {

        class updateServiceFunctionClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Log.d(TAG, httpResponseMsg.toString());
                showDialog("Service is taken off !");
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("area_id", params[0]);
                hashMap.put("date_time", params[1]);
                hashMap.put("status", params[2]);
                finalResult = httpParse.postRequest(hashMap, UserUrl);
                return finalResult;
            }
        }
        updateServiceFunctionClass updateServiceFunctionClass = new updateServiceFunctionClass();
        updateServiceFunctionClass.execute(area_id, date_time, status);
    }

    public class AsyncFetch extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://pubbs.in/api/1.0/GetServiceAreaDetails.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("area_id", area_id);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("no rows")) {
                Toast.makeText(ServiceArea.this, "No Results found", Toast.LENGTH_LONG).show();
                setStatus(false);
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        startStatus = obj.getString("status");
                        date = obj.getString("date_time");
                    }
                    Log.d(TAG, "Launch: " + startStatus + "-----" + date);
                    if (startStatus.equals("start")) {
                        setStatus(true);
                    } else {
                        setStatus(false);
                    }
                } catch (JSONException e) {
                    Log.d(e.toString(), "error");
                    setStatus(false);
                }
            }

        }
    }

    public void sendServiceArea(String adminmobile, String area_id, String area_name, String date_time, String status) {
        class sendServiceAreaFunction extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Log.d(TAG, httpResponseMsg.toString());
                if(httpResponseMsg.toString().equals("Area service is re-launched")) {
                   showDialog("Area service is re-launched");
                }else if (httpResponseMsg.toString().equals("Area Service is Successfully Launched !!!")){
                    showDialog("Area Service is Successfully Launched !!!");
                } else{
                    showDialog("Something went wrong");
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("adminmobile", params[0]);
                hashMap.put("area_name", params[1]);
                hashMap.put("area_id", params[2]);
                hashMap.put("date_time", params[3]);
                hashMap.put("status", params[4]);
                finalResult = httpParse.postRequest(hashMap, url);
                return finalResult;
            }
        }
        sendServiceAreaFunction sendServiceAreaFunction = new sendServiceAreaFunction();
        sendServiceAreaFunction.execute(adminmobile, area_name, area_id, date_time, status);

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
                Intent intent = new Intent(ServiceArea.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
