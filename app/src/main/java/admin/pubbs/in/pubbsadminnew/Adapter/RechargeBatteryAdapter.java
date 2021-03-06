package admin.pubbs.in.pubbsadminnew.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.pubbs.in.pubbsadminnew.List.RechargeBatteryList;
import admin.pubbs.in.pubbsadminnew.R;
/*created by Parita Dey*/

public class RechargeBatteryAdapter extends RecyclerView.Adapter<RechargeBatteryAdapter.MyViewHolder> {
    private List<RechargeBatteryList> rechargeBatteryLists;
    private String TAG = RechargeBatteryAdapter.class.getSimpleName();
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bicycleId;
        public TextView battery_status, battery_status_tv, battery_status_percentage;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            bicycleId = (TextView) view.findViewById(R.id.bicycle_id);
            bicycleId.setTypeface(type1);
            battery_status = (TextView) view.findViewById(R.id.battery_status);
            battery_status.setTypeface(type1);
            battery_status_tv = view.findViewById(R.id.battery_status_tv);
            battery_status_tv.setTypeface(type1);
            battery_status_percentage = view.findViewById(R.id.battery_status_percentage);
            battery_status_percentage.setTypeface(type1);
        }
    }

    public RechargeBatteryAdapter(List<RechargeBatteryList> rechargeBatteryLists) {
        this.rechargeBatteryLists = rechargeBatteryLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recharge_battery_list, parent, false);

        return new RechargeBatteryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RechargeBatteryList rechargeBatteryList = rechargeBatteryLists.get(position);
        holder.bicycleId.setText(rechargeBatteryList.getCycle_id());
        String status = rechargeBatteryList.getBattery_status();
        Log.d(TAG, "battery status:" + status);
        if (Integer.parseInt(status) <= 40) {
            holder.battery_status.setText(rechargeBatteryList.getBattery_status());
            holder.battery_status.setTextColor(Color.RED);
        } else {
            holder.battery_status.setText(rechargeBatteryList.getBattery_status());
        }
    }

    @Override
    public int getItemCount() {
        return rechargeBatteryLists.size();
    }

}
