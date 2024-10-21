package com.example.gestion;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorProveedor extends RecyclerView.Adapter<AdaptadorProveedor.ContactoViewHolder> implements View.OnClickListener{


    ArrayList<Proveedor> listaProveedor1;
    ArrayList<Proveedor> listaOriginal;
    private View.OnClickListener listener;

    public ArrayList<Proveedor> getListaProveedor1() {
        return listaProveedor1;
    }

    public AdaptadorProveedor(ArrayList<Proveedor> listaProveedor1){
        this.listaProveedor1 = listaProveedor1;
        this.listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaProveedor1);
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_proveedor, null, false);
        view.setOnClickListener(this);
        return new AdaptadorProveedor.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {

        holder.numero.setText(listaProveedor1.get(position).getNumero());
        holder.nombre.setText(listaProveedor1.get(position).getNombre());


    }

    public void filter(final String s) {
        if (s.length() == 0) {
            listaProveedor1.clear();
            listaProveedor1.addAll(listaOriginal);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listaProveedor1.clear();
                List<Proveedor> collect = listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(s) )
                        .collect(Collectors.toList());

                listaProveedor1.addAll(collect);
            }
            else {
                listaProveedor1.clear();
                for (Proveedor i : listaOriginal) {
                    if (i.getNombre().toLowerCase().contains(s)) {
                        listaProveedor1.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listaProveedor1.size();
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

        TextView numero, nombre;
        ImageView imageView;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numerotel);
            nombre = itemView.findViewById(R.id.nombre);
            imageView=itemView.findViewById(R.id.imageView5);
        }
    }
}