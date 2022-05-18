package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class reportcheckActivity extends AppCompatActivity {

    PieChart pieChart;
    private Activity v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportcheck);


        pieChart = (PieChart) findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList yValues = new ArrayList();

        yValues.add(new PieEntry(34f, "사과"));
        yValues.add(new PieEntry(23f, "키위"));
        yValues.add(new PieEntry(14f, "귤"));
        yValues.add(new PieEntry(35f, "참외"));
        yValues.add(new PieEntry(40f, "멜론"));
        yValues.add(new PieEntry(40f, "포도"));

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_r);
////
////        LineChart lineChart = (LineChart) findViewById(R.id.chart);
////
////        ArrayList<Entry> entries = new ArrayList<>();
////        entries.add(new Entry(4f, 0));
////        entries.add(new Entry(8f, 1));
////        entries.add(new Entry(6f, 2));
////        entries.add(new Entry(2f, 3));
////        entries.add(new Entry(18f, 4));
////        entries.add(new Entry(9f, 5));
////        entries.add(new Entry(16f, 6));
////        entries.add(new Entry(5f, 7));
////        entries.add(new Entry(3f, 8));
////        entries.add(new Entry(7f, 10));
////        entries.add(new Entry(9f, 11));
////
////        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
////
////        ArrayList<String> labels = new ArrayList<String>();
////        labels.add("January");
////        labels.add("February");
////        labels.add("March");
////        labels.add("April");
////        labels.add("May");
////        labels.add("June");
////        labels.add("July");
////        labels.add("August");
////        labels.add("September");
////        labels.add("October");
////        labels.add("November");
////        labels.add("December");
////
////        LineData data = new LineData(labels, dataset);
////        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
////        /*dataset.setDrawCubic(true); //선 둥글게 만들기
////        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/
////
////        lineChart.setData(data);
////        lineChart.animateY(5000);
//
//}