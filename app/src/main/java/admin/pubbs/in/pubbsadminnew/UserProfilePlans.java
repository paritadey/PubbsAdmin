package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import admin.pubbs.in.pubbsadminnew.Adapter.UserProfilePlanAdapter;
import admin.pubbs.in.pubbsadminnew.BottomSheet.CustomDivider;
import admin.pubbs.in.pubbsadminnew.BottomSheet.RecyclerTouchListener;
import admin.pubbs.in.pubbsadminnew.List.UserProfilePlansList;
import admin.pubbs.in.pubbsadminnew.NetworkCall.AsyncResponse;
import admin.pubbs.in.pubbsadminnew.NetworkCall.SendRequest;

/*created by Parita Dey*/

public class UserProfilePlans extends Fragment implements AsyncResponse {
    private RecyclerView recyclerView;
    private String TAG = UserProfilePlans.class.getSimpleName();
    private UserProfilePlanAdapter userProfilePlanAdapter;
    private List<UserProfilePlansList> userProfilePlansLists = new ArrayList<>();
    String userPhone;

    public UserProfilePlans() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_user_profile_plans, container, false);
        userPhone = args.getString("userPhone");
        Log.d(TAG, "User phone: " + userPhone);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        userProfilePlanAdapter = new UserProfilePlanAdapter(userProfilePlansLists);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(getContext(), LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userProfilePlanAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserProfilePlansList lists = userProfilePlansLists.get(position);
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
            jo.put("method", "getallplandetails");
            jo.put("user", userPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, UserProfilePlans.this, getActivity()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        userProfilePlansLists.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getallplandetails") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            UserProfilePlansList user = new UserProfilePlansList(jo.getString("subscription_plan_name"),
                                    jo.getString("subscription_td"), jo.getString("datetime"),
                                    jo.getInt("amount"), jo.getInt("time_limit_in_day"));
                            userProfilePlansLists.add(user);
                        }
                    }
                } else {
                    showDialog("No Subscription plan is active.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        userProfilePlanAdapter.notifyDataSetChanged();
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
                Intent back = new Intent(getContext(), MyUsers.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

}
