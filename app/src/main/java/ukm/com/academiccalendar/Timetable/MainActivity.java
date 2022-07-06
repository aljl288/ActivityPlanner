package ukm.com.academiccalendar.Timetable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ukm.com.academiccalendar.R;

public class MainActivity extends AppCompatActivity {

    CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);
    }
}
