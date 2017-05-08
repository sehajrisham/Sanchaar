package utils;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nic.civilsurgeonnic.R;

import java.util.List;

/**
 * Created by Sehaj Risham on 2/17/2017.
 */

public class RVAdapterDrugsToBeExpired extends RecyclerView.Adapter<RVAdapterDrugsToBeExpired.PersonViewHolder_DrugsToExpire> {
    List<CardGetSet> data;

    public RVAdapterDrugsToBeExpired(List<CardGetSet> data) {

        this.data = data;
    }

    @Override
    public RVAdapterDrugsToBeExpired.PersonViewHolder_DrugsToExpire onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fragment_drugs_to_expire, parent, false);
        return    new PersonViewHolder_DrugsToExpire(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder_DrugsToExpire holder, int position) {
        holder.tvWarehouseName.setText("Institute: "+data.get(position).WarehouseName);
        holder.tvFullDrug.setText("Full Drug: "+data.get(position).FullDrug);
        holder.tvDrugCode.setText("Drug Code: "+data.get(position).DrugCode);
        holder.tvBatchNo.setText("Batch No.: "+data.get(position).BatchNo);
        holder.tvQuantity.setText("Quantity: "+data.get(position).Quantity);
        holder.tvDaysToExpire.setText("Days To Expire: "+data.get(position).DaysToExpire);
        int num=-1;
try {
     num = Integer.parseInt(data.get(position).DaysToExpire);
}
catch (Exception e)
{

}
       if(num!=-1) {
           if (num <= 60) {
               holder.cv.setCardBackgroundColor(Color.rgb(255, 167, 139));
           } else if (num > 60 && num <= 90) {
               holder.cv.setCardBackgroundColor(Color.rgb(255,217,139));
           } else if (num > 90 && num <= 180){
               holder.cv.setCardBackgroundColor(Color.rgb(255,252,139));
           }
       }


    }

   /* @Override
    public void onBindViewHolder(RVAdapterNotPerformingInstitutes.PersonViewHolder holder, int position) {
        holder.tvInstituteId.setText(data.get(position).InstituteId);
        holder.tvInstituteName.setText(data.get(position).InstituteName);
    }*/

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PersonViewHolder_DrugsToExpire extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvWarehouseName;
        TextView tvFullDrug;
        TextView tvDrugCode;
        TextView tvBatchNo;
        TextView tvQuantity;
        TextView tvDaysToExpire;



        PersonViewHolder_DrugsToExpire(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvWarehouseName = (TextView) itemView.findViewById(R.id.tvWH);
            tvFullDrug = (TextView) itemView.findViewById(R.id.tvfullDrug);
            tvDrugCode = (TextView) itemView.findViewById(R.id.tvDrugCode);
            tvBatchNo = (TextView) itemView.findViewById(R.id.tvBatchNo);
            tvQuantity = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvDaysToExpire = (TextView) itemView.findViewById(R.id.tvDaysToExpire);
        }
    }
}