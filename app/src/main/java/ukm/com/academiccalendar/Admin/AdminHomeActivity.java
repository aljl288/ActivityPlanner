package ukm.com.academiccalendar.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import ukm.com.academiccalendar.MainPageActivity;
import ukm.com.academiccalendar.R;

public class AdminHomeActivity extends AppCompatActivity {

    private CardView LogoutBtn, Events, Feedbacks, Statistic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);


        LogoutBtn = (CardView) findViewById(R.id.admin_logout_btn);


        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminHomeActivity.this, "Logged out successfully...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminHomeActivity.this, MainPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


       Events = (CardView) findViewById(R.id.category_event);
       Feedbacks = (CardView) findViewById(R.id.category_feedback);
       Statistic = (CardView) findViewById(R.id.category_statistic);

       Events.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(AdminHomeActivity.this, EventActivity.class);
               startActivity(intent);
           }
       });

       Feedbacks.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(AdminHomeActivity.this, AdminViewFeedbackActivity.class);
               startActivity(intent);
           }
       });

        Statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
