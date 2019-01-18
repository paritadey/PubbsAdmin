package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
/*created by Parita Dey*/

public class AreaSubscriptionHistoryAdapter extends RecyclerView.Adapter<AreaSubscriptionHistoryAdapter.MyViewHolder> {
    private List<AdminSubscriptionHistoryList> adminSubscriptionHistoryLists;
    private String TAG = AreaSubscriptionHistoryAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView areaname, areaid;//basic_plan_amount, basic_plan_month, basic_plan_mins;
        /*   public TextView standard_plan_amount, standard_plan_month, standard_plan_mins, sweet_plan_amount;
           public TextView sweet_plan_month, sweet_plan_mins, premium_plan_amount, premium_plan_month, premium_plan_mins;*/
        public RelativeLayout subscription_layout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            areaname = (TextView) view.findViewById(R.id.area_name);
            areaname.setTypeface(type1);
            areaid = (TextView) view.findViewById(R.id.area_id);
            areaid.setTypeface(type1);

         /*   basic_plan_amount = view.findViewById(R.id.basic_plan_amount);
            basic_plan_month = view.findViewById(R.id.basic_plan_month);
            basic_plan_mins = view.findViewById(R.id.basic_plan_mins);
            standard_plan_amount = view.findViewById(R.id.standard_plan_amount);
            standard_plan_month = view.findViewById(R.id.standard_plan_month);
            standard_plan_mins = view.findViewById(R.id.standard_plan_mins);
            sweet_plan_amount = view.findViewById(R.id.sweet_plan_amount);
            sweet_plan_month = view.findViewById(R.id.sweet_plan_month);
            sweet_plan_mins = view.findViewById(R.id.sweet_plan_mins);
            premium_plan_amount = view.findViewById(R.id.premium_plan_amount);
            premium_plan_month = view.findViewById(R.id.premium_plan_month);
            premium_plan_mins = view.findViewById(R.id.premium_plan_mins);*/
            subscription_layout = (RelativeLayout) view.findViewById(R.id.area_layout);
        }
    }

    public AreaSubscriptionHistoryAdapter(List<AdminSubscriptionHistoryList> adminSubscriptionHistoryLists) {
        this.adminSubscriptionHistoryLists = adminSubscriptionHistoryLists;
    }

    @Override
    public AreaSubscriptionHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscription_list_history, parent, false);

        return new AreaSubscriptionHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdminSubscriptionHistoryList adminSubscriptionList = adminSubscriptionHistoryLists.get(position);
        holder.areaname.setText(adminSubscriptionList.getArea_name());
        holder.areaid.setText(adminSubscriptionList.getArea_id());

        String area_id = adminSubscriptionList.getArea_id();
        String area_name = adminSubscriptionList.getArea_name();
        String basic_plan_amount =adminSubscriptionList.getBasic_plan_amount();
        String basic_plan_month = adminSubscriptionList.getBasic_plan_month();
        String basic_plan_mins = adminSubscriptionList.getBasic_plan_mins();
        String standard_plan_amount = adminSubscriptionList.getStandard_plan_amount();
        String standard_plan_month = adminSubscriptionList.getStandard_plan_month();
        String standard_plan_mins = adminSubscriptionList.getStandard_plan_mins();
        String sweet_plan_amount = adminSubscriptionList.getSweet_plan_amount();
        String sweet_plan_month = adminSubscriptionList.getSweet_plan_month();
        String sweet_plan_mins = adminSubscriptionList.getSweet_plan_mins();
        String premium_plan_amount = adminSubscriptionList.getPremium_plan_amount();
        String premium_plan_month = adminSubscriptionList.getPremium_plan_month();
        String premium_plan_mins = adminSubscriptionList.getPremium_plan_mins();

        holder.subscription_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Subscription.class);
                intent.putExtra("area_id", area_id);
                intent.putExtra("area_name", area_name);
                intent.putExtra("basic_plan_amount", basic_plan_amount);
                intent.putExtra("basic_plan_month", basic_plan_month);
                intent.putExtra("basic_plan_mins", basic_plan_mins);
                intent.putExtra("standard_plan_amount", standard_plan_amount);
                intent.putExtra("standard_plan_month", standard_plan_month);
                intent.putExtra("standard_plan_mins", standard_plan_mins);
                intent.putExtra("sweet_plan_amount", sweet_plan_amount);
                intent.putExtra("sweet_plan_month", sweet_plan_month);
                intent.putExtra("sweet_plan_mins", sweet_plan_mins);
                intent.putExtra("premium_plan_amount", premium_plan_amount);
                intent.putExtra("premium_plan_month", premium_plan_month);
                intent.putExtra("premium_plan_mins", premium_plan_mins);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminSubscriptionHistoryLists.size();
    }
}
