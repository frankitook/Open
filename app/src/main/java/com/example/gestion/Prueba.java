package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class Prueba extends AppCompatActivity {
    GridLayout mainGrid1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        mainGrid1 =  findViewById(R.id.mainGrid1);

        setSingleEvent(mainGrid1);


    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid1.getChildCount(); i++) {

            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch(finalI){

                        case 0:
                            Intent intent = new Intent(Prueba.this, MainActivity4.class);
                            startActivity(intent);
                            break;

                        case 1:
                            Intent is = new Intent(Prueba.this, MainActivity3.class);
                            startActivity(is);
                            break;

                        case 2:
                            Intent intent2 = new Intent(Prueba.this, MainActivity6.class);
                            startActivity(intent2);
                            break;
                        case 3:

                            Intent intent1 = new Intent(Prueba.this, ActivityOne.class);
                            startActivity(intent1);
                            break;



                    }


                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(Prueba.this,MainActivity.class);
        startActivity(intent);
        return super.onKeyUp(keyCode, event);
    }



}