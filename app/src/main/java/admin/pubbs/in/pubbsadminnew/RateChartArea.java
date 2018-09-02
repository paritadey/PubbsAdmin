package admin.pubbs.in.pubbsadminnew;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/*created by Parita Dey*/

public class RateChartArea extends Fragment {
    private String TAG = RateChartArea.class.getSimpleName();
    public ArrayList<LatLng> markerList = new ArrayList<LatLng>();
    String areaNumber, area_Name, adminMobile;
    public RateChartArea() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rate_chart_area, container, false);
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        Bundle args = getArguments();
        markerList = args.getParcelableArrayList("markerList");
        areaNumber = args.getString("areaNumber");
        area_Name = args.getString("area_Name");
        adminMobile = args.getString("adminMobile");
        Log.d(TAG, "Area Details:"+markerList+"\t"+areaNumber+"\t"+area_Name+"\t"+adminMobile);

        return rootView;
    }


}
