package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainProveedores extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView listaproveedores;
    FloatingActionButton bf;
    AdaptadorProveedor adapter;
    SearchView busca;
    ArrayList<Proveedor> proveedors;
    TextView nohay4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_proveedores);
        busca = findViewById(R.id.busca);
        nohay4 = findViewById(R.id.nohay4);
        listaproveedores = findViewById(R.id.listaproveedores);
        listaproveedores.setLayoutManager(new LinearLayoutManager(this));
        listaproveedores.setHasFixedSize(true);

         adapter = new AdaptadorProveedor(mostrarProveedores());
        listaproveedores.setAdapter(adapter);

        bf=findViewById(R.id.bf);

        proveedors=mostrarProveedores();

        if(proveedors.size()!=0){
            busca.setVisibility(View.VISIBLE);
            listaproveedores.setVisibility(View.VISIBLE);
            nohay4.setVisibility(View.INVISIBLE);

        }else {

            busca.setVisibility(View.INVISIBLE);
            listaproveedores.setVisibility(View.INVISIBLE);
            nohay4.setText("No hay proveedores cargados");

        }


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero=adapter.getListaProveedor1().get(listaproveedores.getChildAdapterPosition(view)).getNumero();
                String nombre=adapter.getListaProveedor1().get(listaproveedores.getChildAdapterPosition(view)).getNombre();
                String direccion= adapter.getListaProveedor1().get(listaproveedores.getChildAdapterPosition(view)).getDireccion();

                Intent i = new Intent(MainProveedores.this,MainActivity7.class);
                i.putExtra("numero",numero);
                i.putExtra("nombre",nombre);
                i.putExtra("direccion",direccion);
                startActivity(i);



            }
        });


        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainProveedores.this,Cargar_proveedores.class);
                startActivity(i);

            }
        });

        busca.setOnQueryTextListener(this);

    }

    public ArrayList<Proveedor> mostrarProveedores() {


        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<Proveedor> listaProveedor1 = new ArrayList<>();
        Proveedor proveedor;
        Cursor c;

        c = db.rawQuery("SELECT * FROM proveedores", null);

        if (c.moveToFirst()) {
            do {
                proveedor = new Proveedor();

                proveedor.setNumero(c.getString(0));
                proveedor.setNombre(c.getString(1));
                proveedor.setDireccion(c.getString(2));
                listaProveedor1.add(proveedor);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return listaProveedor1;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(MainProveedores.this,MainActivity.class);
        startActivity(intent);
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }
}