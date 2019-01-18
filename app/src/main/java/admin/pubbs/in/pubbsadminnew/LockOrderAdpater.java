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

public class LockOrderAdpater extends RecyclerView.Adapter<LockOrderAdpater.MyViewHolder> {
    private List<LockDetailsList> lockDetailsLists;
    private String TAG = LockOrderAdpater.class.getSimpleName();

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
            areaLatLon.setVisibility(View.GONE);
            areaLayout = (RelativeLayout) view.findViewById(R.id.area_layout);
        }
    }

    public LockOrderAdpater(List<LockDetailsList> lockDetailsLists) {
        this.lockDetailsLists = lockDetailsLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list, parent, false);

        return new LockOrderAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LockOrderAdpater.MyViewHolder holder, int position) {
        LockDetailsList lockDetailsList = lockDetailsLists.get(position);
        holder.areaName.setText(lockDetailsList.getOrderNumber());
        holder.areaId.setText(lockDetailsList.getArea_id());
        String order_number = lockDetailsList.getOrderNumber();
        String area_id = lockDetailsList.getArea_id();
        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LockOrderDetails.class);
                intent.putExtra("order_number", order_number);
                intent.putExtra("area_id", area_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lockDetailsLists.size();
    }

}
