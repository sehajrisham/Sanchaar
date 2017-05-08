package utils;

/**
 * Created by Sehaj Risham on 2/9/2017.
 */

public interface DbConstant {

    int DBversion = 1;
    String DBName = "CivilSurgeon";
    //Creating table for INSTITUTES NOT PERFORMING
    String TB_NAME_NOT_PERFORMING_INSTITUTES = "Table_NotPerformingInstitutes";
    String TB_NAME_DRUG_DETAIL = "Table_Drug_Detail";
    String TB_NAME_INSTITUTE_DETAIL = "Table_Institute_Detail";
    String TB_NAME_DRUGS_TO_BE_EXPIRED = "Table_Drugs_To_Be_Expired";
    String  TB_NAME_DETAIL_DISTRICT_WISE_STOCK="Table_Detail_District_Wise_Stock";
    String TB_NAME_DETAIL_INSTITUTE_WISE_STOCK="Table_Detail_Institute_Wise_Stock";
    String TB_NAME_USER_DETAIL="Table_User_Detail";
    String C_INSTNAME = "C_InstituteName";
    String C_DISTNAME = "C_DistrictName";
    String C_WAREHOUSE = "C_Warehouse";
    String C_DATACOMPILED = "C_DataCompiledForMonths";
    String C_DRUGCODE = "C_DrugCode";
    String C_DRUGNAME = "C_DrugName";
    String C_INSTITUTENAME = "C_InstituteName";
    String C_INSTITUTEID = "C_InstituteId";
    String C_WAREHOUSE_EXPIRE = "C_Warehouse_Expire";
    String C_FULLDRUG_EXPIRE = "C_fulldrug_Expire";
    String C_DRUGCODE_EXPIRE = "C_drugcode_Expire";
    String C_BATCHNO_EXPIRE = "C_batchno_Expire";
    String C_QUANTITY_EXPIRE = "C_quantity_Expire";
    String C_DAYSTOEXPIRE_EXPIRE = "C_daystoexpire_Expire";
    String C_USER_DETAIL_INSTITUTE_NAME="C_UserDetail_InstituteName";
    String C_USER_DETAIL_EMAILID="C_UserDetail_EmailId";
    String C_USER_DETAIL_MOBILE="C_UserDetail_Mobile";
    String C_USER_DETAIL_USER_NAME="C_UserDetail_UserName";
    String C_USER_DETAIL_LANDLINE="C_UserDetail_Landline";

    String C_DISTRICT_WISE_STOCK_DRUGID="C_DistrictWiseStock_DrugId";
    String C_DISTRICT_WISE_STOCK_FULLDRUG="C_DistrictWiseStock_FullDrug";
    String C_DISTRICT_WISE_STOCK_CURRENT_STOCK="C_DistrictWiseStock_Current_Stock";

    String C_INSTITUTE_WISE_STOCK_DRUGID="C_InstituteWiseStock_DrugId";
    String C_INSTITUTE_WISE_STOCK_FULLDRUG="C_InstituteWiseStock_FullDrug";
    String C_INSTITUTE_WISE_STOCK_INSTITUTE_NAME="C_InstituteWiseStock_Institute_Name";
    String C_INSTITUTE_WISE_STOCK_CURRENT_STOCK="C_InstituteWiseStock_Current_Stock";


   // public static final String OTP_DBname="IMEI_db";
  String OTP_Table_name="tbl";
  String OTP_Phone_no="Ph_no";
  String  OTP_UserId="UserID";
  String OTP_IMEI_number="IMEI";
  String OTP_Token_Id="Token_Id";
  String OTP_Application_Id="Application_Id";
  String OTP_Login_Bit="Login_Bit";
   String OTP_Login_Verifiy_Bit="LoginVerifyBit";

    String T_notification="Notify";
    String Heading="header";
    String Notify="notify";

    String TABLE_PENDING_RECEIVE ="Table_Pending_Receive";
    String C_id_pending_receive ="id";
    String C_po_no_pending_receive ="po_no";

