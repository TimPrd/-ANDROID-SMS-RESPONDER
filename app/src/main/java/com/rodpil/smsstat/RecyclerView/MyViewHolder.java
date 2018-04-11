package com.rodpil.smsstat.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rodpil.smsstat.R;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView name;
    private TextView phone;
    private TextView counter;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        name = (TextView) itemView.findViewById(R.id.cell_name);
        phone = (TextView) itemView.findViewById(R.id.cell_phone);
        counter = (TextView) itemView.findViewById(R.id.cell_counter);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(MyObject myObject){
        name.setText(myObject.getName());
        phone.setText(myObject.getPhone());
        counter.setText(myObject.getCounter());
        //Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);
    }
}