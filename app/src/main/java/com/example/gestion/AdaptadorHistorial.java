package com.example.gestion;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.ContactoViewHolder>{


    ArrayList<Historial> listaHistorial;

    public ArrayList<Historial> getListaHistorial() {
        return listaHistorial;
    }

    public AdaptadorHistorial(ArrayList<Historial> listaHistorial){
        this.listaHistorial = listaHistorial;


    }


    @NonNull
    @Override
    public AdaptadorHistorial.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_historial_venta, null, false);
        return new ContactoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdaptadorHistorial.ContactoViewHolder holder, int position) {

        holder.nCli.setText("Cliente:"+listaHistorial.get(position).getNombCliente());
        holder.fHis.setText("Fecha:"+listaHistorial.get(position).getFecha());
        holder.hHis.setText("Hora:"+listaHistorial.get(position).getHora());
        holder.cantHis.setText("Cantidad de articulos:"+ listaHistorial.get(position).getCantidadProductos());
        holder.pHis.setText("Importe: $"+listaHistorial.get(position).getTotal());

    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView nCli, fHis,hHis,cantHis,pHis;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            nCli = itemView.findViewById(R.id.nCli);
            fHis = itemView.findViewById(R.id.fHis);
           hHis = itemView.findViewById(R.id.hHis);
           cantHis= itemView.findViewById(R.id.cantHis);
           pHis = itemView.findViewById(R.id.pHis);



        }
    }
}
