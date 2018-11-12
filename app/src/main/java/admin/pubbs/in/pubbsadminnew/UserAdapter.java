package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/*created by Parita Dey*/

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<UserList> userlist;

   public void filterList(List<UserList> userList) {
       this.userlist = userList;
       notifyDataSetChanged();
   }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, userphone, userid, text_required, userIdTv ;
        public ConstraintLayout userListLayout;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            username = (TextView) view.findViewById(R.id.user_name);
            username.setTypeface(type1);
            userid = (TextView) view.findViewById(R.id.user_id);
            userid.setTypeface(type1);
            userphone = (TextView)view.findViewById(R.id.user_phone);
            userphone.setTypeface(type1);
            text_required = (TextView)view.findViewById(R.id.text_required);
            text_required.setTypeface(type1);
            userListLayout = view.findViewById(R.id.user_list_layout);
            userIdTv = (TextView)view.findViewById(R.id.user_id_tv);
            userIdTv.setTypeface(type1);
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
        holder.text_required.setText(user.getUserImei());
        String userName = user.getUserName();//holder.username.getText().toString();
        String userPhone = user.getUserPhone();//holder.userphone.getText().toString();
        String userId = user.getUserId();//holder.userid.getText().toString();
        String userIMEI = user.getUserImei();
        holder.userListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user_profile = new Intent(v.getContext(), UserProfile.class);
                user_profile.putExtra("username", userName);
                user_profile.putExtra("userid", userId);
                user_profile.putExtra("userphone", userPhone);
                user_profile.putExtra("userimei", userIMEI);
                v.getContext().startActivity(user_profile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


}
