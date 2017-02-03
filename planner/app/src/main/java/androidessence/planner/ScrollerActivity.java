package androidessence.planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by priyank.pandya on 31-01-2017.
 */

public class ScrollerActivity extends Activity {

    RelativeLayout ll;
    TextView t;
    int prevY;
    String time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        t = (TextView) findViewById(R.id.textView2);
        ll = (RelativeLayout) findViewById(R.id.ll_parent);

        ll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                final FrameLayout.LayoutParams par = (FrameLayout.LayoutParams) v.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        int val = (int) v.getY();

                        if (v.getY() < 0) {
                            v.setY(0);
                        }

                        if (v.getY() > ((View) v.getParent().getParent()).getHeight() - v.getHeight()) {
                            v.setY(((View) v.getParent()).getHeight() - v.getHeight());
                        }
                        if (v.getY() >= 0
                                && v.getY() <= ((View) v.getParent().getParent()).getHeight() - v.getHeight()) {

                            int topMargin = par.topMargin + ((int) event.getRawY() - prevY);

                            par.topMargin = topMargin;
                            prevY = (int) event.getRawY();
                            v.setLayoutParams(par);


                            int totalHeight = ((View) v.getParent().getParent()).getHeight() - v.getHeight();
                            int minute = totalHeight / 48;
                            int value = val / minute;

                            if (val % minute == 0){
                                int timeSlot = (value * 15);
                                t.setText(getTime(timeSlot));

                            }
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        if (v.getY() >= 0
                                && v.getY() <= ((View) v.getParent().getParent()).getHeight() - v.getHeight()) {
                            par.topMargin += (int) event.getRawY() - prevY;
                            Intent intent = new Intent();
                            intent.putExtra("SCROLLER_TIME", time);
                            setResult(1000, intent);
                            finish();
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        if (v.getY() >= 0
                                && v.getY() <= ((View) v.getParent().getParent()).getHeight() - v.getHeight()) {
                            prevY = (int) event.getRawY();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public String getTime(int min) {
        double hoursAndMinutes = (min / 60) + (double) min % 60 / 100;
        String hoursAndMinutesStr = new DecimalFormat("00.00").format(hoursAndMinutes + 9).replace('.', ':');

        //Log.i("pandya", " " + hoursAndMinutesStr);
        return hoursAndMinutesStr;
    }
}
