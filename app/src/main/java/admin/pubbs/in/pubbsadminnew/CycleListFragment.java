package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class CycleListFragment extends Fragment implements AsyncResponse{
    RecyclerView rv;
    CycleListAdapter ca;
    ProgressDialog pd;
    List<Cycles> cycleList =new ArrayList<>();
    int stn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        stn=getArguments().getInt("id");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Cycles");
        View v=inflater.inflate(R.layout.fragment_stations_list,container,false);
        rv= (RecyclerView) v.findViewById(R.id.rv);
        RecyclerView.LayoutManager mylayout = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mylayout);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        ca=new CycleListAdapter(cycleList,getFragmentManager(),getActivity());
        rv.setAdapter(ca);
        pd.setMessage("Loading...");
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","getallcycles");
            jo.put("stn",stn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url),jo,CycleListFragment.this,getActivity()).executeJsonRequest();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cycle,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add){
            Bundle args=new Bundle();
            args.putInt("stn",stn);
            ScanQRFragment mf=new ScanQRFragment();
            mf.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.myFrame,mf)
                    .addToBackStack(null)
                    .commit();
        }
        if(item.getItemId()==android.R.id.home){
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        cycleList.clear();
        try {
            if (jsonObject.has("method")) {
                if (jsonObject.getString("method").equals("getallcycles")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Cycles c=new Cycles(jo.getString("id"),"",jo.getInt("battery"));
                        cycleList.add(c);
                    }
                }
            }
            ca.notifyDataSetChanged();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(),getResources().getString(R.string.server_error));
        pd.dismiss();
    }
}

