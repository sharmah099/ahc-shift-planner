package androidessence.adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import androidessence.comman.InfoDialog;
import androidessence.planner.MainActivity;
import androidessence.planner.R;
import androidessence.pojo.ShiftItems;

/**
 *
 */
public class DragAdapter extends ItemTouchHelper.SimpleCallback {
    private ItemAdapter mItemAdapter;
    Context context;

    public DragAdapter(ItemAdapter itemAdapter, Context context)
    {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mItemAdapter = itemAdapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public int getBoundingBoxMargin() {
        return super.getBoundingBoxMargin();
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mItemAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        mItemAdapter.remove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            TextView tv2 = (TextView)viewHolder.itemView.findViewById(R.id.time);
            tv2.setVisibility(View.INVISIBLE);

            //TextView tv = (TextView)viewHolder.itemView.findViewById(R.id.movie_val);
            //tv.setVisibility(View.VISIBLE);

            //TextView name  = (TextView)viewHolder.itemView.findViewById(R.id.client_name);
            //name.setTextColor(Color.BLACK);

            ImageView imageView = (ImageView)viewHolder.itemView.findViewById(R.id.add_time);
            imageView.setImageResource(R.mipmap.ic_add);
            viewHolder.itemView.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        //TextView tv = (TextView)viewHolder.itemView.findViewById(R.id.movie_val);
        TextView tvTime = (TextView)viewHolder.itemView.findViewById(R.id.time);

        String actualTime = tvTime.getText().toString();
        RecyclerView view = (RecyclerView) viewHolder.itemView.getParent();
        int totalHeight = view.getHeight();
        int minute = totalHeight/(12*60);
        int val = (int)viewHolder.itemView.getY();
        if (val <= 12 * 60 && val >= 0 && (((minute*val) % 5) == 0)) {
            String time = getTime(val, actualTime);
            //tv.setText(time);
        }
        else {
            //this is just for testing purpose
            //will think about it.
            if (val >=12 * 60 /*&& (((minute*val) % 5) == 0)*/) {
                //tv.setText("12:00");
            }
        }
        return .5f;
    }

    public String getTime(int min, String jobtime) {
        double hoursAndMinutes = (min / 60) + (double) min % 60 / 100;
        //double anotherHr = hoursAndMinutes + returnActualTime(jobtime);
        String hoursAndMinutesStr = new DecimalFormat("00.00").format(hoursAndMinutes).replace('.', ':');

        return hoursAndMinutesStr;
    }


    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        //TextView tv = (TextView)viewHolder.itemView.findViewById(R.id.movie_val);
        String value = "";//tv.getText().toString();
        TextView tv2 = (TextView)viewHolder.itemView.findViewById(R.id.time);
        tv2.setVisibility(View.VISIBLE);
        tv2.setTextColor(Color.BLACK);
        //tv.setVisibility(View.INVISIBLE);

        if (!value.equals("") && !isContains(value)) {
            mItemAdapter.updateList(viewHolder.getAdapterPosition(), value);
        }
        else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //TextView someText = (TextView)viewHolder.itemView.findViewById(R.id.movie_someText);
            //someText.setVisibility(View.INVISIBLE);

            String actualValue = tv2.getText().toString();
            if (!value.isEmpty() && !actualValue.equals(value)) {
                showDialogs();
            }
            mItemAdapter.refresh();
        }
        super.clearView(recyclerView, viewHolder);
    }

    private boolean isContains(String  val)
    {
        boolean isContain = false;
        List<ShiftItems> obj = mItemAdapter.getAllItems();

        for(ShiftItems item : obj) {
            if (item.getTime().equals(val)) {
                isContain = true;
            }
        }

        return isContain;

    }

    void showDialogs()
    {
        MainActivity activity = (MainActivity)context;
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");

        if (prev == null) {
            // Create and show the dialog.
            InfoDialog newFragment = InfoDialog.newInstance();
            newFragment.show(ft, "dialog");
        }
    }

    private double returnActualTime(String time){

        String actualTime = "";
        double val = 0;
        char[] chars = time.toCharArray();
        for (char a: chars) {
            if (a != ':') {
                actualTime = actualTime + a;
            }
            else {
                actualTime = actualTime +'.';
            }

            val = Double.valueOf(actualTime);

        }

        return val;

    }
}
