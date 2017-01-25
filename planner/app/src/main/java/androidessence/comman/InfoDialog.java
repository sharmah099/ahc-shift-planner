package androidessence.comman;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidessence.planner.R;

/**
 * Created by himanshu.sharma on 30-12-2016.
 */

public class InfoDialog extends DialogFragment
{

    public static InfoDialog newInstance()
    {
        return new InfoDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container, false);
        Button button = (Button)v.findViewById(R.id.dialog_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
        return v;
    }
}
