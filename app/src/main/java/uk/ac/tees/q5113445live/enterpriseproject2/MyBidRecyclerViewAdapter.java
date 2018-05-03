package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBidRecyclerViewAdapter extends RecyclerView.Adapter<MyBidRecyclerViewAdapter.ViewHolder>
{

    private List<String> bidID = null;
    private List<String> userID = null;
    private List<String> bidVal = null;
    private  List<HashMap<String, String>> bids = null;
    private final OnListFragmentInteractionListener mListener;
    private static int pos;
    private FirebaseUser fUser;

    public MyBidRecyclerViewAdapter(HashMap<String, HashMap<String, String>> items, OnListFragmentInteractionListener listener)
    {
        bidID = new ArrayList<>();
        userID =new ArrayList<>();
        bidVal = new ArrayList<>();
        bids = new ArrayList<>();
        mListener = listener;
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        for(String key: items.keySet())
        {
            bidID.add(key);
            System.out.println( "h" + key);
        }
        for(String x: bidID)
        {
            bids.add(items.get(x));
            System.out.println("e" + x);
        }
        for (HashMap x: bids)
        {
            for (Object z: x.keySet())
            {
                userID.add(z.toString());
            }
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_advert_item, parent, false);
        pos = -1;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        //holder.mItem = bidID.get(position);
        holder.c.setText(bids.get(position).keySet().toString());
        holder.n.setText(bids.get(position).values().toString());

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.c.getText().toString());
                    pos = position;
                    notifyDataSetChanged();
                    //imageView = view.findViewById(R.id.imageView3);
                }
                else
                {
                    System.out.println("Hello");
                }

            }
        });
        if(pos == position)
        {
            holder.mView.setBackgroundColor(Color.GREEN);
        }
        else
        {
            holder.mView.setBackgroundColor(Color.WHITE);
        }

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.toString());
//                }
//            }
//        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView n;
        public final TextView c;
        //public final TextView d;
        //public final TextView s;
        //public final TextView s;

        public Advert mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            //mBid = view;
            n = view.findViewById(R.id.bid);
            c = (TextView) view.findViewById(R.id.userId);
            //d = (TextView) view.findViewById(R.id.deliver);

            //s = (TextView) view.findViewById(R.id.updateBid);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" +n.getText() +c.getText() + "'";
        }
    }

    @Override
    public int getItemCount() {
        return bidID.size();
    }

    public interface OnListFragmentInteractionListener
    {
        void onListFragmentInteraction(String title);
    }
    public void acceptBid(String advertID)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("advert").child(fUser.getUid()).child(advertID);
        HashMap<String, Boolean> innerMap = new HashMap<String, Boolean>();
        innerMap.put(userID.get(pos), true);
        databaseReference.child("accepted").removeValue();
        databaseReference.child("accepted").setValue(innerMap);

    }
    public void updateAccept(DatabaseReference databaseReference)
    {

    }

//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public String mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
}
