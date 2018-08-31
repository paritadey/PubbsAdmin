package admin.pubbs.in.pubbsadminnew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*created by Parita Dey*/

public class UserProfilePlans extends Fragment {
    private String TAG = UserProfilePlans.class.getSimpleName();
    String userName, userPhone, userId;

    public UserProfilePlans() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        View rootView= inflater.inflate(R.layout.fragment_user_profile_plans, container, false);
        userPhone = args.getString("userPhone");
        Log.d(TAG, "User phone: "+userPhone);

        return rootView;
    }

}
