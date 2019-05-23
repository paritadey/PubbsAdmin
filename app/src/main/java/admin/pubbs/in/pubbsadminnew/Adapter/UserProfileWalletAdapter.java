package admin.pubbs.in.pubbsadminnew.Adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.pubbs.in.pubbsadminnew.List.UserProfileWalletList;
import admin.pubbs.in.pubbsadminnew.R;
/*created by Parita Dey*/

public class UserProfileWalletAdapter extends RecyclerView.Adapter<UserProfileWalletAdapter.MyViewHolder> {
    private List<UserProfileWalletList> userProfileWalletLists;
    private String TAG = UserProfileWalletAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTime;
        public TextView amount, money, debit, credit;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            dateTime = view.findViewById(R.id.date_time);
            dateTime.setTypeface(type1);
            amount = view.findViewById(R.id.amount);
            amount.setTypeface(type2);
            money = view.findViewById(R.id.money);
            money.setTypeface(type1);
            debit = view.findViewById(R.id.debit);
            credit = view.findViewById(R.id.credit);
        }
    }

    public UserProfileWalletAdapter(List<UserProfileWalletList> userProfileWalletLists) {
        this.userProfileWalletLists = userProfileWalletLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_profile_wallet_list, parent, false);

        return new UserProfileWalletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileWalletAdapter.MyViewHolder holder, int position) {
        UserProfileWalletList walletList = userProfileWalletLists.get(position);
        holder.dateTime.setText(walletList.getDateTime());
        holder.debit.setText(walletList.getDebit());
        Log.d(TAG, "Debit:" + holder.debit.getText().toString());
        holder.credit.setText(walletList.getCredit());
        Log.d(TAG, "Credit:" + holder.credit.getText().toString());
        String money_debit = holder.debit.getText().toString();
        String money_credit = holder.credit.getText().toString();
        int mDebit = Integer.parseInt(money_debit);
        int mCredit = Integer.parseInt(money_credit);
        Log.d(TAG, "Debit, credit:" + mDebit + "-" + mCredit);
        int wallet_balnace = setWallet(mCredit, mDebit);
        holder.money.setText(String.valueOf(wallet_balnace));
        holder.amount.setText("Amount Credited");
        holder.money.setText(String.valueOf(wallet_balnace));
        holder.amount.setText("Amount Debited");
    }

    private int setWallet(int mCredit, int mDebit) {
        if (mCredit > mDebit) {
            int money = mCredit - mDebit;
            Log.d(TAG, "Amount Credited");
            return money;
        } else if (mCredit < mDebit) {
            int money = mDebit - mCredit;
            Log.d(TAG, "Amount Debited");
            return money;
        } else
            return 0;
    }

    @Override
    public int getItemCount() {
        return userProfileWalletLists.size();
    }


}
