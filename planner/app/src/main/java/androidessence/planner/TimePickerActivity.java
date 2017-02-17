package androidessence.planner;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidessence.comman.NumberPicker;

public class TimePickerActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    String hr ="09", min ="00";
    Button btnEta;

    String[] hours = new String[]{"09", "10", "11","12", "13", "14", "15", "16", "17", "18", "19", "20"};
    String[] mins = new String[]{"00", "15", "30", "45"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        NumberPicker hrPicker = (NumberPicker)findViewById(R.id.np_time);
        NumberPicker minPicker = (NumberPicker)findViewById(R.id.np_min);

        btnEta = (Button)findViewById(R.id.btn_set_eta);
        btnEta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("HOURS", hr);
                intent.putExtra("MINUTS", min);
                setResult(1003, intent);
                finish();
            }
        });

        hrPicker.setOnValueChangedListener(this);
        minPicker.setOnValueChangedListener(this);

        hrPicker.setDisplayedValues(hours);
        minPicker.setDisplayedValues(mins);

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        switch (picker.getId()) {
            case R.id.np_time:
                hr = (hours[newVal] == null) ? hours[oldVal] : hours[newVal];
                break;
            case  R.id.np_min:
                min = (mins[newVal] == null) ? mins[oldVal] : mins[newVal];
                break;
        }
    }
}
