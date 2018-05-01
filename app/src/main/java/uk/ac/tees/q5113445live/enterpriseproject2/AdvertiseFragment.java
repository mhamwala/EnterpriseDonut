package uk.ac.tees.q5113445live.enterpriseproject2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


/**
 * The following fragment allows for the listing of adverts by users. The fragment will place these
 * adverts onto the Firebase real-time database which is connected.
 */
public class AdvertiseFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Initialise Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private Uri selectedImage;
    private Button addImage;
    private ImageView imageView = null;
    public static final int GET_FROM_GALLERY = 3;
    private  Bitmap bitmap = null;
    private ImageView testImage = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdvertiseFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the advertise fragment.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvertiseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertiseFragment newInstance(String param1, String param2)
    {
        AdvertiseFragment fragment = new AdvertiseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Overridden method called upon fragment creation. Initialises required variables.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    //Overridden method called upon fragment creation. Initialises required views.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_advertise, container, false);
        if (mListener != null)
        {
            mListener.onFragmentInteraction("Advertise an item");
        }
        //Initialises EditText to their corresponding layout item.
        final EditText name = view.findViewById(R.id.advertName);
        final EditText deliveryType = view.findViewById(R.id.deliveryType);
        final EditText distance = view.findViewById(R.id.deliverTo);
        final EditText size = view.findViewById(R.id.size);
        final EditText weight = view.findViewById(R.id.weight);
        final EditText collect = view.findViewById(R.id.collect);
        final Button addImage = view.findViewById(R.id.imageButton);
        final Button captureImage = view.findViewById(R.id.imageButton2);
        final EditText bid = view.findViewById(R.id.enterBid);
        imageView = view.findViewById(R.id.imageView);

        Button advertiseItem = view.findViewById(R.id.button5);


        testImage = view.findViewById(R.id.testImage);

        captureImage.setOnClickListener(new Switch.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                launchCamera();



            }
        });



        addImage.setOnClickListener(new Switch.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                addPicture();



            }
        });


        captureImage.setOnClickListener(new Switch.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                launchCamera();



            }
        });


        advertiseItem.setOnClickListener(new Button.OnClickListener()


        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    //Creates new advert by gather text from user.
                    HashMap<String, String> x;
                    Advert advert = new Advert
                            (
                                    name.getText().toString(),
                                    deliveryType.getText().toString(),
                                    distance.getText().toString(),
                                    collect.getText().toString(),
                                    weight.getText().toString(),
                                    size.getText().toString()
                            );
                    //Create the entry in the database.
                    String key = mDatabase.getDatabase().getReference("advert").push().getKey();
                    newDelivery(advert,user.getUid(), key);
                    uploadPic(key);
                    //Reverts back to home activity.
                    Intent home = new Intent(getActivity(),NavigationDrawer.class);
                    startActivity(home);



                }
                catch (NumberFormatException e)
                {
                    System.out.println("INSIDE NUMBER FORMAT EXCEPTION");
                }
            }
        });

        //addImage = view.findViewById(R.id.imageButton);
        addImage.setOnClickListener(new Switch.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addPicture();
            }
        });

        return view;
    }








    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();

            try {
                imageView.setImageBitmap
                        (MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                                selectedImage));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String title)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(title);
        }
    }

    private void addPicture()
    {


        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                GET_FROM_GALLERY
        );



    }

    private  void launchCamera()
{



    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivity(intent);


}



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else {
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    /**
     * Add an advert to the database following the below structure.
     * Advert>UserID>ItemID
     * @param advert advert to add
     * @param user user adding advert
     * @param id unique id of advert
     */
    private void newDelivery(Advert advert, String user, String id)
    {


        mDatabase.child("advert").child(user).child(id).setValue(advert);

    }

    public void uploadPic(String key)
    {
        //final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setTitle("Adding Image...");
        //progressDialog.show();
        StorageReference ref = mStorageRef.child("AdvertImage/"+ key);

        ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //progressDialog.dismiss();
                System.out.println("Completed");
                //Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
