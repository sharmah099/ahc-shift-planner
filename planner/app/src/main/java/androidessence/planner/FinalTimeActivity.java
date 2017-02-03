package androidessence.planner;

import android.app.Activity;
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

public class FinalTimeActivity extends AppCompatActivity {


    Button firstbtn, secondbtn, thirdbtn, fourthbtn;
    TextView backbtn1;
    String starttime1, starttime;
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_time);

        firstbtn = (Button) findViewById(R.id.btn_final_1);
        secondbtn = (Button) findViewById(R.id.btn_final_2);
        thirdbtn = (Button) findViewById(R.id.btn_final_3);
        fourthbtn = (Button) findViewById(R.id.btn_final_4);
        backbtn1 = (TextView) findViewById(R.id.tv_final_bc);

        Bundle bundle = getIntent().getExtras();
        starttime1 = bundle.getString("starttime1");
        starttime = bundle.getString("starttime");

        firstbtn.setVisibility(View.VISIBLE);
        firstbtn.setText(starttime1);
        firstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalTimeActivity.this,MainActivity.class);
                in.putExtra("starttime1",starttime1);
                startActivity(in);
                finish();
            }
        });

        Calendar calen = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String[] arr = starttime1.split(":");
        String datenew = arr[0];
        int da = Integer.parseInt(datenew);

        calen.set(Calendar.HOUR_OF_DAY, da);
        calen.set(Calendar.MINUTE, 00);
        calen.set(Calendar.SECOND, 00);

        calen.add(Calendar.MINUTE, 15);

        final String starttime2 = sdf.format(calen.getTime());
        secondbtn.setVisibility(View.VISIBLE);
        secondbtn.setText(starttime2);

        secondbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalTimeActivity.this,MainActivity.class);
                in.putExtra("starttime1",starttime2);
                startActivity(in);
                finish();
            }
        });

        calen.getTime();
        calen.add(Calendar.MINUTE, 15);
        final String starttime3 = sdf.format(calen.getTime());
        thirdbtn.setVisibility(View.VISIBLE);
        thirdbtn.setText(starttime3);

        thirdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalTimeActivity.this,MainActivity.class);
                in.putExtra("starttime1",starttime3);
                startActivity(in);
                finish();
            }
        });

        calen.getTime();
        calen.add(Calendar.MINUTE, 15);
        final String starttime4 = sdf.format(calen.getTime());
        fourthbtn.setVisibility(View.VISIBLE);
        fourthbtn.setText(starttime4);

        fourthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalTimeActivity.this,MainActivity.class);
                in.putExtra("starttime1",starttime4);
                startActivity(in);
                finish();
            }
        });


        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FinalTimeActivity.this, HourActivity.class);
                Bundle bunanimation = new Bundle();
                bunanimation.putString("starttime1", starttime1);
                bunanimation.putString("starttime", starttime);
                in.putExtras(bunanimation);
                startActivity(in);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                finish();
            }
        });

        container = (ViewGroup) findViewById(R.id.container);
        setupSharedEelementTransitions();

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
        Intent i = new Intent(FinalTimeActivity.this, HourActivity.class);
        Bundle b = new Bundle();
        b.putString("starttime1", starttime1);
        b.putString("starttime", starttime);
        i.putExtras(b);
        startActivity(i);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        finish();
        finishAfterTransition();
    }
}
