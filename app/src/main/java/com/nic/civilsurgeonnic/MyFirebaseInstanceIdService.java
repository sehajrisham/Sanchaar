package com.nic.civilsurgeonnic;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import utils.DbHandler;

/**
 * Created by RVerma on 11-01-2017.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService
{
    private static  final String TAG = "MyFirebaseInsIDServise";
    DbHandler dbh;
    final String applicationname = "Sanchaar";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        dbh=new DbHandler(MyFirebaseInstanceIdService.this);
        dbh.Token_Refresh(refreshedToken,dbh.mobile_verification(),applicationname);
        Log.d(TAG, "New Token:  " + refreshedToken);
    }


}
