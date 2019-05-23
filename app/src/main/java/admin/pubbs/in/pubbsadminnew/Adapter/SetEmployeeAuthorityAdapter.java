package admin.pubbs.in.pubbsadminnew.Adapter;

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

import admin.pubbs.in.pubbsadminnew.List.EditOperatorList;
import admin.pubbs.in.pubbsadminnew.R;

public class SetEmployeeAuthorityAdapter extends RecyclerView.Adapter<SetEmployeeAuthorityAdapter.MyViewHolder> {
    private List<EditOperatorList> editOperatorLists;
    private String TAG = SetEmployeeAuthorityAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView full_name, admin_mobile, admin_type, active_status;
        public RelativeLayout editOperatorLayout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            editOperatorLayout = view.findViewById(R.id.editOperatorLayout);
            admin_mobile = view.findViewById(R.id.admin_mobile);
            admin_mobile.setTypeface(type1);
            full_name = (TextView) view.findViewById(R.id.full_name);
            full_name.setTypeface(type1);
            admin_type = (TextView) view.findViewById(R.id.admin_type);
            admin_type.setTypeface(type1);
            active_status = view.findViewById(R.id.active_status);
            active_status.setTypeface(type1);
            active_status.setVisibility(View.GONE);
        }
    }

    public SetEmployeeAuthorityAdapter(List<EditOperatorList> editOperatorList) {
        this.editOperatorLists = editOperatorList;
    }

    @Override
    public SetEmployeeAuthorityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_operator_list, parent, false);

        return new SetEmployeeAuthorityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EditOperatorList editOperatorList = editOperatorLists.get(position);
        holder.admin_mobile.setText(editOperatorList.getAdminmobile());
        holder.full_name.setText(editOperatorList.getFullname());
        holder.admin_type.setText(editOperatorList.getAdmin_type());
        int active = editOperatorList.getActive();
        /*if (active == 1) {
            holder.active_status.setText("Status : Active");
        } else {
            holder.active_status.setText("Status : Not Active");
        }*/
        String full_name = editOperatorList.getFullname();
        String admin_type = editOperatorList.getAdmin_type();
        String admin_mobile = editOperatorList.getAdminmobile();
        holder.editOperatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Admin details:" + full_name + "-" + admin_mobile + "-" + admin_type);
              /*  Intent intent = new Intent(v.getContext(), ChangeAuthorityEmployee.class);
                intent.putExtra("full_name", full_name);
                intent.putExtra("admin_mobile", admin_mobile);
                intent.putExtra("admin_type", admin_type);
                v.getContext().startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return editOperatorLists.size();
    }

}
