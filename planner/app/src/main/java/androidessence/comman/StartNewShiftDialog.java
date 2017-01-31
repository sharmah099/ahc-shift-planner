package androidessence.comman;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidessence.planner.R;

/**
 * Created by sanjana.tailor on 1/25/2017.
 */

public class StartNewShiftDialog extends DialogFragment
{
    int count = 8;
    ImageView btnLeft, btnRight;
    Button btnStartNewShift;
    TextView tvFinishTime, tvHrs,tvStartTime;
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final DateFormat DAY_FORMAT = new SimpleDateFormat("E", Locale.getDefault());
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("EEE MM dd HH:mm yy", Locale.getDefault());
    StartNewShiftDialogCloseListener closeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dialog_start_new_shift, container, false);
        tvStartTime = (TextView) rootView.findViewById(R.id.tv_start_new_shift_time);
        tvFinishTime = (TextView) rootView.findViewById(R.id.tv_finish_time_new_shift);
        tvHrs  = (TextView) rootView.findViewById(R.id.tv_hours_new_shift);
        btnLeft = (ImageView) rootView.findViewById(R.id.iv_left_new_shift);
        btnRight = (ImageView) rootView.findViewById(R.id.iv_right_new_shift);
        btnStartNewShift = (Button)rootView.findViewById(R.id.btn_start_new_shift);
        btnStartNewShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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
        String time = TIME_FORMAT.format(new Date());
        tvStartTime.setText("(" +time + ")");
        tvHrs.setText("8");
        displayFinishTime();
        return rootView;
    }

    /**
     * Increment
     */
    private void increment()
    {
        count = count + 1;
        if(count >= 1 && count < 24){
            btnLeft.setImageResource(R.mipmap.ic_left);
            btnLeft.setEnabled(true);
            display(count);
            displayFinishTime();
        } else if(count >= 24 && count <48){
            display(count);
            displayFinishTime();
        } else if(count >= 48) {
            display(count);
            displayFinishTime();
            btnRight.setEnabled(false);
            btnRight.setImageResource(R.mipmap.right_disabled);
        }
    }

    /**
     * Decrement
     */
    private void decrement()
    {
        count = count - 1;
        if(count == 0){
            btnLeft.setEnabled(false);
            btnLeft.setImageResource(R.mipmap.left_disabled);
            display(count);
            displayFinishTime();
        } else if(count >= 1 && count <24) {
            display(count);
            displayFinishTime();
        } else if(count <= 48 && count >= 25){
            btnRight.setEnabled(true);
            btnRight.setImageResource(R.mipmap.ic_right);
            displayFinishTime();
            display(count);
        } else if(25 > count){
            display(count);
            displayFinishTime();
        }
        else {
            btnLeft.setEnabled(false);
            btnLeft.setImageResource(R.mipmap.left_disabled);
        }
    }

    private void displayFinishTime()
    {
        int hr = Integer.valueOf(tvHrs.getText().toString());
        String finishTime = addHrs(hr);
        tvFinishTime.setText(finishTime);
        String splitTime[]= finishTime.split(":");
        String hours=splitTime[0];
        PreferenceClass prefernces = new PreferenceClass(getActivity());
        prefernces.setNewFinishTime(hours+ ":"+ "00");
    }

    /**
     * This method displays.
     */
    private void display(int number)
    {
        tvHrs.setText("" + number);
    }


    private String addHrs(int hrs)
    {
        String time = "";
        String today = "";
        String calDate = " ";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd HH:mm yy");
        try {
            String todayT = DATE_TIME_FORMAT.format(new Date());
            Date start = sdf.parse(todayT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.HOUR, hrs);
            Date end = cal.getTime();
            time = TIME_FORMAT.format(end);
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
        return time + " " + today;
    }

    public void onDismiss(DialogInterface dialog)
    {
        Activity activity = getActivity();
        if(activity instanceof StartNewShiftDialog.StartNewShiftDialogCloseListener) {
            ((StartNewShiftDialog.StartNewShiftDialogCloseListener) activity).handleStartNewDialogClose(dialog);
        }
    }

    public interface StartNewShiftDialogCloseListener
    {
        public void handleStartNewDialogClose(DialogInterface dialog);
    }
}
