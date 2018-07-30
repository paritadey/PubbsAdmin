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

public class CycleListAdapter extends RecyclerView.Adapter<CycleListAdapter.MyViewHolder> {
    private List<Cycles> clist;
    Context mContext;
    String url;
    FragmentManager fragmentManager;

    public CycleListAdapter(List<Cycles> clist, FragmentManager fragmentManager, Context mContext) {
        this.clist = clist;
        this.mContext=mContext;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cycle_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cycles c= clist.get(position);
        holder.cycle_id.setText(c.getCycleId());
        holder.battery_status.setText(String.valueOf(c.getBatteryStatus()));
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cycle_id,battery_status;
        public MyViewHolder(View itemView) {
            super(itemView);
            cycle_id= (TextView) itemView.findViewById(R.id.cycle_id);
            battery_status= (TextView) itemView.findViewById(R.id.battery);
        }
    }
}
