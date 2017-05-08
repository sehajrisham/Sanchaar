package com.nic.civilsurgeonnic;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.DbHandler;

public class OTPScreen extends AppCompatActivity {
    String value;
    Button btotp;
    EditText etotp;
    DbHandler dbh;
    Context context;
    MobileVerification mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        //send otp+phone number
        context=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("OTP");


        init();

       // dbh.mobileverification();

        btotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=etotp.getText().toString();
                if(value==null|| value.equals(""))
                {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Please Enter The OTP",duration);
                    toast.show();

                }
                else{
                  new send_otp().execute(value,dbh.mobile_verification());

                }
            }
        });
    }
    private void init()
    {
        btotp=(Button)findViewById(R.id.btotp);
        etotp=(EditText)findViewById(R.id.etotp);
        dbh=new DbHandler(OTPScreen.this);

    }
    private class send_otp extends AsyncTask<String,Void,String>
    {
        ProgressDialog dialog=new ProgressDialog(OTPScreen.this);
        @Override
        protected void onPreExecute() {
            dialog.setTitle("please wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return dbh.send_otp(params[0],params[1]);


        }


        @Override
        protected void onPostExecute(String aBoolean) {
            dialog.dismiss();


            if (aBoolean.equals("False")) {
                Toast.makeText( OTPScreen.this,"Error in getting OTP", Toast.LENGTH_LONG).show();
            }
            else {
                String var[]=aBoolean.split("#");
                dbh.updateOTP(var[0],var[1],dbh.mobile_verification());
                startActivity(new Intent(OTPScreen.this,Login.class).putExtra("UserId",var[1]));
                finish();
            }



            super.onPostExecute(aBoolean);
        }
    }
    @Override
    public void onResume() {

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                //Do whatever you want with the code here
            }
        }
    };

}
