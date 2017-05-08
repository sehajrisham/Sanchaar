package com.nic.civilsurgeonnic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utils.AndroidDatabaseManager;
import utils.DbHandler;

import static com.nic.civilsurgeonnic.R.id.editText;
import static com.nic.civilsurgeonnic.R.id.editText2;


/**
 * Created by sehdev's on 28/03/2017.
 */

public class Login extends AppCompatActivity {
    Button bt;
    EditText userId;
    EditText password1;
    DbHandler dbh;
    String UserId;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        init();
        try {
            UserId = getIntent().getExtras().getString("UserId");
            userId.setText(UserId);
            userId.setEnabled(false);
        }
        catch(Exception e){
            UserId="";
    }


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password=password1.getText().toString();
                if(password==null|| password.equals(""))
                {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Please Enter The password",duration);
                    toast.show();

                }
                else{
                    new Login.verify_user().execute(UserId,md5(password));

                }


            }
        });
    }
    public void init()
    {
        bt=(Button)findViewById(R.id.bt);
        userId=(EditText) findViewById(editText);
        password1=(EditText)findViewById(editText2);
        dbh=new DbHandler(Login.this);
    }

    public static String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }
        System.out.println(hex.toString());
        return hex.toString();

    }

    private class verify_user extends AsyncTask<String,Void,String>
    {
        ProgressDialog dialog=new ProgressDialog(Login.this);
        @Override
        protected void onPreExecute() {
            dialog.setTitle("please wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return dbh.Load_Login(params[0],params[1]);


        }



        @Override
        protected void onPostExecute(String aBoolean) {
            dialog.dismiss();


            if (aBoolean.equals("False")) {
                Toast.makeText( Login.this,"Invalid User", Toast.LENGTH_LONG).show();
                password1.setText("");
            }
            else {
                dbh.updateLoginBit(UserId);
                dbh.updateLoginVerifyBit(UserId);
                SharedPreferences sharedpreferences;
                sharedpreferences = getSharedPreferences("Codes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("DistCode", aBoolean);
                editor.commit();
                Intent abc=new Intent(Login.this,MainActivity.class);
                startActivity(abc);
                finish();
            }



            super.onPostExecute(aBoolean);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, AndroidDatabaseManager.class));
        super.onBackPressed();
    }
}

