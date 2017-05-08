package com.nic.civilsurgeonnic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.pedant.SweetAlert.SweetAlertDialog;
import utils.DbHandler;
import utils.RVAdapterDistrictWiseStock;
import utils.StaticDrugCode;

public class DistrictWiseStock extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView lvItems;
    DbHandler dbh;
    RVAdapterDistrictWiseStock rv1;
    Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    //MaterialSearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View medicine = inflater.inflate(R.layout.fragment_tabbed, container, false);
        lvItems = (RecyclerView) medicine.findViewById(R.id.rv);
        dbh = new DbHandler(getActivity());

       /* searchView=(MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);*/
        context=getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);



        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        new LoadData().execute();
        /*searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.equals("")) {

                    rv1 = new RVAdapterDistrictWiseStock(dbh.get_district_wise_stock(null));
                    lvItems.setAdapter(rv1);
                    rv1.notifyDataSetChanged();
                }
                else {
                    rv1 = new RVAdapterDistrictWiseStock(dbh.get_district_wise_stock(newText));
                    lvItems.setAdapter(rv1);
                    rv1.notifyDataSetChanged();
                }

                return false;
            }
        });*/
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                new LoadData().execute();
            }
        });
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

            return dbh.Load_District_Wise_Stock_Table(StaticDrugCode.get(),context);

        }

        @Override
        protected void onPostExecute(String s) {
            rv1 = new RVAdapterDistrictWiseStock(dbh.get_district_wise_stock(StaticDrugCode.get()));
            lvItems.setAdapter(rv1);
            rv1.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            if (s.equals("Error")) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("No Internet Connection").show();
            } else if (s.equals("ErrorServer")) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Unable to connect with Server!!!").show();
            }
            super.onPostExecute(s);
        }
    }
    @Override
    public void onRefresh() {
        new LoadData().execute();
    }

}