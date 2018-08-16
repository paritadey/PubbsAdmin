package admin.pubbs.in.pubbsadminnew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserProfileWallet extends Fragment {

    private String TAG = UserProfile.class.getSimpleName();
    String userName, userPhone, userId;

    public UserProfileWallet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_profile_wallet, container, false);
        return rootView;

    }

}
