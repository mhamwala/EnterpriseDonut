package uk.ac.tees.q5113445live.enterpriseproject2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestCourier extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_item);

        final EditText deliveryType = findViewById(R.id.deliveryType);
        final EditText distance = findViewById(R.id.distance);
        final EditText size = findViewById(R.id.size);
        final EditText weight = findViewById(R.id.weight);
        final EditText pay = findViewById(R.id.pay);

        Button advertiseItem = findViewById(R.id.button5);

        advertiseItem.setOnClickListener(new Button.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(View v)
                                                    {
                        try
                        {
                            Delivery delivery = new Delivery
                                    (
                                            deliveryType.getText().toString(),
                                            distance.getText().toString(),
                                            size.getText().toString(),
                                            weight.getText().toString(),
                                            pay.getText().toString()
                                    );
                            newDelivery(delivery, "1");
                           // uploadCourierRequest(delivery);
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("INSIDE NUMBER FORMAT EXCEPTION");
                        }
                    }
                }
        );


    }
    private void newDelivery(Delivery delivery, String id)
    {
        mDatabase.child("delivery").child(id).setValue(delivery);
    }

}
