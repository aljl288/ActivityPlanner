package ukm.com.academiccalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ukm.com.academiccalendar.Model.Applicant;
import ukm.com.academiccalendar.Model.Applications;
import ukm.com.academiccalendar.Prevalent.Prevalent;
import ukm.com.academiccalendar.R;
import ukm.com.academiccalendar.ViewHolder.ApplicationViewHolder;

import static ukm.com.academiccalendar.Prevalent.Prevalent.currentOnlineUser;

public class MyEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedEventRef;
    private TextView eventCalendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_event);

        unverifiedEventRef = FirebaseDatabase.getInstance().getReference().child("Applicant");

        recyclerView = findViewById(R.id.admin_approval_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        eventCalendar = findViewById(R.id.event_calendar);

        eventCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyEvent.this, CalendarViewActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(currentOnlineUser.getMatric());


        FirebaseRecyclerOptions<Applicant> options =
                new FirebaseRecyclerOptions.Builder<Applicant>()
                        .setQuery(unverifiedEventRef.orderByChild("applicationMatric").equalTo(currentOnlineUser.getMatric()), Applicant.class).build();


        FirebaseRecyclerAdapter<Applicant, ApplicationViewHolder> adapter =
                new FirebaseRecyclerAdapter<Applicant,ApplicationViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position, @NonNull final Applicant model) {

                        holder.applyEventName.setText(model.getApplicationName());
                        holder.applyEventMatric.setText(model.getApplicationMatric());
                        holder.viewEventName.setText(model.getApplicationEvent());
                        holder.applyEventDate.setText(model.getDate());
                        holder.applyEventTime.setText(model.getTime());
                        holder.eventStatus.setText(model.getEventstatus());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MyEvent.this, MaintainEvent.class);
                                intent.putExtra("aid", model.getAid());
                                startActivity(intent);

                            }
                        });

                    }
                    @NonNull
                    @Override
                    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_event_view_holder, parent, false);
                        ApplicationViewHolder holder = new ApplicationViewHolder(view);
                        return holder;

                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



}
