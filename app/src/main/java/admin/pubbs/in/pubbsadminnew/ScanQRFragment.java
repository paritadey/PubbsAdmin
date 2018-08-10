package admin.pubbs.in.pubbsadminnew;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by LORD on 8/30/2017.
 */

public class ScanQRFragment extends Fragment implements ZXingScannerView.ResultHandler,AsyncResponse{
    private static final int MY_CAMERA_PERMISSION =234 ;
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private static final String FLASH_STATE = "FLASH_STATE";
    ProgressDialog pd;
    int stn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        stn=getArguments().getInt("stn");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Scan QR");
        if(savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
        }else{
            mFlash = false;
        }
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_PERMISSION);
        }else {
            mScannerView.startCamera();          // Start camera on resume
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        addCnfirmationDialog("Do you want to add this cycle?",result.toString(),getActivity());
    }

    private void addCnfirmationDialog(String msg,final String id,Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    pd.setMessage("Adding...");
                    pd.show();
                    JSONObject jo=new JSONObject();
                    try {
                        jo.put("method","addcycle");
                        jo.put("stn",stn);
                        jo.put("cycle_id",id.replaceAll(":",""));
                        jo.put("cycle_address",id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new SendRequest(getResources().getString(R.string.url),jo,ScanQRFragment.this,getActivity()).executeJsonRequest();
                })
                .setNegativeButton("No",null);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_CAMERA_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mScannerView.startCamera();          // Start camera on resume

                } else {
                    getFragmentManager().beginTransaction().remove(this).commit();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        try {
            if (jsonObject.has("method")) {
                if (jsonObject.getString("method").equals("addcycle")) {
                    Toast.makeText(getActivity(), "Cycle added", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().remove(this).commit();
                    getFragmentManager().popBackStackImmediate();
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(),getResources().getString(R.string.server_error));
        pd.dismiss();
        getFragmentManager().beginTransaction().remove(this).commit();
        getFragmentManager().popBackStackImmediate();
    }
}
