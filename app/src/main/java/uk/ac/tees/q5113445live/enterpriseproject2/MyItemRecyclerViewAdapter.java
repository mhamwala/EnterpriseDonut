package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import uk.ac.tees.q5113445live.enterpriseproject2.TestFragment.OnListFragmentInteractionListener;
import uk.ac.tees.q5113445live.enterpriseproject2.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Delivery> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Delivery> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.c.setText(mValues.get(position).getCollect());
        holder.d.setText(mValues.get(position).getDeliver());
        holder.dt.setText(mValues.get(position).getDeliveryType());
        holder.dis.setText(mValues.get(position).getDistance());
        holder.pa.setText(mValues.get(position).getPay());
        holder.s.setText(mValues.get(position).getSize());
        holder.w.setText(mValues.get(position).getWeight());





        //This needs changed so that the above happens for every child in the list.

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getDeliveryType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView c;
        public final TextView d;
        public final TextView dt;
        public final TextView dis;
        public final TextView pa;
        public final TextView s;
        public final TextView w;
        public Delivery mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            c = (TextView) view.findViewById(R.id.collect);
            d = (TextView) view.findViewById(R.id.deliver);
            dt = (TextView) view.findViewById(R.id.DeliveryType);
            dis = (TextView) view.findViewById(R.id.distance);
            pa = (TextView) view.findViewById(R.id.pay);
            s = (TextView) view.findViewById(R.id.size);
            w= (TextView) view.findViewById(R.id.weight);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + c.getText() + "'" + d.getText() +dt.getText()+d.getText()+pa.getText()+s.getText()+w.getText();
        }
    }
}
