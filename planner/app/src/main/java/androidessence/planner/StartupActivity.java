package androidessence.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import androidessence.comman.DataService;

public class StartupActivity extends AppCompatActivity {

    DataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


    }

    public void onClicked(View view) {

        try {

            service = DataService.getService();
            service.parseJsonData();
        }
        catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Parsing failed", Toast.LENGTH_SHORT).show();
        }


        startActivity(new Intent(this, MainActivity.class));

    }
}
