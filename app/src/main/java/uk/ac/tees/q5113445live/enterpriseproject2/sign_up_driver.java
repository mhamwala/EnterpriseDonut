package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Luke on 15/02/2018.
 */

public class sign_up_driver extends AppCompatActivity
{
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Place location;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_driver_sign_up);

        final EditText emailEdit = findViewById(R.id.enterEmail);
        final EditText passEdit = findViewById(R.id.enterPass);
        final EditText regEdit = findViewById(R.id.enterReg);
        final EditText nameEdit = findViewById(R.id.enterName);
        //final EditText locEdit = findViewById(R.id.enterLoc);
        final EditText numEdit = findViewById(R.id.enterNumber);
        final TextView wallEdit = findViewById((R.id.nav_wallet));
        autocomplete();

        final Switch userSwitch = findViewById(R.id.userSwitch);
        userSwitch.setOnClickListener(new Switch.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean check = userSwitch.isChecked();
                System.out.println("Value of switch" + check);
                changeUserType(v);
            }
        });

        Button signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new Button.OnClickListener()
            {
                  @Override
                  public void onClick(View v)
                {
                      try
                      {
                          //List<String> location = stringManip(locEdit.getText().toString());

                          User user = new User
                                  (
                                          nameEdit.getText().toString(),
                                          emailEdit.getText().toString(),
                                          numEdit.getText().toString(),
                                          location,
                                          "50"
                                  );
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
    private void changeUserType(View v)
    {
        Intent intent;
        intent = new Intent(this, sign_up_user.class);
        startActivity(intent);
        finish();
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
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            newUser(userIn, user.getUid());
                            changeHome();
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(sign_up_driver.this, "Authentication failed.",
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
            changeHome();
        }
    }
    private void changeHome()
    {
        Intent home = new Intent(sign_up_driver.this, NavigationDrawer.class);
        startActivity(home);
    }
    private List<String> stringManip(String locEdit)
    {
        List<String> location = null;

        String houseNumber = locEdit.substring(0,locEdit.indexOf(" "));
        locEdit.replace(houseNumber, "");
        String postCode = locEdit.substring(0, locEdit.indexOf(" "));
        locEdit.replace(postCode, "");
        String city = locEdit.substring(0, locEdit.indexOf(" "));
        location.add(houseNumber);
        location.add(postCode);
        location.add(city);

        return location;
    }
    private void autocomplete()
    {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.enterLoc);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(Place place)
            {
                // TODO: Get info about the selected location.
                location = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}
