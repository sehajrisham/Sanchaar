package com.nic.civilsurgeonnic;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

import cn.pedant.SweetAlert.SweetAlertDialog;
import utils.DbHandler;

public class MobileVerification extends AppCompatActivity {
    Button bt1;
    String value;
    EditText phone_no;
    Intent toMain;
    Intent togps;
    DbHandler dbh;
    Context context;
    TelephonyManager mngr;
    boolean permissionGranted=false;


    private static final int REQUSET_Permission = 0;
    private static final String TAG = "MobileVerification";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobile_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Verify Phone Number");

        final String applicationname = "Sanchaar";
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
        context = this;
        mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        getID();
        // final String number= getID();
        init();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = phone_no.getText().toString();
               if(permissionGranted) {
                   if (value == null || value.equals("")) {
                       Context context = getApplicationContext();
                       int duration = Toast.LENGTH_SHORT;
                       Toast toast = Toast.makeText(context, "Please Enter The Phone Number", duration);
                       toast.show();

                   } else {
                       new send_data().execute(value, mngr.getDeviceId(), token, applicationname);
                    /*Intent abc=new Intent(MobileVerification.this,Login.class);
                    startActivity(abc);*/
                   }
               }
               else {
                   Toast toast = Toast.makeText(context, "Permission unavailable!!!", Toast.LENGTH_SHORT);
                   toast.show();
               }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        bt1 = (Button) findViewById(R.id.bt1);
        phone_no = (EditText) findViewById(R.id.ph_no);
        dbh = new DbHandler(MobileVerification.this);

    }


    private void getID() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(MobileVerification.this, new String[]{Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_PHONE_STATE}, REQUSET_Permission);
            }
        }
        else
            permissionGranted=true;
        return ;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQUSET_Permission:
                permissionGranted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MobileVerification Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class send_data extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(MobileVerification.this);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("please wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return dbh.send_data_to_server(params[0], params[1], params[2], params[3]);


        }


        @Override
        protected void onPostExecute(String aBoolean) {
            dialog.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(MobileVerification.this);


            if (aBoolean.equals("SUCCESS")) {
                //save mobile number here in the database
                phone_no.getText().toString();
                startActivity(new Intent(MobileVerification.this, OTPScreen.class));
                finish();
            } else if (aBoolean.equals("ER101")) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setContentText("Your Mobile number is not registered with us.Kindly update your mobile number at the portal or Contact Support").show();

            } else if (aBoolean.equals("ER102")) {

                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setContentText("This User is registered with other Device in the same application.Contact Support").show();

            }
            else if (aBoolean.equals("ER103")) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setContentText("This Device is " +
                        "registered with other user in the same application.").show();

            } else if (aBoolean.equals("ER104")) {

                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setContentText("This User Is Already Active,Contact Support.").show();

            } else if(aBoolean.equals("ServerError"))
            {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setContentText("Unable to connect with Server").show();

            }

            super.onPostExecute(aBoolean);
        }
    }
}


