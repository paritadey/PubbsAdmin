package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AllFeedback extends RecyclerView.Adapter<AllFeedback.MyViewHolder> {
    private List<Feedbacklist> feedbacklists;
    private String TAG = AdminAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adminmobile, admin_type;
        public TextView admin_type_tv;
        public TextView subject,message,date_time, email;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            adminmobile = (TextView)view.findViewById(R.id.adminmobile);
            adminmobile.setTypeface(type2);
            admin_type_tv = (TextView)view.findViewById(R.id.admin_type_tv);
            admin_type_tv.setTypeface(type1);
            admin_type = (TextView)view.findViewById(R.id.admin_type);
            admin_type.setTypeface(type1);
            subject = (TextView) view.findViewById(R.id.subject);
            message = (TextView)view.findViewById(R.id.message);
            date_time = (TextView)view.findViewById(R.id.date_time);
            email = (TextView)view.findViewById(R.id.email);
        }
    }

    public AllFeedback(List<Feedbacklist> feedbacklists) {
        this.feedbacklists = feedbacklists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_list, parent, false);

        return new AllFeedback.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllFeedback.MyViewHolder holder, int position) {
        Feedbacklist feedbacklist = feedbacklists.get(position);
        holder.adminmobile.setText(feedbacklist.getAdminmobile());
        holder.admin_type.setText(feedbacklist.getAdmin_type());
        holder.subject.setText(feedbacklist.getSubject());
        holder.message.setText(feedbacklist.getMessage());
        holder.date_time.setText(feedbacklist.getDate_time());
        holder.email.setText(feedbacklist.getEmail());
    }

    @Override
    public int getItemCount() {
        return feedbacklists.size();
    }

}
