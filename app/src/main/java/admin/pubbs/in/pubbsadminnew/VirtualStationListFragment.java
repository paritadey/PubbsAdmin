package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class VirtualStationListFragment extends Fragment implements AsyncResponse{
    RecyclerView rv;
    VirtualStationListAdapter vsla;
    ProgressDialog pd;
    List<VirtualStation> stationsList =new ArrayList<>();
    int main_stn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getArguments().getString("name"));
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        main_stn=getArguments().getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_virtual_stations_list,container,false);
        rv= (RecyclerView) v.findViewById(R.id.rv);
        RecyclerView.LayoutManager mylayout = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mylayout);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        vsla=new VirtualStationListAdapter(stationsList,getFragmentManager(),getActivity());
        rv.setAdapter(vsla);
        pd.setMessage("Loading...");
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","getallvirtualstations");
            jo.put("main_stn",main_stn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url),jo,VirtualStationListFragment.this,getActivity()).executeJsonRequest();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.station,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add_station){
            Bundle args=new Bundle();
            args.putInt("main_stn",main_stn);
            args.putDouble("lat",getArguments().getDouble("lat"));
            args.putDouble("lng",getArguments().getDouble("lng"));
            MapFragment mf=new MapFragment();
            mf.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.myFrame,mf)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        stationsList.clear();
        try {
            if (jsonObject.has("method")) {
                if (jsonObject.getString("method").equals("getallvirtualstations")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        VirtualStation stn = new VirtualStation(jo.getString("name"), jo.getString("id"), jo.getString("lat"), jo.getString("lon"));
                        stationsList.add(stn);
                    }
                }
            }
            vsla.notifyDataSetChanged();
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
