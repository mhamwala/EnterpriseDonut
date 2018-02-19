package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserDetailsActivity extends AppCompatActivity
{
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        ValueEventListener userListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(user);
                TextView userName = findViewById(R.id.showUserName);
                userName.setText(user.getName());
                TextView userNumber = findViewById(R.id.showUserNumber);
                userNumber.setText(user.getNumber());
                TextView userLocation = findViewById(R.id.showUserLocation);
                userLocation.setText(user.getLocation());
                TextView userEmail = findViewById(R.id.showUserEmail);
                userEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        mDatabase.addListenerForSingleValueEvent(userListener);


        String name = mDatabase.getKey();
    }
    }




