package utils;

/**
 * Created by Sehaj Risham on 2/17/2017.
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


public class RVAdapterDrugDetail extends RecyclerView.Adapter<RVAdapterDrugDetail.PersonViewHolderDrug> {
    List<CardGetSet2> data;
    static OnItemClickListener listener;


    public RVAdapterDrugDetail(List<CardGetSet2> data) {

        this.data = data;
    }

   /* @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_detail_listview, parent, false);
        return  new PersonViewHolder(view);
    }*/

     @Override
        public PersonViewHolderDrug onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_detail_listview, parent, false);
            return    new PersonViewHolderDrug(v);

        }
    @Override
    public void onBindViewHolder(RVAdapterDrugDetail.PersonViewHolderDrug holder, int position) {
        holder.tvDrugName.setText(data.get(position).DrugName);
        holder.tvDrugCode.setText(data.get(position).DrugCode);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public  class PersonViewHolderDrug extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView tvDrugName;
        TextView tvDrugCode;


        PersonViewHolderDrug(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvDrugName = (TextView) itemView.findViewById(R.id.tvDrugName);
            tvDrugCode = (TextView) itemView.findViewById(R.id.tvDrugCode);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if(listener!=null)
            {
                listener.onItemClick(tvDrugName.getText().toString());
            }
        }
    }

    public void setOnItemClickListner(final RVAdapterDrugDetail.OnItemClickListener listner)
    {
        listener=listner;
    }

    public interface OnItemClickListener {
        public void onItemClick(String Id);
    }

}
