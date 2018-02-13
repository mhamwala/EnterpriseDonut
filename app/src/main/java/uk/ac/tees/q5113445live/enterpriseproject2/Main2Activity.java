package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity
{
    public static final String EXTRA_EMAIL ="uk.ac.tees.q5113445.enterpriseproject2.EMAIL";
    public static final String EXTRA_PASS ="uk.ac.tees.q5113445.enterpriseproject2.PASS";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView email = findViewById(R.id.testEmail);
        //TextView pass =findViewById(R.id.testPass);
        email.setText(getEmail());
        //pass.setText(getPass());
    }

    private String getEmail()
    {
        Intent intent = getIntent();
        String email = intent.getStringExtra(login_activity.EXTRA_EMAIL);
        return email;
    }
    private String getPass()
    {
        Intent intent = getIntent();
        String pass = intent.getStringExtra(login_activity.EXTRA_PASS);
        return pass;
    }
}
