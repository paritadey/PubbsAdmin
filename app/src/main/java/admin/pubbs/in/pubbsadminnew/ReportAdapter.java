package admin.pubbs.in.pubbsadminnew;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 4/9/2018.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    private List<Reports> reportsList =new ArrayList<>();
    private FragmentManager fragmentManager;

    public ReportAdapter(List<Reports> reportsList, FragmentManager fragmentManager) {
        this.reportsList = reportsList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reports reports=reportsList.get(position);
        holder.userIdText.setText(reports.getUserid());
        holder.msgText.setText(reports.getMessage());
        holder.dateTimeText.setText(reports.getDateTime());
    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userIdText,dateTimeText,msgText;
        public MyViewHolder(View itemView) {
            super(itemView);
            userIdText= (TextView) itemView.findViewById(R.id.userid);
            msgText= (TextView) itemView.findViewById(R.id.msg);
            dateTimeText= (TextView) itemView.findViewById(R.id.date_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Bundle args=new Bundle();
                    args.putString("userid",reportsList.get(pos).getUserid());
                    args.putString("message",reportsList.get(pos).getMessage());
                    args.putString("date_time",reportsList.get(pos).getDateTime());
                    ReportDetailsFragment rdf=new ReportDetailsFragment();
                    rdf.setArguments(args);
                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.animator.slide_left_enter,
                                    R.animator.slide_right_enter, R.animator.slide_right_exit,
                                    R.animator.slide_left_exit)
                            .replace(R.id.myFrame,rdf)
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }
            });
        }
    }
}
