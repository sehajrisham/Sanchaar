package com.nic.civilsurgeonnic.TabbedFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nic.civilsurgeonnic.InstituteWiseStock;
import com.nic.civilsurgeonnic.R;

import java.util.ArrayList;

import utils.CardGetSet2;
import utils.DbHandler;
import utils.RVAdapterDrugDetail;
import utils.RVAdapterInstituteDetail;
import utils.StaticDrugCode;

/**
 * Created by Sehaj Risham on 2/17/2017.
 */

public class FragmentInstituteWiseMedicine extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView lvItems;
    DbHandler dbh;
    Context context;
    RVAdapterDrugDetail rv1;
    MaterialSearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<CardGetSet2> list;
    FragmentTransaction fragmentTransaction;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Toolbar toolbar;
    RVAdapterInstituteDetail rv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View medicine= inflater.inflate(R.layout.fragment_tabbed,container,false);
        lvItems=(RecyclerView) medicine.findViewById(R.id.rv);
        dbh=new DbHandler(getActivity());
        list=new ArrayList<>();
        rv1=new RVAdapterDrugDetail(list);

       /* toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);
/*        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.main_container, new TabFragment()).commit()
        ;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();*/

        rv1.setOnItemClickListner(new RVAdapterDrugDetail.OnItemClickListener() {
            @Override
            public void onItemClick(String Id) {

                StaticDrugCode.set(Id);


                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new InstituteWiseStock());
                /*fragmentTransaction.addToBackStack(null);*/
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

            }
        });
        searchView=(MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        context=getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        // new FragmentMedicine.LoadData().execute();
        initializeAdapter();
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.equals("")) {

                    rv1 = new RVAdapterDrugDetail(dbh.get_drug_detail(null));
                    lvItems.setAdapter(rv1);
                    rv1.notifyDataSetChanged();
                }

                else {
                    rv1 = new RVAdapterDrugDetail(dbh.get_drug_detail(newText));
                    lvItems.setAdapter(rv1);
                    rv1.notifyDataSetChanged();
                }

                return false;
            }
        });
        //  swipeRefreshLayout.setOnRefreshListener(this);
/*        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                new LoadData().execute();
            }
        });*/
        return medicine;
    }


    private class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return dbh.Load_Drug_table();

        }

        @Override
        protected void onPostExecute(String s) {
            rv1 = new RVAdapterDrugDetail(dbh.get_drug_detail(null));
            lvItems.setAdapter(rv1);
            rv1.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            if (s.equals("Error")) {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                //new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("No Internet Connection").show();
            } else if (s.equals("ErrorServer")) {
                Toast.makeText(getActivity(),"Unable to connect with Server!!!",Toast.LENGTH_SHORT).show();
                //  new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Unable to connect with Server!!!").show();
            }
            super.onPostExecute(s);
        }
    }


    @Override
    public void onRefresh() {
        new LoadData().execute();
    }

    private void initializeAdapter()
    {
        rv1 = new RVAdapterDrugDetail(dbh.get_drug_detail(null));
        lvItems.setAdapter(rv1);
        rv1.notifyDataSetChanged();
    }
}
