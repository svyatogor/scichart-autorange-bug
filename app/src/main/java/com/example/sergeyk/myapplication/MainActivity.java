package com.example.sergeyk.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.AxisMarkerAnnotation;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.FastMountainRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SciChartBuilder.init(this);
        SciChartBuilder builder = SciChartBuilder.instance();
        final SciChartSurface surface = (SciChartSurface) findViewById(R.id.chartView);

        final XyDataSeries dataSeries = new XyDataSeries<>(Date.class, Double.class);
        dataSeries.setAcceptsUnsortedData(true);


        IAxis xAxis = builder
                .newDateAxis()
                .withAutoRangeMode(AutoRange.Always)
                .build();

        surface.getXAxes().add(xAxis);

        IAxis yAxis = builder
                .newNumericAxis()
                .withAutoRangeMode(AutoRange.Always)
                .build();

        surface.getYAxes().add(yAxis);


        final FastMountainRenderableSeries lineSeries = builder.newMountainSeries()
                .withDataSeries(dataSeries)
                .build();
        surface.getRenderableSeries().add(lineSeries);

        final long t = new Date().getTime();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSuspender.using(surface, new Runnable() {
                    @Override
                    public void run() {
                        dataSeries.clear();
                        double y = 3000d;
                        for(int i = 0; i < 1000; i++) {
                            y += (Math.random() > 0.5 ? 1 : -1) * Math.random();
                            dataSeries.append(new Date(t + 60000 * i), y);
                        }
                    }
                });
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSuspender.using(surface, new Runnable() {
                    @Override
                    public void run() {
                        dataSeries.clear();
                        double y = 0.97;
                        for(int i = 0; i < 1000; i++) {
                            y += (Math.random() > 0.5 ? 1 : -1) * Math.random() / 10000;
                            dataSeries.append(new Date(t + 60000 * i), y);
                        }
                    }
                });
            }
        });

    }
}
