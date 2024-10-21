package com.example.gestion;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorVenta extends RecyclerView.Adapter<AdaptadorVenta.ContactoViewHolder> implements View.OnClickListener{



    ArrayList<ProductoStock> listaProductos8;

    private View.OnClickListener listener;

    public ArrayList<ProductoStock> getListaProductos8() {
        return listaProductos8;
    }

    public AdaptadorVenta(ArrayList<ProductoStock> listaProductos8){
        this.listaProductos8 = listaProductos8;

    }

    @NonNull
    @Override
    public AdaptadorVenta.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_venta, null, false);
        view.setOnClickListener(this);
            return new ContactoViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdaptadorVenta.ContactoViewHolder holder, int position) {
    if(listaProductos8.size()!=0) {
        holder.tv10.setText("COD.ART: "+listaProductos8.get(position).getId());
        holder.tv9.setText("Articulo: "+listaProductos8.get(position).getDescripcion());
        holder.tv12.setText(listaProductos8.get(position).getStock()+" X $" + (listaProductos8.get(position).getPrecio()));
    }
    }

    @Override
    public int getItemCount() {
        return listaProductos8.size();
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

        TextView tv9, tv10, tv12;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            tv9 = itemView.findViewById(R.id.textView9);
            tv10 = itemView.findViewById(R.id.textView10);
            tv12 = itemView.findViewById(R.id.textView12);




        }
    }
}
