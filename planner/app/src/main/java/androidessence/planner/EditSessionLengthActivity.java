package androidessence.planner;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.ArcMotion;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by sanjana.tailor on 2/1/2017.
 */

public class EditSessionLengthActivity extends AppCompatActivity
{
    public static int NEW_START_SHIFT_ACT = 101;

    private ViewGroup container;
    int count = 8;
    ImageView btnLeft, btnRight;
    Button btnCreateNewShift;
    Button btnUpdate;
    TextView tvFinishTime, tvStartTime,tvError;
    EditText etHrs;
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final DateFormat DAY_FORMAT = new SimpleDateFormat("E", Locale.getDefault());
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("EEE MM dd HH:mm yy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_edit_session_length);
        container = (ViewGroup) findViewById(R.id.container);
        tvStartTime = (TextView)findViewById(R.id.tv_start_time);
        tvFinishTime = (TextView)findViewById(R.id.tv_finish_time);
        etHrs = (EditText)findViewById(R.id.et_hours);
        btnLeft = (ImageView) findViewById(R.id.iv_left);
        btnRight = (ImageView)findViewById(R.id.iv_right);
        tvError = (TextView) findViewById(R.id.tv_error);
        etHrs.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    displayFinishTime();
                }
                return false;
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });
        btnUpdate = (Button) findViewById(R.id.btn_update);
        String time = TIME_FORMAT.format(new Date());
        tvStartTime.setText(time + " " + "today");
        etHrs.setText("8");
        etHrs.setSelection(etHrs.getText().length());
        etHrs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isValidInt(etHrs.getText().toString())) {
                    int inputs = Integer.valueOf(etHrs.getText().toString());
                    if (inputs >= 48 || inputs <= 0) {
                        tvError.setVisibility(View.VISIBLE);
                    } else {
                        tvError.setVisibility(View.GONE);
                    }
                    if (inputs >= 48 || inputs == 0) {
                        btnRight.setEnabled(false);
                        btnRight.setImageResource(R.mipmap.right_disabled);
                        btnLeft.setEnabled(false);
                        btnLeft.setImageResource(R.mipmap.left_disabled);
                    } else if (inputs > 0 && inputs < 48) {
                        btnRight.setEnabled(true);
                        btnRight.setImageResource(R.mipmap.ic_right);
                        btnLeft.setImageResource(R.mipmap.ic_left);
                        btnLeft.setEnabled(true);
                    }
                } else {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Please enter value between 1-48");
                    btnRight.setEnabled(false);
                    btnLeft.setEnabled(false);
                }
            }
        });
        displayFinishTime();
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });

        btnCreateNewShift = (Button) findViewById(R.id.btn_create_shift);
        btnCreateNewShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(EditSessionLengthActivity.this, StartNewShiftDialogActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EditSessionLengthActivity.this, btnCreateNewShift, getString(R.string.transition_dialog));
                startActivityForResult(intent, NEW_START_SHIFT_ACT , options.toBundle());
            }
        });

        setupSharedEelementTransitions();

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        container.findViewById(R.id.btn_update).setOnClickListener(dismissListener);
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
    }


    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss()
    {
        setResult(MainActivity.EDIT_SESSION_LENGTH_ACT);
        finishAfterTransition();
    }

    private void displayFinishTime()
    {
        if(isValidInt(etHrs.getText().toString()))
        {
            tvError.setVisibility(View.GONE);
            int hr = Integer.valueOf(etHrs.getText().toString());
            String finishTime = addHrs(hr);
            String addHr = addFinishTime(hr);
            tvFinishTime.setText(finishTime);
            PreferenceClass preferences = new PreferenceClass(getApplicationContext());
            preferences.setFinishTime(addHr);
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

    private String addFinishTime(int hrs)
    {
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd HH:mm yy");
        try {
            String todayT = DATE_TIME_FORMAT.format(new Date());
            Date start = sdf.parse(todayT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.HOUR, hrs);
            Date end = cal.getTime();
            time = TIME_FORMAT.format(end);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return time ;
    }

    /**
     * Decrement
     */
    private void decrement()
    {
        if(!TextUtils.isEmpty(etHrs.getText().toString())) {
            if (isValidInt(etHrs.getText().toString())) {
                tvError.setVisibility(View.GONE);
                int hr = Integer.valueOf(etHrs.getText().toString());
                count = hr - 1;
                if (count == 0) {
                    btnLeft.setEnabled(false);
                    btnLeft.setImageResource(R.mipmap.left_disabled);
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
                    btnLeft.setImageResource(R.mipmap.left_disabled);
                }
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Please enter valid number");
            }
        } else {
            count = count - 1;
            if (count == 0) {
                btnLeft.setEnabled(false);
                btnLeft.setImageResource(R.mipmap.left_disabled);
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
                btnLeft.setImageResource(R.mipmap.left_disabled);
            }
        }
    }

    /**
     * Increment
     */
    private void increment() {
        if(!TextUtils.isEmpty(etHrs.getText().toString())){
            if(isValidInt(etHrs.getText().toString())) {
                tvError.setVisibility(View.GONE);
                int hr = Integer.valueOf(etHrs.getText().toString());
                count = hr + 1;
                if (count >= 1 && count < 24) {
                    btnLeft.setImageResource(R.mipmap.ic_left);
                    btnLeft.setEnabled(true);
                    display(count);
                    displayFinishTime();
                } else if (count >= 24 && count < 48) {
                    display(count);
                    displayFinishTime();
                }
                else if(count == 48) {
                    display(count);
                    displayFinishTime();
                    btnRight.setEnabled(false);
                    btnRight.setImageResource(R.mipmap.right_disabled);
                }
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Please enter valid number");
            }
        } else{
            count = count + 1;
            if (count >= 1 && count < 24) {
                btnLeft.setImageResource(R.mipmap.ic_left);
                btnLeft.setEnabled(true);
                display(count);
                displayFinishTime();
            } else if (count >= 24 && count < 48) {
                display(count);
                displayFinishTime();
            }
            else if(count == 48) {
                display(count);
                displayFinishTime();
                btnRight.setEnabled(false);
                btnRight.setImageResource(R.mipmap.right_disabled);
            }
        }
    }

    /**
     * This method displays.
     */
    private void display(int number) {
        etHrs.setText("" + number);
        etHrs.setSelection(etHrs.getText().length());

    }

}
