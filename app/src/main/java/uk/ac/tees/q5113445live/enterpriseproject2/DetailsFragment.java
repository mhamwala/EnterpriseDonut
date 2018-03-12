package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private FirebaseUser fUser;
    private ImageView imageView;
    private View view;
    private DataSnapshot userData;
    private TextView userName;
    private TextView userNumber;
    private TextView userLocation;
    private TextView userEmail;
    private TextView userReg;


    private OnFragmentInteractionListener mListener;

    public DetailsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        if (mListener != null)
        {
            mListener.onFragmentInteraction("My details");
        }


        super.onCreate(savedInstanceState);


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference("images").child(fUser.getUid());

        if (getArguments() != null)

        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        ValueEventListener userListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                userData = dataSnapshot;
                User user = userData.getValue(User.class);
                nameText(user,view);
                numText(user,view);
                locationText(user,view);
                emailText(user, view);
                regText(user, view);

                System.out.println(user);
                //TextView userText = view.findViewById(R.id.showUserName);
                //userText.setText(fUser.getName());
                imageView = view.findViewById(R.id.imageView);
                try
                {
                    getProfileImage();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                TextView testingUpdate = view.findViewById(R.id.updateDetails);
                testingUpdate.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        updateButton(v);
                    }
                });


                TextView testingUpdateDriver = view.findViewById(R.id.driverUpdate);
                final TextView showReg = view.findViewById(R.id.showRegistration);
                final TextView showRegText = view.findViewById(R.id.registrationNum);
                testingUpdateDriver.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                          showReg.setVisibility(View.VISIBLE);
                          showRegText.setVisibility(View.VISIBLE);
                          updateDriverButton(view);
                    }
                });


        }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        mDatabase.addListenerForSingleValueEvent(userListener);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String title)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void nameText(User user, View view)
    {
        userName = view.findViewById(R.id.showUserName);
        userName.setText(user.getName());
    }
    public void numText(User user, View view)
    {
        userNumber = view.findViewById(R.id.showUserNumber);
        userNumber.setText(user.getNumber());
    }
    public void locationText(User user, View view)
    {
        userLocation = view.findViewById(R.id.showUserLocation);
        userLocation.setText(user.getLocation());
    }
    public void emailText(User user, View view)
    {
        userEmail = view.findViewById(R.id.showUserEmail);
        userEmail.setText(user.getEmail());
    }
    public void regText(User user, View view)
    {
        userReg = view.findViewById(R.id.showRegistration);
        userReg.setText(user.getRegNumber());
    }

    public void updateButton(final View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("users").child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User user = dataSnapshot.getValue(User.class);
                HashMap<String, Object> result = new HashMap<>();
                result.put("name", userName.getText().toString());
                result.put("location", userLocation.getText().toString());
                result.put("email", userEmail.getText().toString());
                result.put("number", userNumber.getText().toString());
                result.put("regNumber", userReg.getText().toString());
                reference.child("users").child(fUser.getUid()).updateChildren(result);
                fUser.updateEmail(userEmail.getText().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);

            }
        });
    }

    public void updateDriverButton(final View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("users").child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                HashMap<String, Object> result = new HashMap<>();
                result.put("driver", true);
                reference.child("users").child(fUser.getUid()).updateChildren(result);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);

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
