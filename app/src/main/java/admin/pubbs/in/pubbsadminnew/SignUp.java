package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Fragment {

    EditText fullname, phone, address, email, password, confirmPassword;
    Button getOtp;

    public SignUp() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fullname = rootView.findViewById(R.id.fullname);
        phone = rootView.findViewById(R.id.mobile);
        address = rootView.findViewById(R.id.address);
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        confirmPassword = rootView.findViewById(R.id.confirm_password);
        getOtp = rootView.findViewById(R.id.otp_btn);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OtpActivity.class));

                /*fullname.setError(null);
                phone.setError(null);
                address.setError(null);
                email.setError(null);
                password.setError(null);
                confirmPassword.setError(null);
                if (fullname.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty()
                        || address.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty()
                        || password.getText().toString().trim().isEmpty() || confirmPassword.getText().toString().trim().isEmpty()) {
                    if (fullname.getText().toString().trim().isEmpty()) {
                        fullname.setError("Enter Full Name");
                    }
                    if (phone.getText().toString().trim().isEmpty()) {
                        phone.setError("Enter Phone Number");
                    }
                    if (address.getText().toString().trim().isEmpty()) {
                        address.setError("Enter Address");
                    }
                    if (email.getText().toString().trim().isEmpty()) {
                        email.setError("Enter Email Address");
                    }
                    if (password.getText().toString().trim().isEmpty()) {
                        password.setError("Enter Password");
                    }
                    if (confirmPassword.getText().toString().trim().isEmpty()) {
                        confirmPassword.setError("Enter Confirm Password");
                    }
                } else {
                    if(confirmPassword.getText().toString().trim().equals(password.getText().toString().trim())){
                        Toast.makeText(getContext(), "Confirm Password is matched", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), OtpActivity.class));
                    }
                }*/
            }
        });
        return rootView;
    }

}
