package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class AddNewBicycleAdapter extends RecyclerView.Adapter<AddNewBicycleAdapter.MyViewHolder> {
    private List<RedistributionList> redistributionlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bicycleId;
        public RelativeLayout cycleLayout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            bicycleId = (TextView) view.findViewById(R.id.bicycle_id);
            bicycleId.setTypeface(type1);
            cycleLayout = (RelativeLayout) view.findViewById(R.id.cycle_layout);
        }
    }

    public AddNewBicycleAdapter(List<RedistributionList> redistributionlist) {
        this.redistributionlist = redistributionlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_bicycle_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RedistributionList redistribution = redistributionlist.get(position);
        holder.bicycleId.setText(redistribution.getBicycleId());
        holder.cycleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return redistributionlist.size();
    }

}
