package ukm.com.academiccalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ukm.com.academiccalendar.Admin.AdminMaintainEventActivity;
import ukm.com.academiccalendar.Admin.EventActivity;

public class MaintainEvent extends AppCompatActivity {

    private TextView eventName, eventMatric;
    private EditText eventDescription;

    private Button updateButton, deleteButton;

    private String applicantID = "";
    private DatabaseReference applicantRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_event2);

        applicantID = getIntent().getStringExtra("aid");
        applicantRef = FirebaseDatabase.getInstance().getReference().child("Applicant").child(applicantID);




        eventName = (TextView) findViewById(R.id.ename_maintain);
        eventMatric = (TextView) findViewById(R.id.ematric_maintain);
        eventDescription = (EditText) findViewById(R.id.edescription_maintain);
        updateButton = (Button) findViewById(R.id.maintain_update_btn);
        deleteButton = (Button) findViewById(R.id.maintain_delete_btn);

        displayApplicantSatus();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateApplication();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteApplication();
            }
        });

    }

    private void deleteApplication() {

        applicantRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(MaintainEvent.this, MyEvent.class);
                startActivity(intent);
                finish();

                Toast.makeText(MaintainEvent.this, "deleted successfully.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateApplication() {
        String mName = eventName.getText().toString();
        String mMatric = eventMatric.getText().toString();
        String mDescription = eventDescription.getText().toString();

        if (mName.equals(""))
        {
            Toast.makeText(this, "Write down Event Name.", Toast.LENGTH_SHORT).show();
        }
        else if (mMatric.equals(""))
        {
            Toast.makeText(this, "Write down Event Date.", Toast.LENGTH_SHORT).show();
        }
        else if (mDescription.equals(""))
        {
            Toast.makeText(this, "Write down Event Description.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("aid", applicantID);
            productMap.put("applicationEvent", mName);
            productMap.put("applicationMatric", mMatric);
            productMap.put("applicationDescription", mDescription);
            productMap.put("status", "0");

            applicantRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(MaintainEvent.this, "updated successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MaintainEvent.this, MyEvent.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    private void displayApplicantSatus() {

        applicantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String mName = dataSnapshot.child("applicationEvent").getValue().toString();
                    String mMatric = dataSnapshot.child("applicationMatric").getValue().toString();
                    String mDescription = dataSnapshot.child("applicationDescription").getValue().toString();

                    eventName.setText(mName);
                    eventMatric.setText(mMatric);
                    eventDescription.setText(mDescription);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
