package admin.pubbs.in.pubbsadminnew.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.pubbs.in.pubbsadminnew.List.RedistributionList;
import admin.pubbs.in.pubbsadminnew.R;

/*created by Parita Dey*/
public class RedistributionAdapter extends RecyclerView.Adapter<RedistributionAdapter.MyViewHolder> {

    private List<RedistributionList> redistributionlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bicycleId;
        public TextView addToStation, text_required;
        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            bicycleId = (TextView) view.findViewById(R.id.bicycle_id);
            bicycleId.setTypeface(type1);
            addToStation = (TextView) view.findViewById(R.id.add_to_station);
            addToStation.setTypeface(type1);
            text_required = view.findViewById(R.id.text_required);
            text_required.setTypeface(type1);
        }
    }
    public RedistributionAdapter(List<RedistributionList> redistributionlist) {
        this.redistributionlist = redistributionlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.redistribution_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RedistributionList redistribution = redistributionlist.get(position);
        holder.bicycleId.setText(redistribution.getBicycleId());
        holder.addToStation.setOnClickListener(new View.OnClickListener() {
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
