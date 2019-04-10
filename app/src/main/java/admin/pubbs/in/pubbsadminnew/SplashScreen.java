package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*created by Parita Dey*/
public class SplashScreen extends AppCompatActivity implements AsyncResponse {
    //xml based variables' declaration
    TextView appName;
    //java file based variables' declaration
    SharedPreferences sharedpreferences;
    Context context;
    boolean internet;
    private String TAG = SplashScreen.class.getSimpleName();
    // private keyword is the access modifier
    //private variables are only accessible inside the class they are declared or inside inner classes.
    private String area_id, admin_mobile, admin_type;
    private int manager, service, driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //declare and initialize typeface
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        appName = findViewById(R.id.app_name);
        appName.setTypeface(type);
        appName.setLetterSpacing(0.1f);
        //get the admin mobile number and admin type from sharedprefernce
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        internet = isConnectingToInternet(context);
        if (internet == true && sharedpreferences.contains("login")) { //if sharedpreference contains the
            // word "login" then it will go to DashboardActivity for Dashboard in app
            admin_mobile = sharedpreferences.getString("adminmobile", "null");
            admin_type = sharedpreferences.getString("admin_type", "null");
            Log.d(TAG, "Admin mobile and type:" + admin_mobile + "-" + admin_type);
            loadData(admin_mobile);
        } else {
            startActivity(new Intent(SplashScreen.this, SignInUp.class));
            internetErrorDialog("Connection Problem !", "Please connect to the internet.");
        }

    }

    //if the app user is an employee then show the dashboard's menuitem accordingly,
    private void showDashboardNavigation(String area_id, int manager, int service, int driver) {
        Log.d(TAG, "Authority:" + area_id + "\t" + manager + "\t" +  "\t" + service + "\t" + driver);
        if (admin_type.equals("Employee") && manager > 0 ||  service > 0 || driver > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashScreen.this, DashBoardActivity.class));
                }
            }, 4000);
        } else if (admin_type.equals("Super Admin") || admin_type.equals("Sub Admin")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashScreen.this, DashBoardActivity.class));
                }
            }, 4000);
        }
    }

    //load data from server if the user is an employee. It is used to fetch employee rank from the server
    public void loadData(String adminmobile) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getEmployeeRank");
            jo.put("adminmobile", adminmobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, SplashScreen.this,
                getApplicationContext()).executeJsonRequest();

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getEmployeeRank") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            area_id = jo.getString("area_id");
                            manager = Integer.parseInt(jo.getString("manager"));
                            service = Integer.parseInt(jo.getString("service"));
                            driver = Integer.parseInt(jo.getString("driver"));
                        }
                        Log.d(TAG, "Authority List of Employee:" + area_id + "\t" + manager + "\t" + "\t" + service + "\t" + driver);
                        showDashboardNavigation(area_id, manager, service, driver);
                    }
                } else {
                    Log.d(TAG, "Not Employee move further");
                    showDashboardNavigation(area_id, manager, service, driver);
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

    //if error occured due to no internet then show this dialogbox
    private void internetErrorDialog(String title, String message) {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        final TextView serverProblem = (TextView) dialogView.findViewById(R.id.server_problem);
        serverProblem.setTypeface(type1);
        serverProblem.setText(title);
        final TextView extraLine = (TextView) dialogView.findViewById(R.id.extra_line);
        extraLine.setTypeface(type1);
        extraLine.setText(message);
        Button ok = (Button) dialogView.findViewById(R.id.ok_btn);
        ok.setTypeface(type2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                finish();
            }
        });

        dialogBuilder.setIcon(R.drawable.ic_logo);
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    //checking if the app is connected with internet
    private boolean isConnectingToInternet(Context applicationContext) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(getApplicationContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void EnableGPS() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location/GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }


}
