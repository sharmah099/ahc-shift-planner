package androidessence.comman;

import android.app.Application;

import androidessence.listeners.AddToShiftListener;

/**
 * Created by shivakumara.manjappa on 2/2/2017.
 */

public class MainApp extends Application
{
    int pos;

    AddToShiftListener addToShiftListener;

    String currentTime;

    String finalTime;

    int currentHour;

    public int getPos()
    {
        return pos;
    }

    public void setPos(int pos)
    {
        this.pos = pos;
    }

    @Override
    public void onCreate()
    {
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

    public String getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(String currentTime)
    {
        this.currentTime = currentTime;
    }

    public String getFinalTime()
    {
        return finalTime;
    }

    public void setFinalTime(String finalTime)
    {
        this.finalTime = finalTime;
    }


    public int getCurrentHour()
    {
        return currentHour;
    }

    public void setCurrentHour(int currentHour)
    {
        this.currentHour = currentHour;
    }
}
