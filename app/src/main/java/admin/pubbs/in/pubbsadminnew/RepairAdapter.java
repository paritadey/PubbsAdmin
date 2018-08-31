package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/*created by Parita Dey*/

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.MyViewHolder> {
    private List<RepairList> repairlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bicycleId;
        public TextView addToRepair, text_required;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            bicycleId = (TextView) view.findViewById(R.id.bicycle_id);
            bicycleId.setTypeface(type1);
            addToRepair = (TextView) view.findViewById(R.id.add_to_repair);
            addToRepair.setTypeface(type1);
            text_required = view.findViewById(R.id.text_required);
            text_required.setTypeface(type1);
        }
    }
    public RepairAdapter(List<RepairList> repairlist) {
        this.repairlist = repairlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repair_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RepairList repair = repairlist.get(position);
        holder.bicycleId.setText(repair.getBicycleId());
        holder.addToRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return repairlist.size();
    }

}
