package androidessence.planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.ArcMotion;
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

import androidessence.comman.MorphDialogToView;
import androidessence.comman.MorphViewToDialog;
import androidessence.comman.PreferenceClass;

public class StartNewShiftDialogActivity extends AppCompatActivity
{
    private ViewGroup container;

    int count = 8;
    ImageView btnLeft, btnRight;
    Button btnStartNewShift;
    TextView tvFinishTime,tvStartTime,tvError, tvHrs;
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
        tvError = (TextView) findViewById(R.id.tv_error);
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
        btnStartNewShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        displayFinishTime();
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

    private void displayFinishTime()
    {
        if(isValidInt(tvHrs.getText().toString())) {
            tvError.setVisibility(View.GONE);
            int hr = Integer.valueOf(tvHrs.getText().toString());
            String finishTime = addHrs(hr);
            tvFinishTime.setText(finishTime);
            String splitTime[] = finishTime.split(":");
            String hours = splitTime[0];
            PreferenceClass prefernces = new PreferenceClass(getApplicationContext());
            prefernces.setNewFinishTime(hours + ":" + "00");
        } else {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Please enter valid number");
        }
    }


    /**
     * Function that checks the int is valid or not
     */
    public static boolean isValidInt(String value)
    {
        boolean retVal;
        try
        {
            Integer.parseInt(value);
            retVal = true;
        }
        catch(NumberFormatException nfe)
        {
            retVal = false;
        }
        return retVal;
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

    /**
     * Increment
     */
    private void increment() {
        if (!TextUtils.isEmpty(tvHrs.getText().toString())) {
            if (isValidInt(tvHrs.getText().toString())) {
                tvError.setVisibility(View.GONE);
                int hr = Integer.valueOf(tvHrs.getText().toString());
                count = hr + 1;
                if (count >= 1 && count < 24) {
                    btnLeft.setImageResource(R.mipmap.ic_left);
                    btnLeft.setEnabled(true);
                    display(count);
                    displayFinishTime();
                } else if (count >= 24 && count < 48) {
                    display(count);
                    displayFinishTime();
                } else if (count == 48) {
                    display(count);
                    displayFinishTime();
                    btnRight.setEnabled(false);
                    btnRight.setImageResource(R.mipmap.ic_right_disabled);
                }
            } else {
                count = count + 1;
                if (count >= 1 && count < 24) {
                    btnLeft.setImageResource(R.mipmap.ic_left);
                    btnLeft.setEnabled(true);
                    display(count);
                    displayFinishTime();
                } else if (count >= 24 && count < 48) {
                    display(count);
                    displayFinishTime();
                } else if (count >= 48) {
                    display(count);
                    displayFinishTime();
                    btnRight.setEnabled(false);
                    btnRight.setImageResource(R.mipmap.ic_right_disabled);
                }
            }
        }
    }

    /**
     * Decrement
     */
    private void decrement()
    {
        if(!TextUtils.isEmpty(tvHrs.getText().toString())) {
            if (isValidInt(tvHrs.getText().toString())) {
                tvError.setVisibility(View.GONE);
                int hr = Integer.valueOf(tvHrs.getText().toString());
                count = hr - 1;
                if (count == 0) {
                    btnLeft.setEnabled(false);
                    btnLeft.setImageResource(R.mipmap.ic_left_disabled);
                    display(count);
                    displayFinishTime();
                } else if (count >= 1 && count < 24) {
                    display(count);
                    displayFinishTime();
                } else if (count <= 48 && count >= 25) {
                    btnRight.setEnabled(true);
                    btnRight.setImageResource(R.mipmap.ic_right);
                    display(count);
                    displayFinishTime();
                } else if (25 > count) {
                    display(count);
                    displayFinishTime();
                } else {
                    btnLeft.setEnabled(false);
                    btnLeft.setImageResource(R.mipmap.ic_left_disabled);
                }
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Please enter valid number");
            }
        }  else {
            count = count - 1;
            if (count == 0) {
                btnLeft.setEnabled(false);
                btnLeft.setImageResource(R.mipmap.ic_left_disabled);
                display(count);
                displayFinishTime();
            } else if (count >= 1 && count < 24) {
                display(count);
                displayFinishTime();
            } else if (count <= 48 && count >= 25) {
                btnRight.setEnabled(true);
                btnRight.setImageResource(R.mipmap.ic_right);
                display(count);
                displayFinishTime();
            } else if (25 > count) {
                display(count);
                displayFinishTime();
            } else {
                btnLeft.setEnabled(false);
                btnLeft.setImageResource(R.mipmap.ic_left_disabled);
            }
        }
    }

}
