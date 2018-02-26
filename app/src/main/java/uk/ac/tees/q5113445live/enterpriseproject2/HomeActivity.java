package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends NavigationDrawer
{
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private FirebaseUser user;
    private ImageView imageView;

    Button tempButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference("images").child(user.getUid());

        ValueEventListener userListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(user);
                TextView userText = findViewById(R.id.showUserName);
                userText.setText(user.getName());
                imageView = findViewById(R.id.imageView);
                try
                {
                    getProfileImage();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        mDatabase.addListenerForSingleValueEvent(userListener);


        String name = mDatabase.getKey();
        //requestCourierButton();
        signOutButton();
        settingsButton();
        driverButton();
        userButton();
    }

    public void userButton()
    {
        tempButton = findViewById(R.id.userButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(home);
            }
        });

    }

    public void signOutButton()
    {
        tempButton = findViewById(R.id.signOut);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent home = new Intent(HomeActivity.this, login_activity.class);
                startActivity(home);
            }
        });

    }

    public void settingsButton() {
        tempButton = findViewById(R.id.settingsButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(home);
            }
        });

    }

    public void driverButton()
    {
        tempButton = findViewById(R.id.driverButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(HomeActivity.this, DriverActivity.class);
                startActivity(home);
            }
        });

    }
    public void getProfileImage() throws IOException
    {
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(imageView);
    }
}
