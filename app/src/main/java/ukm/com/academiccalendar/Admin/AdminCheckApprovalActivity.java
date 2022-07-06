package ukm.com.academiccalendar.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import ukm.com.academiccalendar.R;
import ukm.com.academiccalendar.ViewHolder.ApplicationViewHolder;

public class AdminCheckApprovalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedEventRef;
    private EditText userInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_check_approval);

        unverifiedEventRef = FirebaseDatabase.getInstance().getReference().child("Applicant");

        recyclerView = findViewById(R.id.admin_approval_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Applicant> options =
                new FirebaseRecyclerOptions.Builder<Applicant>()
                        .setQuery(unverifiedEventRef.orderByChild("status").equalTo("0"), Applicant.class).build();


        FirebaseRecyclerAdapter<Applicant, ApplicationViewHolder> adapter =
                new FirebaseRecyclerAdapter<Applicant,ApplicationViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position, @NonNull final Applicant model) {

                        holder.applyEventName.setText(model.getApplicationName());
                        holder.applyEventMatric.setText(model.getApplicationMatric());
                        holder.viewEventName.setText(model.getApplicationEvent());
                        holder.applyEventDate.setText(model.getDate());
                        holder.applyEventTime.setText(model.getTime());
                        holder.viewEventDetails.setText(model.getApplicationDescription());


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                final String applicationID = model.getAid();
                                final String applicationEvent = model.getApplicationEvent();

                                CharSequence options []= new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckApprovalActivity.this);
                                builder.setTitle("Do you want to approve this request?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int position) {
                                        if(position == 0)
                                        {
                                            ApproveApplication(applicationID, applicationEvent);
                                        }
                                        if (position == 1)
                                        {


                                            NotApproveApplication(applicationID, applicationEvent);

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }
                    @NonNull
                    @Override
                    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_application_view_holder, parent, false);
                        ApplicationViewHolder holder = new ApplicationViewHolder(view);
                        return holder;

                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }




    private void ApproveApplication(String applicationID,String applicationEvent)
    {


        unverifiedEventRef.child(applicationID).child("status").setValue(applicationEvent+"_1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(AdminCheckApprovalActivity.this, "Approved", Toast.LENGTH_SHORT).show();




            }
        });

        unverifiedEventRef.child(applicationID).child("eventstatus").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(AdminCheckApprovalActivity.this, "Approved", Toast.LENGTH_SHORT).show();




            }
        });
    }

    private void NotApproveApplication(final String applicationID, final String applicationEvent) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckApprovalActivity.this);
        builder.setTitle("Reason?");
        builder.setMessage("Please fill in the reason");

        userInput = new EditText(this);
        builder.setView(userInput);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = userInput.getText().toString();

                unverifiedEventRef.child(applicationID).child("eventstatus").setValue("Not Approved. " + txt);
                unverifiedEventRef.child(applicationID).child("status").setValue(applicationEvent+"_0");
                Toast.makeText(AdminCheckApprovalActivity.this, "Thank you for your reply.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


            }
}
