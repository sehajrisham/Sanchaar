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
import utils.RVAdapterInstituteWiseStock;
import utils.StaticDrugCode;
import utils.StaticInstituteCode;

public class InstituteWiseStock extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView lvItems;
    DbHandler dbh;
    RVAdapterInstituteWiseStock rv1;
    Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    //MaterialSearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View medicine = inflater.inflate(R.layout.fragment_tabbed, container, false);
        lvItems = (RecyclerView) medicine.findViewById(R.id.rv);
        dbh = new DbHandler(getActivity());
        context=getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);
        context=getActivity();



        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        new LoadData().execute();

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

            return dbh.Load_Institute_Wise_Stock_Table(StaticDrugCode.get(), StaticInstituteCode.get(),context );

        }

        @Override
        protected void onPostExecute(String s) {
            rv1 = new RVAdapterInstituteWiseStock(dbh.get_institute_wise_stock(StaticInstituteCode.get()));
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