package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaP1;
    ArrayList<Producto> listaArrayProductos;
    ArrayList<Producto> listaProd;
     SearchView buscando;
    AdaptadorSeleccion adapter1;
    TextView columid,columdesc,columprec,nohay5;

 private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buscando=findViewById(R.id.buscando);

        columid=findViewById(R.id.columid);
        columdesc=findViewById(R.id.columdesc);
        columprec=findViewById(R.id.columprec);
        nohay5=findViewById(R.id.nohay5);
        listaP1 = findViewById(R.id.listaP1);
        listaP1.setLayoutManager(new LinearLayoutManager(this));
        listaP1.setHasFixedSize(true);

        listaArrayProductos = new ArrayList<>();

         adapter1= new AdaptadorSeleccion(mostrarContactos2());

        buscando.setOnQueryTextListener(this);

        listaProd=mostrarContactos2();

        if (listaProd.size()!=0){
            columid.setVisibility(View.VISIBLE);
            columdesc.setVisibility(View.VISIBLE);
            columprec.setVisibility(View.VISIBLE);
            listaP1.setVisibility(View.VISIBLE);
            buscando.setVisibility(View.VISIBLE);
            nohay5.setVisibility(View.INVISIBLE);
        }else {
            columid.setVisibility(View.INVISIBLE);
            columdesc.setVisibility(View.INVISIBLE);
            columprec.setVisibility(View.INVISIBLE);
            listaP1.setVisibility(View.INVISIBLE);
            buscando.setVisibility(View.INVISIBLE);
            nohay5.setText("No hay articulos cargados");

        }

        adapter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // String id12= lista12.get(listaP1.getChildAdapterPosition(view)).getId();
                //String descripcion12=lista12.get(listaP1.getChildAdapterPosition(view)).getDescripcion();
                //Float precio12=lista12.get(listaP1.getChildAdapterPosition(view)).getPrecio();


               // agrega(id12,descripcion12,precio12);



                String id12=adapter1.getListaProductos5().get(listaP1.getChildAdapterPosition(view)).getId();
                String descripcion12= adapter1.getListaProductos5().get(listaP1.getChildAdapterPosition(view)).getDescripcion();
                Float precio12=adapter1.getListaProductos5().get(listaP1.getChildAdapterPosition(view)).getPrecio();


                Intent is = new Intent(MainActivity2.this, IndicarCantidad.class);
                is.putExtra("id123",id12);
                is.putExtra("desc123",descripcion12);
                is.putExtra("prec123",Float.toString(precio12));
                 startActivity(is);
            }
        });

        listaP1.setAdapter(adapter1);

    }





    public ArrayList<Producto> mostrarContactos2() {

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<Producto> listaContactos10 = new ArrayList<>();
        Producto producto;
        Cursor c = db.rawQuery("SELECT id,descripcion,precio FROM productos WHERE cant > 0 ", null);



        if (c.moveToFirst()) {
            do {
                producto = new Producto();

                producto.setId(c.getString(0));
                producto.setDescripcion(c.getString(1));
                producto.setPrecio(c.getFloat(2));

                Cursor  h = db.rawQuery("SELECT * FROM listacompra WHERE idart='"+c.getString(0)+"'", null);

                if(h.getCount()==0){
                listaContactos10.add(producto);
                }
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return listaContactos10;

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter1.filter(s);
        return false;
    }
}