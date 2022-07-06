package ukm.com.academiccalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import ukm.com.academiccalendar.Model.Events;

import com.squareup.picasso.Picasso;

import static ukm.com.academiccalendar.Prevalent.Prevalent.currentOnlineUser;


public class EventRegisterActivity extends AppCompatActivity {

    private static final Pattern MATRIC_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{7,8}" +               //at least 7 characters,most 8
                    "$");

    private String saveCurrentDate, saveCurrentMonth, eventRandomKeyforcalendar,eventRandomKey,eventRandomKey1, saveCurrentTime;

    private Button registerEventButton;
    private ImageView eventImage;
    private TextView eventDate, eventDescription, eventName;
    private EditText username, usermatric, userdescription, userevent;
    private String eventID = "";
    private String Ename, Edate;
    private DatabaseReference eventsRef;
    private Events model;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_event);

        eventID = getIntent().getStringExtra("ename");


        registerEventButton = (Button) findViewById(R.id.register_event_button);
        eventImage = (ImageView) findViewById(R.id.event_image_details);
        eventName = (TextView) findViewById(R.id.event_name_details);
        eventDate = (TextView) findViewById(R.id.event_date_details);
        eventDescription = (TextView) findViewById(R.id.event_description_details);
        username = (EditText) findViewById(R.id.event_register_name);
        usermatric = (EditText) findViewById(R.id.event_register_matric);
        userdescription = (EditText) findViewById(R.id.event_register_description);
        loadingBar = new ProgressDialog(this);

        getEventDetails(eventID);





        registerEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                regitserEvent();
            }
        });

    }
    private boolean validateDescription() {
        String descriptionInput = userdescription.getText().toString().trim();
        if (descriptionInput.isEmpty()) {
            userdescription.setError("Field can't be empty");
            return false;
        } else if (descriptionInput.length() > 30) {
            userdescription.setError("Exceed Limit");
            return false;
        } else {
            userdescription.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = username.getText().toString().trim();
        if (nameInput.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if (nameInput.length() > 30) {
            username.setError("Exceed Limit");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validateMatric() {
        String matricInput = usermatric.getText().toString().trim();
        if (matricInput.isEmpty()) {
            usermatric.setError("Field can't be empty");
            return false;
        } else if (!MATRIC_PATTERN.matcher(matricInput).matches()) {
            usermatric.setError("Invalid Matric Format");
            return false;
        } else {
            usermatric.setError(null);
            return true;
        }
    }


    private void regitserEvent() {

        String name = username.getText().toString();
        String matric = usermatric.getText().toString();
        String description = userdescription.getText().toString();



        if(!validateMatric()|!validateName()|!validateDescription()){
            return;
        }
        else{
            loadingBar.setTitle("Sending Request");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail2(matric,name,description);
            ValidateEmail(matric);
            calendarview();

        }

    }

    private void calendarview() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(currentOnlineUser.getMatric());

        final String eventdate = Edate;
        final String event = Ename;
        String matric = currentOnlineUser.getMatric();

        eventRandomKeyforcalendar = matric+eventdate;




        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("eventname", event);
                userdataMap.put("date", eventdate);
                userdataMap.put("status", "0");

                RootRef.child("Calendar").child(eventRandomKeyforcalendar).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Intent intent = new Intent(EventRegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }


                                else {
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ValidateEmail2(final String matric, final String name,final String description) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentMonth = new SimpleDateFormat("MMMM");
        saveCurrentMonth = currentMonth.format(calendar.getTime());

        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM, dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());


        eventRandomKey1 = saveCurrentDate+saveCurrentTime;

        final String event = Ename;

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Applicant").child(matric).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("aid", eventRandomKey1);
                    userdataMap.put("applicationMatric", matric);
                    userdataMap.put("applicationName", name);
                    userdataMap.put("applicationDescription", description);
                    userdataMap.put("date", saveCurrentDate);
                    userdataMap.put("time", saveCurrentTime);
                    userdataMap.put("status", "0");
                    userdataMap.put("eventstatus", "Not Approved");
                    userdataMap.put("applicationEvent", event);




                    RootRef.child("Applicant").child(eventRandomKey1).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(EventRegisterActivity.this,"Please Wait For Approval", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(EventRegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }


                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(EventRegisterActivity.this, "Network Error: Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{

                    Toast.makeText(EventRegisterActivity.this, matric+" already exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(EventRegisterActivity.this, "Please try again using another matric.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EventRegisterActivity.this,HomeActivity.class);



                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void ValidateEmail(final String matric){

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentMonth = new SimpleDateFormat("MMMM");
        saveCurrentMonth = currentMonth.format(calendar.getTime());

        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM, dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        eventRandomKey = saveCurrentMonth;
        final String event = Ename;

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Applications").child(matric).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("aid", eventRandomKey);
                    userdataMap.put("applicationMatric", matric);


                    RootRef.child("Applications").child( eventRandomKey ).child(eventRandomKey1).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(EventRegisterActivity.this,"Please Wait For Approval", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(EventRegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }


                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(EventRegisterActivity.this, "Network Error: Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{

                    Toast.makeText(EventRegisterActivity.this, matric+" already exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(EventRegisterActivity.this, "Please try again using another matric.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EventRegisterActivity.this,HomeActivity.class);



                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





    private void getEventDetails(String eventID) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        eventsRef.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Ename = dataSnapshot.child("ename").getValue().toString();
                    Edate = dataSnapshot.child("eventdate").getValue().toString();

                    Events events = dataSnapshot.getValue(Events.class);

                    eventName.setText(events.getEname());
                    eventDate.setText(events.getEventdate());
                    eventDescription.setText(events.getDescription());
                    Picasso.get().load(events.getImage()).into(eventImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}