package androidessence.planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidessence.adapters.DragAdapter;
import androidessence.adapters.ItemAdapter;
import androidessence.adapters.NamesAdepter;
import androidessence.comman.DataService;
import androidessence.comman.DividerItemDecoration;
import androidessence.listeners.StartActivityForResultListner;
import androidessence.pojo.IncompleteItems;
import androidessence.pojo.ShiftItems;

public class MainActivity extends AppCompatActivity implements StartActivityForResultListner, View.OnClickListener
{

    ItemTouchHelper helper;
    ItemAdapter itemAdapter;
    RecyclerView movieRecyclerView, incompleteRv;
    TextView tvExpand;
    ImageView ivExpand;
    NamesAdepter namesAdapter;
    LinearLayout parent;
    int filterPanelWidth;

    List<ShiftItems> movieList = null;
    List<IncompleteItems> inCompleteItems = null;

    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the toolbar as actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_menu_white_24dp);

        tvExpand = (TextView)findViewById(R.id.tv_expend);
        tvExpand.setOnClickListener(this);

        ivExpand = (ImageView) findViewById(R.id.iv_expand_col);
        ivExpand.setOnClickListener(this);

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
        itemAdapter = new ItemAdapter(this, sh, this);
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
    public void onStartAct(String time, String name,  int pos, boolean fromIncomplete) {
        this.pos = pos;
        ArrayList<ShiftItems> listItem = (ArrayList<ShiftItems>) itemAdapter.getAllItems();
        Intent intent = new Intent(this, CalenerActivity.class);
        intent.putParcelableArrayListExtra("ALL_TIME", listItem);
        intent.putExtra("TIME", time);
        intent.putExtra("NAME", name);
        intent.putExtra("FROM_INCOMP", fromIncomplete);
        startActivityForResult(intent, 1000);
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recreateAdapter(final String time , final String name)
    {
        helper.attachToRecyclerView(movieRecyclerView);
        // Setup Adapter
        movieList.add(new ShiftItems(time, "", "", name, "", "", "", "", ""));
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
                helper.attachToRecyclerView(null);
            } else {
                ivExpand.setImageResource(R.mipmap.ic_collapse);
                //tvExpand.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_collapse, 0);
                incompleteRv.setVisibility(View.GONE);
                setHeightInCollapse();
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

}
