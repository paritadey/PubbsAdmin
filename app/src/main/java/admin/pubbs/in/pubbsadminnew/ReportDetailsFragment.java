package admin.pubbs.in.pubbsadminnew;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by LORD on 4/9/2018.
 */

public class ReportDetailsFragment extends Fragment {
    TextView userid,msg,date_time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_report_details,container,false);
        userid= (TextView) v.findViewById(R.id.userid);
        msg= (TextView) v.findViewById(R.id.msg);
        date_time= (TextView) v.findViewById(R.id.date_time);
        msg.setMovementMethod(new ScrollingMovementMethod());
        userid.setText(getArguments().getString("userid"));
        msg.setText(getArguments().getString("message"));
        date_time.setText(getArguments().getString("date_time"));

        return v;
    }
}
