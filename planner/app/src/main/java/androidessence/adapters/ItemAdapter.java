package androidessence.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidessence.listeners.PlannerItemClickListener;
import androidessence.planner.R;
import androidessence.listeners.StartActivityForResultListner;
import androidessence.pojo.ShiftItems;

/**
 *
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<ShiftItems> items;
    int currentPosition = -1;

    StartActivityForResultListner listnerAct;

    PlannerItemClickListener plannerItemClickListener;

    public ItemAdapter(Context context, List<ShiftItems> items, StartActivityForResultListner listnerAct,
                       PlannerItemClickListener plannerItemClickListener){
        this.mContext = context;
        this.items = items;
        this.listnerAct = listnerAct;
        this.plannerItemClickListener = plannerItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = (LayoutInflater.from(mContext).inflate(R.layout.list_item_job, parent, false));
        final ViewHolder viewHolder = new ViewHolder(itemView);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> patientInfoList = new ArrayList<String>();
                patientInfoList.add(viewHolder.clientName.getText().toString());
                patientInfoList.add(viewHolder.lastName.getText().toString());
                patientInfoList.add(viewHolder.dob.getText().toString());
                plannerItemClickListener.onItemClicked(view, patientInfoList);
            }
        });
        
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindMovie(items.get(position));
        ShiftItems item = items.get(position);
        final String time = item.getTime();
        final String name = item.getName();
        String dgrMesg = item.getDraggedMsg();

        if (dgrMesg != null && dgrMesg.equals("Updated")) {
            holder.itemView.setBackgroundColor(Color.BLUE);
            holder.clientName.setTextColor(Color.WHITE);
            holder.time.setTextColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.clientName.setTextColor(Color.BLACK);
            holder.time.setTextColor(Color.BLACK);

        }
        holder.eta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listnerAct.onStartAct(time, name,  position, false, holder.eta.getText().toString(),v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int fromPosition, int toPosition)
    {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public final TextView clientName, lastName, time, hr, dob, gender, action, eta, priority;
        public final ImageView ivStatus;


        public ViewHolder(View view){
            super(view);
            clientName = (TextView) view.findViewById(R.id.tv_client_name);
            lastName = (TextView) view.findViewById(R.id.tv_client_name_last);
            time =  (TextView) view.findViewById(R.id.tv_time);
            hr = (TextView) view.findViewById(R.id.tv_hour_minute);
            action = (TextView)view.findViewById(R.id.tv_job_action);
            dob = (TextView)view.findViewById(R.id.tv_dob);
            gender = (TextView) view.findViewById(R.id.tv_client_gender);
            priority = (TextView)view.findViewById(R.id.tv_priority);

            eta = (TextView) view.findViewById(R.id.add_time);
            ivStatus = (ImageView) view.findViewById(R.id.iv_status);


        }

        public void bindMovie(ShiftItems items)
        {
            String [] names = splitName(items.getName());
            this.clientName.setText(names[0]+",");
            if (names.length == 2)
                this.lastName.setText(names[1]);
            this.time.setText(items.getTime());
            this.hr.setText(items.getHour() +"/"+ items.getMin());
            this.gender.setText(items.getGender());
            this.action.setText(items.getAction());
            this.dob.setText(items.getDob()+" ("+""+items.getAge()+")");

            setPriority(this.priority, items.getAction());
            setStatusIcon(this.ivStatus, items.getStatus());

            this.eta.setText(items.getEta());
        }

    }

    public void updateList(int position , String text)
    {
        boolean isUpdated = false;
        for (ShiftItems item : items) {
            //item.setDraggedMsg("");
            isUpdated = items.get(position).getTime().equals(text);
        }

        items.get(position).setTime(text);
        if (!isUpdated) {
            //items.get(position).setDraggedMsg("Updated");

        }

        refresh();
    }

    public void updateList(int position)
    {
        for (ShiftItems item : items) {
            item.setDraggedMsg("");
        }
        items.get(position).setDraggedMsg("Updated");
        refresh();
    }

    public List<ShiftItems> getAllItems() {
        return items;
    }

    public void refresh()
    {
        Collections.sort(items, new Comparator<ShiftItems>() {
            @Override
            public int compare(ShiftItems lhs, ShiftItems rhs) {
                return lhs.getTime().compareTo(rhs.getTime());
            }
        });

        notifyDataSetChanged();
    }

    public void refresh(List<ShiftItems> list) {
        items = list;

    }

    private String[] splitName(String name) {

        String[] names = name.split(" ");
        return names;
    }

    private void setPriority(TextView tvPriority, String action ){

        if (action.equals("Dress wounds")) {
            tvPriority.setText("D");
        }
        else if (action.equals("Administer medication")){
            tvPriority.setText("U");
            tvPriority.setBackgroundColor(Color.BLUE);
        }
        else if (action.equals("Initial Assessment")){
            tvPriority.setText("S");
            tvPriority.setBackgroundColor(Color.BLUE);
        }
    }

    private void setStatusIcon(ImageView tvStatus, String status){

        if (status.equals("submitted")) {
            tvStatus.setImageResource(R.mipmap.ic_finish_visit);
        }
        else if (status.equals("new")){
            tvStatus.setImageResource(R.mipmap.ic_new);
        }
        else if (status.equals("in progress")){
            tvStatus.setImageResource(R.mipmap.ic_inprogress);
        }
    }
}
