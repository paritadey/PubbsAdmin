package admin.pubbs.in.pubbsadminnew.Adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.pubbs.in.pubbsadminnew.List.UserProfilePlansList;
import admin.pubbs.in.pubbsadminnew.R;

public class UserProfilePlanAdapter extends RecyclerView.Adapter<UserProfilePlanAdapter.MyViewHolder> {
    private List<UserProfilePlansList> userProfilePlansLists;
    private String TAG = UserProfilePlanAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subscription_name, subscription_id, date_time;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            subscription_name = view.findViewById(R.id.subscription_name);
            subscription_name.setTypeface(type2);
            subscription_id = view.findViewById(R.id.subscription_id);
            subscription_id.setTypeface(type1);
            date_time = view.findViewById(R.id.date_time);
            date_time.setTypeface(type1);
        }
    }

    public UserProfilePlanAdapter(List<UserProfilePlansList> userProfilePlansLists) {
        this.userProfilePlansLists = userProfilePlansLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_plan_list, parent, false);

        return new UserProfilePlanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfilePlanAdapter.MyViewHolder holder, int position) {
        UserProfilePlansList plansList = userProfilePlansLists.get(position);
        holder.subscription_name.setText(plansList.getSubscription_name());
        holder.subscription_id.setText(plansList.getSubscription_id());
        holder.date_time.setText(plansList.getDate_time());
    }


    @Override
    public int getItemCount() {
        return userProfilePlansLists.size();
    }


}