    String T_inspection_note ="inspection_table";
    String C_id_inspect="id";
    String C_PO_no_inspect="po_no";


    String Create_OTP_Table = "Create Table " + OTP_Table_name + " ("+
            OTP_Phone_no+ " Text,"+
            OTP_IMEI_number+ " Text,"+
            OTP_Token_Id+ " Text,"+
            OTP_UserId+ " Text," +
            OTP_Application_Id+ " Text," +
            OTP_Login_Bit+ " Text," +
            OTP_Login_Verifiy_Bit+ " Text)";

    String Create_Table_NotPerformingInstitutes = "Create Table " + TB_NAME_NOT_PERFORMING_INSTITUTES + " (" + C_INSTNAME + " TEXT,"
            + C_DISTNAME + " TEXT,"
            + C_WAREHOUSE + " TEXT,"
            + C_DATACOMPILED + " TEXT)";

    String Create_Table_Drugs_To_Be_Expired = "Create Table " + TB_NAME_DRUGS_TO_BE_EXPIRED + " (" + C_WAREHOUSE_EXPIRE + " TEXT,"
            + C_FULLDRUG_EXPIRE + " TEXT,"
            + C_DRUGCODE_EXPIRE + " TEXT,"
            + C_BATCHNO_EXPIRE + " TEXT,"
            + C_QUANTITY_EXPIRE + " TEXT,"
            + C_DAYSTOEXPIRE_EXPIRE + " TEXT)";

    String Create_Table_Drug_Detail = "Create Table " + TB_NAME_DRUG_DETAIL + " (" + C_DRUGCODE + " TEXT,"
            + C_DRUGNAME + " TEXT)";

    String Create_Table_Institute_Detail = "Create Table " + TB_NAME_INSTITUTE_DETAIL + " (" + C_INSTITUTENAME + " TEXT,"
            + C_INSTITUTEID + " TEXT)";

    String Create_Table_UserDetail = "Create Table " + TB_NAME_USER_DETAIL + " (" + C_USER_DETAIL_INSTITUTE_NAME + " TEXT,"
            + C_USER_DETAIL_EMAILID + " TEXT,"
            + C_USER_DETAIL_MOBILE + " TEXT,"
            + C_USER_DETAIL_USER_NAME + " TEXT,"
            + C_USER_DETAIL_LANDLINE + " TEXT)";

    String Create_Table_DistrictWiseStock = "Create Table " + TB_NAME_DETAIL_DISTRICT_WISE_STOCK + " (" + C_DISTRICT_WISE_STOCK_DRUGID + " TEXT,"
            + C_DISTRICT_WISE_STOCK_FULLDRUG + " TEXT,"
            + C_DISTRICT_WISE_STOCK_CURRENT_STOCK + " TEXT)";

    String Create_Table_InstituteWiseStock = "Create Table " + TB_NAME_DETAIL_INSTITUTE_WISE_STOCK + " (" + C_INSTITUTE_WISE_STOCK_DRUGID + " TEXT,"
            + C_INSTITUTE_WISE_STOCK_FULLDRUG + " TEXT,"
            + C_INSTITUTE_WISE_STOCK_INSTITUTE_NAME + " TEXT,"
            + C_INSTITUTE_WISE_STOCK_CURRENT_STOCK + " TEXT)";

    String Create_Notify="Create Table "+T_notification+" ("+Heading+" TEXT,"
            +Notify+" TEXT)";

    String Create_Table_pending="Create Table "+ TABLE_PENDING_RECEIVE +" ("+ C_id_pending_receive +" TEXT,"
            + C_po_no_pending_receive +" TEXT)";
    String Create_Table_inspection_note="Create Table "+ T_inspection_note +" ("+C_id_inspect+" TEXT,"
            +C_PO_no_inspect+" TEXT)";

    String Del_pending="Delete from "+ TABLE_PENDING_RECEIVE;
    String Del_inspect="Delete from "+ T_inspection_note;

}
