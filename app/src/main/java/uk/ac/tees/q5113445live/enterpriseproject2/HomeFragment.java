package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
 * Fragment to show the home screen. This is the landing page of the app once a user has been logged
 * in.
 */
public class HomeFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //region Firebase variables
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private DataSnapshot dataSnapshot;
    private FirebaseUser fUser;
    private DataSnapshot userData;

    private FirebaseUser user;
    //endregion

    private ImageView imageView;
    private View view;
    private TextView userRating;
    private RatingBar rateBar;
    private Button rateButton;
    private User rateUser;

    private OnFragmentInteractionListener mListener;
    public HomeFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //region Methods called upon fragment creation
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference("images").child(user.getUid());
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


    }

    //This method is for initialising buttons
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (mListener != null)
        {
            mListener.onFragmentInteraction("Home");
        }

        // Here we will can create click listners etc for all the gui elements on the fragment.
        // For eg: Button btn1= (Button) view.findViewById(R.id.frag1_btn1);
        // btn1.setOnclickListener(...

        ValueEventListener userListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                userData = dataSnapshot;
                User user = dataSnapshot.getValue(User.class);

                userRating = view.findViewById(R.id.rating);
                rateBar = view.findViewById(R.id.ratingBar);
                rateButton = view.findViewById(R.id.placeRating);
                rateUser = dataSnapshot.getValue(User.class);

                rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        //int a = rateBar.getNumStars();
                        rateUser.setRating(v);
                        Toast.makeText(getContext(), "Confirm " + v + " Rating?", Toast.LENGTH_SHORT).show();
                    }
                });

                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        updateRating(view);
                    }
                });

                TextView userText = view.findViewById(R.id.showUserName);

                //updateRating(view);

                userText.setText(user.getName());
                imageView = view.findViewById(R.id.imageView);
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
        /*
        signOutButton(view);
        settingsButton(view);
        driverButton(view);
        userButton(view);
        */
        return view;
    }
    //endregion

    public void updateRating(final View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("users").child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("rating", rateUser.getRating());
                reference.child("users").child(fUser.getUid()).updateChildren(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction("Home");
        }
    }

    //region Attach and Detach
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
    //endregion

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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void getProfileImage() throws IOException
    {
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(imageView);

    }

    //region Legacy methods for changing activities.
    /*public void userButton(View view)
    {
        tempButton = view.findViewById(R.id.userButton);
        tempButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getActivity(), UserActivity.class);
                startActivity(home);
            }
        });
    }


    public void signOutButton(View view)
    {
        tempButton = view.findViewById(R.id.signOut);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent home = new Intent(getActivity(), login_activity.class);
                startActivity(home);
            }
        });

    }

    public void settingsButton(View view)
    {
        tempButton = view.findViewById(R.id.settingsButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getActivity(), SettingsActivity.class);
                startActivity(home);
            }
        });

    }

    public void driverButton(View view)
    {
        tempButton = view.findViewById(R.id.driverButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent home = new Intent(getActivity(), DriverActivity.class);
                startActivity(home);
            }
        });

    }
    */
    //endregion
}
