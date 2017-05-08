package com.nic.civilsurgeonnic.TabbedFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nic.civilsurgeonnic.R;

import java.util.ArrayList;

import utils.CardGetSet;
import utils.DbHandler;
import utils.RVAdapterInstituteDetail;

/**
 * Created by Sehaj Risham on 2/17/2017.
 */

public class TabbedFragment_InstituteDetail extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {


    RecyclerView lvItems;
    DbHandler dbh;
    RVAdapterInstituteDetail rv1;
    MaterialSearchView searchView;
    Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<CardGetSet> list;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View medicine= inflater.inflate(R.layout.fragment_tabbed_fragment__institute_detail,container,false);
        lvItems=(RecyclerView) medicine.findViewById(R.id.rv);
        dbh=new DbHandler(getActivity());
        list=new ArrayList<>();
        rv1=new RVAdapterInstituteDetail(list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);



        searchView=(MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        context=getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);

        // new LoadData().execute();

        initializeAdapter();

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.equals("")) {

                    rv1 = new RVAdapterInstituteDetail(dbh.get_institute_detail(null));
                    lvItems.setAdapter(rv1);
                    rv1.notifyDataSetChanged();
                }
                else {
                    rv1 = new RVAdapterInstituteDetail(dbh.get_institute_detail(newText));
                    lvItems.setAdapter(rv1);
                    rv1.notifyDataSetChanged();
                }

                return false;
            }
        });
        // swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
/*        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                new LoadData().execute();
            }
        });*/
        /*rv1=new RVAdapterInstituteDetail(dbh.get_institute_detail(null));
        lvItems.setAdapter(rv1);*/
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
            return dbh.Load_Institute_table(getActivity());

        }

        @Override
        protected void onPostExecute(String s) {
            rv1 = new RVAdapterInstituteDetail(dbh.get_institute_detail(null));
            lvItems.setAdapter(rv1);
            rv1.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

            if (s.equals("ErrorServer")) {
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            } else if (s.equals("Error")) {

                Toast.makeText(getActivity(),"Database Error",Toast.LENGTH_SHORT).show();
                /// /new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("").show();
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
        rv1 = new RVAdapterInstituteDetail(dbh.get_institute_detail(null));
        lvItems.setAdapter(rv1);
        rv1.notifyDataSetChanged();
    }


}
