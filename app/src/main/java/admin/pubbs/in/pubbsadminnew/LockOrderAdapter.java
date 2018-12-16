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

public class LockOrderAdapter extends RecyclerView.Adapter<LockOrderAdapter.MyViewHolder> {
    private List<LockDetailsList> lockDetailsLists;
    private String TAG = LockOrderAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView areaId;
        public RelativeLayout areaLayout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            areaId = (TextView) view.findViewById(R.id.area_id);
            areaId.setTypeface(type1);
            areaLayout = (RelativeLayout) view.findViewById(R.id.area_layout);
        }
    }

    public LockOrderAdapter(List<LockDetailsList> lockDetailsLists) {
        this.lockDetailsLists = lockDetailsLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lock_list, parent, false);

        return new LockOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LockOrderAdapter.MyViewHolder holder, int position) {
        LockDetailsList lockDetailsList = lockDetailsLists.get(position);
        holder.areaId.setText(lockDetailsList.getArea_id());

        holder.areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return lockDetailsLists.size();
    }

}
