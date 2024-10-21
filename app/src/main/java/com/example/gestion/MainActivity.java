package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 1000);
        } else {
        }



        mainGrid =  findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid);


    }


    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {

            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch(finalI){

                        case 0:

                            Intent intent = new Intent(MainActivity.this, More.class);
                            startActivity(intent);
                            break;

                        case 1:
                            Intent is = new Intent(MainActivity.this, Prueba.class);
                            startActivity(is);

                            break;

                        case 2:
                            Intent g = new Intent(MainActivity.this, MainProveedores.class);
                            startActivity(g);
                            break;
                        case 3:
                            Intent w = new Intent(MainActivity.this, HistorialDeVentas.class);
                            startActivity(w);
                            break;

                        case 4:
                            Intent h = new Intent(MainActivity.this, GraficoEstadistico.class);
                            startActivity(h);
                            break;

                        case 5:

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Advertencia");
                            builder.setMessage("¿Quieres salir de la aplicacion?");
                            builder.setNegativeButton("Cancelar", null);

                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    salir();
                                }

                            });
                            builder.show();

                            break;


                    }


                }
            });
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Quieres salir de la aplicacion?");

        builder.setNegativeButton("Cancelar", null);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                salir();
            }

        });
        builder.show();

        return super.onKeyUp(keyCode, event);
    }

    public void salir(){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void provisorio(int finalI){

        Intent intent = new Intent(MainActivity.this, ActivityOne.class);
        intent.putExtra("info", "Esta es la actividad:  " + finalI);
        startActivity(intent);

    }


}