package admin.pubbs.in.pubbsadminnew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class UserProfileTrips extends Fragment {
    private RecyclerView recyclerView;
    private UserProfileTripsAdapter userProfileTripsAdapter;
    private List<UserProfileTripsList> userProfileTripsLists = new ArrayList<>();

    public UserProfileTrips() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        prepareUserTripsData();
        return rootView;
    }

    private void prepareUserTripsData() {
        UserProfileTripsList tripsList = new UserProfileTripsList
                ("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
        tripsList = new UserProfileTripsList("16 March 2018", "WB213426G", "23 mins", "5");
        userProfileTripsLists.add(tripsList);
    }
}
