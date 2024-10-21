package com.example.gestion;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorSeleccion extends RecyclerView.Adapter<AdaptadorSeleccion.ContactoViewHolder> implements View.OnClickListener{

    ArrayList<Producto> listaProductos5;


    ArrayList<Producto> listaOriginal;
    private View.OnClickListener listener;

    public ArrayList<Producto> getListaProductos5() {
        return listaProductos5;
    }

    public AdaptadorSeleccion(ArrayList<Producto> listaContactos){
        this.listaProductos5 = listaContactos;
        this.listaOriginal=new ArrayList<>();
        listaOriginal.addAll(listaProductos5);

    }

    @NonNull
    @Override
    public AdaptadorSeleccion.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_seleccion, null, false);
        view.setOnClickListener(this);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSeleccion.ContactoViewHolder holder, int position) {

        holder.textview14.setText(listaProductos5.get(position).getId());
        holder.textview15.setText(listaProductos5.get(position).getDescripcion());
        holder.textview16.setText("$"+ listaProductos5.get(position).getPrecio());


    }

    @Override
    public int getItemCount() {
        return listaProductos5.size();
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

    public void filter(final String s) {
        if (s.length() == 0) {
            listaProductos5.clear();
            listaProductos5.addAll(listaOriginal);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listaProductos5.clear();
                List<Producto> collect = listaOriginal.stream()
                        .filter(i -> i.getDescripcion().toLowerCase().contains(s))
                        .collect(Collectors.toList());

                listaProductos5.addAll(collect);
            }
            else {
                listaProductos5.clear();
                for (Producto i : listaOriginal) {
                    if (i.getDescripcion().toLowerCase().contains(s)) {
                        listaProductos5.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }



    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView textview14, textview15, textview16;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            textview14 = itemView.findViewById(R.id.textView14);
            textview15 = itemView.findViewById(R.id.textView15);
            textview16 = itemView.findViewById(R.id.textView16);

        }
    }
}
