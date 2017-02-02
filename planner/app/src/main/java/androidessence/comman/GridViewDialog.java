package androidessence.comman;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidessence.adapters.GridViewAdapter;
import androidessence.planner.MainActivity;
import androidessence.planner.R;

public class GridViewDialog extends DialogFragment
{
    GridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.grid_view_dialog, container, false);
        android.widget.GridView gridView = (android.widget.GridView) rootView.findViewById(R.id.grid_view);

        adapter = new GridViewAdapter(getContext(),this,Utils.getTimeList());
        gridView.setAdapter(adapter);
        adapter.addToShiftJob(((MainActivity)getActivity()));

        return rootView;
    }

    public void dismissDialog()
    {
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("gridViewDialog");
        if (prev != null) {
            GridViewDialog df = (GridViewDialog) prev;
            df.dismiss();
        }
    }
}
