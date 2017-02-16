package androidessence.comman;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Created by himanshu.sharma on 15-02-2017.
 */

public class CustomNumberPicker extends NumberPicker
{
    private Context context;
    public CustomNumberPicker(Context context)
    {
        super(context);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {

        super(context, attrs);
    }
    public void showPicker(String [] values, int max, int min)
    {
        this.setDisplayedValues(values);
        this.setMaxValue(max);
        this.setMinValue(min);
    }
}
