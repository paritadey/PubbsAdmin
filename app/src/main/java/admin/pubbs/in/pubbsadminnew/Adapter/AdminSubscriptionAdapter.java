package admin.pubbs.in.pubbsadminnew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import admin.pubbs.in.pubbsadminnew.AreaSubscriptionHistory;
import admin.pubbs.in.pubbsadminnew.List.AdminSubscriptionList;
import admin.pubbs.in.pubbsadminnew.R;
/*created by Parita Dey*/

public class AdminSubscriptionAdapter extends RecyclerView.Adapter<AdminSubscriptionAdapter.MyViewHolder> {
    private List<AdminSubscriptionList> adminSubscriptionLists;
    private String TAG = AdminSubscriptionAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView admin_mobile, email, area_id;
        public RelativeLayout admin_layout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            area_id = view.findViewById(R.id.area_id);
            area_id.setTypeface(type1);
            admin_mobile = (TextView) view.findViewById(R.id.admin_mobile);
            admin_mobile.setTypeface(type1);
            email = (TextView) view.findViewById(R.id.email);
            email.setTypeface(type1);
            admin_layout = (RelativeLayout) view.findViewById(R.id.admin_layout);
        }
    }

    public AdminSubscriptionAdapter(List<AdminSubscriptionList> adminSubscriptionLists) {
        this.adminSubscriptionLists = adminSubscriptionLists;
    }

    @Override
    public AdminSubscriptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_subscription_list, parent, false);

        return new AdminSubscriptionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdminSubscriptionList adminSubscriptionList = adminSubscriptionLists.get(position);
        holder.area_id.setText(adminSubscriptionList.getArea_id());
        holder.admin_mobile.setText(adminSubscriptionList.getAdminmobile());
        holder.email.setText(adminSubscriptionList.getEmail());
        String adminmobile =adminSubscriptionList.getAdminmobile();
        String area_id = adminSubscriptionList.getArea_id();
        holder.admin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AreaSubscriptionHistory.class);
                intent.putExtra("adminmobile", adminmobile);
                intent.putExtra("area_id", area_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminSubscriptionLists.size();
    }

}
