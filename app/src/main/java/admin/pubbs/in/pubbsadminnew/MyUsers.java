package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserList> userList = new ArrayList<>();
    EditText inputSearch;
    TextView userListTv, filter, sort;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        back = findViewById(R.id.back_button);
        inputSearch = findViewById(R.id.input_search);
        userListTv = findViewById(R.id.user_list_tv);
        inputSearch.setTypeface(type1);
        userListTv.setTypeface(type1);
        filter = findViewById(R.id.filter);
        filter.setTypeface(type3);
        sort = findViewById(R.id.sort);
        sort.setTypeface(type3);
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
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyUsers.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        prepareUserData();
    }

    private void prepareUserData() {
        UserList user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);

        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);
        user = new UserList("Tiphane Michael", "9876543210", "ID : 325TY56K");
        userList.add(user);

        userAdapter.notifyDataSetChanged();
    }

}
