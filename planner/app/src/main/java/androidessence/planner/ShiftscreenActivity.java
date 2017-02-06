package androidessence.planner;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidessence.adapters.ShiftJobAdapter;
import androidessence.comman.DividerItemDecoration;
import androidessence.listeners.RecyclerTouchListener;
import androidessence.pojo.ShiftJobsTime;

public class ShiftscreenActivity extends Activity {

    RecyclerView list1, list2, list3;
    LinearLayout ll3, ll6, ll7;
    SimpleDateFormat sdf;
    Calendar cal;
    String starttime, starttimesecond, Starttimesecond1;
    private ShiftJobAdapter shiftjobadapterrange, shiftjobadapterhour, shiftjobadapterminute;
    private List<ShiftJobsTime> shiftjobrange = new ArrayList<>();
    private List<ShiftJobsTime> shiftjobhour = new ArrayList<>();
    private List<ShiftJobsTime> shiftjobtimeminute = new ArrayList<>();
    String selection = "";
    int positionHour = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiftscreen);

        list1 = (RecyclerView) findViewById(R.id.list1);
        list2 = (RecyclerView) findViewById(R.id.list2);
        list3 = (RecyclerView) findViewById(R.id.list3);

        sdf = new SimpleDateFormat("HH:mm");
        cal = Calendar.getInstance();

        shiftjobadapterrange = new ShiftJobAdapter(ShiftscreenActivity.this, shiftjobrange);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list1.setLayoutManager(mLayoutManager);
        list1.setItemAnimator(new DefaultItemAnimator());

        list1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        list1.setAdapter(shiftjobadapterrange);

        preparejobdata();

        defaultselectionmethod();

      final  int selectedposition = 0;
        list1.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                int itemposition = list1.getChildAdapterPosition(view);

                ShiftJobsTime sjt = shiftjobrange.get(position);
                String selection = sjt.getTime();
                if (sjt.getTime().equalsIgnoreCase("09:00 - 12:00")) {
                    defaultselectionmethod();

                } else if (sjt.getTime().equalsIgnoreCase("13:00 - 16:00")) {
                    String previoustime = sdf.format(cal.getTime());
                    if (previoustime.equalsIgnoreCase("12:00")) {
                        second13function();
                    } else {
                        cal.set(Calendar.HOUR_OF_DAY, 12);
                        cal.set(Calendar.MINUTE, 00);
                        cal.set(Calendar.SECOND, 00);
                        second13function();
                    }

                } else if (sjt.getTime().equalsIgnoreCase("17:00 - 20:00")) {

                    String previoustime = sdf.format(cal.getTime());
                    if (previoustime.equalsIgnoreCase("16:00")) {
                        second16function();
                    } else {
                        cal.set(Calendar.HOUR_OF_DAY, 16);
                        cal.set(Calendar.MINUTE, 00);
                        cal.set(Calendar.SECOND, 00);
                        second16function();

                    }

                }

            }
        }));

    }

    private void preparejobdata() {
        ShiftJobsTime shiftjobtime = new ShiftJobsTime("09:00 - 12:00");
        shiftjobrange.add(shiftjobtime);

        shiftjobtime = new ShiftJobsTime("13:00 - 16:00");
        shiftjobrange.add(shiftjobtime);

        shiftjobtime = new ShiftJobsTime("17:00 - 20:00");
        shiftjobrange.add(shiftjobtime);

        shiftjobadapterrange.notifyDataSetChanged();
    }


    public void defaultselectionmethod() {

        // start time 9
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        starttime = sdf.format(cal.getTime());

        // spliting inorder to add one hour to previous time.
        String[] arr = starttime.split(":");
        String datenew = arr[0];
        int da = Integer.parseInt(datenew);


        cal.set(Calendar.HOUR_OF_DAY, da);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);

        final String sttime = sdf.format(cal.getTime());
        // add next hour to previous time

        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime1 = sdf.format(cal.getTime());


        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime2 = sdf.format(cal.getTime());


        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime3 = sdf.format(cal.getTime());

        if (shiftjobhour != null) {
            shiftjobhour.clear();
        }

        shiftjobadapterhour = new ShiftJobAdapter(ShiftscreenActivity.this, shiftjobhour);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list2.setLayoutManager(mLayoutManager);
        list2.setItemAnimator(new DefaultItemAnimator());

        list2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        list2.setAdapter(shiftjobadapterhour);

        preparetimedata(sttime, starttime1, starttime2, starttime3);

        minutecalculation("");

        list2.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                positionHour = position;

                int perioditematposition = list2.getChildLayoutPosition(view);
                ShiftJobsTime minute = shiftjobhour.get(position);
                String selection = minute.getTime();

                if (selection.equalsIgnoreCase("09:00")) {

                    minutecalculation(sttime);
                } else if (selection.equalsIgnoreCase("10:00")) {

                    minutecalculation(starttime1);
                } else if (selection.equalsIgnoreCase("11:00")) {

                    minutecalculation(starttime2);
                } else if (selection.equalsIgnoreCase("12:00")) {
                    minutecalculation(starttime3);
                }


            }
        }));


    }

    public void second13function() {
        cal.add(Calendar.HOUR_OF_DAY, 1);

        starttimesecond = sdf.format(cal.getTime());
        String[] arr = starttimesecond.split(":");
        String datenew = arr[0];
        int da = Integer.parseInt(datenew);

        cal.set(Calendar.HOUR_OF_DAY, da);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        final String sttime = sdf.format(cal.getTime());
        // add next hour to previous time

        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime1 = sdf.format(cal.getTime());


        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime2 = sdf.format(cal.getTime());


        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime3 = sdf.format(cal.getTime());
        if (shiftjobhour != null) {
            shiftjobhour.clear();
        }

        shiftjobadapterhour = new ShiftJobAdapter(ShiftscreenActivity.this, shiftjobhour);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list2.setLayoutManager(mLayoutManager);
        list2.setItemAnimator(new DefaultItemAnimator());

        list2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        list2.setAdapter(shiftjobadapterhour);

        preparetimedata(sttime, starttime1, starttime2, starttime3);

        list2.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                int perioditematposition = list2.getChildLayoutPosition(view);
                ShiftJobsTime minute = shiftjobhour.get(position);
                String selection = minute.getTime();

                if (selection.equalsIgnoreCase("13:00")) {

                    minutecalculation(sttime);
                } else if (selection.equalsIgnoreCase("14:00")) {

                    minutecalculation(starttime1);
                } else if (selection.equalsIgnoreCase("15:00")) {

                    minutecalculation(starttime2);
                } else if (selection.equalsIgnoreCase("16:00")) {
                    minutecalculation(starttime3);
                }


            }
        }));


    }

    public void second16function() {
        cal.add(Calendar.HOUR_OF_DAY, 1);

        Starttimesecond1 = sdf.format(cal.getTime());
        String[] arr = Starttimesecond1.split(":");
        String datenew = arr[0];
        int da = Integer.parseInt(datenew);

        cal.set(Calendar.HOUR_OF_DAY, da);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        final String sttime = sdf.format(cal.getTime());
        // add next hour to previous time

        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime1 = sdf.format(cal.getTime());


        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime2 = sdf.format(cal.getTime());


        cal.add(Calendar.HOUR_OF_DAY, 1);
        final String starttime3 = sdf.format(cal.getTime());

        if (shiftjobhour != null) {
            shiftjobhour.clear();
        }
        shiftjobadapterhour = new ShiftJobAdapter(ShiftscreenActivity.this, shiftjobhour);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list2.setLayoutManager(mLayoutManager);
        list2.setItemAnimator(new DefaultItemAnimator());

        list2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        list2.setAdapter(shiftjobadapterhour);

        preparetimedata(sttime, starttime1, starttime2, starttime3);

        list2.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                int perioditematposition = list2.getChildLayoutPosition(view);
                ShiftJobsTime minute = shiftjobhour.get(position);
                String selection = minute.getTime();

                if (selection.equalsIgnoreCase("17:00")) {

                    minutecalculation(sttime);
                } else if (selection.equalsIgnoreCase("18:00")) {

                    minutecalculation(starttime1);
                } else if (selection.equalsIgnoreCase("19:00")) {

                    minutecalculation(starttime2);
                } else if (selection.equalsIgnoreCase("20:00")) {
                    minutecalculation(starttime3);
                }


            }
        }));
    }


    public void preparetimedata(String sttime, String starttime1, String starttime2, String starttime3) {


        ShiftJobsTime shiftjobtimes = new ShiftJobsTime(sttime);
        shiftjobhour.add(shiftjobtimes);

        shiftjobtimes = new ShiftJobsTime(starttime1);
        shiftjobhour.add(shiftjobtimes);

        shiftjobtimes = new ShiftJobsTime(starttime2);
        shiftjobhour.add(shiftjobtimes);

        shiftjobtimes = new ShiftJobsTime(starttime3);
        shiftjobhour.add(shiftjobtimes);

        shiftjobadapterhour.notifyDataSetChanged();
    }


    public void minutecalculation(String time) {

        if (shiftjobtimeminute.size() > 0) {
            shiftjobtimeminute.clear();
        }
        final String startminute;
        startminute = time;

        String[] arr = startminute.split(":");
        final String newdate = arr[0];


        shiftjobadapterminute = new ShiftJobAdapter(ShiftscreenActivity.this, shiftjobtimeminute);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list3.setLayoutManager(mLayoutManager);
        list3.setItemAnimator(new DefaultItemAnimator());

        list3.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        list3.setAdapter(shiftjobadapterminute);

        prepareminutetime();

        if(positionHour!= -1 && shiftjobhour.get(positionHour)!= null) {
            list3.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ShiftJobsTime jobsTime = shiftjobhour.get(positionHour);
                    ShiftJobsTime minute = shiftjobtimeminute.get(position);
                    selection = minute.getTime();
                    String hour = jobsTime.getTime();
                    String[] arr = hour.split(":");
                    final String hourtime = arr[0];

                    finish();
                }
            }));
        }
    }

    public void prepareminutetime() {


        ShiftJobsTime shiftjobtimeminutes = new ShiftJobsTime("00");
        shiftjobtimeminute.add(shiftjobtimeminutes);

        shiftjobtimeminutes = new ShiftJobsTime("15");
        shiftjobtimeminute.add(shiftjobtimeminutes);

        shiftjobtimeminutes = new ShiftJobsTime("30");
        shiftjobtimeminute.add(shiftjobtimeminutes);

        shiftjobtimeminutes = new ShiftJobsTime("45");
        shiftjobtimeminute.add(shiftjobtimeminutes);

        shiftjobadapterminute.notifyDataSetChanged();
    }


}
