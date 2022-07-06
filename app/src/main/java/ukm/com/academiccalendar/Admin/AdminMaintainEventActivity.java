package ukm.com.academiccalendar.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import ukm.com.academiccalendar.R;

public class AdminMaintainEventActivity extends AppCompatActivity {


    private Button applyChangesBtn, deleteBtn;
    private TextView eventdate;
    private EditText ename, description;
    private ImageView imageView;

    private String eventID ="";
    private DatabaseReference eventsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_maintain_event);

        eventID = getIntent().getStringExtra("ename");
        eventsRef = FirebaseDatabase.getInstance().getReference().child("Events").child(eventID);


        applyChangesBtn = findViewById(R.id.apply_changes_btn);
        deleteBtn = findViewById(R.id.delete_event_btn);
        ename = findViewById(R.id.event_name_maintain);
        eventdate = findViewById(R.id.event_date_maintain);
        description = findViewById(R.id.event_description_maintain);
        imageView = findViewById(R.id.event_image_maintain);

        final Calendar myCalendar = Calendar.getInstance();

        final TextView edittext= (TextView) findViewById(R.id.event_date_maintain);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "d-M-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AdminMaintainEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        displaySpecificEventInfo();






        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChanges();
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteThisProduct();
            }
        });
    }





    private void deleteThisProduct()
    {
        eventsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Intent intent = new Intent(AdminMaintainEventActivity.this, EventActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainEventActivity.this, "The Event Is deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void applyChanges()
    {
        String eName = ename.getText().toString();
        String eDate = eventdate.getText().toString();
        String eDescription = description.getText().toString();

        if (eName.equals(""))
        {
            Toast.makeText(this, "Write down Event Name.", Toast.LENGTH_SHORT).show();
        }
        else if (eDate.equals(""))
        {
            Toast.makeText(this, "Write down Event Date.", Toast.LENGTH_SHORT).show();
        }
        else if (eDescription.equals(""))
        {
            Toast.makeText(this, "Write down Event Description.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("eid", eventID);
            productMap.put("description", eDescription);
            productMap.put("eventdate", eDate);
            productMap.put("ename", eName);

            eventsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainEventActivity.this, "Changes applied successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AdminMaintainEventActivity.this, EventActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }





    private void displaySpecificEventInfo()
    {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String eName = dataSnapshot.child("ename").getValue().toString();
                    String eDate = dataSnapshot.child("eventdate").getValue().toString();
                    String eDescription = dataSnapshot.child("description").getValue().toString();
                    String eImage = dataSnapshot.child("image").getValue().toString();


                    ename.setText(eName);
                    eventdate.setText(eDate);
                    description.setText(eDescription);
                    Picasso.get().load(eImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}