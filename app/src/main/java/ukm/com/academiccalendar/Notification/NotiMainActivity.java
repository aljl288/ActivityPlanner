package ukm.com.academiccalendar.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;

import ukm.com.academiccalendar.R;

public class NotiMainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "activity_planner_id";
    private static final String CHANNEL_NAME = "activity_planner_name";
    private static final String CHANNEL_DESC = "activity_planner_noti";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_main);
    }

    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);

    }
}
