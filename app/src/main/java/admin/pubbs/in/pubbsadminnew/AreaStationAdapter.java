package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class AreaStationAdapter extends RecyclerView.Adapter<AreaStationAdapter.MyViewHolder> {
    private List<AreaList> areaLists;
    private String TAG = AreaStationAdapter.class.getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView areaName, areaId, areaLatLon;
        public RelativeLayout areaLayout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            areaName = (TextView) view.findViewById(R.id.area_name);
            areaName.setTypeface(type1);
            areaId = (TextView) view.findViewById(R.id.area_id);
            areaId.setTypeface(type1);
            areaLatLon = (TextView) view.findViewById(R.id.area_lat_lon);
            areaLayout = (RelativeLayout) view.findViewById(R.id.area_layout);
        }
    }

    public AreaStationAdapter(List<AreaList> areaLists) {
        this.areaLists = areaLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list, parent, false);

        return new AreaStationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AreaStationAdapter.MyViewHolder holder, int position) {
        AreaList areaList = areaLists.get(position);
        holder.areaName.setText(areaList.getAreaName());
        holder.areaId.setText(areaList.getAreaId());
        holder.areaLatLon.setText(areaList.getAreaLatLon());
        String area_name = areaList.getAreaName();
        String area_id = areaList.getAreaId();
        String area_latlon = areaList.getAreaLatLon();
        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowAllStations.class);
                intent.putExtra("area_name", area_name);
                intent.putExtra("area_id", area_id);
                intent.putExtra("area_latlon", area_latlon);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaLists.size();
    }
}
