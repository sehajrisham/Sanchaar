package com.nic.civilsurgeonnic.TabbedFragments;

/**
 * Created by RVerma on 27-04-2017.
 */


import android.app.ProgressDialog;
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

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nic.civilsurgeonnic.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import utils.DbConstant;
import utils.DbHandler;
import utils.RVAdapterNotify;
/**
 * Created by sehdev's on 17/04/2017.
 */

public class Notify extends Fragment{
    DbHandler dbh;
    RVAdapterNotify rv4;
    RecyclerView lvItems;
    Context context;
    private PtrClassicFrameLayout mPtrFrame;
    private MaterialSearchView searchView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private int offSet = 0;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View list = inflater.inflate(R.layout.notify, container, false);
        context=getActivity();
        searchView =(MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);

        lvItems = (RecyclerView) list.findViewById(R.id.rv_notify);
        dbh=new DbHandler(getActivity());
        context=getActivity();
        mPtrFrame = (PtrClassicFrameLayout) list.findViewById(R.id.rotate_header_grid_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler()
        {
            @Override
            public void onRefreshBegin(in.srain.cube.views.ptr.PtrFrameLayout frame)


            {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                rv4= new RVAdapterNotify(dbh.show_notify(query));
                lvItems.setAdapter(rv4);
                rv4.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                {
                    rv4= new RVAdapterNotify(dbh.show_notify(null));
                    lvItems.setAdapter(rv4);
                    rv4.notifyDataSetChanged();
                }
                else
                {
                    rv4= new RVAdapterNotify(dbh.show_notify(newText));
                    lvItems.setAdapter(rv4);
                    rv4.notifyDataSetChanged();
                }
                return false;
            }
        });


        if(dbh.isTableExists(DbConstant.T_notification)) {
            rv4=new RVAdapterNotify( dbh.show_notify(null));
            lvItems.setAdapter(rv4);
        } else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("No Data Found");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                public void onClick(SweetAlertDialog sweetAlertDialog) {
                 sweetAlertDialog.dismiss();
                }
            });
            sweetAlertDialog.show();
        }

        return list;
    }



    private void updateData()
    {
        new Loading().execute();
    }




    private class Loading extends AsyncTask<Void,Void,String>
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

            return dbh.getData_forinspection();



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
                rv4=new RVAdapterNotify(dbh.show_notify(null));
                lvItems.setAdapter(rv4);
            }
            super.onPostExecute(s);
            mPtrFrame.setKeepHeaderWhenRefresh( false );
        }
    }

}

