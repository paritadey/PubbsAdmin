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

import admin.pubbs.in.pubbsadminnew.List.AreaList;
import admin.pubbs.in.pubbsadminnew.R;
import admin.pubbs.in.pubbsadminnew.SetAreaAgreement;
/*created by Parita Dey*/

public class AreaLegalAdapter extends RecyclerView.Adapter<AreaLegalAdapter.MyViewHolder> {
    private List<AreaList> areaLists;
    private String TAG = AreaLegalAdapter.class.getSimpleName();

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

    public AreaLegalAdapter(List<AreaList> areaLists) {
        this.areaLists = areaLists;
    }

    @Override
    public AreaLegalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list, parent, false);

        return new AreaLegalAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AreaLegalAdapter.MyViewHolder holder, int position) {
        AreaList areaList = areaLists.get(position);
        holder.areaName.setText(areaList.getAreaId());
        holder.areaId.setText(areaList.getAreaName());
        holder.areaLatLon.setText(areaList.getAreaLatLon());
        String areaname = areaList.getAreaName();
        String areaid = areaList.getAreaId();
        String latlon = areaList.getAreaLatLon();
        Log.d(TAG, "LatLong:" + latlon);
        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SetAreaAgreement.class);
                intent.putExtra("areaname", areaname);
                intent.putExtra("areaid", areaid);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaLists.size();
    }

}
