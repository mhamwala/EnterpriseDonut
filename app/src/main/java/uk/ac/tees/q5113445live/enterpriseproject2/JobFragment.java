package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link MyItemRecyclerViewAdapter.OnListFragmentInteractionListener}
 * interface.
 */
public class JobFragment extends Fragment implements MyItemRecyclerViewAdapter.OnListFragmentInteractionListener
{
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String DRIVER_BOOLEAN = "driverCheck";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabase;
    private FirebaseUser user;
    private DatabaseReference bidDatabase;
    private boolean driverCheck;
    private ArrayList<String> location;

    private FirebaseUser fUser;
    private HashMap<String, String> advertMap;
    private String userBidOn;
    private ArrayList advertKey;
    private MyItemRecyclerViewAdapter.OnListFragmentInteractionListener mListener;
    public static final List<Advert> ITEMS = new ArrayList<Advert>();
    public static final List<String> ADVERTID = new ArrayList<>();
    public static final Map<String, Advert> ITEM_MAP = new HashMap<String, Advert>();

    private Button updateBid;
    private User pUser;
    private DataSnapshot snapshot;
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter recycleAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobFragment()
    {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JobFragment newInstance(int columnCount, boolean d)
    {
        JobFragment fragment = new JobFragment();
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
        location = new ArrayList<>();
        advertMap = new HashMap<>();
        advertKey = new ArrayList();
        refresh();
        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            driverCheck = getArguments().getBoolean(DRIVER_BOOLEAN);
        }
        MapFragment m = (MapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.addMap);

        //m.isHidden();
//        m = new MapFragment();

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final DataSnapshot snapshot = dataSnapshot;
                pUser = snapshot.getValue(User.class);

                //final TextView walletText = getView().findViewById(R.id.nav_wallet);
                new User
                        (
                                pUser.getWallet().toString()
                        );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (mListener != null)
        {
            mListener.onListFragmentInteraction("View My Adverts");
        }
        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        checkDriver(view);
        updateBid = view.findViewById(R.id.updateBid);
        updateBid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view2)
            {
                recycleAdapter.updateBid(view, advertKey,mDatabase, advertMap, pUser);
                Bundle args = new Bundle();
                int x = recycleAdapter.getPosition();
                args.putString("LAT", "0");
                args.putString("LNG", "0");
                //m.setArguments(args);
//                Fragment f = null;
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.mainFrame, f);
//                ft.commit();



            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof MyItemRecyclerViewAdapter.OnListFragmentInteractionListener)
        {
            mListener = (MyItemRecyclerViewAdapter.OnListFragmentInteractionListener) context;
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



    private static void addItem(Advert item,String id)
    {
        //Adds the items to a static list which is shown to the user
        ITEMS.add(item);
        ADVERTID.add(id);

//        ITEM_MAP.put(item.getName(),item);
//        ITEM_MAP.put(item.getFrom(),item);
//        ITEM_MAP.put(item.getTo(),item);
//        ITEM_MAP.put(item.getDeliveryType(), item);
//        ITEM_MAP.put(item.getSize(),item);
//        ITEM_MAP.put(item.getWeight(),item);
    }

    private void recyclerMethod(View view)
    {
        //Recyclers which handles the showing of items to the user.
        if (view.findViewById(R.id.list3) instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list3);
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recycleAdapter = new MyItemRecyclerViewAdapter(ITEMS,ADVERTID, mListener,0);
            recyclerView.setAdapter(recycleAdapter);

        }
    }
    private void refresh()
    {
        ITEMS.clear();
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
                            Advert advert = child.getValue(Advert.class);
                            if(!advertKey.contains(child.getKey()))
                            {
                                advertKey.add(child.getKey());
                            }
                            if (!child.getKey().equals(user.getUid()))
                            {
                                userBidOn = dataSnapshot.getKey();
                                advertMap.put(child.getKey().toString(),userBidOn);
                            }

//                            location = getLocation(advert.from,advert.to);
//                            advert.setFrom(location.get(0));
//                            advert.setTo(location.get(1));
                            addItem(advert, child.getKey().toString());
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
                            //addItem(advert);
                            recyclerMethod(view);
                        }
                    }
                }

//                for (DataSnapshot child : dataSnapshot.getChildren())
//                {
//                    if(!advertKey.contains(child.getKey()))
//                    {
//                        advertKey.add(child.getKey());
//                    }
//                    if (!child.getKey().equals(user.getUid()))
//                    {
//                        userBidOn = dataSnapshot.getKey();
//                        advertMap.put( child.getKey().toString(),userBidOn);
//                    }
//                    //userData = dataSnapshot;
//                    // bidText(advert,view);
//                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                //Initialises user to stored data and populates TextViews in layout.Advert advert = userData.getValue(Advert.class);
                //Creates separate section for bids
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
//                {
//                    if (!userSnapshot.getKey().equals(fUser.getUid()))
//                    {
//                        userBidOn = userSnapshot.getKey();
//                        for (DataSnapshot advertSnapshot: userSnapshot.getChildren())
//                        {
//                            advertMap.put( advertSnapshot.getKey().toString(),userBidOn);
//                        }
//                    }
//                }
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
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

    @Override
    public void onListFragmentInteraction(String title)
    {

    }

}
