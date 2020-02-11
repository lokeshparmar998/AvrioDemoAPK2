package com.avrioenergy.avriodemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.highsoft.highcharts.common.hichartsclasses.HIBoost;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIData;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPanning;
import com.highsoft.highcharts.common.hichartsclasses.HIScrollablePlotArea;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.core.HIChartView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LineChart extends Fragment {
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_chart_frgment,container,false);

        //Highcharts
        HIChartView chartView = view.findViewById(R.id.hc);
        chartView.plugins = new ArrayList<>(Arrays.asList("boost"));

        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("line");
        chart.setPinchType("x");

        HIPanning hiPanning = new HIPanning();
        hiPanning.setEnabled(true);
        chart.setPanning(hiPanning);

        options.setChart(chart);

        HIScrollablePlotArea hiScrollablePlotArea = new HIScrollablePlotArea();
        hiScrollablePlotArea.setScrollPositionY(1);
        hiScrollablePlotArea.setScrollPositionX(1);// with 1 the graph will move to the right with -1 it will move towards left
        hiScrollablePlotArea.setMinWidth(500);

        chart.setScrollablePlotArea(hiScrollablePlotArea);

        HIBoost boost = new HIBoost();
        boost.setUseGPUTranslations(true);
        options.setBoost(boost);

        HITitle title = new HITitle();
        title.setText("Highcharts drawing 500 points");
        options.setTitle(title);
        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Using the Boost module");
        options.setSubtitle(subtitle);

        HITooltip tooltip = new HITooltip();
        tooltip.setValueDecimals(2);
        tooltip.setFollowTouchMove(true);
        options.setTooltip(tooltip);
        //Toast.makeText(getContext(),"hello",Toast.LENGTH_SHORT).show();
        final HILine line = new HILine();
        final HIData hiData = new HIData();


       final Handler mHandler = new Handler();

        //volley
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url ="https://us-central1-linechartapi-211c3.cloudfunctions.net/DataPointsURL";

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        Thread.sleep(2000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //  hiData.setY(Math.random()*100);
                                //  line.addPoint(hiData,true,false);
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                //Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                                                try {
                                                    hiData.setY(response.getInt("y"));
                                                    line.addPoint(hiData,true,false);
                                                }
                                                catch (JSONException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // TODO: Handle error

                                            }
                                        });
                                queue.add(jsonObjectRequest);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        line.setLineWidth(0.5);
        options.setSeries(new ArrayList(Collections.singletonList(line)));
        chartView.setOptions(options);


        return view;
    }
}
