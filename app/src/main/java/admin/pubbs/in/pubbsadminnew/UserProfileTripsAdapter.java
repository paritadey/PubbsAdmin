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
        public TextView arrival_time, booking_id, duration, money_tv, money;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            arrival_time = (TextView) view.findViewById(R.id.arrival_time);
            arrival_time.setTypeface(type1);
            booking_id = view.findViewById(R.id.booking_id);
            booking_id.setTypeface(type1);
            duration = view.findViewById(R.id.duration);
            duration.setTypeface(type1);
            money = view.findViewById(R.id.money);
            money.setTypeface(type1);
            money_tv = view.findViewById(R.id.money_tv);
            money_tv.setTypeface(type2);
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
        holder.arrival_time.setText(tripsList.getArrivalTime());
        holder.booking_id.setText("Booking ID: "+tripsList.getBookingId());
        holder.duration.setText(tripsList.getDuration()+"mins");
        holder.money.setText(tripsList.getAmount());
    }

    @Override
    public int getItemCount() {
        return userTrips.size();
    }

}
