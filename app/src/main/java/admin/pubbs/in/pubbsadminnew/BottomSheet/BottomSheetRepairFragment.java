package admin.pubbs.in.pubbsadminnew.BottomSheet;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import admin.pubbs.in.pubbsadminnew.R;
/*created by Parita Dey*/

//bottomsheet fragment, on clicking a button in any xml this fragment will be shown with its own xml designed
public class BottomSheetRepairFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private TextView descriptionHeader, description, areaNow;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.bottom_sheet_repair_fragment, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/AvenirLTStd-Book.otf");
        descriptionHeader = view.findViewById(R.id.description_header);
        descriptionHeader.setTypeface(type);

        description = view.findViewById(R.id.description);
        description.setTypeface(type);

        areaNow = view.findViewById(R.id.area_now);
        areaNow.setTypeface(type);

        ImageView showMap = (ImageView) view.findViewById(R.id.show_map);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


}
