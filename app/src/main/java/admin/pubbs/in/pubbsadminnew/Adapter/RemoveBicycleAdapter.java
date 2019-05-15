package admin.pubbs.in.pubbsadminnew.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.pubbs.in.pubbsadminnew.List.RemoveBicycleList;
import admin.pubbs.in.pubbsadminnew.R;
/*created by Parita Dey*/

public class RemoveBicycleAdapter extends RecyclerView.Adapter<RemoveBicycleAdapter.MyViewHolder> {
    private List<RemoveBicycleList> removeBicycleLists;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView address;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            address = (TextView) view.findViewById(R.id.bicycle_id);
            address.setTypeface(type1);
        }
    }
    public RemoveBicycleAdapter(List<RemoveBicycleList> removeBicycleLists) {
        this.removeBicycleLists = removeBicycleLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.remove_bicycle_list, parent, false);

        return new RemoveBicycleAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RemoveBicycleList removeBicycleList = removeBicycleLists.get(position);
        holder.address.setText(removeBicycleList.getCycle_id());
    }

    @Override
    public int getItemCount() {
        return removeBicycleLists.size();
    }


}
