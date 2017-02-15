package androidessence.planner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidessence.comman.DataService;
import androidessence.comman.MainApp;

public class StartupActivity extends AppCompatActivity implements View.OnClickListener
{
    DataService service;
    TextView startTime, visitTime;
    ImageView left_icon;
    ImageView right_icon;
    static int count ;
    Button startShift;
    Toolbar toolbar;
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());

    MainApp mainApp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        //set the toolbar as actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_menu_white_24dp);

        startTime = (TextView) findViewById(R.id.tv_start_time);
        visitTime = (TextView) findViewById(R.id.tv_visit_time);
        left_icon = (ImageView) findViewById(R.id.iv_left);
        right_icon = (ImageView) findViewById(R.id.iv_right);
        startShift = (Button) findViewById(R.id.bt_start_shift);
        startShift.setOnClickListener(this);

        Calendar cal =  Calendar.getInstance();
        cal.set(Calendar.HOUR,8);
        count =  cal.get(Calendar.HOUR);
        startTime.setText(""+count);

        // set click for leftIcon
        left_icon.setOnClickListener(this);

        // set click for rightIcon
        right_icon.setOnClickListener(this);

        IntentFilter mTime = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeChangedReceiver, mTime);

        //Method used to display selected time.
        display(count);

    }
    private final BroadcastReceiver timeChangedReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(Intent.ACTION_TIME_TICK))
            {
                Log.i("test", "acttion_time_tick_called ");

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

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.iv_left:
                decrement();
                break;
            case R.id.iv_right:
                increment();
                break;
            case R.id.bt_start_shift:
                try {
                    service = DataService.getService();
                    service.parseJsonData();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Parsing failed", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("starttime1","");
                startActivity(intent);
                break;
        }
    }

    /**
     * Increment
     */
    private void increment()
    {
        count = count + 1;
        startTime.setText("" + count);
        display(count);
    }

    /**
     * Decrement
     */
    private void decrement()
    {
        count = count - 1;
        startTime.setText(""+count);
        display(count);
    }

    /**
     * This method displays the amount of experience on the screen.
     */
    private void display(int count)
    {
        String newTime = TIME_FORMAT.format(new Date());

        String[] parts = newTime.split(":");
        String hh = parts[0];
        String mm = parts[1];
        int i = Integer.valueOf(mm);
        if(i>=0 && i<=14)
        {
            String currentTime = hh.concat(":00");
            mainApp = (MainApp)getApplicationContext();
            mainApp.setCurrentTime(currentTime);

            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date timeFormat = null;
            s = s.concat(":00");
            try {
                timeFormat = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(timeFormat);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

                String finalTime = dateFormat.format(cal.getTime());

                mainApp.setFinalTime(finalTime);

                visitTime.setText("Visits shown will be from now ("+currentTime+") to " + finalTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else  if (i>=15 && i<=29) {
            String currentTime = hh.concat(":15");
            mainApp = (MainApp)getApplicationContext();
            mainApp.setCurrentTime(currentTime);
            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date timeFormat = null;
            s = s.concat(":15");
            try {
                timeFormat = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(timeFormat);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String finalTime = dateFormat.format(cal.getTime());

                mainApp.setFinalTime(finalTime);

                visitTime.setText("Visits shown will be from now ("+currentTime+") to " + finalTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else  if (i>=30 && i<=44) {
            String currentTime =  hh.concat(":30");
            mainApp = (MainApp)getApplicationContext();
            mainApp.setCurrentTime(currentTime);
            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date timeFormat = null;
            s = s.concat(":30");
            try {
                timeFormat = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(timeFormat);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
                String finalTime = dateFormat.format(cal.getTime());

                mainApp.setFinalTime(finalTime);

                visitTime.setText("Visits shown will be from now ("+finalTime+") to " +finalTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else  if (i>=45 && i<=59) {
            String currentTime = hh.concat(":45");
            mainApp = (MainApp)getApplicationContext();
            mainApp.setCurrentTime(currentTime);
            int val = Integer.valueOf(hh);
            val  = val + count;
            String s = String.valueOf(val);
            Date timeFormat = null;
            s = s.concat(":45");
            try {
                timeFormat = TIME_FORMAT.parse(s);
                Calendar cal = Calendar.getInstance();
                cal.setTime(timeFormat);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String finalTime = dateFormat.format(cal.getTime());

                mainApp.setFinalTime(finalTime);

                visitTime.setText("Visits shown will be from now ("+finalTime+") to " + finalTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
