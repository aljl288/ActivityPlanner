package ukm.com.academiccalendar.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ukm.com.academiccalendar.HomeActivity;
import ukm.com.academiccalendar.R;

public class EventActivity extends AppCompatActivity {

    private DatabaseReference EventsRef;

    private Button createEventBtn, maintainEventsBtn, checkApproval, approvalList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_event);

        createEventBtn = (Button) findViewById(R.id.create_event_btn);
        maintainEventsBtn = (Button) findViewById(R.id.maintain_event_btn);
        checkApproval = (Button) findViewById(R.id.check_approval_btn);

        maintainEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
                finish();
            }
        });

        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, AdminAddEventActivity.class);
                startActivity(intent);
            }
        });

        checkApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, AdminCheckApprovalActivity.class);
                startActivity(intent);
                
            }
        });




    }
}
