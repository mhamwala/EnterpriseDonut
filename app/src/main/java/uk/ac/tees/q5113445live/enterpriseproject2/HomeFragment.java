package uk.ac.tees.q5113445live.enterpriseproject2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
    private DatabaseReference aDatabase;
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
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter recycleAdapter;
    private int mColumnCount = 1;
    public static final List<Advert> ITEMS = new ArrayList<Advert>();
    public static final List<String> ADVERTID = new ArrayList<>();
    private HashMap<String, String> advertMap;
    private String userBidOn;
    private ArrayList advertKey;
    private ViewFlipper mViewFlipper;
    private float initialX;
    private Button flip;


    private OnFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter.OnListFragmentInteractionListener aListener;
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
        aDatabase = FirebaseDatabase.getInstance().getReference("advert");
        mStorageRef = FirebaseStorage.getInstance().getReference("images").child(user.getUid());

        advertMap = new HashMap<>();
        advertKey = new ArrayList();
        refresh();
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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewFlipper =  view.findViewById(R.id.homeViewFlipper);
        flip = view.findViewById(R.id.flipbutton);
        flip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mViewFlipper.showNext();
            }
        });
        checkDriver2(view);
        checkDriver(view);

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
                        Toast.makeText(getContext(), "Confirm " + v + "Rating?", Toast.LENGTH_SHORT).show();
                    }
                });

                rateButton.setOnClickListener(new View.OnClickListener()
                {
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


    public void updateRating(final View view) //allows you to give the driver a rating
    {
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
        if (context instanceof MyItemRecyclerViewAdapter.OnListFragmentInteractionListener)
        {
            aListener = (MyItemRecyclerViewAdapter.OnListFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
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

    public void getProfileImage() throws IOException //gets the users profile image if they have and displays it, if not then this area remains blank
    {
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(imageView);

    }
    private void recyclerMethod2(View view)
    {
        //Recyclers which handles the showing of items to the user.
        if (view.findViewById(R.id.list4) instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list4);
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recycleAdapter = new MyItemRecyclerViewAdapter(ITEMS,ADVERTID, aListener, 1);

            recyclerView.setAdapter(recycleAdapter);
        }

    }
    private void recyclerMethod3(View view)
    {
        //Recyclers which handles the showing of items to the user.
        if (view.findViewById(R.id.list5) instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list5);
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recycleAdapter = new MyItemRecyclerViewAdapter(ITEMS,ADVERTID, aListener, 0);

            recyclerView.setAdapter(recycleAdapter);
        }

    }

    public void checkDriver2(final View view)
    {
        aDatabase.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                boolean driverCheck = true;
                //Add each advert to a list.
                if(driverCheck)
                {
                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        for(DataSnapshot advert: child.getChildren())
                        {
                            for(DataSnapshot accepted: advert.getChildren())
                            {
                                for(DataSnapshot bid: accepted.getChildren())
                                {
                                    if (user.getUid().equals(bid.getKey()))
                                    {
                                        Advert advert2 = child.getValue(Advert.class);
                                        if (!advertKey.contains(child.getKey()))
                                        {
                                            advertKey.add(child.getKey());
                                        }
                                        if (!child.getKey().equals(user.getUid()))
                                        {
                                            userBidOn = dataSnapshot.getKey();
                                            advertMap.put(child.getKey().toString(), userBidOn);
                                        }

//                            location = getLocation(advert.from,advert.to);
//                            advert.setFrom(location.get(0));
//                            advert.setTo(location.get(1));
                                        addItem(advert2, child.getKey().toString());
                                        recyclerMethod2(view);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //region Unused Overrides
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
            //endregionfdUnu
        });
    }

    public void checkDriver(final View view)
    {
        aDatabase.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                //Add each advert to a list.
                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    if(user.getUid().equals(dataSnapshot.getKey()))
                    {
                        Advert advert = child.getValue(Advert.class);
                        if(!advertKey.contains(child.getKey()))
                        {
                            //Singular adverts
                            advertKey.add(child.getKey());
                        }
                        if (!child.getKey().equals(user.getUid()))
                        {
                            //UserBidOn = users adverts
                            userBidOn = dataSnapshot.getKey();
                            advertMap.put( child.getKey().toString(),userBidOn);
                        }

//                            location = getLocation(advert.from,advert.to);
//                            advert.setFrom(location.get(0));
//                            advert.setTo(location.get(1));
                        addItem(advert,child.getKey().toString());
                        recyclerMethod3(view);
                    }
                    else
                    {

                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    private static void addItem(Advert item, String id)
    {
        //Adds the items to a static list which is shown to the user
        ITEMS.add(item);
        ADVERTID.add(id);
    }
    private void refresh()
    {
        ITEMS.clear();
        ADVERTID.clear();
    }


}
