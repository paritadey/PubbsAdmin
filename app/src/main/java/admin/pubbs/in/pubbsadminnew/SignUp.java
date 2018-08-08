package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends Fragment {

    EditText fullname, phone, address, email, password, confirmPassword;
    Button getOtp;
    TextView fullnameTv, mobileTv,emailTv, addressTv,passwordTv,confirmPasswordTv;


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

        fullnameTv = rootView.findViewById(R.id.fullname_tv);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/AvenirLTStd-Book.otf");
        fullnameTv.setTypeface(type);
        fullname = rootView.findViewById(R.id.fullname);
        fullname.setTypeface(type);
        fullname.setLetterSpacing(0.1f);

        mobileTv = rootView.findViewById(R.id.mobile_tv);
        mobileTv.setTypeface(type);
        phone = rootView.findViewById(R.id.mobile);
        phone.setTypeface(type);
        phone.setLetterSpacing(0.1f);

        addressTv = rootView.findViewById(R.id.address_tv);
        addressTv.setTypeface(type);
        address = rootView.findViewById(R.id.address);
        address.setTypeface(type);
        address.setLetterSpacing(0.1f);

        emailTv = rootView.findViewById(R.id.email_tv);
        emailTv.setTypeface(type);
        email = rootView.findViewById(R.id.email);
        email.setTypeface(type);
        email.setLetterSpacing(0.1f);

        passwordTv = rootView.findViewById(R.id.password_tv);
        passwordTv.setTypeface(type);
        password = rootView.findViewById(R.id.password);
        password.setTypeface(type);
        password.setLetterSpacing(0.1f);

        confirmPasswordTv = rootView.findViewById(R.id.confirm_password_tv);
        confirmPasswordTv.setTypeface(type);
        confirmPassword = rootView.findViewById(R.id.confirm_password);
        confirmPassword.setTypeface(type);
        confirmPassword.setLetterSpacing(0.1f);

        getOtp = rootView.findViewById(R.id.otp_btn);
        getOtp.setTypeface(type);
        getOtp.setLetterSpacing(0.1f);
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
