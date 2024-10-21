package com.example.gestion;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ContactoViewHolder> implements View.OnClickListener {

    ArrayList<ProductoStock> listaProductos3;
    private View.OnClickListener listener;

    public Adaptador(ArrayList<ProductoStock> listaContactos){
        this.listaProductos3 = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_productos, null, false);
        view.setOnClickListener(this);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewID.setText(listaProductos3.get(position).getId());
        holder.viewDescripcion.setText(listaProductos3.get(position).getDescripcion());
        holder.viewPrecio.setText("$"+ Float.toString(listaProductos3.get(position).getPrecio()));
        holder.viewStock.setText(Integer.toString(listaProductos3.get(position).getStock()));
    }

    @Override
    public int getItemCount() {
        return listaProductos3.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
    this.listener=listener;

    }

    @Override
    public void onClick(View view) {

        if(listener!=null){

            listener.onClick(view);

        }

    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewID, viewDescripcion, viewPrecio,viewStock;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewID = itemView.findViewById(R.id.viewID);
            viewDescripcion = itemView.findViewById(R.id.viewDescripcion);
            viewPrecio = itemView.findViewById(R.id.viewPrecio);
            viewStock = itemView.findViewById(R.id.viewStock);

        }
    }
}
