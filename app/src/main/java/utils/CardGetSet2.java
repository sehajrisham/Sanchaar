package utils;

/**
 * Created by Sehaj Risham on 3/22/2017.
 */

public class CardGetSet2 {
    public String DrugName,DrugCode,DrugId,FullDrug,Current_Stock,InstituteName;
    public CardGetSet2 (String DrugName,String DrugCode)
    {
        this.DrugName=DrugName;
        this.DrugCode=DrugCode;

    }
    public CardGetSet2 (String DrugId,String FullDrug,String InstituteName,String Current_Stock)
    {
        this.DrugId=DrugId;
        this.FullDrug=FullDrug;
        this.Current_Stock=Current_Stock;
        this.InstituteName=InstituteName;

    }

}
