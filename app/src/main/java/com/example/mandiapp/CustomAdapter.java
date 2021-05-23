package com.example.mandiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    ArrayList<cropModel> itemList;
    public CustomAdapter(ArrayList<cropModel> item) {
        this.itemList = item;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_weight,viewGroup,false);

        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.Holder holder, int position) {
                holder.Single_SerialNo.setText(itemList.get(position).getSerial());
                holder.Single_Weight.setText(itemList.get(position).getString());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView Single_SerialNo,Single_Weight;
        public Holder(View view) {
            super(view);
            Single_SerialNo=view.findViewById(R.id.singleNO);
            Single_Weight=view.findViewById(R.id.singleWeight);

        }
    }
}
