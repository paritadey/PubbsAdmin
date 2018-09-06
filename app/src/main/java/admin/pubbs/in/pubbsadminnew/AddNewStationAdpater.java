package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class AddNewStationAdpater extends RecyclerView.Adapter<AddNewStationAdpater.MyViewHolder> {
    private List<AreaList> areaLists;
    String latlon;
    Context mContext;
    String areaLatLang, areaname, areaid;
    private String TAG = AddNewStationAdpater.class.getSimpleName();

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

    public AddNewStationAdpater(List<AreaList> areaLists) {
        this.areaLists = areaLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list, parent, false);

        return new AddNewStationAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AreaList areaList = areaLists.get(position);
        holder.areaName.setText(areaList.getAreaName());
        holder.areaId.setText(areaList.getAreaId());
        holder.areaLatLon.setText(areaList.getAreaLatLon());
        areaname = holder.areaName.getText().toString();
        areaid = holder.areaId.getText().toString();
        latlon = holder.areaLatLon.getText().toString();
        Log.d(TAG, "LatLong:" + latlon);
        areaLatLang = Arrays.toString(latlon.split("lat/lng:"));
        Log.d(TAG, "Array of latlng:" + areaLatLang);
        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddStationInMap.class);
                intent.putExtra("area_name", areaname);
                intent.putExtra("area_id", areaid);
                intent.putExtra("latlon", areaLatLang);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaLists.size();
    }
}
