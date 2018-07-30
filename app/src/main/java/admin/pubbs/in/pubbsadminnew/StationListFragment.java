package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
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

public class StationListFragment extends Fragment implements SearchView.OnQueryTextListener,AsyncResponse{
    RecyclerView rv;
    StationListAdapter sla;
    ProgressDialog pd;
    List<Stations> stationsList =new ArrayList<>();
    int area;
    String title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        title=getArguments().getString("area");
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        area=getArguments().getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(title);
        View v=inflater.inflate(R.layout.fragment_stations_list,container,false);
        rv= (RecyclerView) v.findViewById(R.id.rv);
        RecyclerView.LayoutManager mylayout = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mylayout);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        sla=new StationListAdapter(stationsList,getFragmentManager(),getActivity());
        rv.setAdapter(sla);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.station,menu);
        // Associate searchable configuration with the SearchView
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                sla.setFilter(stationsList);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                sla.setFilter(stationsList);
                return true;
            }
        });
    }

    private List<Stations> filter(List<Stations> stations, String query) {
        query = query.toLowerCase();
        final List<Stations> filteredModelList = new ArrayList<>();
        for (Stations station : stations) {
            final String text = station.getStation_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(station);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add_station){
            Bundle args=new Bundle();
            args.putInt("area",area);
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
    public void onResume() {
        super.onResume();
        pd.setMessage("Loading...");
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","getallstations");
            jo.put("area",area);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url),jo,StationListFragment.this,getActivity()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        stationsList.clear();
        try {
            if (jsonObject.has("method")) {
                if (jsonObject.getString("method").equals("getallstations")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Stations stn = new Stations(jo.getString("name"), jo.getString("id"), jo.getString("area"), jo.getString("lat"), jo.getString("lon"), jo.getString("rp"),jo.getInt("virtual")==1?true:false,jo.getInt("available"));
                        stationsList.add(stn);
                    }
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        sla.setFilter(stationsList);
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(),getResources().getString(R.string.server_error));
        pd.dismiss();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Stations> filteredList = filter(stationsList, newText);
        sla.setFilter(filteredList);
        return false;
    }
}
