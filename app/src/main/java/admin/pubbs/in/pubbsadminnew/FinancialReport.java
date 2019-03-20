package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FinancialReport extends AppCompatActivity implements View.OnClickListener {
    TextView financial_report_tv;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_report);
        initView();

    }

    private void initView() {
        //initializing the typeface/fonts for this particular screen
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        financial_report_tv = findViewById(R.id.financial_report_tv);
        financial_report_tv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        ProgressBar progress = (ProgressBar) findViewById(R.id.circle_progress_bar);
        progress.setProgress(33);

    }

    //on back button press move back to ManageOperator class from Financial class
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FinancialReport.this, ManageOperator.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                //on back button imageview press move back to ManageOperator class from Financial class
                Intent intent = new Intent(FinancialReport.this, ManageOperator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
