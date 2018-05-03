package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MyAdvertDetailsActivity extends AppCompatActivity
        implements MyBidRecyclerViewAdapter.OnListFragmentInteractionListener {

    private ViewAdvertFragment test;

    private TextView delivery;
    private TextView size;
    private TextView from;
    private TextView weight;
    private TextView advertName;

    private RecyclerView recyclerView;
    private MyBidRecyclerViewAdapter recycleAdapter;
    private int mColumnCount;
    private MyBidRecyclerViewAdapter.OnListFragmentInteractionListener mListener;
    private Advert a;
    private List<HashMap<String, String>> bidList;
    private List<String> bidID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advert_details);

        test = new ViewAdvertFragment();
        mColumnCount = 1;
        Intent in = getIntent();
        Bundle b = in.getExtras();

        a = (Advert) b.getSerializable("details");
        System.out.println(a.toString());
        delivery = this.findViewById(R.id.deliveryType);
        size = this.findViewById(R.id.size);
        from = this.findViewById(R.id.from);
        weight = this.findViewById(R.id.weight);
        advertName = this.findViewById(R.id.advertName);

        //addBID();
        delivery.setText(a.getDeliveryType());
        size.setText(a.getSize());
        from.setText(a.getFrom().getAddress());
        weight.setText(a.getWeight());
        advertName.setText(a.getName());
        recyclerMethod(findViewById(R.id.list2));
        //        final String a = test.viewAdvertDetails();
      //  System.out.println("YEAHHH its ya boyy "+a);
    }
    private void recyclerMethod(View view)
    {
        //Recyclers which handles the showing of items to the user.
        if (view.findViewById(R.id.list2) instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list2);
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recycleAdapter = new MyBidRecyclerViewAdapter(a.getBid(), mListener);
            recyclerView.setAdapter(recycleAdapter);
        }
    }

    @Override
    public void onListFragmentInteraction(String title)
    {

    }
//    private void addBID()
//    {
//         bidList.add((HashMap<String, String>) a.getBid().values());
//    }


}
