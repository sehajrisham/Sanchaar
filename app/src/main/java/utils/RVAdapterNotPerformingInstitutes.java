package utils;

/**
 * Created by Sehaj Risham on 2/9/2017.
 */


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nic.civilsurgeonnic.R;

import java.util.List;

//import android.support.v7.widget.CardView;



public class RVAdapterNotPerformingInstitutes extends RecyclerView.Adapter<RVAdapterNotPerformingInstitutes.PersonViewHolder> {
    List<CardGetSet> data;



    public RVAdapterNotPerformingInstitutes(List<CardGetSet> data) {

        this.data = data;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
        return    new PersonViewHolder(v);

    }
    @Override
    public void onBindViewHolder(RVAdapterNotPerformingInstitutes.PersonViewHolder holder, int position) {
        holder.tvInstituteName.setText("Institute Name: "+data.get(position).InstituteName);
        holder.tvDistrictName.setText("District Name: "+data.get(position).DistrictName);
        holder.tvWarehouseName.setText("Warehouse Name: "+data.get(position).WarehouseName);
        holder.tvDataCompiled.setText("Data Compiled of months: "+data.get(position).DataCompiled);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public  class PersonViewHolder extends RecyclerView.ViewHolder  {
        CardView cv;
        TextView tvInstituteName;
        TextView tvDistrictName;
        TextView tvWarehouseName;
        TextView tvDataCompiled;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvInstituteName = (TextView) itemView.findViewById(R.id.tvExpDate);
            tvDistrictName = (TextView) itemView.findViewById(R.id.tvMfgDate);
            tvWarehouseName = (TextView) itemView.findViewById(R.id.tvWarehouse);
            tvDataCompiled = (TextView) itemView.findViewById(R.id.tvDataCompiled);

        }



    }


}
