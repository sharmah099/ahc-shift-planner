package androidessence.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidessence.planner.R;
import androidessence.pojo.ShiftJobsTime;

public class ShiftJobAdapter extends RecyclerView.Adapter<ShiftJobAdapter.ShiftViewHolder>
{
    List<ShiftJobsTime> shiftTiming = null;
    Context context;
    int selectedposition = -1;


    public ShiftJobAdapter(Context context, List<ShiftJobsTime> shiftTiming)
    {
        this.context = context;
        this.shiftTiming = shiftTiming;
    }

    @Override
    public ShiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = (LayoutInflater.from(context).inflate(R.layout.activity_listview, parent, false));
        return new ShiftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShiftViewHolder holder, final int position)
    {
        ShiftJobsTime shiftjobstime = shiftTiming.get(position);
        holder.timing.setText(shiftjobstime.getTime());

        if (selectedposition == position) {
            // Here I am just highlighting the background
            holder.itemView.setBackgroundColor(Color.parseColor("#d3d3d3"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedposition);
                selectedposition = position;
                notifyItemChanged(selectedposition);
            }
        });
    }

    public static class ShiftViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView timing;

        public ShiftViewHolder(View view)
        {
            super(view);
            timing = (TextView) view.findViewById(R.id.tv_text);
            view.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return shiftTiming.size();
    }
}
