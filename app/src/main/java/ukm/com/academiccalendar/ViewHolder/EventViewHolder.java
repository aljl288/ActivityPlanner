package ukm.com.academiccalendar.ViewHolder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ukm.com.academiccalendar.Interface.itemClickListner;
import ukm.com.academiccalendar.R;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtEventName, txtEventDescription, txtEventDate;
    public ImageView imageView;
    public itemClickListner listner;

    @SuppressLint("WrongViewCast")
    public EventViewHolder(View itemView) {

        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.app_event_image);
        txtEventName = (TextView) itemView.findViewById(R.id.app_event_name);
        txtEventDescription = (TextView) itemView.findViewById(R.id.app_event_description);
        txtEventDate = (TextView) itemView.findViewById(R.id.app_event_date);
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