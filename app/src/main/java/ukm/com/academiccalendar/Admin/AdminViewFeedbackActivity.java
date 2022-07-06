package ukm.com.academiccalendar.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ukm.com.academiccalendar.Model.Feedbacks;
import ukm.com.academiccalendar.R;
import ukm.com.academiccalendar.ViewHolder.FeedbackViewHolder;

public class AdminViewFeedbackActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference EventsRef;
    private Button deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_feedback);


        recyclerView = findViewById(R.id.admin_feedback_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EventsRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks");



    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Feedbacks> options = new FirebaseRecyclerOptions.Builder<Feedbacks>().setQuery(EventsRef, Feedbacks.class).build();


        FirebaseRecyclerAdapter<Feedbacks, FeedbackViewHolder> adapter = new FirebaseRecyclerAdapter<Feedbacks, FeedbackViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position, @NonNull Feedbacks model) {
                holder.FeedbackEventName.setText(model.getFeventname());
                holder.FeedbackName.setText(model.getFname());
                holder.FeedbackContact.setText(model.getFcontactnumber());
                holder.FeedbackDescription.setText(model.getFdescription());
                holder.FeedbackDate.setText(model.getDate());
                holder.FeedbackTime.setText(model.getTime());


            }

            @NonNull
            @Override
            public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_feedback_view_holder, parent, false);
                FeedbackViewHolder holder = new FeedbackViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public void onCancelled(@NonNull DatabaseError databaseError) {

    };
}



