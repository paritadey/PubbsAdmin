package admin.pubbs.in.pubbsadminnew;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/*created by Parita Dey*/

public class MyUsers extends AppCompatActivity implements AsyncResponse {//, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserList> userList = new ArrayList<>();
    EditText inputSearch;
    TextView userListTv, filter, sort;
    ImageView back;
    ProgressBar circularProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_users);
        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        circularProgressbar = findViewById(R.id.circularProgressbar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(circularProgressbar, "progress", 100, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        back = findViewById(R.id.back_button);
        inputSearch = findViewById(R.id.input_search);
        userListTv = findViewById(R.id.user_list_tv);
        inputSearch.setTypeface(type1);
        userListTv.setTypeface(type1);
        filter = findViewById(R.id.filter);
        filter.setTypeface(type3);
        sort = findViewById(R.id.sort);
        sort.setTypeface(type3);
        //Recylcer view will show the objects
        recyclerView = findViewById(R.id.recycler_view);
        userAdapter = new UserAdapter(userList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CustomDivider(this, LinearLayoutManager.VERTICAL, 8));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserList lists = userList.get(position);

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        back.setOnClickListener(new View.OnClickListener() {
            //this will redirect back to the previous page Dashboard clearing the stack history
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyUsers.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        //on text change in input search edittext show the filtered results
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());//show the search result

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<UserList> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (UserList s : userList) {
            final String searchText = s.getUserName().toLowerCase();
            if (text.contains(searchText)) {
                filterdNames.add(s);
            }
        }
        userAdapter.filterList(filterdNames);
    }

    @Override
    public void onResume() {
        //on tapping the menu item of 'My Users' in DashboardActivity fetch the result from the server
        super.onResume();
        loadData();
    }


    //loadData() will fetch the result set from the server
    private void loadData() {
        circularProgressbar.setVisibility(View.VISIBLE);
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", "getalluser");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url), jo, MyUsers.this, getApplicationContext()).executeJsonRequest();
    }

    //if any error occurred or success msg will show via a dialog box
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
                if (circularProgressbar.isEnabled()) {
                    circularProgressbar.setVisibility(View.GONE);
                }
                Intent intent = new Intent(MyUsers.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        circularProgressbar.setVisibility(View.GONE);
        userList.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getalluser") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            UserList user = new UserList(jo.getString("name"), jo.getString("mobile"), jo.getString("id"),
                                    jo.getString("imei"));
                            userList.add(user);
                        }
                    } else {
                        showDialog("No user is present.");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(VolleyError error) {
        // AppConfig.alertMsg(getApplicationContext(), getResources().getString(R.string.server_error));
        showDialog("Server Error !");
    }

}
