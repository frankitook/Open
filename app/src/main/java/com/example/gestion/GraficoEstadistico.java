package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class GraficoEstadistico extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private TextView nohay,nohay3;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String > lableName;

    ArrayList<MonthlySalesData> monthlySalesDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_estadistico);

        pieChart = findViewById(R.id.activity_main_piechart);

        barChart = findViewById(R.id.mp_BarChart);
        nohay = findViewById(R.id.nohay);
        nohay3 = findViewById(R.id.nohay3);
        setupPieChart();
        loadPieChartData();


        barEntriesArrayList = new ArrayList<>();
        lableName = new ArrayList<>();

        fillMonthlySalesArrayList();

        for (int i = 0; i < monthlySalesDataArrayList.size(); i++) {
            String month = monthlySalesDataArrayList.get(i).getMonth();
            int sales = monthlySalesDataArrayList.get(i).getSales();
            barEntriesArrayList.add(new BarEntry(i, sales));
            lableName.add(month);
        }

        BarDataSet barDataSet = new BarDataSet(barEntriesArrayList, "");

        ArrayList<Integer> colors = new ArrayList<>();


        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.COLORFUL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.LIBERTY_COLORS) {
            colors.add(color);
        }

        barDataSet.setColors(colors);

        Description description = new Description();
        description.setText("Dias");
        barChart.setDescription(description);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(lableName.size());

        barChart.animateY(1000);
        barChart.invalidate();



    }

    private void fillMonthlySalesArrayList(){

        monthlySalesDataArrayList.clear();


        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<Historial> listaHistorial10 = new ArrayList<>();
        Historial historial;
        Cursor c = db.rawQuery("SELECT * FROM historial", null);

        if (c.moveToFirst()) {
            do {
                historial = new Historial();

                historial.setId(c.getInt(0));
                historial.setFecha(c.getString(1));
                historial.setHora(c.getString(2));
                historial.setTotal(c.getString(3));
                historial.setNombCliente(c.getString(4));
                historial.setCantidadProductos(c.getInt(5));
                listaHistorial10.add(historial);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        if(listaHistorial10.size()!=0){
            barChart.setVisibility(View.VISIBLE);
            nohay3.setVisibility(View.INVISIBLE);
            String texto;
            String mes;
            String anio1;
            int anio;
            Calendar fecha = Calendar.getInstance();

            anio = fecha.get(Calendar.YEAR);

            int enero = 0, febrero = 0, marzo = 0, abril = 0, mayo = 0, junio = 0, julio = 0, agosto = 0, septiembre1 = 0, octubre = 0, noviembre = 0, diciembre = 0;

            for (int i = 0; i < listaHistorial10.size(); i++) {
                texto = listaHistorial10.get(i).getFecha();
                anio1 = texto.split("/")[2];

                if (anio == Integer.parseInt(anio1)) {


                    mes = texto.split("/")[1];

                    if (Integer.parseInt(mes) == 1) {
                        enero = enero + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 2) {
                        febrero = febrero + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 3) {
                        marzo = marzo + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 4) {
                        abril = abril + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 5) {
                        mayo = mayo + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 6) {
                        junio = junio + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 7) {
                        julio = julio + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 8) {
                        agosto = agosto + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 9) {
                        septiembre1 = septiembre1 + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 10) {
                        octubre = octubre + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 11) {
                        noviembre = noviembre + listaHistorial10.get(i).getCantidadProductos();
                    }
                    if (Integer.parseInt(mes) == 12) {
                        diciembre = diciembre + listaHistorial10.get(i).getCantidadProductos();
                    }

                }
            }


            if (enero != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Ene", enero));
            }
            if (febrero != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Feb", febrero));
            }
            if (marzo != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Mar", marzo));
            }
            if (abril != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Abr", abril));
            }
            if (mayo != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("May", mayo));
            }
            if (junio != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Jun", junio));
            }
            if (julio != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Jul", julio));
            }
            if (agosto != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Ago", agosto));
            }
            if (septiembre1 != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Sep", septiembre1));
            }
            if (octubre != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Oct", octubre));
            }
            if (noviembre != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Nov", noviembre));
            }
            if (diciembre != 0) {
                monthlySalesDataArrayList.add(new MonthlySalesData("Dic", diciembre));
            }


        }else {

            barChart.setVisibility(View.INVISIBLE);
            nohay3.setVisibility(View.VISIBLE);
            nohay3.setText("No hay ventas realizadas");


        }


    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setHoleRadius(30);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(0);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setEnabled(false);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();


        SQLiteService admin = new SQLiteService(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<Historial> listaHistorial10 = new ArrayList<>();
        Historial historial;
        Cursor c = db.rawQuery("SELECT * FROM historial", null);

        if (c.moveToFirst()) {
            do {
                historial = new Historial();

                historial.setId(c.getInt(0));
                historial.setFecha(c.getString(1));
                historial.setHora(c.getString(2));
                historial.setTotal(c.getString(3));
                historial.setNombCliente(c.getString(4));
                historial.setCantidadProductos(c.getInt(5));
                listaHistorial10.add(historial);
            } while (c.moveToNext());
        }

        c.close();
        db.close();



if(listaHistorial10.size()!=0) {
pieChart.setVisibility(View.VISIBLE);
nohay.setVisibility(View.INVISIBLE);
    String texto;
    String mes;

    int enero = 0, febrero = 0, marzo = 0, abril = 0, mayo = 0, junio = 0, julio = 0, agosto = 0, septiembre = 0, octubre = 0, noviembre = 0, diciembre = 0;

    for (int i = 0; i < listaHistorial10.size(); i++) {
        texto = listaHistorial10.get(i).getFecha();

        mes = texto.split("/")[1];

        if (Integer.parseInt(mes) == 1) {
            enero++;
        }
        if (Integer.parseInt(mes) == 2) {
            febrero++;
        }
        if (Integer.parseInt(mes) == 3) {
            marzo++;
        }
        if (Integer.parseInt(mes) == 4) {
            abril++;
        }
        if (Integer.parseInt(mes) == 5) {
            mayo++;
        }
        if (Integer.parseInt(mes) == 6) {
            junio++;
        }
        if (Integer.parseInt(mes) == 7) {
            julio++;
        }
        if (Integer.parseInt(mes) == 8) {
            agosto++;
        }
        if (Integer.parseInt(mes) == 9) {
            septiembre++;
        }
        if (Integer.parseInt(mes) == 10) {
            octubre++;
        }
        if (Integer.parseInt(mes) == 11) {
            noviembre++;
        }
        if (Integer.parseInt(mes) == 12) {
            diciembre++;
        }


    }

    if (enero != 0) {
        entries.add(new PieEntry(enero, "Enero"));
    }
    if (febrero != 0) {
        entries.add(new PieEntry(febrero, "Febrero"));
    }
    if (marzo != 0) {
        entries.add(new PieEntry(marzo, "Marzo"));
    }
    if (abril != 0) {
        entries.add(new PieEntry(abril, "Abril"));
    }
    if (mayo != 0) {
        entries.add(new PieEntry(mayo, "Mayo"));
    }
    if (junio != 0) {
        entries.add(new PieEntry(junio, "Junio"));
    }
    if (julio != 0) {
        entries.add(new PieEntry(julio, "Julio"));
    }
    if (agosto != 0) {
        entries.add(new PieEntry(agosto, "Agosto"));
    }
    if (septiembre != 0) {
        entries.add(new PieEntry(septiembre, "Septiembre"));
    }
    if (octubre != 0) {
        entries.add(new PieEntry(octubre, "Octubre"));
    }
    if (noviembre != 0) {
        entries.add(new PieEntry(noviembre, "Noviembre"));
    }
    if (diciembre != 0) {
        entries.add(new PieEntry(diciembre, "Diciembre"));
    }


    ArrayList<Integer> colors = new ArrayList<>();


    for (int color : ColorTemplate.MATERIAL_COLORS) {
        colors.add(color);
    }

    for (int color : ColorTemplate.COLORFUL_COLORS) {
        colors.add(color);
    }

    for (int color : ColorTemplate.LIBERTY_COLORS) {
        colors.add(color);
    }


    PieDataSet dataSet = new PieDataSet(entries, "");
    dataSet.setColors(colors);

    PieData data = new PieData(dataSet);
    data.setDrawValues(true);
    data.setValueFormatter(new PercentFormatter(pieChart));
    data.setValueTextSize(12f);
    data.setValueTextColor(Color.BLACK);

    pieChart.setData(data);
    pieChart.invalidate();

    pieChart.animateY(1000, Easing.EaseInOutQuad);
}else {
    pieChart.setVisibility(View.INVISIBLE);
    nohay.setText("No hay ventas realizadas");



}



    }
}