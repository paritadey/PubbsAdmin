package admin.pubbs.in.pubbsadminnew;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 10/24/2016.
 */

public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.MyViewHolder> {
    private List<Stations> slist =new ArrayList<>();
    Context mContext;
    String url;
    FragmentManager fragmentManager;

    public StationListAdapter(List<Stations> slist,FragmentManager fragmentManager,Context mContext) {
        this.slist = slist;
        this.mContext=mContext;
        this.fragmentManager=fragmentManager;
    }

    public void setFilter(List<Stations> sList){
        slist=new ArrayList<>();
        slist.addAll(sList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stations_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Stations s= slist.get(position);
        holder.station_title.setText(s.getStation_name());
        holder.available_text.setText(String.valueOf(s.getAvailable()));
        if (!s.isVirtual()){
            holder.main_stn_icon.setImageResource(R.drawable.ic_star_black_24dp);
        }else {
            holder.main_stn_icon.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView station_title,available_text;
        ImageView main_stn_icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            station_title= (TextView) itemView.findViewById(R.id.station_name);
            available_text= (TextView) itemView.findViewById(R.id.available);
            main_stn_icon= (ImageView) itemView.findViewById(R.id.main_stn_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos=getAdapterPosition();
                    Bundle args=new Bundle();
                    args.putString("name",slist.get(pos).getStation_name());
                    args.putInt("id",Integer.parseInt(slist.get(pos).getStation_id()));
                    args.putBoolean("virtual",slist.get(pos).isVirtual());
                    args.putDouble("lat",Double.parseDouble(slist.get(pos).getLat()));
                    args.putDouble("lng",Double.parseDouble(slist.get(pos).getLon()));
                    StationFragment sf=new StationFragment();
                    sf.setArguments(args);
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.myFrame,sf)
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }
            });
        }
    }
}
