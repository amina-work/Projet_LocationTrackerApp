package com.example.locationtrackerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Initialize variable
    Activity activity;
    ArrayList<ContactModel> arrayList;

    //create constructor
    public  MainAdapter(Activity activity,ArrayList<ContactModel> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initilize contact model
        ContactModel model = arrayList.get(position);

        //set name
        holder.tvName.setText(model.getName());
        //set number
        holder.tvNumber.setText(model.getNumber());
    }

    @Override
    public int getItemCount() {
        //REturn array list size
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize Holder
        TextView tvName, tvNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Asign variables
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
            //send location request to contact
            smsManager = SmsManager.getDefault();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        smsManager.sendTextMessage(tvNumber.getText().toString(), null, "send me location", null, null);
                        Toast.makeText(activity.getApplication(), "sms sent ... please don't stop the app", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplication(), "failed to send sms", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
