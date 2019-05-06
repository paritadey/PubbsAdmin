package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private List<BankList> bankLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bank_name, account_number;
        public TextView branch_name, ifsc;

        public MyViewHolder(View view) {
            super(view);
            Typeface type1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
            Typeface type2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

            bank_name = (TextView) view.findViewById(R.id.bank_name);
            bank_name.setTypeface(type1);
            branch_name = (TextView) view.findViewById(R.id.branch_name);
            branch_name.setTypeface(type1);
            ifsc = view.findViewById(R.id.ifsc);
            ifsc.setTypeface(type1);
            account_number = view.findViewById(R.id.account_number);
            account_number.setTypeface(type1);
        }
    }

    public PaymentAdapter(List<BankList> bankLists) {
        this.bankLists = bankLists;
    }

    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_list, parent, false);

        return new PaymentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentAdapter.MyViewHolder holder, int position) {
        BankList bankList = bankLists.get(position);
        holder.bank_name.setText(bankList.getBank_name());
        holder.branch_name.setText("Branch:"+ ""+bankList.getBranch_name());
        holder.ifsc.setText("IFSC:"+""+bankList.getIfsc());
        holder.ifsc.setText("A/C:"+""+bankList.getAccount_number());
    }

    @Override
    public int getItemCount() {
        return bankLists.size();
    }


}
