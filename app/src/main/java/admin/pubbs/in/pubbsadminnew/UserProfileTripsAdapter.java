package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/*created by Parita Dey*/

public class UserProfileTripsAdapter extends RecyclerView.Adapter<UserProfileTripsAdapter.MyViewHolder> {
    private List<UserProfileTripsList> userTrips;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bicycleId;
        public TextView dateTime, timeStamp, money, text_required;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            bicycleId = (TextView) view.findViewById(R.id.bicycle_id);
            bicycleId.setTypeface(type1);
            dateTime = view.findViewById(R.id.date_time);
            dateTime.setTypeface(type1);
            timeStamp = view.findViewById(R.id.time_stamp);
            timeStamp.setTypeface(type1);
            money = view.findViewById(R.id.money);
            money.setTypeface(type1);
            text_required = view.findViewById(R.id.text_required);
            text_required.setTypeface(type1);
        }
    }

    public UserProfileTripsAdapter(List<UserProfileTripsList> userTrips) {
        this.userTrips = userTrips;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_trips_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserProfileTripsList tripsList = userTrips.get(position);
        holder.bicycleId.setText(tripsList.getBicycleId());
        holder.dateTime.setText(tripsList.getDateTime());
        holder.timeStamp.setText(tripsList.getTimeStamp());
        holder.money.setText(tripsList.getMoney());
    }

    @Override
    public int getItemCount() {
        return userTrips.size();
    }

}
