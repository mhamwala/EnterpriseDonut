package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        AdvertiseFragment.OnFragmentInteractionListener,
        DetailsFragment.OnFragmentInteractionListener,
        MyItemRecyclerViewAdapter.OnListFragmentInteractionListener,
        MyBidRecyclerViewAdapter.OnListFragmentInteractionListener,
        JobFragment.OnListFragmentInteractionListener,
        LocationFragment.OnFragmentInteractionListener

{

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private FirebaseUser user;
    private ImageView imageView;
    private boolean driver;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference("images").child(user.getUid());
        getDetails();

        //NOTE:  Open fragment1 initially.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new HomeFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //NOTE: creating fragment object
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home)
        {
            fragment = new HomeFragment();
        }
        else if (id == R.id.nav_advertise)
        {
            fragment = new AdvertiseFragment();
        }
        else if (id == R.id.nav_account)
        {
            fragment = new DetailsFragment();
        }
        else if (id == R.id.nav_advertised)
        {
            fragment = new ViewAdvertFragment();
        }
        else if (id == R.id.nav_jobs)
        {
            //fragment = advertiseJobs(driver, fragment, ft);
            fragment = new Map();
        }

        else if (id == R.id.nav_signout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent home = new Intent(this, login_activity.class);
            startActivity(home);
        }
        //NOTE: Fragment changing code
        if (fragment != null)
        {
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getDetails()
    {
        ValueEventListener userListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //These text boxes need to be sorted within onCreateView
                User user = dataSnapshot.getValue(User.class);
                driver = user.isDriver();

                TextView userText = findViewById(R.id.nav_name);
                userText.setText(user.getName());



                TextView walletText = findViewById(R.id.nav_wallet);
                walletText.setText(user.getWallet());

                imageView = findViewById(R.id.nav_profile);
                try
                {
                    getProfileImage();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        mDatabase.addListenerForSingleValueEvent(userListener);
    }
    public void getProfileImage() throws IOException
    {
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(imageView);
    }
    public Fragment advertiseJobs(boolean d, Fragment f, FragmentTransaction ft)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("driverCheck", d);
        f = new JobFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onListFragmentInteraction(String title)
    {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onFragmentInteraction(String title)
    {
        getSupportActionBar().setTitle(title);
    }

}
