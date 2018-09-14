package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class DeleteStationAdpater extends RecyclerView.Adapter<DeleteStationAdpater.MyViewHolder> {
    private List<DeleteStationList> deleteStationLists;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView station_name, station_id, area_name, area_id;
        public RelativeLayout deleteStationLayout;
        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            deleteStationLayout = view.findViewById(R.id.deleteStationLayout);
            station_name = (TextView) view.findViewById(R.id.stationName);
            station_name.setTypeface(type1);
            station_id = (TextView) view.findViewById(R.id.stationId);
            station_id.setTypeface(type1);
            area_name = view.findViewById(R.id.area_name);
            area_id = view.findViewById(R.id.area_id);
        }
    }

    public DeleteStationAdpater(List<DeleteStationList> deleteStationLists) {
        this.deleteStationLists = deleteStationLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_station_list, parent, false);

        return new DeleteStationAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DeleteStationList deleteStationList = deleteStationLists.get(position);
        holder.station_name.setText(deleteStationList.getStation_name());
        holder.station_id.setText(deleteStationList.getStation_id());
        String station_name = deleteStationList.getStation_name();
        String station_id = deleteStationList.getStation_id();
        String area_name = deleteStationList.getArea_name();
        String area_id = deleteStationList.getArea_id();
        holder.deleteStationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteSingleStation.class);
                intent.putExtra("station_name", station_name);
                intent.putExtra("station_id", station_id);
                intent.putExtra("area_name", area_name);
                intent.putExtra("area_id", area_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deleteStationLists.size();
    }
}
