package uk.ac.tees.q5113445live.enterpriseproject2;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.tees.q5113445live.enterpriseproject2.JobFragment.OnListFragmentInteractionListener;

import uk.ac.tees.q5113445live.enterpriseproject2.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * specified {@link OnListFragmentInteractionListener}.
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Advert> mValues;
    private final OnListFragmentInteractionListener mListener;
    private static final String TAG = "Bidding Activity";
    private TextView userBid;
    private DataSnapshot userData;
    private DatabaseReference mDatabase;
    private DatabaseReference uid;
    private Button updateBid;
    private String userKey;
    private FirebaseUser fUser;
    //private ArrayList advertKey;
    private String userBidOn;
    private HashMap<String, String> advertMap;
    private int pos;
    private ArrayList value;


    public MyItemRecyclerViewAdapter(List<Advert> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    public interface OnListFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String title);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        pos = -1;
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        final View parentView =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_list, parent, false);
        advertMap = new HashMap<>();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("advert");


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.mItem = mValues.get(position);
        holder.n.setText(mValues.get(position).getName());
        holder.c.setText(mValues.get(position).getFrom());
        holder.d.setText(mValues.get(position).getTo());
        //holder.s.setText(mValues.get(position).getBid());


        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getDeliveryType());
                    pos = position;
                    notifyDataSetChanged();
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

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView n;
        public final TextView c;
        public final TextView d;
        //public final TextView s;

        public Advert mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            //mBid = view;
            n = view.findViewById(R.id.itemName);
            c = (TextView) view.findViewById(R.id.collect);
            d = (TextView) view.findViewById(R.id.deliver);
            //s = (TextView) view.findViewById(R.id.bid);

        }

        @Override
        public String toString()
        {
            return super.toString() + " '" +n.getText() +c.getText() + "'" + d.getText();
        }
    }

    public void bidText(Advert advert, View view)
    {
        //userBid = view.findViewById(R.id.bid);
        //userBid.setText(advert.getBid());
    }

    public void updateBid(final View view, final ArrayList<String> advertKey, DatabaseReference reference, final HashMap<String, String> advertMap)
    {
        //Trying to return name after bid is placed!
        if(pos == -1)
        {
            Toast.makeText(view.getContext(), "Please select job", Toast.LENGTH_SHORT).show();

        }
        else
            {
            userKey = fUser.getUid();
            String id = reference.child(advertMap.get(String.valueOf(advertKey.get(pos)))).
                    child(String.valueOf(advertKey.get(pos))).child("bid").push().getKey();
            HashMap<Object, Object> result = new HashMap<>();
            TextView bidUser = view.findViewById(R.id.enterBid);
            result.put(userKey, bidUser.getText().toString());
            reference.child(advertMap.get(String.valueOf(advertKey.get(pos)))).
                    child(String.valueOf(advertKey.get(pos))).child("bid").child(id).setValue(result);
            Log.d(TAG, "Bid Added:success");
            Toast.makeText(view.getContext(), "Bid Added!", Toast.LENGTH_SHORT).show();
        }
    }
    public void findUser()
    {

    }

}
