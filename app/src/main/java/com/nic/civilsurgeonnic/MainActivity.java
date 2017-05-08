package com.nic.civilsurgeonnic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentDrugsToExpireInstituteWise;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentInstitute;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentMedicine;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentNonPerformingInstitutes;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentUserDetail;
import com.nic.civilsurgeonnic.TabbedFragments.Notify;

import utils.DbHandler;

/**
 * Created by RVerma on 27-02-2017.
 */

public class MainActivity extends AppCompatActivity {
DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
MaterialSearchView searchView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ArrayAdapter<String> adapter;
Button button;
    DbHandler db;
    private static final String TAG = "MainActivity";
    private static final int TIME_DELAY=3000;
    private static long back_pressed;
    boolean doubleBackToExit=false;
    TextView email,mobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  FirebaseApp.initializeApp(this);*/
        setContentView(R.layout.activity_main);
        db = new DbHandler(MainActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        searchView=(MaterialSearchView) findViewById(R.id.search_view);
/*        email=(TextView) findViewById(R.id.email);
        mobile=(TextView)findViewById(R.id.mobile);*/
       /* button=(Button) findViewById(R.id.bt1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token= FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG,"Token: "+ token);
                Toast.makeText( MainActivity.this,token, Toast.LENGTH_SHORT).show();
            }
        });*/

      // View view_header= LayoutInflater.from(getApplicationContext()).inflate(R.layout.navigation_drawer_header,drawerLayout);
     //   email=(TextView) view_header.findViewById(R.id.email);
     //   mobile=(TextView)view_header.findViewById(R.id.mobileno);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.main_container, new TabFragment()).commit();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //  fragmentTransaction.add(R.id.main_container,new FragmentMedicine());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Sanchaar");
        String userid = db.get_user_id();
      //  email.setText(userid);




        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new TabFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Home");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);

                        break;
                    case R.id.id_notify:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Notify());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Notifications");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.id_drug:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FragmentMedicine());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Drug Detail");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.id_institute:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FragmentInstitute());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Institute Detail");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.id_non_performing_institute:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FragmentNonPerformingInstitutes());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Non Performing Institutes");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.id_drugs_to_be_expired:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FragmentDrugsToExpireInstituteWise());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Drugs To Expire");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.id_user_detail:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FragmentUserDetail());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle("Institute Directory");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        fragmentTransaction.addToBackStack(null);
                        getFragmentManager().popBackStack();
                        break;
                    case R.id.id_logout:
                        String userid = db.get_user_id();
                       db.updateLoginBit_Logout(userid);
                       startActivity(new Intent(MainActivity.this,Login.class).putExtra("UserId",userid));

                        finish();
                        break;
                }
                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }
        else if(!doubleBackToExit){
            this.doubleBackToExit=true;
            Toast.makeText(this,"Please click Back Again to exit",Toast.LENGTH_SHORT).show();

     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             doubleBackToExit = false;
         }
     },3000);

    }
        else

        {
            super.onBackPressed();

        }}
}






