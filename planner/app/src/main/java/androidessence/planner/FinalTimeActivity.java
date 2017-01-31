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

public class FinalTimeActivity extends Activity {


    Button firstbtn, secondbtn, thirdbtn, fourthbtn;
    TextView backbtn1;
    String starttime1, starttime;

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
    }
}
