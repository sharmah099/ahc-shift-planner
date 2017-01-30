package androidessence.comman;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sanjana.tailor on 1/30/2017.
 */

public class PreferenceClass
{
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences sharedpreferences;

    public PreferenceClass(Context context)
    {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Returns the shift time
     */
    public String getFinishTime()
    {
        return sharedpreferences.getString("finishTime", "");
    }

    /**
     * set the shift time
     */
    public void setFinishTime(String shiftDateTime)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("finishTime", shiftDateTime);
        editor.commit();
    }


    public void clearEndTime()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("finishTime");
        editor.commit();
    }

    /**
     * Returns the shift time
     */
    public String getNewFinishTime()
    {
        return sharedpreferences.getString("newfinishTime", "");
    }

    /**
     * set the shift time
     */
    public void setNewFinishTime(String shiftDateTime)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("newfinishTime", shiftDateTime);
        editor.commit();
    }


    public void clearNewFinsihTime()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("newfinishTime");
        editor.commit();
    }
}
