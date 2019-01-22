package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
/*created by Parita Dey*/

public class AreaSubscriptionHistoryAdapter extends RecyclerView.Adapter<AreaSubscriptionHistoryAdapter.MyViewHolder> {
    private List<AdminSubscriptionHistoryList> adminSubscriptionHistoryLists;
    private String TAG = AreaSubscriptionHistoryAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subscription_name;
        public RelativeLayout subscription_layout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            subscription_name = (TextView) view.findViewById(R.id.subscription_name);
            subscription_name.setTypeface(type1);
            subscription_layout = (RelativeLayout) view.findViewById(R.id.area_layout);
        }
    }

    public AreaSubscriptionHistoryAdapter(List<AdminSubscriptionHistoryList> adminSubscriptionHistoryLists) {
        this.adminSubscriptionHistoryLists = adminSubscriptionHistoryLists;
    }

    @Override
    public AreaSubscriptionHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscription_list_history, parent, false);

        return new AreaSubscriptionHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdminSubscriptionHistoryList adminSubscriptionList = adminSubscriptionHistoryLists.get(position);
        holder.subscription_name.setText(adminSubscriptionList.getSubscriptionPlanName());

        String startDate = adminSubscriptionList.getStartDate();
        String endDate = adminSubscriptionList.getEndDate();
        int rideNumber = adminSubscriptionList.getRideNumber();
        int rideTime = adminSubscriptionList.getRideTime();
        int money = adminSubscriptionList.getMoney();
        String description = adminSubscriptionList.getDescription();
        int carryForward = adminSubscriptionList.getCarryForward();

        holder.subscription_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscription history:" + startDate + "\t" + endDate + "\t"
                        + rideNumber + "\t" + rideTime + "\t" + money + "\t" + description + "\t" + carryForward);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminSubscriptionHistoryLists.size();
    }
}
