package utils;

/**
 * Created by RVerma on 27-04-2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nic.civilsurgeonnic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehdev's on 20/03/2017.
 */

public class RVAdapterPendingReceive extends RecyclerView.Adapter<RVAdapterPendingReceive.PersonViewHolder>{

    List<CardGetSet_pending_receive> data;
    public RVAdapterPendingReceive(ArrayList<CardGetSet_pending_receive> data) {
        this.data = data;

    }

    @Override
    public RVAdapterPendingReceive.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_receive_listview, parent, false);
        return    new PersonViewHolder(v);

    }



    @Override
    public void onBindViewHolder(RVAdapterPendingReceive.PersonViewHolder holder, int position) {
        holder.C_id2.setText(data.get(position).Id);
        holder.C_PO_no2.setText(data.get(position).PO_no);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cr_pending;
        TextView C_PO_no2;
        TextView C_id2;

        PersonViewHolder(View itemView) {
            super(itemView);
            cr_pending = (CardView) itemView.findViewById(R.id.cr_pending);
            C_PO_no2= (TextView) itemView.findViewById(R.id.tvid2);
            C_id2 = (TextView) itemView.findViewById(R.id.tvpono2);
        }
    }
}
