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


public class RVAdapterUserDetail extends RecyclerView.Adapter<RVAdapterUserDetail.PersonViewHolder> {
    List<CardGetSet> data;


    public RVAdapterUserDetail(List<CardGetSet> data) {

        this.data = data;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fragment_user_detail, parent, false);
        return    new PersonViewHolder(v);

    }
    @Override
    public void onBindViewHolder(RVAdapterUserDetail.PersonViewHolder holder, int position) {
        holder.tvInstituteName.setText("InstituteName: "+data.get(position).InstituteName);
        holder.tvEmailId.setText("EmailId: "+data.get(position).EMailID);
        holder.tvMobile.setText("Mobile: "+data.get(position).Mobile);
        holder.tvUserName.setText("Username: "+data.get(position).UserName);
        holder.tvLandline.setText("Landline: "+data.get(position).Landline);
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
        TextView tvInstituteName;
        TextView tvEmailId;
        TextView tvMobile;
        TextView tvUserName;
        TextView tvLandline;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvInstituteName = (TextView) itemView.findViewById(R.id.tvInstituteNameuserdetail);
            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
            tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvLandline = (TextView) itemView.findViewById(R.id.tvLandline);

        }



    }



}
