package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 1/27/2017.
 */
public class RateAdapter extends RecyclerView.Adapter<RateAdapter.MyViewHolder> {
   List<Rates> rates =new ArrayList<>();
    Context context;
    int pos;
    FragmentManager fragmentManager;
    ProgressDialog pd;


    public RateAdapter(List<Rates> rates, FragmentManager fragmentManager, Context context) {
        this.rates = rates;
        this.context = context;
        this.fragmentManager=fragmentManager;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rate_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rates rate= rates.get(position);
        holder.duration.setText(rate.getDuration()+" Hours");
        holder.rate_text.setText(context.getResources().getString(R.string.rupee_symbol)+rate.getRate());
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements AsyncResponse {
        TextView duration,rate_text;
        ImageView delete;
        public MyViewHolder(final View itemView) {
            super(itemView);
            duration= (TextView) itemView.findViewById(R.id.duration);
            rate_text= (TextView) itemView.findViewById(R.id.rate);
            delete= (ImageView) itemView.findViewById(R.id.delete_rate);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCnfirmMsg(rates.get(getAdapterPosition()).getId());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    Bundle args=new Bundle();
                    args.putString("duration",rates.get(pos).getDuration());
                    args.putString("rate",rates.get(pos).getRate());
                    args.putInt("id",rates.get(pos).getId());
                    RateDetailsFragment rdf=new RateDetailsFragment();
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

        public void deleteCnfirmMsg(final int id){
            new AlertDialog.Builder(context)
                    .setTitle(context.getResources().getString(R.string.app_name))
                    .setMessage("Sure to delete this rate?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            pd=new ProgressDialog(context);
                            pd.setCancelable(false);
                            pd.setMessage("Please wait...");
                            pd.show();
                            JSONObject jsonObject =new JSONObject();
                            try {
                                jsonObject.put("method","deleterate");
                                jsonObject.put("id",id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            new SendRequest(context.getResources().getString(R.string.url),
                                    jsonObject,MyViewHolder.this,context
                                    ).executeJsonRequest();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            pd.dismiss();
            if (jsonObject.has("method")){
                try {
                    if (jsonObject.getString("method").equals("deleterate") && jsonObject.getBoolean("success")){
                        Toast.makeText(context, "Rate removed successfully", Toast.LENGTH_SHORT).show();
                        rates.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onResponseError(VolleyError error) {
            pd.dismiss();
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
