package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 4/9/2018.
 */

public class ReportFragment extends Fragment implements AsyncResponse{
    RecyclerView rv;
    ReportAdapter ra;
    List<Reports> reportsList=new ArrayList<>();
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";



    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;

    ProgressDialog pd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("Loading reports...");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Reports");
        View v=inflater.inflate(R.layout.fragment_reports,container,false);
        rv= (RecyclerView) v.findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        ra=new ReportAdapter(reportsList,getFragmentManager());
        rv.setAdapter(ra);
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
        pd.show();
        JSONObject jo=new JSONObject();
        try {
            jo.put("method","getreports");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendRequest(getResources().getString(R.string.url),jo,ReportFragment.this,getActivity()).executeJsonRequest();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        pd.dismiss();
        if (jsonObject.has("method")){
            reportsList.clear();
            try {
                if (jsonObject.getString("method").equals("getreports") && jsonObject.getBoolean("success")){
                    JSONArray ja=jsonObject.getJSONArray("data");
                    for (int i=0;i<ja.length();i++){
                        JSONObject jo=ja.getJSONObject(i);
                        Reports r=new Reports(jo.getString("userid"),jo.getString("message"),jo.getString("date_time"));
                        reportsList.add(r);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ra.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseError(VolleyError error) {
        pd.dismiss();
    }

}
