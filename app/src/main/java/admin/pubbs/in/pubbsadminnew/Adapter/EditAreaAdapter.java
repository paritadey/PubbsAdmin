package admin.pubbs.in.pubbsadminnew.Adapter;

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

import admin.pubbs.in.pubbsadminnew.AreaStations;
import admin.pubbs.in.pubbsadminnew.EditStation;
import admin.pubbs.in.pubbsadminnew.List.AreaList;
import admin.pubbs.in.pubbsadminnew.R;

public class EditAreaAdapter extends RecyclerView.Adapter<EditAreaAdapter.MyViewHolder> {
    private List<AreaList> areaLists;
    private String TAG = EditAreaAdapter.class.getSimpleName();

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

    public EditAreaAdapter(List<AreaList> areaLists) {
        this.areaLists = areaLists;
    }

    @Override
    public EditAreaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list, parent, false);

        return new EditAreaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditAreaAdapter.MyViewHolder holder, int position) {
        AreaList areaList = areaLists.get(position);
        holder.areaName.setText(areaList.getAreaName());
        holder.areaId.setText(areaList.getAreaId());
        holder.areaLatLon.setText(areaList.getAreaLatLon());
        String areaname =areaList.getAreaName(); //holder.areaName.getText().toString();
        String areaid = areaList.getAreaId();//holder.areaId.getText().toString();
        String latlon = areaList.getAreaLatLon();//holder.areaLatLon.getText().toString();
        Log.d(TAG, "LatLong:" + latlon);
        //String areaLatLang = Arrays.toString(latlon.split("lat/lng:"));
        //Log.d(TAG, "Array of latlng:" + areaLatLang);
        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditStation.class);
                intent.putExtra("area_name", areaid);
                intent.putExtra("area_id", areaname);
               // intent.putExtra("latlon", latlon);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaLists.size();
    }

}
