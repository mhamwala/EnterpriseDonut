package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestCourier extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_courier);

        final EditText deliveryType = findViewById(R.id.deliveryType);
        final EditText distance = findViewById(R.id.distance);
        final EditText size = findViewById(R.id.size);
        final EditText weight = findViewById(R.id.weight);
        final EditText pay = findViewById(R.id.pay);

    }
}
