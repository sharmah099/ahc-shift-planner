package androidessence.listeners;

import android.view.View;

/**
 * Created by himanshu.sharma on 19-01-2017.
 */

public interface StartActivityForResultListner
{
    void onStartAct(String time, String name,  int pos, boolean fromIncomplete, String etaType,View view);
    void onShowDialogAddShift(View view, int position);
}
