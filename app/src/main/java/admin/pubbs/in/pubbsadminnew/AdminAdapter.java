package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {
    private List<AdminList> adminLists;
    private String TAG = AdminAdapter.class.getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView full_name, email, adminmobile, date_time, area_name, area_id;
        public TextView adminmobile_tv, area_name_tv;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            full_name = (TextView) view.findViewById(R.id.full_name);
            full_name.setTypeface(type2);
            email = (TextView) view.findViewById(R.id.email);
            email.setTypeface(type1);
            adminmobile = (TextView)view.findViewById(R.id.adminmobile);
            adminmobile.setTypeface(type1);
            date_time = (TextView)view.findViewById(R.id.date_time);
            date_time.setTypeface(type1);
            area_name = (TextView)view.findViewById(R.id.area_name);
            area_name.setTypeface(type1);
            area_id = (TextView) view.findViewById(R.id.area_id);
            area_id.setTypeface(type1);
            adminmobile_tv = (TextView)view.findViewById(R.id.adminmobile_tv);
            adminmobile_tv.setTypeface(type1);
            area_name_tv = (TextView)view.findViewById(R.id.area_name_tv);
            area_name_tv.setTypeface(type1);
        }
    }

    public AdminAdapter(List<AdminList> adminLists) {
        this.adminLists = adminLists;
    }

    @Override
    public AdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_list, parent, false);

        return new AdminAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdminList adminList = adminLists.get(position);
        holder.full_name.setText(adminList.getFull_name());
        holder.email.setText(adminList.getEmail());
        holder.adminmobile.setText(adminList.getAdminmobile());
        holder.date_time.setText(adminList.getDate_time());
        holder.area_name.setText(adminList.getArea_name());
        holder.area_id.setText(adminList.getArea_id());
    }

    @Override
    public int getItemCount() {
        return adminLists.size();
    }


}
