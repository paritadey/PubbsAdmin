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
public class BottomSheetAddNewStation extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private TextView descriptionHeader, description_, areaNow, description;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.bottom_sheet_area_description, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(),"fonts/AvenirLTStd-Book.otf");
        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Medium.otf");
        Typeface type3 = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");

        descriptionHeader = view.findViewById(R.id.description_header);
        descriptionHeader.setTypeface(type1);
        descriptionHeader.setText(R.string.add_station_);
        description_ = view.findViewById(R.id.description_);
        description_.setTypeface(type2);
        description_.setText(R.string.add_station_header);
        description = view.findViewById(R.id.description);
        description.setTypeface(type1);
        description.setText(R.string.add_station_help);
        areaNow = view.findViewById(R.id.area_now);
        areaNow.setTypeface(type1);
        areaNow.setText(R.string.select_station);
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
