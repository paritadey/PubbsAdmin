package admin.pubbs.in.pubbsadminnew;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 1/27/2017.
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.MyViewHolder> {
   List<AreaPojo> alist=new ArrayList<>();
    Context context;
    int pos;
    FragmentManager fragmentManager;


    public AreaAdapter(List<AreaPojo> alist, FragmentManager fragmentManager, Context context) {
        this.alist = alist;
        this.context = context;
        this.fragmentManager=fragmentManager;
    }

    public void setFilter(List<AreaPojo> areaList){
        alist=new ArrayList<>();
        alist.addAll(areaList);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AreaPojo ap=alist.get(position);
        holder.area.setText(ap.getName());
    }

    @Override
    public int getItemCount() {
        return alist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView area;
        public MyViewHolder(final View itemView) {
            super(itemView);
            area= (TextView) itemView.findViewById(R.id.textView8);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    Bundle args=new Bundle();
                    args.putInt("id",alist.get(pos).getId());
                    args.putString("area",alist.get(pos).getName());
                    args.putDouble("lat",Double.parseDouble(alist.get(pos).getLat()));
                    args.putDouble("lng",Double.parseDouble(alist.get(pos).getLon()));
                    StationListFragment sf=new StationListFragment();
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
