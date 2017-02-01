package androidessence.comman;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidessence.adapters.GridViewAdapter;
import androidessence.planner.R;

public class GridViewDialog extends DialogFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.grid_view_dialog, container, false);
        android.widget.GridView gridView = (android.widget.GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(new GridViewAdapter(getContext(), Utils.getTimeList()));

        return rootView;
    }
}
