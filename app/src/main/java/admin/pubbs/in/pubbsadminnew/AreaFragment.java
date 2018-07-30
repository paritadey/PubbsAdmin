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

public class AreaFragment extends Fragment  implements SearchView.OnQueryTextListener,AsyncResponse {
    RecyclerView rv;
    AreaAdapter aa;
    ProgressDialog pd;
    List<AreaPojo> alist =new ArrayList<>();
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        final List<AreaPojo> filteredList = filter(alist, s);
        aa.setFilter(filteredList);
        return false;
    }

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
        getActivity().setTitle("Area");
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
        aa=new AreaAdapter(alist,getFragmentManager(),getActivity());
        rv.setAdapter(aa);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.area,menu);
        // Associate searchable configuration with the SearchView
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                aa.setFilter(alist);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                aa.setFilter(alist);
                return true;
            }
        });
    }

    private List<AreaPojo> filter(List<AreaPojo> areas, String query) {
        query = query.toLowerCase();
        final List<AreaPojo> filteredModelList = new ArrayList<>();
        for (AreaPojo area : areas) {
            final String text = area.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(area);
            }
        }
        return filteredModelList;
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
            jo.put("method","getallarea");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url),jo,AreaFragment.this,getActivity()).executeJsonRequest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add_area){
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.myFrame,new MapFragment())
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
        alist.clear();
        if (jsonObject.has("method")) {
            try {
                if (jsonObject.getString("method").equals("getallarea") && jsonObject.getBoolean("success")) {
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            AreaPojo cm = new AreaPojo(jo.getInt("id"),jo.getString("name"),jo.getString("lat"),jo.getString("lon"));
                            alist.add(cm);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        aa.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(VolleyError error) {
        AppConfig.alertMsg(getActivity(),getResources().getString(R.string.server_error));
        pd.dismiss();
    }
}
