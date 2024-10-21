package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModificarProductoSeleccionado extends AppCompatActivity {

    private TextView MPSdescripcion,MPSprecio,MPSsa;

    private EditText MPScantidad;

    private Button MPSeliminar,MPSactualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto_seleccionado);

        MPSdescripcion=findViewById(R.id.MPSdescripcion);
        MPSprecio=findViewById(R.id.MPSprecio);
        MPSsa=findViewById(R.id.MPSsa);

        MPScantidad=findViewById(R.id.MPScantidad);

        MPSeliminar=findViewById(R.id.MPSeliminar);
        MPSactualizar=findViewById(R.id.MPSactualizar);


        MPSdescripcion.setText(getIntent().getStringExtra("MPSdesc"));
        MPSprecio.setText(getIntent().getStringExtra("MPSprec"));

        MPScantidad.setText(getIntent().getStringExtra("MPScant"));


        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM productos WHERE id='"+ getIntent().getStringExtra("MPSid")+"'", null);
        c.moveToFirst();
        MPSsa.setText("Cantidad en stock: "+ c.getInt(3));
        db.close();
        c.close();

        MPSactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteService admin = new SQLiteService(ModificarProductoSeleccionado.this);
                SQLiteDatabase db = admin.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM productos WHERE id='"+ getIntent().getStringExtra("MPSid")+"'", null);
                c.moveToFirst();

                SQLiteService admin1 = new SQLiteService(ModificarProductoSeleccionado.this);
                SQLiteDatabase db1 = admin1.getWritableDatabase();

                if(!MPScantidad.getText().toString().isEmpty()){
                    if(c.getInt(3)>= Integer.parseInt(MPScantidad.getText().toString())){
                        if(Integer.parseInt(MPScantidad.getText().toString())!=0) {


                db1.execSQL("UPDATE listacompra SET cantart="+ Integer.parseInt(MPScantidad.getText().toString()) +" WHERE idart='" + getIntent().getStringExtra("MPSid") + "'");

                  db1.close();
                  db.close();
                  c.close();


                Intent intent = new Intent(ModificarProductoSeleccionado.this, MainActivity8.class);
                startActivity(intent);
                        }else {

                            MPScantidad.setError("Ingrese cantidad mayor a 0");

                        }


                    }else {MPScantidad.setError("No hay stock suficiente");}


                }else {

                    MPScantidad.setError("Ingrese cantidad");
                }

            }
        });


        MPSeliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(ModificarProductoSeleccionado.this);
                builder.setTitle("Advertencia");
                builder.setMessage("¿Desea eliminar este producto?");

                builder.setNegativeButton("Cancelar", null);


                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteService admin = new SQLiteService(getApplicationContext());
                        SQLiteDatabase db2 = admin.getWritableDatabase();
                db2.execSQL("DELETE FROM listacompra WHERE idart='"+getIntent().getStringExtra("MPSid")+"'");
                db2.close();
                Intent k = new Intent(ModificarProductoSeleccionado.this, MainActivity8.class);
                startActivity(k);


            }

        });
        builder.show();



            }
        });




    }
}