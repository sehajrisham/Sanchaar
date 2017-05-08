package utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sehaj Risham on 2/9/2017.
 */

public class DbHandler  extends SQLiteOpenHelper implements DbConstant {

    final String NameSpace = "http://tempuri.org/";
    String LoadMasterMathod = "GetNotPerformingInstitutes";
    String SoapLinkMaster = "http://tempuri.org/master";
    static String LoadOtp="Verifyuser";
    static String OtpVerification="verifyOtp";
    String result=null;


    String LoadGetNotPerformingInstitutes = "GetNotPerformingInstitutes";
    String LoadGetDrugDetails="GetDrug";
    String LoadGetInstituteDetail="GetInstituteDetail";
    String LoadGetDrugInstituteDetail="GetDrugInstitute";
    String LoadGetExpiryStocck="GetExpiryStock";
    String LoadGetUserDetail="GetUserDetail";
    String LoadDistrictWiseStock="GetDistrictwiseStock";
    String LoadInstituteWiseStock="GetInstitutewiseStock";
    String LoadUserLoginDetail="Login";
    String LoadTokenRefresh="Updatetoken";
    String LoadGetMasterCodes="getmastercodes";
    static String LoadGetInspection = "GetInspection_WHk";
    static String LoadGetPendingreceive = "GetPending_Whk";

    String SoapLinkGetNotPerformingInstitutes = "http://tempuri.org/GetNotPerformingInstitutes";
    String SoapLinkGetDrugInstitute = "http://tempuri.org/GetDrugInstitute";
    String SoapLinkGetDrug = "http://tempuri.org/GetDrug";
    String SoapLinkGetExpiryStock = "http://tempuri.org/GetExpiryStock";
    String SoapLinkGetInstitute = "http://tempuri.org/GetInstituteDetail";
    String SoapLinkGetUserDetail="http://tempuri.org/GetUserDetail";
    String SoapLinkGetDistrictWiseStock = "http://tempuri.org/GetDistrictwiseStock";
    String SoapLinkGetInstituteWiseStock = "http://tempuri.org/GetInstitutewiseStock";
    String SoapLinkGetMasterCodes = "http://tempuri.org/getmastercodes";
    static String SoapLinkGetOTP="http://tempuri.org/Verifyuser";
    static String SoapLinkOtpVerification="http://tempuri.org/verifyOtp";
    static String SoapLinkGetpending = "http://tempuri.org/GetPending_Whk";
    static String SoapLinkGetInspection = "http://tempuri.org/GetInspection_WHk";
    String SoapLinkGetLogin="http://tempuri.org/Login";
    String SoapLinkGetTokenRefresh="http://tempuri.org/Updatetoken";
    final String URL = "http://android.dpmuhry.gov.in/Sanchaar.asmx";
    final String OTPURL="http://android.dpmuhry.gov.in/otp.asmx";
    JSONObject jsonResponse;
    SQLiteDatabase db;

