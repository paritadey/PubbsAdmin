package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/*created by Parita Dey*/

public class AllStationAdapter extends RecyclerView.Adapter<AllStationAdapter.MyViewHolder> {
    private List<StationList> stationLists;
    private String TAG = AdminAdapter.class.getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView full_name, adminmobile, area_name, station_name, station_id;
        public TextView adminmobile_tv, area_name_tv, admin_name_tv;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            full_name = (TextView) view.findViewById(R.id.full_name);
            full_name.setTypeface(type1);
            adminmobile = (TextView)view.findViewById(R.id.adminmobile);
            adminmobile.setTypeface(type1);
            area_name = (TextView)view.findViewById(R.id.area_name);
            area_name.setTypeface(type1);
            station_name = (TextView) view.findViewById(R.id.station_name);
            station_name.setTypeface(type2);
            station_id = (TextView)view.findViewById(R.id.station_id);
            adminmobile_tv = (TextView)view.findViewById(R.id.adminmobile_tv);
            adminmobile_tv.setTypeface(type1);
            area_name_tv = (TextView)view.findViewById(R.id.area_name_tv);
            area_name_tv.setTypeface(type1);
            admin_name_tv = (TextView)view.findViewById(R.id.admin_name_tv);
            admin_name_tv.setTypeface(type1);
        }
    }

    public AllStationAdapter(List<StationList> stationLists) {
        this.stationLists = stationLists;
    }

    @Override
    public AllStationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.super_admin_all_stations, parent, false);

        return new AllStationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StationList stationList = stationLists.get(position);
        holder.full_name.setText(stationList.getFull_name());
        holder.adminmobile.setText(stationList.getAdminmobile());
        holder.area_name.setText(stationList.getArea_name());
        holder.station_name.setText(stationList.getStation_name());
        holder.station_id.setText(stationList.getStation_id());
    }

    @Override
    public int getItemCount() {
        return stationLists.size();
    }
}
