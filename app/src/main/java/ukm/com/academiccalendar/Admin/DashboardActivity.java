package ukm.com.academiccalendar.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ukm.com.academiccalendar.R;

public class DashboardActivity extends AppCompatActivity {

    AnyChartView anyChartView;
    String months[] = {"January", "February", "March", "April", "May" , "June", "July", "August", "September", "October", "November", "December"};
    int earnings[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        DatabaseReference checkFBvalue = FirebaseDatabase.getInstance().getReference().child("Applications");
        checkFBvalue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int xJanuary = (int) dataSnapshot.child("January").getChildrenCount();
                int xFebruary = (int) dataSnapshot.child("February").getChildrenCount();
                int xMarch = (int) dataSnapshot.child("March").getChildrenCount();
                int xApril = (int) dataSnapshot.child("April").getChildrenCount();
                int xMay = (int) dataSnapshot.child("May").getChildrenCount();
                int xJune = (int) dataSnapshot.child("June").getChildrenCount();
                int xJuly = (int) dataSnapshot.child("July").getChildrenCount();
                int xAugust = (int) dataSnapshot.child("August").getChildrenCount();
                int xSeptember = (int) dataSnapshot.child("September").getChildrenCount();
                int xOctober = (int) dataSnapshot.child("October").getChildrenCount();
                int xNovember = (int) dataSnapshot.child("November").getChildrenCount();
                int xDecember = (int) dataSnapshot.child("December").getChildrenCount();

                int earnings[] = {xJanuary, xFebruary, xMarch, xApril, xMay, xJune, xJuly, xAugust, xSeptember, xOctober, xNovember, xDecember};



                anyChartView = findViewById(R.id.pie);


                Pie pie = AnyChart.pie();
                List<DataEntry> dataentries = new ArrayList<>();

                for (int i = 0; i<months.length; i++){
                    dataentries.add(new ValueDataEntry(months[i], earnings[i]));

                }

                pie.data(dataentries);
                anyChartView.setChart(pie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
