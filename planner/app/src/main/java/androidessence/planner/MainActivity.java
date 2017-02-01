package androidessence.planner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidessence.adapters.DragAdapter;
import androidessence.adapters.ItemAdapter;
import androidessence.adapters.NamesAdepter;
import androidessence.comman.AddToShiftDialog;
import androidessence.comman.DataService;
import androidessence.comman.DividerItemDecoration;
import androidessence.comman.EditSessionLengthDialog;
import androidessence.comman.GridViewDialog;
import androidessence.comman.PreferenceClass;
import androidessence.comman.StartNewShiftDialog;
import androidessence.listeners.AddToShiftListener;
import androidessence.listeners.PlannerItemClickListener;
import androidessence.listeners.StartActivityForResultListner;
import androidessence.pojo.IncompleteItems;
import androidessence.pojo.ShiftItems;

public class MainActivity extends AppCompatActivity implements StartActivityForResultListner,
        View.OnClickListener,EditSessionLengthDialog.MyDialogCloseListener,
        StartNewShiftDialog.StartNewShiftDialogCloseListener, PlannerItemClickListener, AddToShiftListener
{

    ItemTouchHelper helper;
    ItemAdapter itemAdapter;
    RecyclerView movieRecyclerView, incompleteRv;
    TextView tvExpand;
    ImageView ivExpand;
    NamesAdepter namesAdapter;
    LinearLayout parent;
    int filterPanelWidth;
    TextView tvEdit;
    TextView tvFinish;
    TextView tvStart;

    List<ShiftItems> movieList = null;
    List<IncompleteItems> inCompleteItems = null;

    int positionAddToShift = -1;

    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());


    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the toolbar as actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvExpand = (TextView)findViewById(R.id.tv_expend);
        tvExpand.setOnClickListener(this);

        ivExpand = (ImageView) findViewById(R.id.iv_expand_col);
        ivExpand.setOnClickListener(this);

        tvEdit = (TextView) findViewById(R.id.tv_edit);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        tvStart =(TextView) findViewById(R.id.tv_start);
        String time = TIME_FORMAT.format(new Date());
        tvStart.setText(time);
        displayFinishTime();
        PreferenceClass preferences = new PreferenceClass(this);
        String finishTime = preferences.getFinishTime();
        if(!TextUtils.isEmpty(finishTime)) {
            tvFinish.setText(preferences.getFinishTime());
        }
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                EditSessionLengthDialog dialogFragment = new EditSessionLengthDialog();
                dialogFragment.show(fm, "EditSessionLength");
            }
        });


        // Setup RecyclerView
        movieRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieRecyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        movieRecyclerView.setLayoutManager(mLayoutManager);
        movieRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());

        DataService service = DataService.getService();
        List<ShiftItems> sh = service.getShiftItems();
        // Setup Adapter
        itemAdapter = new ItemAdapter(this, sh, this, this);
        movieRecyclerView.setAdapter(itemAdapter);

        // Setup ItemTouchHelper
        ItemTouchHelper.Callback callback = new DragAdapter(itemAdapter, this);
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(movieRecyclerView);

        incompleteRv = (RecyclerView)findViewById(R.id.movie_recycler_incomp);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        incompleteRv.setLayoutManager(linearLayoutManager);


        inCompleteItems = service.getListIncompleteItems();
        namesAdapter = new NamesAdepter(inCompleteItems, this, this);
        incompleteRv.setAdapter(namesAdapter);
        incompleteRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        incompleteRv.setItemAnimator(new DefaultItemAnimator());

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        filterPanelWidth = (int) ((metrics.widthPixels) * -0.40);

        parent = (LinearLayout)findViewById(R.id.ll_parent);

    }

    @Override
    public void onStartAct(String time, String name,  int pos, boolean fromIncomplete, String etaType) {
        this.pos = pos;
        ArrayList<ShiftItems> listItem = (ArrayList<ShiftItems>) itemAdapter.getAllItems();

        if (etaType.equalsIgnoreCase("Set ETA1")) {
            Intent intent = new Intent(this, ScrollerActivity.class);
            //intent.putParcelableArrayListExtra("ALL_TIME", listItem);
            //intent.putExtra("TIME", time);
            //intent.putExtra("NAME", name);
            //intent.putExtra("FROM_INCOMP", fromIncomplete);
            startActivityForResult(intent, 1001);
        }
        else if (etaType.equalsIgnoreCase("Set ETA2")) {
            Intent intent = new Intent(this, PeriodActivity.class);
            //intent.putParcelableArrayListExtra("ALL_TIME", listItem);
            //intent.putExtra("TIME", time);
            //intent.putExtra("NAME", name);
            //intent.putExtra("FROM_INCOMP", fromIncomplete);
            startActivityForResult(intent, 1000);
        }
        else if(etaType.equalsIgnoreCase("Set ETA3"))
        {
            FragmentManager fm = getFragmentManager();
            GridViewDialog dialogFragment = new GridViewDialog();
            dialogFragment.show(fm, "gridViewDialog");
        }
    }

    @Override
    public void onShowDilaogAddShift(int pos) {
        positionAddToShift = pos;
        FragmentManager fm = getFragmentManager();
        AddToShiftDialog dialogFragment = new AddToShiftDialog();
        dialogFragment.setListener(this);
        dialogFragment.show(fm, "dialog");
    }

    @Override
    public void onAddShiftCanclled() {

        positionAddToShift = -1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {

            if (requestCode == 1000) {

                boolean isFromIncomp = data.getBooleanExtra("FROM_INCOMP", false);
                String time = data.getStringExtra("NEWTIME");
                String name = data.getStringExtra("REVERT_NAME");

                if (isFromIncomp) {
                    tvExpand.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_expand, 0);
                    incompleteRv.setVisibility(View.GONE);
                    for(IncompleteItems in : inCompleteItems) {
                        if (in.getName().equals(name)) {
                            inCompleteItems.remove(in);
                            break;
                        }
                    }
                    setHeightInCollapse();
                    recreateAdapter(time, name);
                }else {
                    itemAdapter.updateList(pos, time);
                }
            }
            else if (requestCode == 1001) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recreateAdapter(final String time , final String name)
    {
        helper.attachToRecyclerView(movieRecyclerView);
        // Setup Adapter
        movieList.add(new ShiftItems(time, "", "", name, "", "", "", "", "", ""));
        itemAdapter.refresh(movieList);
        itemAdapter.updateList((movieList.size() - 1));
    }

    @Override
    public void onClick(View view)
    {
        if (inCompleteItems != null && !inCompleteItems.isEmpty()) {
            if (incompleteRv.getVisibility() == View.GONE) {
                ivExpand.setImageResource(R.mipmap.ic_expand);
                //tvExpand.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_expand, 0);
                incompleteRv.setVisibility(View.VISIBLE);
                namesAdapter.refresh(inCompleteItems);
                setHeightInExpand();
                expand(incompleteRv);
                helper.attachToRecyclerView(null);
            } else {
                ivExpand.setImageResource(R.mipmap.ic_collapse);
                //tvExpand.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_collapse, 0);
                //incompleteRv.setVisibility(View.GONE);
                //setHeightInCollapse();
                collapse(incompleteRv);
                helper.attachToRecyclerView(movieRecyclerView);
            }
        }
    }


    private void setHeightInExpand()
    {
        ViewGroup.LayoutParams icom = incompleteRv.getLayoutParams();
        icom.height= icom.WRAP_CONTENT;
        incompleteRv.setLayoutParams(icom);

        ViewGroup.LayoutParams actual=movieRecyclerView.getLayoutParams();
        actual.height = actual.WRAP_CONTENT;
        movieRecyclerView.setLayoutParams(actual);
    }

    private void setHeightInCollapse()
    {
        //int height = getDeviceHieght();
        ViewGroup.LayoutParams icom=incompleteRv.getLayoutParams();
        icom.height=0;
        incompleteRv.setLayoutParams(icom);

        ViewGroup.LayoutParams actual=movieRecyclerView.getLayoutParams();
        actual.height = actual.MATCH_PARENT;
        movieRecyclerView.setLayoutParams(actual);
    }


    @Override
    public void handleDialogClose(DialogInterface dialog)
    {
        PreferenceClass preferenceClass = new PreferenceClass(getApplicationContext());
        tvFinish.setText(preferenceClass.getFinishTime());
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceClass preferenceClass = new PreferenceClass(getApplicationContext());
        preferenceClass.clearEndTime();
        preferenceClass.clearNewFinsihTime();
    }

    private void displayFinishTime()
    {
        int hr = 8;
        String finishTime = addHrs(hr);
        tvFinish.setText(finishTime);
        PreferenceClass prefernces = new PreferenceClass(getApplicationContext());
        prefernces.setFinishTime(finishTime);
    }

    private String addHrs(int hrs)
    {
        String time ="";
        String today = "";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        try {
            String todayT = TIME_FORMAT.format(new Date());
            Date start = sdf.parse(todayT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.HOUR, hrs);
            Date end = cal.getTime();
            time = TIME_FORMAT.format(end);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return time;
    }

    @Override
    public void handleStartNewDialogClose(DialogInterface dialog)
    {
        PreferenceClass preferenceClass = new PreferenceClass(getApplicationContext());
        tvFinish.setText(preferenceClass.getNewFinishTime());
    }

    @Override
    public void onItemClicked(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        JoboverviewFragment jobOverviewFragment = new JoboverviewFragment();
        jobOverviewFragment.show(fm ,"");
    }

    public static void expand(final View v) {
        v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight() - 100;

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RecyclerView.LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if(targtetHeight > 0) {
            a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public  void addToShift()
    {
        inCompleteItems.remove(positionAddToShift);
        namesAdapter.refresh(inCompleteItems);

        Intent intent = new Intent(this, ScrollerActivity.class);
        startActivityForResult(intent, 1001);
    }



}
