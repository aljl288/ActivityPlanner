package ukm.com.academiccalendar.ViewHolder;

import android.annotation.SuppressLint;
import android.view.View;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ukm.com.academiccalendar.Interface.itemClickListner;
import ukm.com.academiccalendar.R;

public class FeedbackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView FeedbackEventName,FeedbackName, FeedbackContact, FeedbackDescription, FeedbackDate, FeedbackTime;


    public itemClickListner listner;

    @SuppressLint("WrongViewCast")
    public FeedbackViewHolder(View itemView) {

        super(itemView);

        FeedbackEventName = (TextView) itemView.findViewById(R.id.submit_feedback_eventname);
        FeedbackName = (TextView) itemView.findViewById(R.id.submit_feedback_name);
        FeedbackContact = (TextView) itemView.findViewById(R.id.submit_feedback_contact);
        FeedbackDescription = (TextView) itemView.findViewById(R.id.submit_feedback);
        FeedbackTime = (TextView) itemView.findViewById(R.id.submit_feedback_time);
        FeedbackDate = (TextView) itemView.findViewById(R.id.submit_feedback_date);
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