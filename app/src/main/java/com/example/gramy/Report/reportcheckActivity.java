package com.example.gramy.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.gramy.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class reportcheckActivity extends AppCompatActivity {

    PieChart pieChart;
    BarChart BarChart;
    LineChart lineChart;
    private Activity v;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportcheck);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        BarChart = (BarChart) findViewById(R.id.BarChart);

        BarChart();
        lineChart();

    }

    public void BarChart(){
        ArrayList<BarEntry> entry_chart = new ArrayList<>();

        ArrayList<String> test = new ArrayList<>();
        test.add("");
        test.add("??????");
        test.add("?????????");
        test.add("??????");
        test.add("?????????");
        test.add("??????");
        test.add("??????");


        BarData barData = new BarData();
        BarChart.getXAxis();
        entry_chart.add(new BarEntry(1, 95)); //entry_chart1??? ?????? ???????????? ?????????.
        entry_chart.add(new BarEntry(2, 29));
        entry_chart.add(new BarEntry(3, 68));
        entry_chart.add(new BarEntry(4, 21));
        entry_chart.add(new BarEntry(5, 78));
        entry_chart.add(new BarEntry(6, 55));

        BarDataSet barDataSet = new BarDataSet(entry_chart, "");
        barDataSet.setColor(Color.BLUE);
        barData.addDataSet(barDataSet); // ?????? BarDataSet ??? ????????? ????????? ????????? DataSet ??? ?????????.

        XAxis xAxis = BarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(test));
        YAxis yAxis = BarChart.getAxisLeft();
        YAxis yAxis1 = BarChart.getAxisRight();

        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String yValue = String.valueOf((int)value)+"%";
                return yValue;
            }
        });

        BarChart.setData(barData); // ????????? ?????? DataSet ??? ?????????.
        BarChart.invalidate(); // ?????? ????????????
        BarChart.setTouchEnabled(false);

    }

    public void lineChart(){
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat.format(date);
        System.out.println("??????"+DateFormat);

        ArrayList<String> arr_date = new ArrayList<>();
        arr_date.add("");
        arr_date.add("05-14");
        arr_date.add("05-15");
        arr_date.add("05-16");
        arr_date.add("05-17");
        arr_date.add("05-18");
        arr_date.add("05-19");

        LineData lineData = new LineData();
        ArrayList<Entry> entry_line = new ArrayList<>();
        entry_line.add(new BarEntry(1, 5000)); //entry_chart1??? ?????? ???????????? ?????????.
        entry_line.add(new BarEntry(2, 6000));
        entry_line.add(new BarEntry(3, 4300));
        entry_line.add(new BarEntry(4, 7000));
        entry_line.add(new BarEntry(5, 5500));
        entry_line.add(new BarEntry(6, 4750));

        LineDataSet lineDataSet = new LineDataSet(entry_line, "");
        lineDataSet.setColor(Color.BLUE);
        lineData.addDataSet(lineDataSet); // ?????? BarDataSet ??? ????????? ????????? ????????? DataSet ??? ?????????.

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(arr_date));
        YAxis yAxis = lineChart.getAxisLeft();

        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String yValue = String.valueOf((int)value)+"???";
                return yValue;
            }
        });

        lineChart.setData(lineData); // ????????? ?????? DataSet ??? ?????????.
        lineChart.invalidate(); // ?????? ????????????
        lineChart.setTouchEnabled(false);
    }

}