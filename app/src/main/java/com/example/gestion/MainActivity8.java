package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity8 extends AppCompatActivity {

    private FloatingActionButton b9;
    private Button bcp,bfp;
    RecyclerView listavender;
    ArrayList<Producto> listaArrayProductos;
    ArrayList<ProductoStock> lista150 = new ArrayList<>();
    private TextView importe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);


        b9=findViewById(R.id.botonflotante);

        bcp=findViewById(R.id.bcp);
        bfp=findViewById(R.id.bfp);

        importe=findViewById(R.id.importe);
        listavender = findViewById(R.id.listavender);
        listavender.setLayoutManager(new LinearLayoutManager(this));
        listavender.setHasFixedSize(true);
        AdaptadorVenta adapter12 = new AdaptadorVenta(mostrarContactos233());



        adapter12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity8.this,ModificarProductoSeleccionado.class);

                intent.putExtra("MPSid",adapter12.getListaProductos8().get(listavender.getChildAdapterPosition(view)).getId());
                intent.putExtra("MPSdesc",adapter12.getListaProductos8().get(listavender.getChildAdapterPosition(view)).getDescripcion());
                intent.putExtra("MPSprec",Float.toString(adapter12.getListaProductos8().get(listavender.getChildAdapterPosition(view)).getPrecio()));
                intent.putExtra("MPScant",Integer.toString(adapter12.getListaProductos8().get(listavender.getChildAdapterPosition(view)).getStock()));

                startActivity(intent);


            }
        });


        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(MainActivity8.this, MainActivity2.class);
                startActivity(is);

            }
        });

        bcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity8.this);
                builder.setTitle("Advertencia");
                builder.setMessage("¿Esta seguro que desea cancelar el pedido?");
                builder.setNegativeButton("Cancelar", null);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(MainActivity8.this, MainActivity.class);
                        startActivity(intent);

                    }

                });
                builder.show();

            }
        });




    listavender.setAdapter(adapter12);

  lista150=mostrarContactos233();
        float importe1=0;

  for (int i =0 ; i< lista150.size();i++){

      importe1= importe1 + ((lista150.get(i).getPrecio())*(lista150.get(i).getStock()));

  }

  importe.setText(" $"+ String.format("%.2f",importe1));


        int art=0;

        for (int i =0 ; i< lista150.size();i++){

            art= art + lista150.get(i).getStock();

        }

        float finalImporte = importe1;
        int finalArt = art;
        bfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String impcad= Float.toString(finalImporte);
                Intent intent = new Intent(MainActivity8.this, FinalizarPedido.class);
                intent.putExtra("cantArt",Integer.toString(finalArt));
                intent.putExtra("impcad",impcad);
                startActivity(intent);

            }
        });




}

    public ArrayList<ProductoStock> mostrarContactos233() {

        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<ProductoStock> listaContactos10 = new ArrayList<>();
        ProductoStock producto;
        Cursor  c = db.rawQuery("SELECT * FROM listacompra ", null);

        if (c.moveToFirst()) {
            do {
                producto = new ProductoStock();

                producto.setId(c.getString(0));
                producto.setDescripcion(c.getString(1));
                producto.setPrecio(c.getFloat(2));
                producto.setStock(c.getInt(3));
                listaContactos10.add(producto);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return listaContactos10;

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Quieres cancelar la venta?");

        builder.setNegativeButton("Cancelar", null);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity8.this, MainActivity.class);
                startActivity(intent);
            }

        });
        builder.show();

        return super.onKeyUp(keyCode, event);
    }


}

















