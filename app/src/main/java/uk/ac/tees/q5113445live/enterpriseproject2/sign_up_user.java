package uk.ac.tees.q5113445live.enterpriseproject2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class sign_up_user extends AppCompatActivity
{
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    public static final int GET_FROM_GALLERY = 3;
    private  Bitmap bitmap = null;
    private ImageView testImage = null;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        setContentView(R.layout.activity_sign_up);

        final EditText emailEdit = findViewById(R.id.enterEmail);
        final EditText passEdit = findViewById(R.id.enterPass);
        final EditText nameEdit = findViewById(R.id.enterName);
        final EditText locEdit = findViewById(R.id.enterLoc);
        final EditText numEdit = findViewById(R.id.enterNumber);
        //final Switch userSwitch = findViewById(R.id.userSwitch);
        final Button addImage = findViewById(R.id.addImage);
        testImage = findViewById(R.id.testImage);

//        //userSwitch.setChecked(false);
//        userSwitch.setOnClickListener(new Switch.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                boolean check = userSwitch.isChecked();
//                System.out.println("Value of switch" + check);
//                //changeUserType(v);
//            }
//        });

        addImage.setOnClickListener(new Switch.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                addPicture();

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
                           User user = new User
                           (
                               nameEdit.getText().toString(),
                               emailEdit.getText().toString(),
                               locEdit.getText().toString(),
                               numEdit.getText().toString()
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();

            try
            {
                testImage.setImageBitmap
                (MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                selectedImage));
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private void changeHome()
    {
        Intent home = new Intent(sign_up_user.this, NavigationDrawer.class);
        startActivity(home);
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
                            uploadProfile();

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
    private void uploadProfile()
    {
        if(selectedImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = mStorageRef.child("images/"+ mAuth.getCurrentUser().getUid());
            ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(sign_up_user.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            changeHome();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(sign_up_user.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }

    private void addPicture()
    {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                GET_FROM_GALLERY
        );

    }
//    private void changeUserType(View v)
//    {
//        Intent intent;
//        intent = new Intent(this, sign_up_driver.class);
//        startActivity(intent);
//        finish();
//    }
    private void newUser(User user, String id)
    {
        mDatabase.child("users").child(id).setValue(user);
    }




    private void updateUI(FirebaseUser currentUser)
    {
        if(currentUser != null)
        {

        }
    }
}
