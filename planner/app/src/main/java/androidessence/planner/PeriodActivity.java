package androidessence.planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidessence.planner.HourActivity;
import androidessence.planner.MainActivity;
import androidessence.planner.R;

public class PeriodActivity extends Activity {


    Button btn1, btn2, btn3;
    static String starttime, starttime2, starttime3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);


        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        starttime = sdf.format(cal.getTime());

        cal.add(Calendar.HOUR_OF_DAY, 3);
        final String endtime = sdf.format(cal.getTime());

        btn1.setText(starttime + "-" + endtime);
        btn1.setVisibility(View.VISIBLE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callintentfunction(starttime);
                finish();
            }
        });

        cal.add(Calendar.HOUR_OF_DAY, 1);
        starttime2 = sdf.format(cal.getTime());
        cal.add(Calendar.HOUR_OF_DAY, 3);

        final String endtime2 = sdf.format(cal.getTime());

        btn2.setText(starttime2 + "-" + endtime2);

        //starttime = starttime2;
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callintentfunction(starttime2);
                finish();
            }
        });


        cal.add(Calendar.HOUR_OF_DAY, 1);
        starttime3 = sdf.format(cal.getTime());
        cal.add(Calendar.HOUR, 3);
        final String endtime3 = sdf.format(cal.getTime());
        btn3.setVisibility(View.VISIBLE);
        btn3.setText(starttime3 + "-" + endtime3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callintentfunction(starttime3);
                finish();
            }
        });

    }

    public void callintentfunction(String starttime) {

        Intent i = new Intent(getApplicationContext(), HourActivity.class);
        Bundle bunanimation = new Bundle();
        bunanimation.putString("starttime", starttime);
        i.putExtras(bunanimation);
        startActivity(i);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
