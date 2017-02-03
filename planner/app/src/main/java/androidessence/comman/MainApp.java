package androidessence.comman;

import android.app.Application;

/**
 * Created by shivakumara.manjappa on 2/2/2017.
 */

public class MainApp extends Application {
    int pos;

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
}
