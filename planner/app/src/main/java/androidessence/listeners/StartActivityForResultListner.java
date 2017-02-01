package androidessence.listeners;

/**
 * Created by himanshu.sharma on 19-01-2017.
 */

public interface StartActivityForResultListner {
    void onStartAct(String time, String name,  int pos, boolean fromIncomplete, String etaType);
    void onShowDilaogAddShift(int pos);
}
