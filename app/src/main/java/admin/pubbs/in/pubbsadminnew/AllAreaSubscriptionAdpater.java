package admin.pubbs.in.pubbsadminnew;

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

public class AllAreaSubscriptionAdpater extends RecyclerView.Adapter<AllAreaSubscriptionAdpater.MyViewHolder> {
    private List<AreaList> areaLists;
    private String TAG = AllAreaSubscriptionAdpater.class.getSimpleName();

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

    public AllAreaSubscriptionAdpater(List<AreaList> areaLists) {
        this.areaLists = areaLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list, parent, false);

        return new AllAreaSubscriptionAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AreaList areaList = areaLists.get(position);
        holder.areaName.setText(areaList.getAreaName());
        holder.areaId.setText(areaList.getAreaId());
        holder.areaLatLon.setText(areaList.getAreaLatLon());
        String areaname = areaList.getAreaName(); //holder.areaName.getText().toString();
        String areaid = areaList.getAreaId();//holder.areaId.getText().toString();
        String latlon = areaList.getAreaLatLon();//holder.areaLatLon.getText().toString();
        Log.d(TAG, "LatLong:" + latlon);
        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AreaSubscription.class);
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
