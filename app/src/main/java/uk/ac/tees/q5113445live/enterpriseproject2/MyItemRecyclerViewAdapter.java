package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.tees.q5113445live.enterpriseproject2.TestFragment.OnListFragmentInteractionListener;
import uk.ac.tees.q5113445live.enterpriseproject2.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Advert> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Advert> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        //Bid bid = new Bid()
        holder.mItem = mValues.get(position);
        holder.n.setText(mValues.get(position).getName());
        holder.c.setText(mValues.get(position).getFrom());
        holder.d.setText(mValues.get(position).getTo());

//        holder.dt.setText(mValues.get(position).getDeliveryType());
//        holder.s.setText(mValues.get(position).getSize());
//        holder.w.setText(mValues.get(position).getWeight());

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
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

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView n;
        public final TextView c;
        public final TextView d;
//        public final TextView dt;
//        public final TextView s;
//        public final TextView w;
        public Advert mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            //mBid = view;
            n = view.findViewById(R.id.itemName);
            c = (TextView) view.findViewById(R.id.collect);
            d = (TextView) view.findViewById(R.id.deliver);

//            dt = (TextView) view.findViewById(R.id.DeliveryType);
//            s = (TextView) view.findViewById(R.id.size);
//            w= (TextView) view.findViewById(R.id.weight);
        }

        @Override
        public String toString() {
            return super.toString() + " '" +n.getText() +c.getText() + "'" + d.getText();
        }
    }
}
