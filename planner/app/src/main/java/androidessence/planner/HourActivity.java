package androidessence.planner;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidessence.comman.MorphDialogToView;
import androidessence.comman.MorphViewToDialog;
import androidessence.planner.FinalTimeActivity;
import androidessence.planner.PeriodActivity;
import androidessence.planner.R;

public class HourActivity extends AppCompatActivity {


    Button btn4, btn5, btn6, btn7;
    TextView backbtn;
    MainActivity ma;
    String starttime;
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour);
        ma = new MainActivity();

        Bundle bundle = getIntent().getExtras();
        starttime = bundle.getString("starttime");


        btn4 = (Button) findViewById(R.id.btn_11);
        btn5 = (Button) findViewById(R.id.btn_12);
        btn6 = (Button) findViewById(R.id.btn_13);
        btn7 = (Button) findViewById(R.id.btn_14);
        backbtn = (TextView) findViewById(R.id.tv_bc);

        //  starttime = ma.starttime;

        final String starttime1 = starttime;

        btn4.setVisibility(View.VISIBLE);
        btn4.setText(starttime1);


        Calendar call = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String[] arr = starttime.split(":");
        String datenew = arr[0];
        int da = Integer.parseInt(datenew);

        call.set(Calendar.HOUR_OF_DAY, da);
        call.set(Calendar.MINUTE, 00);
        call.set(Calendar.SECOND, 00);


        call.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime2 = sdf.format(call.getTime());
        btn5.setVisibility(View.VISIBLE);
        btn5.setText(starttime2);


        call.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime3 = sdf.format(call.getTime());
        btn6.setVisibility(View.VISIBLE);
        btn6.setText(starttime3);


        call.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime4 = sdf.format(call.getTime());
        btn7.setVisibility(View.VISIBLE);
        btn7.setText(starttime4);


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentfunction(starttime1, starttime,btn4);

                finish();

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentfunction(starttime2, starttime,btn5);
                finish();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intentfunction(starttime3, starttime,btn6);
                finish();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentfunction(starttime4, starttime,btn7);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HourActivity.this, PeriodActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HourActivity.this, v, getString(R.string.transition_dialog));
                startActivityForResult(intent, 1000 , options.toBundle());
                dismiss();
            }
        });

        container = (ViewGroup) findViewById(R.id.container);
        setupSharedEelementTransitions();

    }

    public void intentfunction(String starttime1, String starttime,View view)
    {
        Intent intent = new Intent(HourActivity.this, FinalTimeActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HourActivity.this, view, getString(R.string.transition_dialog));
        Bundle bunanimation = new Bundle();
        bunanimation.putString("starttime1", starttime1);
        bunanimation.putString("starttime", starttime);
        intent.putExtras(bunanimation);
        startActivityForResult(intent, 1000 , options.toBundle());
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(HourActivity.this, PeriodActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);

        dismiss();
        finishAfterTransition();
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


    private void dismiss()
    {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }
}
