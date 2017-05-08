package com.nic.civilsurgeonnic.TabbedFragments;

/**
 * Created by RVerma on 27-04-2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nic.civilsurgeonnic.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import utils.DbHandler;
import utils.DbConstant;
import utils.RVAdapterPendingReceive;

/**
 * Created by sehdev's on 13/02/2017.
 */
public class Pending_Receive extends android.app.Fragment{

    DbHandler dbh;
    RVAdapterPendingReceive rv;
    RecyclerView lvItems;
    Context context;
    private PtrClassicFrameLayout mPtrFrame;
    private MaterialSearchView searchView;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View list = inflater.inflate(R.layout.pending_receive, container, false);
        context=getActivity();
        searchView =(MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);

        lvItems = (RecyclerView) list.findViewById(R.id.rv4);
        dbh=new DbHandler(getActivity());
        context=getActivity();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);
        mPtrFrame = (PtrClassicFrameLayout) list.findViewById(R.id.rotate_header_grid_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler()
                                {
                                    @Override
                                    public void onRefreshBegin(PtrFrameLayout frame)
                                    {
                                        updateData();

                                    }

                                    @Override
                                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
                                    {
                                        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                                    }


                                }


        );
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.equals(""))
                {
                    rv= new RVAdapterPendingReceive(dbh.show_pending(null));
                    lvItems.setAdapter(rv);
                    rv.notifyDataSetChanged();
                }
                else
                {
                    rv= new RVAdapterPendingReceive(dbh.show_pending(newText));
                    lvItems.setAdapter(rv);
                    rv.notifyDataSetChanged();
                }
                return false;
            }
        });




        if(isNetworkAvailable())
        {

            new LoadData().execute();
        }
        else {
            if(dbh.isTableExists(DbConstant.TABLE_PENDING_RECEIVE)) {
                rv=new RVAdapterPendingReceive( dbh.show_pending(null));
                lvItems.setAdapter(rv);
            } else {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("No Data Found");

                sweetAlertDialog.show();
            }
        }
        return list;
    }


    private void updateData()
    {
        new LoadData().execute();
    }
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class LoadData extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog=new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            dialog.setTitle("please wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            return dbh.getData_pendingreceive();



        }


        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            if(s.equals("DB"))
            {
                new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("DB Failure").show();
            }
            else if(s.equals("Internet"))
            {
                new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("Unable to connect with Server!!!").show();
            }
            else {
                rv=new RVAdapterPendingReceive(dbh.getdata_forpending());
                lvItems.setAdapter(rv);
            }
            super.onPostExecute(s);
            mPtrFrame.setKeepHeaderWhenRefresh(false);
        }
    }




}

