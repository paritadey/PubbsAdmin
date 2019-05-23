package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import admin.pubbs.in.pubbsadminnew.Adapter.UserProfileTripsAdapter;
import admin.pubbs.in.pubbsadminnew.BottomSheet.CustomDivider;
import admin.pubbs.in.pubbsadminnew.BottomSheet.RecyclerTouchListener;
import admin.pubbs.in.pubbsadminnew.List.UserProfileTripsList;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

public class UserProfileTrips extends android.support.v4.app.Fragment implements AsyncResponse {
    private RecyclerView recyclerView;
    private UserProfileTripsAdapter userProfileTripsAdapter;
    private List<UserProfileTripsList> userProfileTripsLists = new ArrayList<>();
    private String TAG = UserProfileTrips.class.getSimpleName();
    String userPhone;

    public UserProfileTrips() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        userPhone = args.getString("userPhone");
        Log.d(TAG, "User phone: "+userPhone);

        View rootView = inflater.inflate(R.layout.fragment_user_profile_trips, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        userProfileTripsAdapter = new UserProfileTripsAdapter(userProfileTripsLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(getContext(), LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userProfileTripsAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserProfileTripsList lists = userProfileTripsLists.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return rootView;
    }
   @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getusertrips");
            jo.put("user", userPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, UserProfileTrips.this, getActivity()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        userProfileTripsLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getusertrips") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            UserProfileTripsList user = new UserProfileTripsList(jo.getString("booking_id"),
                                    jo.getString("arrival_time"), jo.getInt("total_amount"), jo.getInt("duration"));
                            userProfileTripsLists.add(user);
                        }
                    }
                } else {
                    showDialog("No trips is present.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        userProfileTripsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResponseError(VolleyError error) {
        showDialog("Server Error !");
    }

    private void showDialog(String message) {
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
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
               /* Intent back = new Intent(getContext(), MyUsers.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);*/
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
