package androidessence.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import androidessence.comman.DataService;

public class StartupActivity extends AppCompatActivity implements View.OnClickListener
{
    DataService service;
    TextView startTime, visitTime;
    ImageView left_icon;
    ImageView right_icon;
    static int count ;
    int value = 9;
    Button startShift;
    Toolbar toolbar;

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

        //Method used to display selected time.
        display(count);
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
                startActivity(new Intent(this, MainActivity.class));
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
    private void display(int currentTime)
    {
        currentTime = value + currentTime;
        visitTime.setText("visits shown will be from now (9:00) to " + currentTime + "" + ":00");
    }
}
