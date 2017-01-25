package androidessence.planner;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidessence.comman.InfoDialog;
import androidessence.pojo.ShiftItems;

public class CalenerActivity extends AppCompatActivity {

    TextView time, name;
    DatePicker dtPicker;
    TimePicker tmPicker;
    Button btnOk;

    Calendar cal = Calendar.getInstance();
    Calendar dateTime = Calendar.getInstance();
    Date date;

    ArrayList<Date> allTime = null;

    InfoDialog newFragment = null;
    boolean isFromIncomp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calener);


        allTime = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        String ti = b.getString("TIME");
        String na = b.getString("NAME");
        isFromIncomp = b.getBoolean("FROM_INCOMP", false);
        ArrayList<ShiftItems> listData = b.getParcelableArrayList("ALL_TIME");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String d = "2017-01-18";
        String combinedDate = d +" "+ ti;

        for (int i = 0; i< listData.size(); i++) {
            String time = listData.get(i).getTime();
            String combindDate = d +" "+ time;
            try {
                Date date = dateFormat.parse(combindDate);
                allTime.add(date);
            }
            catch (Exception e) {

            }

        }

        try {
            date = dateFormat.parse(combinedDate);
            cal.setTime(date);
            cal.add(Calendar.HOUR, 5);

        }
        catch (Exception e) {

        }

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setEnabled(false);

        time = (TextView) findViewById(R.id.tv_time);
        time.setText(ti);

        name = (TextView)findViewById(R.id.tv_name);
        name.setText(na);

        dtPicker = (DatePicker) findViewById(R.id.dt_picker);
        dtPicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                getDate();

            }
        });
        tmPicker = (TimePicker)findViewById(R.id.tm_picker);
        tmPicker.setCurrentHour(date.getHours());
        tmPicker.setCurrentMinute(date.getMinutes());
        //tmPicker.setHour(date.getHours());
        //tmPicker.setMinute(date.getMinutes());
        tmPicker.setIs24HourView(true);
        tmPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                getDate();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = getDate();
                int hr = date.getHours();
                int min = date.getMinutes();

                String hour = String.valueOf(hr);
                String minuts = String.valueOf(min);
                if (hour.length() == 1) {
                    hour = "0"+hour;
                }

                if (minuts.length() == 1) {
                    minuts = "0"+minuts;
                }

                String finalString = hour+":"+minuts;

                Intent intent = new Intent();
                intent.putExtra("REVERT_NAME", name.getText().toString());
                intent.putExtra("NEWTIME", finalString);
                intent.putExtra("FROM_INCOMP",isFromIncomp);
                setResult(1000, intent);
                finish();

            }
        });

    }

    public Date getDate()
    {
        dateTime.set(Calendar.YEAR, dtPicker.getYear());
        dateTime.set(Calendar.MONTH, dtPicker.getMonth());
        dateTime.set(Calendar.DATE, dtPicker.getDayOfMonth());
        dateTime.set(Calendar.HOUR_OF_DAY, tmPicker.getCurrentHour()); //Hour of day takes care of AM/PM
        dateTime.set(Calendar.MINUTE, tmPicker.getCurrentMinute());
        dateTime.set(Calendar.SECOND, 00);
        dateTime.set(Calendar.MILLISECOND, 00);

        Date validateTime = dateTime.getTime();
        Date currentTime = cal.getTime();

        if (currentTime.before(validateTime)) {
            btnOk.setEnabled(false);
        }
        else {
            if (allTime.contains(validateTime)) {
                showDialogs();
                btnOk.setEnabled(false);
            } else {
                dismissDialog();
                btnOk.setEnabled(true);
            }
        }
        return dateTime.getTime();

    }

    void dismissDialog()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            newFragment.dismiss();
            ft.remove(prev);
        }
    }

    void showDialogs()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");

        if (prev == null) {
            // Create and show the dialog.
            newFragment = InfoDialog.newInstance();
            newFragment.show(ft, "dialog");
        }
    }
}
