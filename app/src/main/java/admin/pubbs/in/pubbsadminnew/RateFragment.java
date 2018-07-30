package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 8/23/2017.
 */

public class RateFragment extends Fragment implements AsyncResponse{
    RecyclerView rv;
    RateAdapter rateAdapter;
    ProgressDialog pd;
    List<Rates> rates =new ArrayList<>();
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";


    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    FragmentManager fragmentManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Rates");
        fragmentManager=getFragmentManager();
        View v=inflater.inflate(R.layout.fragment_area,container,false);
        rv= (RecyclerView) v.findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rateAdapter=new RateAdapter(rates,getFragmentManager(),getActivity());
        rv.setAdapter(rateAdapter);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        pd.setMessage("Loading...");
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","getallrates");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url),jo,RateFragment.this,getActivity()).executeJsonRequest();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.rate,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add){
            getFragmentManager()
            .beginTransaction()
                    .setCustomAnimations(R.animator.slide_left_enter,
                            R.animator.slide_right_enter, R.animator.slide_right_exit,
                            R.animator.slide_left_exit)
                    .replace(R.id.myFrame,new RateDetailsFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
        if(item.getItemId()==android.R.id.home){
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        rates.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getallrates") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            Rates r=new Rates(
                                    jo.getInt("id"),
                                    jo.getString("duration"),
                                    jo.getString("rate")
                            );
                            rates.add(r);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        rateAdapter.notifyDataSetChanged();
        AppConfig.setRates(rates);
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(),getResources().getString(R.string.server_error));
        pd.dismiss();
    }
}
