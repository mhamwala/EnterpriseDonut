package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * The following fragment finds extended location information from a Users postcode.
 * Currently unused, but may be of future use.
 */
public class LocationFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private String cityName = "";
    private String countryName = "";

    private OnFragmentInteractionListener mListener;

    public LocationFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2)
    {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //region Methods called upon fragment creation
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLocation();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_location, container, false);
        TextView houseNum = view.findViewById(R.id.showHouseNum);


        TextView city = view.findViewById(R.id.showCity);
        city.setText(cityName);

        TextView country = view.findViewById(R.id.showCountry);
        country.setText(countryName);


        return view;
    }
    //endregion

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String title) {
        if (mListener != null) {


            mListener.onFragmentInteraction(title);
        }
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void getLocation()
    {
        Geocoder gps = new Geocoder(getActivity(), Locale.getDefault());
        if (gps.isPresent()) {
            try {
                List<Address> list = gps.getFromLocationName("TS1 1LY", 1);
                Address address = list.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                countryName = address.getCountryCode();
                cityName = address.getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
