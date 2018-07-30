package admin.pubbs.in.pubbsadminnew;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LORD on 10/24/2016.
 */

public class VirtualStationListAdapter extends RecyclerView.Adapter<VirtualStationListAdapter.MyViewHolder> {
    private List<VirtualStation> slist;
    Context mContext;
    String url;
    FragmentManager fragmentManager;

    public VirtualStationListAdapter(List<VirtualStation> slist, FragmentManager fragmentManager, Context mContext) {
        this.slist = slist;
        this.mContext=mContext;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stations_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VirtualStation s= slist.get(position);
        holder.station_title.setText(s.getStation_name());
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView station_title,address;
        public MyViewHolder(View itemView) {
            super(itemView);
            station_title= (TextView) itemView.findViewById(R.id.station_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos=getAdapterPosition();
                    Bundle args=new Bundle();
                    args.putString("name",slist.get(pos).getStation_name());
                    args.putBoolean("virtual",false);
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
