package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Following activity allows users to login to the app using Firebase Authentication.
 */
public class login_activity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    public static final String EXTRA_EMAIL ="uk.ac.tees.q5113445.enterpriseproject2.EMAIL";
    public static final String EXTRA_PASS ="uk.ac.tees.q5113445.enterpriseproject2.PASS";

    public Button but1;

    @Override
    public void onStart()
    {
        super.onStart();
        //Check if user is signed if (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //User is passed in to decide what activity to change to.
    private void updateUI(FirebaseUser currentUser)
    {
        if(currentUser != null)
        {
            Intent home = new Intent(login_activity.this,
            NavigationDrawer.class);
            startActivity(home);
            finish();
        }
        else
        {
            //Show message saying if Email password doesn't exist or other errors.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailEdit = findViewById(R.id.usernameText);
        final EditText passEdit = findViewById(R.id.passwordText);

        //region Buttons and listeners. Forgot Password, Sign In and Sign up.
        TextView forgotPass = findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO:Create code for sending email to reset password.
               //Starts the forgot password activity.
            }

        });

        TextView newUser = this.findViewById(R.id.notMember);
        newUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Starts the sign_up_user activity
                signUp(view);

            }

        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = emailEdit.getText().toString();
                String password = passEdit.getText().toString();
                signIn(email,password);
            }
        }
        );
        //endregion


        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Starts the sign up activity.
     * @param view
     */
    public void signUp(View view)
    {
        Intent intent;
        intent = new Intent(this, sign_up_user.class);
        startActivity(intent);
    }
    private void signIn(String email, String password) //this uses the users stored username and password to sign in, if any are wrong it notifies user
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(login_activity.this, "LOGGING ON...",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            // once log in is successful sends user to landing page. nathan
                            Intent home = new Intent(login_activity.this, NavigationDrawer.class);
                            startActivity(home);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login_activity.this, "Incorrect email or password.\nPlease try again!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }



}


