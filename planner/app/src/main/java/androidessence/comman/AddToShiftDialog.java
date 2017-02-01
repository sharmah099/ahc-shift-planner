package androidessence.comman;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidessence.listeners.AddToShiftListener;
import androidessence.planner.R;


public class AddToShiftDialog extends DialogFragment implements View.OnClickListener {

    Context context;
    TextView tvCancel;
    Button addToshift;
    AddToShiftListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.add_to_shift_dialog, container, false);
        tvCancel = (TextView)rootView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);

        addToshift = (Button)rootView.findViewById(R.id.btn_add_shift);
        addToshift.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_cancel:
               dismissDialog();
                listener.onAddShiftCanclled();
                break;
            case R.id.btn_add_shift:
                dismissDialog();
                listener.addToShift();
                break;

        }
    }

    public void dismissDialog(){
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            AddToShiftDialog df = (AddToShiftDialog) prev;
            df.dismiss();
        }
    }

    public void setListener(AddToShiftListener listener) {
        this.listener = listener;
    }
}
