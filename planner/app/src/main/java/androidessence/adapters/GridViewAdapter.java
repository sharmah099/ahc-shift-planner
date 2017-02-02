package androidessence.adapters;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidessence.comman.GridViewDialog;
import androidessence.listeners.AddToShiftListener;
import androidessence.planner.R;
import androidessence.pojo.GridViewTime;

public class GridViewAdapter extends BaseAdapter
{
    private Context context;
    GridViewDialog gridViewDialog;
    AddToShiftListener listener;

    List<GridViewTime> timeList = new ArrayList<>();

    public GridViewAdapter(Context context,GridViewDialog gridViewDialog, List<GridViewTime> list)
    {
        this.context = context;
        timeList = list;
        this.gridViewDialog = gridViewDialog;
    }

    @Override
    public int getCount()
    {
        return timeList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return timeList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    private static class ViewHolder
    {
        public TextView textview;

        ViewHolder(View v)
        {
            textview = (TextView) v.findViewById(R.id.time);
        }
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        View view = convertView;
        ViewHolder holder;

        final GridViewTime list = timeList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_view_items, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textview.setText("" + list.getTimeInterval());

        holder.textview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.updateTime(list.getTimeInterval());
                notifyDataSetChanged();
                gridViewDialog.dismissDialog();
            }
        });
        return view;
    }
    public void addToShiftJob(AddToShiftListener listener)
    {
        this.listener = listener;
    }
}