    public DbHandler(Context context) {
        super(context, DBName, null, DBversion);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_NotPerformingInstitutes);
        db.execSQL(Create_Table_Drug_Detail);
        db.execSQL(Create_Table_Institute_Detail);
        db.execSQL(Create_Table_Drugs_To_Be_Expired);
        db.execSQL(Create_Table_UserDetail);
        db.execSQL(Create_Table_DistrictWiseStock);
        db.execSQL(Create_Table_InstituteWiseStock);
        db.execSQL(Create_OTP_Table);
        db.execSQL(Create_Table_pending);
        db.execSQL(Create_Notify);
       // db.execSQL(Create_Table_DrugDetail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
///////////////OTP////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public long dbinsert(ContentValues cv)
    { long l =0;
        SQLiteDatabase writeableDB = getWritableDatabase();
       l= writeableDB.insert(OTP_Table_name, null, cv);
        writeableDB.close();
        return l;


    }
    public Boolean appid()
    {

        SQLiteDatabase db=getReadableDatabase();
        Cursor r= db.rawQuery("select * from "+ DbConstant.OTP_Table_name+" where "+OTP_Application_Id +" like '%Sanchaar%'",null);
        if(r.getCount()>0) {
            return true;
        }
           else
               return false;


    }




    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);

        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }

    }


    public String  mobile_verification() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=null;


            c = db.rawQuery("select * from " + DbConstant.OTP_Table_name , null);

        if (c.getCount() > 0) {
            c.moveToFirst();

             return  c.getString(0);
        }
        else
        {
            return "NULL";
        }


    }

    public String get_user_id(){
        SQLiteDatabase db= getReadableDatabase();
        Cursor c=null;
        c= db.rawQuery("select "+ OTP_UserId +" from " + DbConstant.OTP_Table_name, null);

        if(c.getCount() > 0){
            c.moveToFirst();
            return c.getString(0);

        }
        else
        {
            return "NULL";
        }
    }
    public void updateOTP(String applicationId,String userId,String PhoneNumber){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(OTP_Application_Id,applicationId);
        cv.put(OTP_UserId,userId);


        db.update(OTP_Table_name,cv,OTP_Phone_no+"='"+PhoneNumber+"'", null);

    }
    public void updateLoginBit(String userId){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(OTP_Login_Bit,"Y");


        db.update(OTP_Table_name,cv,OTP_UserId+"='"+userId+"'", null);

    }
    public void updateLoginBit_Logout(String userId){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(OTP_Login_Verifiy_Bit,"N");


        db.update(OTP_Table_name,cv,OTP_UserId+"='"+userId+"'", null);

    }
   public void updateLoginVerifyBit(String userId){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(OTP_Login_Verifiy_Bit,"Y");


        db.update(OTP_Table_name,cv,OTP_UserId+"='"+userId+"'", null);

    }

    public Boolean checklogin(String userid){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c= db.rawQuery("select "+OTP_Login_Verifiy_Bit+" from "+ DbConstant.OTP_Table_name,null);

        if(c.getCount() > 0){
            c.moveToFirst();
            if(c.getString(0).equals("Y")){
                return true;
            }
            else
                return false;


        }
        return false;


    }
    public String send_data_to_server(String phone_no,String imei_number,String token_id,String application_id) {
        SoapObject soap = new SoapObject(NameSpace, LoadOtp);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("Mobile");
        pi.setValue(phone_no);
        pi.setType(String.class);
        soap.addProperty(pi);


        pi =new PropertyInfo();
        pi.setName("IMEI");
        pi.setValue(imei_number);
        pi.setType(String.class);
        soap.addProperty(pi);

        pi =new PropertyInfo();
        pi.setName("Token_id");
        pi.setValue(token_id);
        pi.setType(String.class);
        soap.addProperty(pi);

        pi =new PropertyInfo();
        pi.setName("Applicationname");
        pi.setValue(application_id);
        pi.setType(String.class);
        soap.addProperty(pi);


        SQLiteDatabase db=getReadableDatabase();
        SoapSerializationEnvelope ser = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ser.dotNet = true;
        ser.setOutputSoapObject(soap);
        long s=0;
        String[] arr=null;
        try {
            HttpTransportSE http = new HttpTransportSE(OTPURL);
            http.call(SoapLinkGetOTP, ser);
            SoapPrimitive per = (SoapPrimitive) ser.getResponse();
            result = per.toString();
            //arr = result.split("#");
            if (result.equals("SUCCESS"))
            {


                    String sql = "delete from " + OTP_Table_name;
                    db.execSQL(sql);
                    db.close();

                    ContentValues cv=new ContentValues();
                    cv.put(OTP_Phone_no,phone_no);
                    cv.put(OTP_IMEI_number,imei_number);
                    cv.put(OTP_Token_Id,token_id);
                    cv.put(OTP_Login_Bit,"N");
                    cv.put(OTP_Login_Verifiy_Bit,"N");
                    s = dbinsert(cv);




            }



        }


         catch (Exception e) {
            e.printStackTrace();
             return "ServerError";

        }
        return result;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public String send_otp(String otp,String phone_number) {
        SoapObject soap = new SoapObject(NameSpace, OtpVerification);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("otp");
        pi.setValue(otp);
        pi.setType(String.class);
        soap.addProperty(pi);


        pi =new PropertyInfo();
        pi.setName("mobilenumber");
        pi.setValue(phone_number);
        pi.setType(String.class);
        soap.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("ApplicationName");
        pi.setValue("Sanchaar");
        pi.setType(String.class);
        soap.addProperty(pi);


        SoapSerializationEnvelope ser = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ser.dotNet = true;
        ser.setOutputSoapObject(soap);


        try {
            HttpTransportSE http = new HttpTransportSE(OTPURL);
            http.call(SoapLinkOtpVerification, ser);
            SoapPrimitive per = (SoapPrimitive) ser.getResponse();
            result = per.toString();
            return result;


        }


        catch (Exception e) {
            e.printStackTrace();
            return "server";
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CardGetSet> get_Institute_not_performing(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=null;
        ArrayList<CardGetSet> data = new ArrayList<>();
        if(var==null)
        {
            c = db.rawQuery("select * from " + TB_NAME_NOT_PERFORMING_INSTITUTES , null);
        }
        else {
            c = db.rawQuery("select * from " + TB_NAME_NOT_PERFORMING_INSTITUTES +" where "+ C_INSTNAME+" like '%"+var+"%'", null);
        }
        //   c = db.rawQuery("select * from " + TB_NAME, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                data.add(new CardGetSet(c.getString(0), c.getString(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet("No data" , "No Data" , "No data", "No data"));

        }
        return data;
    }

    public ArrayList<CardGetSet> get_User_Detail(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=null;
        ArrayList<CardGetSet> data = new ArrayList<>();
        if(var==null)
        {
            c = db.rawQuery("select * from " + TB_NAME_USER_DETAIL , null);
        }
        else {
            c = db.rawQuery("select * from " + TB_NAME_USER_DETAIL +" where "+  C_USER_DETAIL_USER_NAME+" like '%" +var+"%' OR "+ C_USER_DETAIL_INSTITUTE_NAME+" like '%" +var+"%'", null);
        }
        //   c = db.rawQuery("select * from " + TB_NAME, null);
        if (c.getCount() > 0) {
             c.moveToFirst();
            do {
                data.add(new CardGetSet(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet("No data" , "No Data" , "No data", "No data", "No Data"));

        }
        return data;
    }

    public ArrayList<CardGetSet2> get_drug_detail(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        ArrayList<CardGetSet2> data = new ArrayList<>();
        if(var==null)
        {
            c = db.rawQuery("select * from " + TB_NAME_DRUG_DETAIL , null);
        }
        else {
            c = db.rawQuery("select * from " + TB_NAME_DRUG_DETAIL +" where "+ C_DRUGNAME+" like '%" +var+"%' OR "+ C_DRUGCODE+" like '%" +var+"%'", null);
        }

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                data.add(new CardGetSet2(c.getString(0), c.getString(1)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet2("No data", "No data"));

        }
        return data;
    }

    public ArrayList<CardGetSet> get_institute_detail(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        ArrayList<CardGetSet> data = new ArrayList<>();
        if(var==null)
        {
            c = db.rawQuery("select * from " + TB_NAME_INSTITUTE_DETAIL , null);
        }
        else {
            c = db.rawQuery("select * from " + TB_NAME_INSTITUTE_DETAIL +" where "+ C_INSTITUTENAME+" like '%" +var+"%' OR "+ C_INSTITUTEID+" like '%" +var+"%'", null);
        }
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                data.add(new CardGetSet(c.getString(0),c.getString(1)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet("No data","No Data"));

        }
        return data;
    }


    public ArrayList<CardGetSet> get_drugs_to_be_expired(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=null;
        ArrayList<CardGetSet> data = new ArrayList<>();
        if(var==null)
        {
          c = db.rawQuery("select * from " + TB_NAME_DRUGS_TO_BE_EXPIRED, null);
        }
        else {
            c = db.rawQuery("select * from " + TB_NAME_DRUGS_TO_BE_EXPIRED +" where "+ C_FULLDRUG_EXPIRE+" like '%" +var+"%' OR "+ C_DRUGCODE_EXPIRE+" like '%" +var+"%'", null);
        }
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                data.add(new CardGetSet(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4) , c.getString(5)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet("No data","No data","No data","No data","No data","No data"));

        }
        return data;
    }

    public ArrayList<CardGetSet> get_district_wise_stock(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=null;
        ArrayList<CardGetSet> data = new ArrayList<>();
        if(var==null)
        {
            c = db.rawQuery("select * from " + TB_NAME_DETAIL_DISTRICT_WISE_STOCK , null);
        }
        else {
            c = db.rawQuery("select * from " + TB_NAME_DETAIL_DISTRICT_WISE_STOCK +" where "+ C_DISTRICT_WISE_STOCK_DRUGID+" like '%"+var+"%'", null);
        }
        //   c = db.rawQuery("select * from " + TB_NAME, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                data.add(new CardGetSet(c.getString(0), c.getString(1), c.getString(2)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet("No data" , "No Data" , "No data"));

        }
        return data;
    }

    public ArrayList<CardGetSet2> get_institute_wise_stock(String var) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=null;
        ArrayList<CardGetSet2> data = new ArrayList<>();

            c = db.rawQuery("select * from " + TB_NAME_DETAIL_INSTITUTE_WISE_STOCK , null);

        //   c = db.rawQuery("select * from " + TB_NAME, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                data.add(new CardGetSet2(c.getString(0), c.getString(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
        }
        else
        {
            data.add(new CardGetSet2("No data" , "No Data" , "No data", "No data"));

        }
        return data;
    }

    public String Load_InstitutesNotPerforming_table(Context context) {
        String res = null;
        SharedPreferences prefs = context.getSharedPreferences("Codes", MODE_PRIVATE);
        SoapObject request = new SoapObject(NameSpace, LoadGetNotPerformingInstitutes);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("DCode");
        pi.setType(String.class);
        pi.setValue(prefs.getString("DistCode","0"));

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetNotPerformingInstitutes, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        delete_nonperforminginstitutes();


        int lengthJsonArr;
        try {
            res = "{ Institutes_not_responding :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Institutes_not_responding");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_INSTNAME, jsonChildNode.optString("InstituteName").toString());
                values.put(C_DISTNAME, jsonChildNode.optString("DName").toString());
                values.put(C_WAREHOUSE, jsonChildNode.optString("Warehouse").toString());
                values.put(C_DATACOMPILED, jsonChildNode.optString("Datacompliledformonth").toString());

                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_NOT_PERFORMING_INSTITUTES, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    private void delete_nonperforminginstitutes()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_NOT_PERFORMING_INSTITUTES;
            db.execSQL(sql);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }
    private void delete_userdetail()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_USER_DETAIL;
            db.execSQL(sql);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }
    private void delete_drugstoexpire()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_DRUGS_TO_BE_EXPIRED;
            db.execSQL(sql);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }
    private void delete_drugdetail()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_DRUG_DETAIL;
            db.execSQL(sql);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }
    private void delete_institute()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_INSTITUTE_DETAIL;
            db.execSQL(sql);
            db.close();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }

    private void delete_districtwisestock()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_DETAIL_DISTRICT_WISE_STOCK;
            db.execSQL(sql);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }

    private void delete_institutewisestock()
    {
        try {
            SQLiteDatabase db=getWritableDatabase();
            String sql = "delete from " + TB_NAME_DETAIL_INSTITUTE_WISE_STOCK;
            db.execSQL(sql);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

    }


    public String Load_Drug_Institute_table(Context context) {
        String res = null;
        SharedPreferences prefs = context.getSharedPreferences("Codes", MODE_PRIVATE);
        SoapObject request = new SoapObject(NameSpace, LoadGetDrugInstituteDetail);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("InstituteDCode");
        pi.setType(String.class);
        pi.setValue(prefs.getString("DistCode","0"));
        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetDrugInstitute, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        delete_drugdetail();
        delete_institute();


        String[]  Response =res.split("#");
        String[] JsonNames={"Drug_Detail","Institute_Detail"};
        int lengthJsonArr;
        try {
            for(int i=0;i<JsonNames.length;i++)
            {
                Response[i] ="{\""+JsonNames[i]+"\" :"+Response[i]+"}";
                jsonResponse = new JSONObject(Response[i]);
                JSONArray jsonMainNode = jsonResponse.optJSONArray(JsonNames[i]);
                lengthJsonArr = jsonMainNode.length();
                for (int j = 0; j < lengthJsonArr; j++) {

                    ContentValues values = new ContentValues();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                    if(i==0)
                    {
                        values.put(C_DRUGCODE, jsonChildNode.optString("Drugcode").toString());
                        values.put(C_DRUGNAME, jsonChildNode.optString("drug").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(TB_NAME_DRUG_DETAIL, null, values);
                        writeableDB.close();}
                    if(i==1){
                        values.put(C_INSTITUTENAME, jsonChildNode.optString("institutename").toString());
                        values.put(C_INSTITUTEID, jsonChildNode.optString("instituteID").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(TB_NAME_INSTITUTE_DETAIL, null, values);
                        writeableDB.close();

                    }

                }
            }
            /*Load_Institute_table(Response[i]);*/
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    public String Load_Drug_table() {

        String res = null;
        SoapObject request = new SoapObject(NameSpace, LoadGetDrugDetails);
/*        PropertyInfo pi=new PropertyInfo();
        pi.setName("DCode");
        pi.setType(String.class);
        pi.setValue("7");

        request.addProperty(pi);*/

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetDrug, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        delete_drugdetail();


        int lengthJsonArr;
        try {
            res = "{ Drug_detail :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Drug_detail");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_DRUGCODE, jsonChildNode.optString("Drugcode").toString());
                values.put(C_DRUGNAME, jsonChildNode.optString("drug").toString());


                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_DRUG_DETAIL, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    public String Load_Institute_table(Context context) {

        String res = null;
        SharedPreferences prefs = context.getSharedPreferences("Codes", MODE_PRIVATE);
        SoapObject request = new SoapObject(NameSpace, LoadGetInstituteDetail);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("InstituteDCode");
        pi.setType(String.class);
        pi.setValue(prefs.getString("DistCode","0"));

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetInstitute, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        delete_institute();


        int lengthJsonArr;
        try {
            res = "{ Institute_detail :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Institute_detail");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_INSTITUTENAME, jsonChildNode.optString("institutename").toString());

                values.put(C_INSTITUTEID, jsonChildNode.optString("instituteID").toString());


                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_INSTITUTE_DETAIL, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return "Success";
    }

    public String Load_Drugs_to_Expire(Context context,String Instid) {
        String res = null;
        SharedPreferences prefs = context.getSharedPreferences("Codes", MODE_PRIVATE);
        SoapObject request = new SoapObject(NameSpace, LoadGetExpiryStocck);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("DCode");
        pi.setType(String.class);
        pi.setValue(prefs.getString("DistCode","0"));
        request.addProperty(pi);

       pi=new PropertyInfo();
        pi.setName("InstId");
        pi.setType(String.class);
        pi.setValue(Instid);
        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetExpiryStock, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        delete_drugstoexpire();


        int lengthJsonArr;
        try {
            res = "{ Drugs_to_Expire :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Drugs_to_Expire");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_WAREHOUSE_EXPIRE, jsonChildNode.optString("WH").toString());
                values.put(C_FULLDRUG_EXPIRE, jsonChildNode.optString("FullDrug").toString());
                values.put(C_DRUGCODE_EXPIRE, jsonChildNode.optString("drugcode").toString());
                values.put(C_BATCHNO_EXPIRE, jsonChildNode.optString("BatchNo").toString());
                values.put(C_QUANTITY_EXPIRE, jsonChildNode.optString("Quantity").toString());
                values.put(C_DAYSTOEXPIRE_EXPIRE, jsonChildNode.optString("days_to_expire").toString());

                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_DRUGS_TO_BE_EXPIRED, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    public String Load_User_Detail_Table() {
        String res = null;
        SoapObject request = new SoapObject(NameSpace, LoadGetUserDetail);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("WarehouseID");
        pi.setType(String.class);
        pi.setValue("003268");

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetUserDetail, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        delete_userdetail();


        int lengthJsonArr;
        try {
            res = "{ User_Detail :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("User_Detail");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_USER_DETAIL_INSTITUTE_NAME, jsonChildNode.optString("InstituteName").toString());
                values.put(C_USER_DETAIL_EMAILID, jsonChildNode.optString("EMailID").toString());
                values.put(C_USER_DETAIL_MOBILE, jsonChildNode.optString("Mobile").toString());
                values.put(C_USER_DETAIL_USER_NAME, jsonChildNode.optString("UserName").toString());
                values.put(C_USER_DETAIL_LANDLINE, jsonChildNode.optString("Landline").toString());

                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_USER_DETAIL, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    public String Load_District_Wise_Stock_Table(String Id,Context context) {
        String res = null;
        SharedPreferences prefs = context.getSharedPreferences("Codes", MODE_PRIVATE);
        SoapObject request = new SoapObject(NameSpace, LoadDistrictWiseStock);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("InstituteDCode");
        pi.setType(String.class);
        pi.setValue(prefs.getString("DistCode","0"));

        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("drugcode");
        pi.setType(String.class);
        pi.setValue(Id);

        request.addProperty(pi);
        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetDistrictWiseStock, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        delete_districtwisestock();

        int lengthJsonArr;
        try {
            res = "{ District_Wise_Stock :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("District_Wise_Stock");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_DISTRICT_WISE_STOCK_DRUGID, jsonChildNode.optString("DrugId").toString());
                values.put(C_DISTRICT_WISE_STOCK_FULLDRUG, jsonChildNode.optString("FullDrug").toString());
                values.put(C_DISTRICT_WISE_STOCK_CURRENT_STOCK, jsonChildNode.optString("Current_Stock").toString());

                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_DETAIL_DISTRICT_WISE_STOCK, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    public String Load_Institute_Wise_Stock_Table(String DrugId,String InstId,Context context) {
        String res = null;
        SharedPreferences prefs = context.getSharedPreferences("Codes", MODE_PRIVATE);
        SoapObject request = new SoapObject(NameSpace, LoadInstituteWiseStock);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("InstituteDCode");
        pi.setType(String.class);
        pi.setValue(prefs.getString("DistCode","0"));

        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("drugcode");
        pi.setType(String.class);
        pi.setValue(DrugId);

        request.addProperty(pi);
        
        pi=new PropertyInfo();
        pi.setName("InstituteID");
        pi.setType(String.class);
        pi.setValue(InstId);

        request.addProperty(pi);
        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetInstituteWiseStock, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        delete_institutewisestock();

        int lengthJsonArr;
        try {
            res = "{ Institute_Wise_Stock :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Institute_Wise_Stock");
            lengthJsonArr = jsonMainNode.length();
            for (int j = 0; j < lengthJsonArr; j++) {

                ContentValues values = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                values.put(C_INSTITUTE_WISE_STOCK_DRUGID, jsonChildNode.optString("DrugId").toString());
                values.put(C_INSTITUTE_WISE_STOCK_FULLDRUG, jsonChildNode.optString("FullDrug").toString());
                values.put(C_INSTITUTE_WISE_STOCK_INSTITUTE_NAME, jsonChildNode.optString("InstituteName").toString());
                values.put(C_INSTITUTE_WISE_STOCK_CURRENT_STOCK, jsonChildNode.optString("Current_Stock").toString());

                SQLiteDatabase writeableDB = getWritableDatabase();
                writeableDB.insert(TB_NAME_DETAIL_INSTITUTE_WISE_STOCK, null, values);
                writeableDB.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
        return "Success";
    }

    public String Load_Login(String userid,String password) {

        String res = null;
        SoapObject request = new SoapObject(NameSpace, LoadUserLoginDetail);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("UserId");
        pi.setType(String.class);
        pi.setValue(userid);

        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("Password");
        pi.setType(String.class);
        pi.setValue(password);

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetLogin, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
             res = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
    if(res.equals("True"))
    {
        SoapObject request1 = new SoapObject(NameSpace, LoadGetMasterCodes);
        PropertyInfo pi1=new PropertyInfo();
        pi.setName("UserId");
        pi.setType(String.class);
        pi.setValue(userid);

        request1.addProperty(pi);

        SoapSerializationEnvelope envolpe1 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe1.dotNet = true;
        envolpe1.setOutputSoapObject(request1);
        HttpTransportSE androidHTTP1 = new HttpTransportSE(URL);

        try {
            androidHTTP1.call(SoapLinkGetMasterCodes, envolpe1);
            SoapPrimitive response = (SoapPrimitive) envolpe1.getResponse();
            res = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }
    }
    else
        res="False";
        return res;
    }

    public String Token_Refresh(String tokenid,String mobilenumber,String application) {

        String res = null;
        SoapObject request = new SoapObject(NameSpace, LoadTokenRefresh);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("Token");
        pi.setType(String.class);
        pi.setValue(tokenid);

        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("mobilenumber");
        pi.setType(String.class);
        pi.setValue(mobilenumber);

        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("Application");
        pi.setType(String.class);
        pi.setValue(application);

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetTokenRefresh, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "ErrorServer";
        }


    }


    public void insert_notification(String Head, String Content)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Heading,Head);
        cv.put(Notify,Content);
        db.insert(T_notification,null,cv);

    }

    public ArrayList<CardGetSet_notify> get_notify()
    {
        return show_notify(null);
    }

    public ArrayList<CardGetSet_notify> show_notify(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CardGetSet_notify> arr = new ArrayList<>();
        Cursor cr;
        if (search == null) {
            cr = db.rawQuery("Select * from " + T_notification, null);
        } else {
            cr = db.rawQuery("Select * from " + T_notification + " WHERE " + Heading + " LIKE '%" + search + "%'  OR " + Notify + " LIKE '%" + search + "%'", null);

        }


        if (cr.getCount() > 0) {
            cr.moveToFirst();
            do {
                String data =cr.getString(0)+cr.getString(1);
                arr.add(new CardGetSet_notify(cr.getString(0),
                        cr.getString(1)));
            } while (cr.moveToNext());
        }
        return arr;

    }

    public String getData_forinspection() {
        String res = null;

        SoapObject request = new SoapObject(NameSpace, LoadGetInspection);

        ArrayList<CardGetSet> data = new ArrayList<>();

        PropertyInfo pi = new PropertyInfo();
        pi.setName("wh_id");
        pi.setType(String.class);
        pi.setValue("003268");

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetInspection, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Internet";
        }
        int lengthJsonArr;
        ContentValues cv = null;
        try {
            res = "{ Institutes_not_responding :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Institutes_not_responding");
            lengthJsonArr = jsonMainNode.length();
            del_inspect();
            for (int j = 0; j < lengthJsonArr; j++) {
                cv = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);

                cv.put(C_id_inspect, jsonChildNode.optString("id").toString());
                cv.put(C_PO_no_inspect, jsonChildNode.optString("PO_No").toString());

                SQLiteDatabase sd = getWritableDatabase();
                sd.insert(T_inspection_note, null, cv);
                sd.close();


            }
        } catch (Exception e) {
            e.printStackTrace();
            return "DB";
        }
        return "Success";

    }

    public String getData_pendingreceive() {
        String res = null;

        SoapObject request = new SoapObject(NameSpace, LoadGetPendingreceive);

        ArrayList<CardGetSet_pending_receive> data = new ArrayList<>();

        PropertyInfo pi = new PropertyInfo();
        pi.setName("wh_id");
        pi.setType(String.class);
        pi.setValue("003268");

        request.addProperty(pi);

        SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet = true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP = new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkGetpending, envolpe);
            SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
            res = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Internet";
        }
        int lengthJsonArr;
        ContentValues cv = null;
        try {
            res = "{ Institutes_not_responding :" + res + " }";
            jsonResponse = new JSONObject(res);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Institutes_not_responding");
            lengthJsonArr = jsonMainNode.length();
            del_pending();
            for (int j = 0; j < lengthJsonArr; j++) {
                cv = new ContentValues();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);

                cv.put(C_id_pending_receive, jsonChildNode.optString("id").toString());
                cv.put(C_po_no_pending_receive, jsonChildNode.optString("PO_No").toString());

                SQLiteDatabase sd = getWritableDatabase();
                sd.insert(TABLE_PENDING_RECEIVE, null, cv);
                sd.close();


            }
        } catch (Exception e) {
            e.printStackTrace();
            return "DB";
        }
        return "Success";

    }
    public void del_pending() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(Del_pending);
    }

    public void del_inspect() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(Del_inspect);
    }

    public ArrayList<CardGetSet_pending_receive> getdata_forpending() {
        return show_pending(null);
    }

    public ArrayList<CardGetSet_pending_receive> show_pending(String search) {
        SQLiteDatabase db = getReadableDatabase();
        //Cursor cr=db.rawQuery("Select * from "+TABLE_PENDING_RECEIVE, null);

        ArrayList<CardGetSet_pending_receive> arr = new ArrayList<>();
        Cursor cr;
        if (search == null) {
            cr = db.rawQuery("Select * from " + TABLE_PENDING_RECEIVE, null);
        } else {
            cr = db.rawQuery("Select * from " + TABLE_PENDING_RECEIVE + " WHERE " + C_id_pending_receive + " LIKE '%" + search + "%'  OR " + C_po_no_pending_receive + " LIKE '%" + search + "%'", null);
        }
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            do {

                arr.add(new CardGetSet_pending_receive("Id :" + cr.getString(0),
                        "PO NO:" + cr.getString(1)));

            } while (cr.moveToNext());
        }
        return arr;
    }

    public boolean isTableExists(String tableName) {
        boolean isExist = false;
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            }
            cursor.close();
        }
        cursor.close();
        db.close();
        return isExist;
    }
}

