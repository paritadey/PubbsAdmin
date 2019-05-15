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

import admin.pubbs.in.pubbsadminnew.List.SupportUserChatList;
import admin.pubbs.in.pubbsadminnew.R;
import admin.pubbs.in.pubbsadminnew.SupportChatReply;

public class SupportChatAdapter extends RecyclerView.Adapter<SupportChatAdapter.MyViewHolder> {
    private List<SupportUserChatList> supportUserChatLists;
    private String TAG = SupportChatAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout feedback_layout;
        public TextView sender, date_time_tv, date_time, message, email;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            sender = (TextView) view.findViewById(R.id.sender);
            sender.setTypeface(type2);
            date_time_tv = (TextView) view.findViewById(R.id.date_time_tv);
            date_time_tv.setTypeface(type1);
            date_time = (TextView) view.findViewById(R.id.date_time);
            date_time.setTypeface(type1);
            message = (TextView) view.findViewById(R.id.message);
            email = (TextView) view.findViewById(R.id.email);
            feedback_layout = view.findViewById(R.id.feedback_layout);
        }
    }

    public SupportChatAdapter(List<SupportUserChatList> supportUserChatLists) {
        this.supportUserChatLists = supportUserChatLists;
    }

    @Override
    public SupportChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.support_chat_list, parent, false);

        return new SupportChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SupportChatAdapter.MyViewHolder holder, int position) {
        SupportUserChatList supportUserChatList = supportUserChatLists.get(position);
        holder.sender.setText(supportUserChatList.getSender());
        holder.date_time.setText(supportUserChatList.getDate_time());
        holder.message.setText(supportUserChatList.getMessage());
        holder.email.setText(supportUserChatList.getEmail());
        String sender = supportUserChatList.getSender();
        String date_time = supportUserChatList.getDate_time();
        String user_message = supportUserChatList.getMessage();
        String user_email = supportUserChatList.getEmail();
        String area_id = supportUserChatList.getArea_id();

        holder.feedback_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "User message and email : " + user_message + "\t" + user_email + "\t" + area_id);
                Intent intent = new Intent(v.getContext(), SupportChatReply.class);
                intent.putExtra("sender", sender);
                intent.putExtra("date_time", date_time);
                intent.putExtra("user_message", user_message);
                intent.putExtra("user_email", user_email);
                intent.putExtra("area_id", area_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return supportUserChatLists.size();
    }
}

