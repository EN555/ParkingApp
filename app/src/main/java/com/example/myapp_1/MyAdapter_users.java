package com.example.myapp_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_users extends RecyclerView.Adapter<MyAdapter_users.MyViewHolder1> {


    Context context;

    ArrayList<UserManager> list;

    public MyAdapter_users(Context context, ArrayList<UserManager> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        UserManager user = list.get(position);
        holder.firstName.setText(user.getName());
        holder.email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView firstName, email;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tvfirstName);
            email = itemView.findViewById(R.id.tvlastName);
        }
    }
}
