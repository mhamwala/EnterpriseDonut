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

public class sign_up extends AppCompatActivity
{
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final EditText emailEdit = findViewById(R.id.enterEmail);
        final EditText passEdit = findViewById(R.id.enterPass);
        final EditText nameEdit = findViewById(R.id.enterName);
        final EditText locEdit = findViewById(R.id.enterLoc);
        final EditText numEdit = findViewById(R.id.enterNumber);

        Button signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new Button.OnClickListener()
               {
                   @Override
                   public void onClick(View v)
                   {

                       try
                       {
                           User user = new User();
                           user.setEmail( emailEdit.getText().toString());
                           user.setName(nameEdit.getText().toString());
                           user.setLocation(locEdit.getText().toString());
                           double number = Double.parseDouble(numEdit.getText().toString());
                           user.setNumber(number);
                           String password = passEdit.getText().toString();
                           createAccount(user, password);
                       }
                       catch (NumberFormatException e)
                       {
                           System.out.println("INSIDE NUMBER FORMAT EXCEPTION");
                       }



                   }
               }
        );
    }
    private void newUser(User user, String id)
    {
        mDatabase.child("users").child(id).setValue(user);
    }

    private void createAccount(final User userIn, String password)
    {
        Log.d(TAG, "createAccount:" + userIn.getEmail());
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(userIn.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
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
                            Toast.makeText(sign_up.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser)
    {
        if(currentUser != null)
        {

        }
    }
}
