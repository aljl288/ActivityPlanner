package ukm.com.academiccalendar.ViewHolder;

import android.annotation.SuppressLint;
import android.view.View;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import ukm.com.academiccalendar.Interface.itemClickListner;
import ukm.com.academiccalendar.R;

public class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView applyEventName, applyEventMatric, viewEventDetails, viewEventName, applyEventDate, applyEventTime, eventStatus;

    public itemClickListner listner;

    @SuppressLint("WrongViewCast")
    public ApplicationViewHolder(View itemView) {

        super(itemView);

        applyEventName = (TextView) itemView.findViewById(R.id.apply_event_name);
        applyEventMatric = (TextView) itemView.findViewById(R.id.apply_event_matric);
        applyEventDate = (TextView) itemView.findViewById(R.id.apply_event_date);
        applyEventTime = (TextView) itemView.findViewById(R.id.apply_event_time);
        viewEventName = (TextView) itemView.findViewById(R.id.view_event_name);
        viewEventDetails = (TextView) itemView.findViewById(R.id.view_event_description);
        eventStatus =  (TextView) itemView.findViewById(R.id.view_event_status);
    }

    public void setItemClickListner(itemClickListner listner)
    {
        this.listner = listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(),false);

    }
}