package ukm.com.academiccalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    private String Fdescription, Fcontact, Fname, Fename,saveCurrentDate, saveCurrentTime;
    Spinner SpinnerName;
    private Button submitFeedback;
    private EditText InputFeedbackName, InputFeedbackDescription, InputFeedbackContact;

    private String eventRandomKey;
    private DatabaseReference FeedbacksRef;
    private ProgressDialog loadingBar;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_submit_feedback);



        FeedbacksRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

        submitFeedback = (Button) findViewById(R.id.send_feedback);
        InputFeedbackName =(EditText) findViewById(R.id.feedback_name);
        InputFeedbackDescription = (EditText) findViewById(R.id.feedback_description);
        InputFeedbackContact = (EditText) findViewById(R.id.contact_number);
        SpinnerName = (Spinner) findViewById(R.id.event_name_spinner);
        loadingBar = new ProgressDialog(this);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events");
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(FeedbackActivity.this,android.R.layout.simple_spinner_dropdown_item, spinnerDataList);

        SpinnerName.setAdapter(adapter);
        retrieveData();

        submitFeedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                ValidateEventData();
            }
        });
    }

    public void retrieveData(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item:dataSnapshot.getChildren()){
                    String eventName = item.child("ename").getValue(String.class);

                    spinnerDataList.add(eventName);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void ValidateEventData()
    {
        Fname = InputFeedbackName.getText().toString();
        Fcontact = InputFeedbackContact.getText().toString();
        Fdescription = InputFeedbackDescription.getText().toString();
        Fename = SpinnerName.getSelectedItem().toString();




        if (TextUtils.isEmpty(Fname))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Fcontact))
        {
            Toast.makeText(this, "Please write your contact number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Fdescription))
        {
            Toast.makeText(this, "Please write your feedback...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }



    private void StoreProductInformation()
    {
        loadingBar.setTitle("Sending Feedback");
        loadingBar.setMessage("Please wait a moment");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        eventRandomKey = saveCurrentDate + saveCurrentTime;
        SaveProductInfoToDatabase();
    }



    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("fid", eventRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("fdescription", Fdescription);
        productMap.put("feventname", Fename);
        productMap.put("fcontactnumber", Fcontact);
        productMap.put("fname", Fname);

        FeedbacksRef.child(eventRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(FeedbackActivity.this, "Send successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(FeedbackActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}