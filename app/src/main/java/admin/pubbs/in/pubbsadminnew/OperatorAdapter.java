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

import java.util.List;
/*created by Parita Dey*/

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.MyViewHolder> {
    private List<OperatorList> operatorLists;
    private String TAG = OperatorAdapter.class.getSimpleName();

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

    public OperatorAdapter(List<OperatorList> operatorList) {
        this.operatorLists = operatorList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_operator_list, parent, false);

        return new OperatorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OperatorList operatorList = operatorLists.get(position);
        holder.fullname.setText(operatorList.getFullname());
        holder.adminmobile.setText(operatorList.getAdminmobile());
        holder.admin_type.setText(operatorList.getAdmin_type());
        String full_name = operatorList.getFullname();
        String admin_type = operatorList.getAdmin_type();
        String admin_mobile = operatorList.getAdminmobile();
        String area_name = operatorList.getArea_name();
        holder.deleteOperatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.d(TAG, "Admin details:"+full_name+"-"+admin_mobile+"-"+admin_type+"-"+area_name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorLists.size();
    }


}
