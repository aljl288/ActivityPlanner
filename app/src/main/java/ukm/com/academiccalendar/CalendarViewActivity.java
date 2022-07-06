package ukm.com.academiccalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

import ukm.com.academiccalendar.Prevalent.Prevalent;
import ukm.com.academiccalendar.R;

import static ukm.com.academiccalendar.Prevalent.Prevalent.currentOnlineUser;


public class CalendarViewActivity extends AppCompatActivity {
    private CalendarView calendarView;

    private TextView tvdate;
    private TextView cal;
    private String date1;
    private String eventdate;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        tvdate = findViewById(R.id.tvdate);

        cal = findViewById(R.id.calendar_details);
        calendarView = findViewById(R.id.calendar_view);






    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(currentOnlineUser.getMatric());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                ref.child(currentOnlineUser.getMatric());
                month += 1;

                date1 = dayOfMonth + "-" + month + "-" + year;
                tvdate.setText(date1);
                String name = currentOnlineUser.getMatric();

                reference = FirebaseDatabase.getInstance().getReference().child("Calendar").child(name+date1);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       try {

                        //   eventdate = "30-6-2020";
                           eventdate = dataSnapshot.child("date").getValue().toString();


                           showevent();
                       }
                       catch (Exception e){
                           eventdate = "0";
                           showevent();
                       }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


         /*
*/            }
        });


    }

    private void showevent() {

        final String event = eventdate;
        String d = event.replaceAll("\\D+", "");
        String d1 = date1.replaceAll("\\D+", "");
        int a = Integer.parseInt(d);
        int b = Integer.parseInt(d1);
        if (a == b) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String calEaten = dataSnapshot.child("eventname").getValue().toString();
                    cal.setText(calEaten);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else if (a!=b){
            cal.setText("No Event");
        }
    }
}