package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {

    Button tempButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        RequestCourierButton();
    }

    public void RequestCourierButton() {
        tempButton = findViewById(R.id.requestButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(UserActivity.this, RequestCourier.class);
                startActivity(home);
            }
        });

    }
}
