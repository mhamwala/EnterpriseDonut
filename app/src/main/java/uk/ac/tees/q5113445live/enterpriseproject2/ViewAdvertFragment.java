package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link MyItemRecyclerViewAdapter.OnListFragmentInteractionListener}
 * interface.
 */
public class ViewAdvertFragment extends Fragment implements MyItemRecyclerViewAdapter.OnListFragmentInteractionListener{
    private static final String ARG_COLUMN_COUNT = "column-count";
    //private static final String DRIVER_BOOLEAN = "driverCheck";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabase;
    private FirebaseUser user;
    private DatabaseReference bidDatabase;
    private boolean driverCheck;
    private ArrayList<String> location;
    private ArrayList<String> bid;
    private FirebaseUser fUser;
    private HashMap<String, String> advertMap;
    private String userBidOn;
    private ArrayList advertKey;
    private MyItemRecyclerViewAdapter.OnListFragmentInteractionListener mListener;
    public static final List<Advert> ITEMS = new ArrayList<Advert>();
    public static final List<String> ADVERTID = new ArrayList<>();
    public static final Map<String, Advert> ITEM_MAP = new HashMap<String, Advert>();
    private Button updateBid;
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter recycleAdapter;
    private Button removeAd;
    private int position;
    public ViewAdvertFragment()
    {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param columnCount
     * @return A new instance of fragment ViewAdvertFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAdvertFragment newInstance(int columnCount) {
        ViewAdvertFragment fragment = new ViewAdvertFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
            //driverCheck = getArguments().getBoolean(DRIVER_BOOLEAN);
        }
        getPosition(pos);
    }

    public int getPosition(int pos)
    {
        int a = pos;
        System.out.println("Position in getPosition Method is :" + position);
        return a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (mListener != null)
        {
            mListener.onListFragmentInteraction("View Adverts");

        }
        final View view = inflater.inflate(R.layout.fragment_user_adverts, container, false);
        checkDriver(view);

        System.out.println("Position after getPosition call is :" + position);
        removeAd = view.findViewById(R.id.removeAdvert);
        removeAd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view2)
            {
                getPosition(pos);
                System.out.println("Position onClick is :" + pos);

                //removeAdvert();
                System.out.println("HELOOOOOOOSODOSAD:  " + position);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

//    public void removeAdvert()
//    {
//        getPosition(position);
//        removeAd.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view2)
//            {
//                System.out.println("HELOOOOOOOSODOSAD:  " + position);
//            }
//        });
//    }

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

    private static void addItem(Advert item, String id)
    {
        //Adds the items to a static list which is shown to the user
        ITEMS.add(item);
        ADVERTID.add(id);
        ITEM_MAP.put(item.getName(),item);
        ITEM_MAP.put(item.getFrom(),item);
        ITEM_MAP.put(item.getTo(),item);
        ITEM_MAP.put(item.getDeliveryType(), item);
        ITEM_MAP.put(item.getSize(),item);
        ITEM_MAP.put(item.getWeight(),item);
    }

    private void recyclerMethod(View view)
    {
        //Recyclers which handles the showing of items to the user.
        if (view.findViewById(R.id.list) instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recycleAdapter = new MyItemRecyclerViewAdapter(ITEMS,ADVERTID, mListener);
            recyclerView.setAdapter(recycleAdapter);

        }

    }
    private void refresh()
    {
        ITEMS.clear();
        ADVERTID.clear();
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
                        recyclerMethod(view);
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

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onListFragmentInteraction("View Adverts");

        }
    }

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



    @Override
    public void onListFragmentInteraction(String title)
    {

    }
}
