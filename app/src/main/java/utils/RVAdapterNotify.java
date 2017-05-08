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

import java.util.List;



public class RVAdapterNotify extends RecyclerView.Adapter<RVAdapterNotify.PersonViewHolder> {


    List<CardGetSet_notify> Data;

    public RVAdapterNotify(List<CardGetSet_notify> data)
    {
        this.Data=data;
    }




    @Override
    public RVAdapterNotify.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notify_listview, parent, false);
        return    new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVAdapterNotify.PersonViewHolder holder, int position) {
        holder.data.setText(Data.get(position).data);
        holder.head.setText(Data.get(position).head);


    }



    @Override
    public int getItemCount() {
        return (Data.size());
    }

    public  class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cr_notify;
        TextView data;
        TextView head;

        PersonViewHolder(View itemView) {
            super(itemView);
            cr_notify = (CardView) itemView.findViewById(R.id.cr_notify);
            data= (TextView) itemView.findViewById(R.id.tv_data);
            head = (TextView) itemView.findViewById(R.id.tv_head);
        }
    }
}
