package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverActivity extends AppCompatActivity
{
    Button but1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        VehicleDetailsButton();
        AllJobsButton();
    }


    public void VehicleDetailsButton()
    {
       but1 = findViewById(R.id.VehicleDetails);
       but1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(DriverActivity.this, VehicleDetailsActivity.class);
                startActivity(home);
            }
        });

    }

    public void AllJobsButton()
    {
        but1 = findViewById(R.id.AllJobs);
        but1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(DriverActivity.this, AllAvaliableJobsActivity.class);
                startActivity(home);
            }
        });

    }




}
