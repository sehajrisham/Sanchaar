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
 * Created by Sehaj Risham on 2/17/2017.
 */

public class RVAdapterInstituteDetail extends RecyclerView.Adapter<RVAdapterInstituteDetail.PersonViewHolder_Institute> {
    List<CardGetSet> data;
    static OnItemClickListener listener;

    public RVAdapterInstituteDetail(List<CardGetSet> data) {

        this.data = data;
    }

    @Override
    public RVAdapterInstituteDetail.PersonViewHolder_Institute onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.institute_detail_listview, parent, false);
        return    new PersonViewHolder_Institute(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder_Institute holder, int position) {

        holder.tvInstituteName.setText(data.get(position).InstituteName);
        holder.tvInstituteId.setText(data.get(position).InstituteId);
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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class PersonViewHolder_Institute extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView tvInstituteName;
        TextView tvInstituteId;



        PersonViewHolder_Institute(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvInstituteName = (TextView) itemView.findViewById(R.id.tvInstituteName);
            tvInstituteId = (TextView) itemView.findViewById(R.id.tvInstituteId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null)
            {
                listener.onItemClick(tvInstituteId.getText().toString());
            }
        }
    }
    public void setOnItemClickListner(final RVAdapterInstituteDetail.OnItemClickListener listner)
    {
        listener=  listner;
    }

    public interface OnItemClickListener {
        public void onItemClick(String Id);
    }
}