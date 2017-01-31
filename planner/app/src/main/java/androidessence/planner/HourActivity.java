package androidessence.planner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidessence.planner.FinalTimeActivity;
import androidessence.planner.PeriodActivity;
import androidessence.planner.R;

public class HourActivity extends Activity {


    Button btn4, btn5, btn6, btn7;
    TextView backbtn;
    MainActivity ma;
    String starttime;

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

                intentfunction(starttime1, starttime);

                finish();

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentfunction(starttime2, starttime);
                finish();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intentfunction(starttime3, starttime);
                finish();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentfunction(starttime4, starttime);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HourActivity.this, PeriodActivity.class);
                // ma.callpopup();
                startActivity(i);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                finish();
            }
        });

    }

    public void intentfunction(String starttime1, String starttime) {


        Intent in = new Intent(HourActivity.this, FinalTimeActivity.class);
        Bundle bunanimation = new Bundle();
        bunanimation.putString("starttime1", starttime1);
        bunanimation.putString("starttime", starttime);
        in.putExtras(bunanimation);
        startActivity(in);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(HourActivity.this, PeriodActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);

        finish();
    }
}
