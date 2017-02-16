package androidessence.planner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidessence.comman.CustomNumberPicker;

public class TimePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        CustomNumberPicker hrPicker = (CustomNumberPicker)findViewById(R.id.np_time);
        CustomNumberPicker minPicker = (CustomNumberPicker)findViewById(R.id.np_min);

        hrPicker.showPicker(new String[]{"9", "10", "11","12", "13", "14", "15", "16", "17", "18", "19", "20"}, 11, 0);
        minPicker.showPicker(new String[]{"00", "15", "30", "45"}, 3, 0);

    }
}
