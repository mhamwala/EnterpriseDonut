package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyAdvertDetailsActivity extends AppCompatActivity {

    private ViewAdvertFragment test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advert_details);
        test = new ViewAdvertFragment();

//        final String a = test.viewAdvertDetails();
      //  System.out.println("YEAHHH its ya boyy "+a);
    }


}
