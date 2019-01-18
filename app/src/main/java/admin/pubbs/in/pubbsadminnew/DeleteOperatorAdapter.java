package admin.pubbs.in.pubbsadminnew;

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
/*created by Parita Dey*/

public class DeleteOperatorAdapter extends RecyclerView.Adapter<DeleteOperatorAdapter.MyViewHolder> {
    private List<DeleteOperatorList> deleteOperatorLists;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fullname, area_name, adminmobile, admin_type;
        public RelativeLayout deleteOperatorLayout;
        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            deleteOperatorLayout = view.findViewById(R.id.deleteOperatorLayout);
            fullname = (TextView) view.findViewById(R.id.fullname);
            fullname.setTypeface(type1);
            admin_type = (TextView) view.findViewById(R.id.admin_type);
            admin_type.setTypeface(type1);
            adminmobile = view.findViewById(R.id.adminmobile);
            adminmobile.setTypeface(type1);
            area_name = view.findViewById(R.id.area_name);
        }
    }

    public DeleteOperatorAdapter(List<DeleteOperatorList> deleteOperatorList) {
        this.deleteOperatorLists = deleteOperatorList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_operator_list, parent, false);

        return new DeleteOperatorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DeleteOperatorList deleteOperatorList = deleteOperatorLists.get(position);
        holder.fullname.setText(deleteOperatorList.getFullname());
        holder.adminmobile.setText(deleteOperatorList.getAdminmobile());
        holder.admin_type.setText(deleteOperatorList.getAdmin_type());
        String full_name = deleteOperatorList.getFullname();
        String admin_type = deleteOperatorList.getAdmin_type();
        String admin_mobile = deleteOperatorList.getAdminmobile();
        String area_name = deleteOperatorList.getArea_name();
        holder.deleteOperatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteSingleOperator.class);
                intent.putExtra("full_name", full_name);
                intent.putExtra("admin_mobile", admin_mobile);
                intent.putExtra("area_name", area_name);
                intent.putExtra("admin_type", admin_type);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deleteOperatorLists.size();
    }


}
