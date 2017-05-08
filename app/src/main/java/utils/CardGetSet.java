package utils;

/**
 * Created by Sehaj Risham on 2/9/2017.
 */
public class CardGetSet {

    public String DrugId,InstituteName,DistrictName,WarehouseName,DataCompiled,InstituteId,FullDrug,DrugCode,BatchNo,Quantity,Current_Stock,DaysToExpire,EMailID,Mobile,UserName,Landline;

    //Not Performing Institutes
    public CardGetSet (String InstituteName,String DistrictName,String WarehouseName,String DataCompiled)
    {
        this.InstituteName=InstituteName;
        this.DistrictName=DistrictName;
        this.WarehouseName=WarehouseName;
        this.DataCompiled=DataCompiled;
    }
    //Institute Detail
    public CardGetSet (String InstituteName,String InstituteId)
    {
        this.InstituteName=InstituteName;
        this.InstituteId=InstituteId;
    }
    //Drugs To Be Expired
    public CardGetSet (String WarehouseName,String FullDrug,String DrugCode,String BatchNo,String Quantity,String DaysToExpire)
    {
        this.WarehouseName=WarehouseName;
        this.FullDrug=FullDrug;
        this.DrugCode=DrugCode;
        this.BatchNo=BatchNo;
        this.Quantity=Quantity;
        this.DaysToExpire=DaysToExpire;
    }

    public CardGetSet (String InstituteName,String EMailID,String Mobile,String UserName,String Landline)
    {
        this.InstituteName=InstituteName;
        this.EMailID=EMailID;
        this.Mobile=Mobile;
        this.UserName=UserName;
        this.Landline=Landline;

    }

    public CardGetSet (String DrugId,String FullDrug,String Current_Stock)
    {
        this.DrugId=DrugId;
        this.FullDrug=FullDrug;
        this.Current_Stock=Current_Stock;


    }


}
