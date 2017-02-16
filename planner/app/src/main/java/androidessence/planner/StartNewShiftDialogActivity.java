package androidessence.planner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidessence.comman.MainApp;
import androidessence.comman.MorphDialogToView;
import androidessence.comman.MorphViewToDialog;

public class StartNewShiftDialogActivity extends AppCompatActivity
{
    private ViewGroup container;

    static int count ;
    ImageView btnLeft, btnRight;
    Button btnStartNewShift;
    TextView tvFinishTime,tvStartTime, tvHrs;
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final DateFormat DAY_FORMAT = new SimpleDateFormat("E", Locale.getDefault());
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("EEE MM dd HH:mm yy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_shift_dialog);
        container = (ViewGroup) findViewById(R.id.container);
        setupSharedEelementTransitions();
        tvStartTime = (TextView) findViewById(R.id.tv_start_new_shift_time);
        tvFinishTime = (TextView) findViewById(R.id.tv_finish_time_new_shift);
        tvHrs  = (TextView) findViewById(R.id.tv_hours_new_shift);
        btnLeft = (ImageView) findViewById(R.id.iv_left_new_shift);
        btnRight = (ImageView)findViewById(R.id.iv_right_new_shift);
        btnStartNewShift = (Button)findViewById(R.id.btn_start_new_shift);
        MainApp app = (MainApp)this.getApplicationContext();
        Calendar cal =  Calendar.getInstance();
        cal.set(Calendar.HOUR, app.getCurrentHour());
        count =  cal.get(Calendar.HOUR);
        tvHrs.setText(" "+ app.getCurrentHour());
        display(app.getCurrentHour());
        IntentFilter mTime = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeChangedReceiver, mTime);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });
        btnStartNewShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setupSharedEelementTransitions()
    {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphViewToDialog sharedEnter = new MorphViewToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToView sharedReturn = new MorphDialogToView();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        container.findViewById(R.id.btn_start_new_shift).setOnClickListener(dismissListener);
    }

    public void dismiss()
    {
        Intent intent =  new Intent();
        intent.putExtra("finishTime","AC");
        setResult(Activity.RESULT_CANCELED, intent);
        finishAfterTransition();
    }

    /**
     * Decrement
     */
    private void decrement()
    {
        MainApp app = (MainApp)this.getApplicationContext();
        int hr = app.getCurrentHour();
        count = hr - 1;
        tvHrs.setText(" " + count);
        app.setCurrentHour(count);
        if (count == 0) {
            btnLeft.setEnabled(false);
            btnLeft.setImageResource(R.mipmap.ic_left_disabled);
            display(count);
        } else {
            btnRight.setEnabled(true);
            btnRight.setImageResource(R.mipmap.ic_right);
            display(count);
        }
    }


    /**
     * Increment
     */
    private void increment()
    {
        MainApp app = (MainApp)this.getApplicationContext();
        int hr = app.getCurrentHour();
        count = hr + 1;
        tvHrs.setText(" " + count);
        app.setCurrentHour(count);
        if (count == 48) {
            btnRight.setEnabled(false);
            btnRight.setImageResource(R.mipmap.ic_right_disabled);
            display(count);
        }
        else {
            btnLeft.setImageResource(R.mipmap.ic_left);
            btnLeft.setEnabled(true);
            display(count);
        }
    }

    /* *
    * This method is used to round off current time
    * e.g. 09:10 = 09:00
    *      09:19 = 09:15
    *      09:28 = 09:30
    *      09:42 = 09:45
    *      10:00 = 10 :000*/
    private void display(int count)
    {
        if(count == 48 ){
            btnRight.setEnabled(false);
            btnRight.setImageResource(R.mipmap.ic_right_disabled);
        } else {
            btnRight.setEnabled(true);
            btnRight.setImageResource(R.mipmap.ic_right);
        }

        if(count == 0 ) {
            btnLeft.setEnabled(false);
            btnLeft.setImageResource(R.mipmap.ic_left_disabled);
        } else {
            btnLeft.setEnabled(true);
            btnLeft.setImageResource(R.mipmap.ic_left);
        }
        String today = "";
        String calDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd HH:mm yy");
        MainApp app = (MainApp)this.getApplicationContext();
        app.setCurrentHour(count);
        try {
            String todayT = DATE_TIME_FORMAT.format(new Date());
            Date start = sdf.parse(todayT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.HOUR, count);
            Date end = cal.getTime();
            today = DAY_FORMAT.format(end);
            calDate = DATE_FORMAT.format(end);
            String todaysDate = DATE_FORMAT.format(new Date());
            if(todaysDate.equals(calDate))
            {
                today = "today";
            } else {
                today = today;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        String time = TIME_FORMAT.format(new Date());
        String[] parts = time.split(":");
        String hh = parts[0];
        String mm = parts[1];
        int i = Integer.valueOf(mm);
        if(i>=0 && i<=14)
        {
            String currentTime = hh.concat(":00");
            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date time1 = null;
            s = s.concat(":00");
            try {
                time1 = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                tvStartTime.setText("(" +currentTime + ")");
                tvFinishTime.setText(dateFormat.format(cal.getTime()) + " " + today);
                String splitTime[] = (dateFormat.format(cal.getTime()).split(":"));
                String hours = splitTime[0];
                if(hours.equals("00")) {
                    app.setFinalTime(dateFormat.format(cal.getTime()));
                } else {
                    app.setFinalTime(hours + ":" + "00");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else  if (i>=15 && i<=29)
        {
            String currentTime = hh.concat(":15");
            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date time1 = null;
            s = s.concat(":15");
            try {
                time1 = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                tvStartTime.setText("(" +currentTime + ")");
                tvFinishTime.setText(dateFormat.format(cal.getTime()) + " " + today);
                String splitTime[] = (dateFormat.format(cal.getTime()).split(":"));
                String hours = splitTime[0];
                if(hours.equals("00")) {
                    app.setFinalTime(dateFormat.format(cal.getTime()));
                } else {
                    app.setFinalTime(hours + ":" + "00");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else  if (i>=30 && i<=44) {
            String currentTime =  hh.concat(":30");

            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date time1 = null;
            s = s.concat(":30");
            try {
                time1 = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                tvStartTime.setText("(" +currentTime + ")");
                tvFinishTime.setText(dateFormat.format(cal.getTime()) + " " + today);
                String splitTime[] = (dateFormat.format(cal.getTime()).split(":"));
                String hours = splitTime[0];
                if(hours.equals("00")) {
                    app.setFinalTime(dateFormat.format(cal.getTime()));
                } else {
                    app.setFinalTime(hours + ":" + "00");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else  if (i>=45 && i<=59) {
            String currentTime = hh.concat(":45");
            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date time1 = null;
            s = s.concat(":45");
            try {
                time1 = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                tvStartTime.setText("(" + currentTime + ")");
                tvFinishTime.setText(dateFormat.format(cal.getTime()) + " " + today);
                String splitTime[] = (dateFormat.format(cal.getTime()).split(":"));
                String hours = splitTime[0];
                if(hours.equals("00")) {
                    app.setFinalTime(dateFormat.format(cal.getTime()));
                } else {
                    app.setFinalTime(hours + ":" + "00");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private final BroadcastReceiver timeChangedReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(Intent.ACTION_TIME_TICK))
            {
                Log.i("test", "action_time_tick_called ");

                //Method used to display selected time.
                display(count);
            }
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(timeChangedReceiver);
    }

}
