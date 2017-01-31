package androidessence.comman;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class JobItemPriorityCustomView extends TextView
{
    /**
     * Constructor
     * @param context		The context
     * @param attrs			The attributes
     */
    public JobItemPriorityCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * Constructor
     * @param context		The context
     * @param color			The background colour
     * @param label			The label that is to be overlaid (as priority)
     */
    public JobItemPriorityCustomView(Context context, int color, String label)
    {
        super(context);
        getPriorityBitmap(color, label);
    }

    /**
     * Function called to set the icon
     */
    public void getPriorityBitmap(int priorityColour, String prioritySymbol)
    {
        LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        setLayoutParams(param);
        setText(prioritySymbol);
        setTextSize(20);
        if (priorityColour != -1) {
            //set text colour to white if the background is NOT white
            setTextColor(getResources().getColor(android.R.color.white));
        }
        setBackgroundColor(priorityColour);
        setGravity(Gravity.CENTER);
    }
}