package admin.pubbs.in.pubbsadminnew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
/*created by Parita Dey*/

public class SubAdminAdpater extends RecyclerView.Adapter<SubAdminAdpater.MyViewHolder> {
    private List<SubadminList> subadminLists;
    private String TAG = SubAdminAdpater.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fullname, adminmobile, areaid;
        public RelativeLayout subadminLayout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            fullname = (TextView) view.findViewById(R.id.subadmin_name);
            fullname.setTypeface(type1);
            adminmobile = (TextView) view.findViewById(R.id.subadmin_mobile);
            adminmobile.setTypeface(type1);
            areaid = view.findViewById(R.id.area_id);
            subadminLayout = view.findViewById(R.id.sub_admin_layout);
        }
    }

    public SubAdminAdpater(List<SubadminList> subadminLists) {
        this.subadminLists = subadminLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_admin_list, parent, false);

        return new SubAdminAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubAdminAdpater.MyViewHolder holder, int position) {
        SubadminList subadminList = subadminLists.get(position);
        holder.fullname.setText(subadminList.getFull_name());
        holder.adminmobile.setText(subadminList.getAdmin_mobile());
        holder.areaid.setText(subadminList.getArea_id());
        String fullname = subadminList.getFull_name(); //holder.areaName.getText().toString();
        String adminmobile = subadminList.getAdmin_mobile();//holder.areaId.getText().toString();
        String areaid = subadminList.getArea_id();//holder.areaLatLon.getText().toString();
        holder.subadminLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddLock.class);
                intent.putExtra("fullname", fullname);
                intent.putExtra("adminmobile", adminmobile);
                intent.putExtra("areaid", areaid);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subadminLists.size();
    }

}
