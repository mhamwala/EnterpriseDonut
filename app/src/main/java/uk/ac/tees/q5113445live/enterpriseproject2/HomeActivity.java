package uk.ac.tees.q5113445live.enterpriseproject2;

import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
{
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        ValueEventListener userListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(user);
                TextView userText = findViewById(R.id.showUserEmail);
                userText.setText(user.getName());


                System.out.println("HELLO");
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
