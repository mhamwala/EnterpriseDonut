package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        Button requestService = findViewById(R.id.button5);

        requestService.setOnClickListener(new Button.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(View v)
                                                    {
                        try
                        {
                            Delivery delivery = new Delivery(
                                            deliveryType.getText().toString(),
                                            distance.getText().toString(),
                                            size.getText().toString(),
                                            weight.getText().toString(),
                                            pay.getText().toString()
                                    );
                           // uploadCourierRequest(delivery);
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("INSIDE NUMBER FORMAT EXCEPTION");
                        }
                    }
                }
        );


    }
/*
    private void uploadCourierRequest(final Delivery deliveryIn)
    {
        Log.d(TAG, "createAccount:" + userIn.getEmail());
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(userIn.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            newUser(userIn, user.getUid());
                            updateUI(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(sign_up_user.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }
*/
}
