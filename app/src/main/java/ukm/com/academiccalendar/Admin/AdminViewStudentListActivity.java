package ukm.com.academiccalendar.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import ukm.com.academiccalendar.Model.Applicant;
import ukm.com.academiccalendar.Model.Applications;
import ukm.com.academiccalendar.R;
import ukm.com.academiccalendar.ViewHolder.ApplicationViewHolder;

public class AdminViewStudentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedEventRef;
    private String eventID = "";
    private String Ename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_student_list);

        eventID = getIntent().getStringExtra("eid");


        unverifiedEventRef = FirebaseDatabase.getInstance().getReference().child("Applicant");

        recyclerView = findViewById(R.id.student_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");
        eventsRef.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Ename = dataSnapshot.child("ename").getValue().toString();

                }


                FirebaseRecyclerOptions<Applicant> options =
                        new FirebaseRecyclerOptions.Builder<Applicant>()
                                .setQuery(unverifiedEventRef.orderByChild("status").equalTo(Ename+"_1"), Applicant.class).build();
                Toast.makeText(AdminViewStudentListActivity.this, Ename, Toast.LENGTH_SHORT).show();


                FirebaseRecyclerAdapter<Applicant, ApplicationViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Applicant, ApplicationViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position, @NonNull final Applicant model) {

                                holder.applyEventName.setText(model.getApplicationName());
                                holder.applyEventMatric.setText(model.getApplicationMatric());
                                holder.viewEventName.setText(model.getApplicationEvent());



                            }

                            @NonNull
                            @Override
                            public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_student_list, parent, false);
                                ApplicationViewHolder holder = new ApplicationViewHolder(view);
                                return holder;


                            }
                        };
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}










