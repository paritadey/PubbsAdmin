package admin.pubbs.in.pubbsadminnew;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class BottomSheetAddBank extends BottomSheetDialogFragment {
    BottomSheetBehavior mBehavior;
    TextView add_bank_account_tv, description;
    TextInputLayout ifsc_layout, account_number_layout, confirm_account_number_layout, account_holder_layout, phone_number_layout;
    TextInputEditText ifsc, account_number, confirm_account_number, account_holder, phone_number;
    Button confirm;
    String user_ifsc, user_account_number, user_confirm_account_number, user_account_holder, user_phone_number, admin_mobile, admin_type, area_id, finalResult;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    String UserUrl = "http://pubbs.in/api/1.0/operator_bank_credentials.php";
    private String TAG = BottomSheetAddBank.class.getSimpleName();
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.bottom_sheet_add_bank, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        sharedPreferences = getContext().getSharedPreferences(getResources().getString(R.string.sharedPreferences), MODE_PRIVATE);
        admin_mobile = sharedPreferences.getString("admin_mobile", null);
        admin_type = sharedPreferences.getString("admin_type", "null");
        area_id = sharedPreferences.getString("area_id", null);
        Log.d(TAG, "Admin Details:" + admin_mobile + "-" + admin_type + "-" + area_id);
        add_bank_account_tv = view.findViewById(R.id.add_bank_account_tv);
        add_bank_account_tv.setTypeface(type1);
        description = view.findViewById(R.id.description);
        description.setTypeface(type1);
       /* ifsc_layout = view.findViewById(R.id.ifsc_layout);
        ifsc = view.findViewById(R.id.ifsc);
        ifsc.setTypeface(type1);
        ifsc.addTextChangedListener(new MyTextWatcher(ifsc));
        account_number_layout = view.findViewById(R.id.account_holder_layout);
        account_number = view.findViewById(R.id.account_number);
        account_number.setTypeface(type1);
        account_number.addTextChangedListener(new MyTextWatcher(account_number));
        confirm_account_number_layout = view.findViewById(R.id.confirm_account_number_layout);
        confirm_account_number = view.findViewById(R.id.confirm_account_number);
        confirm_account_number.setTypeface(type1);
        confirm_account_number.addTextChangedListener(new MyTextWatcher(confirm_account_number));
        account_holder_layout = view.findViewById(R.id.account_holder_layout);
        account_holder = view.findViewById(R.id.account_holder);
        account_holder.setTypeface(type1);
        account_holder.addTextChangedListener(new MyTextWatcher(account_holder));
        phone_number_layout = view.findViewById(R.id.phone_number_layout);
        phone_number = view.findViewById(R.id.phone_number);
        phone_number.setTypeface(type1);
        phone_number.addTextChangedListener(new MyTextWatcher(phone_number));
        confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ifsc.toString().isEmpty() && !account_number.toString().isEmpty() && !confirm_account_number.toString().isEmpty() &&
                        !account_holder.toString().isEmpty() && !phone_number.toString().isEmpty() &&
                        account_number.toString().equals(confirm_account_number.toString())) {
                    sendOperatorBankDetails(admin_mobile, area_id, admin_type, user_ifsc, user_account_number, user_account_holder,
                            user_phone_number);
                }else{
                    Log.d(TAG, "No Data entered");
                }
            }
        });
*/
        return dialog;
    }

    private void sendOperatorBankDetails(String admin_mobile, String area_id, String admin_type, String user_ifsc,
                                         String user_account_number, String user_account_holder, String user_phone_number) {
        class sendOperatorBankDetailsClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show
                        (getContext(), "Connecting to the server", "Adding Bank Details...",
                                true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Log.d(TAG, "Asynctask msg:" + httpResponseMsg.toString());
                Toast.makeText(getContext(), httpResponseMsg.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("admin_mobile", params[0]);
                hashMap.put("area_id", params[1]);
                hashMap.put("admin_type", params[2]);
                hashMap.put("user_ifsc", params[3]);
                hashMap.put("user_account_number", params[4]);
                hashMap.put("user_account_holder", params[5]);
                hashMap.put("user_phone_number", params[6]);

                finalResult = httpParse.postRequest(hashMap, UserUrl);

                return finalResult;
            }
        }

        sendOperatorBankDetailsClass operatorClass = new sendOperatorBankDetailsClass();

        operatorClass.execute(admin_mobile, area_id, admin_type, user_ifsc, user_account_number, user_account_holder,
                user_phone_number);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


  /*  private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.ifsc:
                    user_ifsc = editable.toString().trim();
                    Log.d(TAG, "IFSC:" + user_ifsc);
                    break;
                case R.id.account_number:
                    user_account_number = editable.toString().trim();
                    Log.d(TAG, "Account number:" + user_account_number);
                    break;
                case R.id.confirm_account_number:
                    user_confirm_account_number = editable.toString().trim();
                    Log.d(TAG, "Confirm account number:" + user_confirm_account_number);
                    break;
                case R.id.account_holder:
                    user_account_holder = editable.toString().trim();
                    Log.d(TAG, "Account holder name:" + user_account_holder);
                    break;
                case R.id.phone_number:
                    user_phone_number = editable.toString().trim();
                    Log.d(TAG, "User phone number:" + user_phone_number);
                    break;
            }
        }
    }*/
}
