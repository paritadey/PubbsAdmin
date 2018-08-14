package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<UserList> userlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, userphone, userid ;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            username = (TextView) view.findViewById(R.id.user_name);
            username.setTypeface(type1);
            userid = (TextView) view.findViewById(R.id.user_id);
            userid.setTypeface(type1);
            userphone = (TextView)view.findViewById(R.id.user_phone);
            userphone.setTypeface(type2);
        }
    }
    public UserAdapter(List<UserList> userlist) {
        this.userlist = userlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserList user = userlist.get(position);
        holder.username.setText(user.getUserName());
        holder.userid.setText(user.getUserId());
        holder.userphone.setText(user.getUserPhone());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


}
