package admin.pubbs.in.pubbsadminnew.NetworkCall;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;

/**
 * Created by DOS20 on 3/31/2017.
 */

public class SendRequest {

    private Context context;
    JSONObject jsonObject;
    Map<String,String> requestMap=new HashMap<>();
    String url;
    AsyncResponse asyncResponse =null;
    //ProgressDialog pd;

    public SendRequest(String url, JSONObject jsonObject, AsyncResponse asyncResponse, Context context) {
        this.context = context;
        this.jsonObject = jsonObject;
        this.url = url;
        this.asyncResponse = asyncResponse;
        Log.v("PUBBS_REQUEST",jsonObject.toString());
    }

    public SendRequest(String url, AsyncResponse asyncResponse,Context context) {
        this.context = context;
        this.url = url;
        this.asyncResponse = asyncResponse;
    }

    public void executeJsonRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pd.dismiss();
                asyncResponse.onResponse(response);
                Log.v("PUBBS_RESPONSE",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                asyncResponse.onResponseError(error);
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
        /*pd=new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();*/
    }

    public void executeStringRequest(){

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //pd.dismiss();
                            try {
                                jsonObject=new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            asyncResponse.onResponse(jsonObject);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // pd.dismiss();
                    asyncResponse.onResponseError(error);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param=new HashMap<>();
                    if (jsonObject!=null){
                        Iterator<String> iter = jsonObject.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            Object value=null;
                            try {
                                value = jsonObject.get(key);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                            param.put(key,value.toString());
                        }
                    }else{
                        param=requestMap;
                    }
                    return param;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
        /*pd=new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();*/
    }


    public void executeRequest(){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jo=null;
                        try {
                            jo=new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        asyncResponse.onResponse(jo);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pd.dismiss();
                asyncResponse.onResponseError(error);
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
        /*pd=new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();*/
    }

}
