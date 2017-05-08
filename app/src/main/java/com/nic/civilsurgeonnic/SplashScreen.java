package com.nic.civilsurgeonnic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import utils.DbHandler;

import static com.nic.civilsurgeonnic.R.id.editText;

/**
 * Created by Sehaj Risham on 2/17/2017.
 */

public class SplashScreen extends AppCompatActivity {

    DbHandler dbh;
    Context context;

    EditText userid;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final String TAG = "SplashScreen";
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        context = this;
        userid=(EditText) findViewById(editText);
        dbh = new DbHandler(SplashScreen.this);
        if (!doesDatabaseExist(getApplicationContext(), dbh.DBName)) {
            new LoadData().execute();
        } else {
            if(dbh.appid()){
                String userid = dbh.get_user_id();
               if( dbh.checklogin(userid)) {
                   startActivity(new Intent(SplashScreen.this,MainActivity.class));
                   finish();
               }
                else{
                startActivity(new Intent(SplashScreen.this,Login.class).putExtra("UserId",userid));
                finish();}
           }
            else
            {
                startActivity(new Intent(SplashScreen.this,MobileVerification.class));
               finish();
            }
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /*public boolean appid(){

        SQLiteDatabase db=getReadableDatabase();
        Cursor r= db.rawQuery("select * from "+ OTP_Table_name,null);
        if(r.getCount()>0)
        {
            r.moveToFirst();
            return true;
        }
        else
            return false;

    }*/

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SplashScreen Page") // TODO: Define a title for the content shown.
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

    private class LoadData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return dbh.Load_Drug_Institute_table(context);

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Error")) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("No Internet Connection").show();
            } else if (s.equals("ErrorServer")) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Unable to connect with Server!!!").show();
            } else {
                startActivity(new Intent(SplashScreen.this, MobileVerification.class));
                finish();
            }
            super.onPostExecute(s);
        }
    }
}
