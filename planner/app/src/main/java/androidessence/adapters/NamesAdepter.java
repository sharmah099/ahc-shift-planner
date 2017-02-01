package androidessence.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidessence.planner.R;
import androidessence.listeners.StartActivityForResultListner;
import androidessence.pojo.IncompleteItems;

public class NamesAdepter extends RecyclerView.Adapter<NamesAdepter.ViewHolder> {
    List<IncompleteItems> list;
    Context context;

    StartActivityForResultListner listnerAct;
    public NamesAdepter(List<IncompleteItems> namelist, Context context, StartActivityForResultListner listner)
    {
        list = namelist;
        this.context = context;
        listnerAct = listner;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName, lastName;
        public TextView time, date;
        public ImageView icon;




        public ViewHolder(View itemView) {
            super(itemView);
            firstName = (TextView) itemView.findViewById(R.id.tv_first_name);
            lastName = (TextView) itemView.findViewById(R.id.tv_last_name);
            time = (TextView)itemView.findViewById(R.id.tv_incomp_time);
            date = (TextView)itemView.findViewById(R.id.tv_incomp_date);
            icon = (ImageView) itemView.findViewById(R.id.add_time);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_incomp, viewGroup, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, final int position) {

        IncompleteItems item = list.get(position);
        final String name = item.getName();
        final String time = item.getTime();
        final String date = item.getDate();
        String[] names = splitName(item.getName());
        myViewHolder.firstName.setText(names[0]+",");
        if (names.length == 2)
        myViewHolder.lastName.setText(names[1]);
        myViewHolder.time.setText(item.getTime());
        myViewHolder.date.setText(item.getDate());
        myViewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listnerAct.onShowDilaogAddShift(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void refresh(List<IncompleteItems> items)
    {
        list = items;
        notifyDataSetChanged();
    }

    private String[] splitName(String name) {

        String[] names = name.split(" ");
        return names;
    }
}
