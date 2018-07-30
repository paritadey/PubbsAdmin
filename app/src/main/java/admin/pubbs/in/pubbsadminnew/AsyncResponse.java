package admin.pubbs.in.pubbsadminnew;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by DOS20 on 3/31/2017.
 */

public interface AsyncResponse {
    void onResponse(JSONObject jsonObject);
    void onResponseError(VolleyError error);
}
