package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String DRIVER_BOOLEAN = "driverCheck";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabase;
    private FirebaseUser user;
    private DatabaseReference bidDatabase;
    private boolean driverCheck;
    private ArrayList<String> location;
    private ArrayList<String> bid;

    public static final List<Advert> ITEMS = new ArrayList<Advert>();
    public static final List<Bid> BIDS = new ArrayList<Bid>();
    public static final Map<String, Advert> ITEM_MAP = new HashMap<String, Advert>();
    public static final Map<String, Bid> BID_MAP = new HashMap<String, Bid>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestFragment()
    {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TestFragment newInstance(int columnCount, boolean d)
    {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference("advert");
        bidDatabase = FirebaseDatabase.getInstance().getReference("advert").child(user.getUid()).child("Bids");
        location = new ArrayList<>();

        //checkDriver();

        refresh();
        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            driverCheck = getArguments().getBoolean(DRIVER_BOOLEAN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        checkDriver(view);
        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener
    {
        //TODO: make it so that the card expands when clicking on it
        void onListFragmentInteraction(String title);
    }



    private static void addItem(Advert item)
    {
        //Adds the items to a static list which is shown to the user
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(),item);
        ITEM_MAP.put(item.getFrom(),item);
        ITEM_MAP.put(item.getTo(),item);
        ITEM_MAP.put(item.getDeliveryType(), item);
        ITEM_MAP.put(item.getSize(),item);
        ITEM_MAP.put(item.getWeight(),item);
    }

    private static void addBid(Bid bid)
    {
        //Adds the items to a static list which is shown to the user
        BIDS.add(bid);
        BID_MAP.put(bid.getDriverName(),bid);
        BID_MAP.put(bid.getPrice(),bid);
    }

    private void recyclerMethod(View view)
    {
        //Recyclers which handles the showing of items to the user.
        if (view instanceof RecyclerView)
        {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(ITEMS,BIDS, mListener));
        }
    }
    private void refresh()
    {
        ITEMS.clear();
        BIDS.clear();
        ITEM_MAP.clear();
    }
    public void checkDriver(final View view)
    {

        mDatabase.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {

                //Add each advert to a list.
                if(driverCheck)
                {

                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        if(user.getUid().equals(dataSnapshot.getKey()))
                        {

                        }
                        else
                        {
                            bidDatabase.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
                            Advert advert = child.getValue(Advert.class);
                            Bid bids = child.getValue(Bid.class);
                            location = getLocation(advert.from,advert.to);
                            bid = getBid(bids.driverName, bids.price);
                            advert.setFrom(location.get(0));
                            advert.setTo(location.get(1));
                            bids.setDriverName(bid.get(0));
                            bids.setPrice(bid.get(1));
                            addItem(advert);
                            addBid(bids);
                            recyclerMethod(view);
                        }

                    }
                }
                else
                {
                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        System.out.println(user.getUid());
                        if(user.getUid().equals(dataSnapshot.getKey()))
                        {
                            Advert advert = child.getValue(Advert.class);
                            Bid bids = child.getValue(Bid.class);
                            addItem(advert);
                            addBid(bids);
                            recyclerMethod(view);
                        }
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
//    public void getDriver()
//    {
//        ValueEventListener userListener = new ValueEventListener()
//        {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                User user = dataSnapshot.getValue(User.class);
//                driver = user.isDriver();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        userDatabase.addValueEventListener(userListener);
//    }
    public ArrayList<String> getLocation(String from, String to)
    {
        ArrayList<String> location = new ArrayList<>();

        String cityFrom = "";
        String cityTo = "";
        Geocoder gps = new Geocoder(getActivity(), Locale.getDefault());
        if (gps.isPresent()) {
            try {
                List<Address> list = gps.getFromLocationName(from, 1);
                Address address = list.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                cityFrom = address.getLocality();

                list = gps.getFromLocationName(to, 1);
                address = list.get(0);
                lat = address.getLatitude();
                lng = address.getLongitude();
                cityTo = address.getLocality();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        location.add(cityFrom);
        location.add(cityTo);

        return location;
    }

    public ArrayList<String> getBid(String name, String price)
    {
        ArrayList<String> bids = new ArrayList<>();

        name = "Musa";
        price = "10";

//        DatabaseReference ref = bidDatabase;
//
//        ref.addValueEventListener(new ValueEventListener() {
//                                      @Override
//                                      public void onDataChange(DataSnapshot dataSnapshot) {
//                                          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                                              Log.e(TAG, "======="+postSnapshot.child("email").getValue());
//                                              Log.e(TAG, "======="+postSnapshot.child("name").getValue());
//                                          }
//                                      }
//
//                                      @Override
//                                      public void onCancelled(DatabaseError databaseError) {
//                                          System.out.println("The read failed: " + databaseError.getCode());
//                                      }
//                                  });

        bid.add(name);
        bid.add(price);

        return bids;
    }
}
