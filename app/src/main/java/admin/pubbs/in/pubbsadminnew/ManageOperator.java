package admin.pubbs.in.pubbsadminnew;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageOperator extends AppCompatActivity implements View.OnClickListener {

    TextView manageOperatorTv;
    ImageView back;
    TextView addOperator, editOperator, deleteOperator;
    CardView addOperatorCard, editOperatorCard, deleteOperatorCard;
    private String TAG = ManageOperator.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_operator);
        initView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManageOperator.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void initView() {
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        addOperatorCard = findViewById(R.id.addOperator_card);
        editOperatorCard = findViewById(R.id.editOperator_card);
        deleteOperatorCard = findViewById(R.id.deleteOperator_card);
        manageOperatorTv = findViewById(R.id.manage_operator_tv);
        manageOperatorTv.setTypeface(type1);
        back = findViewById(R.id.back_button);
        addOperator = findViewById(R.id.add_operator);
        addOperator.setTypeface(type1);
        editOperator = findViewById(R.id.edit_operator);
        editOperator.setTypeface(type1);
        deleteOperator = findViewById(R.id.delete_operator);
        deleteOperator.setTypeface(type1);
        addOperatorCard.setOnClickListener(this);
        editOperatorCard.setOnClickListener(this);
        deleteOperatorCard.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.addOperator_card:
                startActivity(new Intent(ManageOperator.this, AddOperator.class));
                break;
            case R.id.editOperator_card:
                break;
            case R.id.deleteOperator_card:
                startActivity(new Intent(ManageOperator.this, DeleteOperatorArea.class));
                break;
            case R.id.back_button:
                Intent intent = new Intent(ManageOperator.this, AddNewBicycle.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
