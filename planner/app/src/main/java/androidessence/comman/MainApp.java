package androidessence.comman;

import android.app.Application;

import androidessence.listeners.AddToShiftListener;

/**
 * Created by shivakumara.manjappa on 2/2/2017.
 */

public class MainApp extends Application {
    int pos;

    AddToShiftListener addToShiftListener;

    public int getPos()
    {
        return pos;
    }

    public void setPos(int pos)
    {
        this.pos = pos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AddToShiftListener getAddToShiftListener()
    {
        return addToShiftListener;
    }

    public void setAddToShiftListener(AddToShiftListener addToShiftListener)
    {
        this.addToShiftListener = addToShiftListener;
    }
}
