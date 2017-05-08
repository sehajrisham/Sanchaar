package utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nic.civilsurgeonnic.R;

import java.util.List;

/**
 * Created by RVerma on 29-03-2017.
 */



public class RVAdapterInstituteWiseStock extends RecyclerView.Adapter<RVAdapterInstituteWiseStock.PersonViewHolder> {
    List<CardGetSet2> data;



    public RVAdapterInstituteWiseStock(List<CardGetSet2> data) {

        this.data = data;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_institute_wise_stock, parent, false);
        return    new PersonViewHolder(v);

    }
    @Override
    public void onBindViewHolder(RVAdapterInstituteWiseStock.PersonViewHolder holder, int position) {
        holder.tvdrugid.setText("Drug Id      : "+data.get(position).InstituteName);
        holder.tvfulldrug.setText("Full Drug    : "+data.get(position).FullDrug);
        holder.tvinstitute.setText("Institute    : "+data.get(position).InstituteName);
        holder.tvcurrentstock.setText("Current Stock: "+data.get(position).Current_Stock);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public  class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvdrugid;
        TextView tvfulldrug;
        TextView tvcurrentstock;
        TextView tvinstitute;




        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvdrugid = (TextView) itemView.findViewById(R.id.tvdrugid);
            tvfulldrug = (TextView) itemView.findViewById(R.id.tvfulldrug);
            tvinstitute = (TextView) itemView.findViewById(R.id.tvInstitute);
            tvcurrentstock = (TextView) itemView.findViewById(R.id.tvcurrentstock);

        }




    }



}
