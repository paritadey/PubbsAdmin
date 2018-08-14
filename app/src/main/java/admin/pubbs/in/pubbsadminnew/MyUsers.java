package admin.pubbs.in.pubbsadminnew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MyUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserList> userList = new ArrayList<>();
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        prepareUserData();
    }
    private void prepareUserData() {
        UserList user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);

        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);
        user = new UserList("Tiphane Michael","9876543210", "ID : 325TY56K" );
        userList.add(user);

        userAdapter.notifyDataSetChanged();
    }

}
