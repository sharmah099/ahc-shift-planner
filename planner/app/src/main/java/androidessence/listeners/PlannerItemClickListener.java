package androidessence.listeners;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by ananthakrishnan.anna on 1/25/2017.
 */

public interface PlannerItemClickListener
{
    void onItemClicked(View view, ArrayList<String> patientInfoList);
}
