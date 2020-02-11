package com.avrioenergy.avriodemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.jawnnypoo.physicslayout.Physics;
import com.jawnnypoo.physicslayout.PhysicsRelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Devices extends Fragment {
    BubbleChart bubbleChart1,bubbleChart2,bubbleChart3,bubbleChart4,bubbleChart5,bubbleChart6;
    PhysicsRelativeLayout layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.devices_fragment,container,false);

        bubbleChart1 = (BubbleChart) view.findViewById(R.id.bubbleChart1);
        bubbleChart2 = (BubbleChart) view.findViewById(R.id.bubbleChart2);
        bubbleChart3 = (BubbleChart) view.findViewById(R.id.bubbleChart3);
        bubbleChart4 = (BubbleChart) view.findViewById(R.id.bubbleChart4);
        bubbleChart5 = (BubbleChart) view.findViewById(R.id.bubbleChart5);
        bubbleChart6 = (BubbleChart) view.findViewById(R.id.bubbleChart6);

        layout = (PhysicsRelativeLayout) view.findViewById(R.id.physics_layout);

        bubbleChart1.setBubbleColor(Color.parseColor("#63e06d"));
        bubbleChart1.setBubbleSize(200);
        bubbleChart1.setBubbleText("Fan01");
        bubbleChart1.setBubbleTextColor(Color.parseColor("#ffffff"));

        bubbleChart2.setBubbleColor(Color.parseColor("#FF8162"));
        bubbleChart2.setBubbleSize(150);
        bubbleChart2.setBubbleText("Fan02");
        bubbleChart2.setBubbleTextColor(Color.parseColor("#ffffff"));

        bubbleChart3.setBubbleColor(Color.parseColor("#00574B"));
        bubbleChart3.setBubbleSize(170);
        bubbleChart3.setBubbleText("Ac");
        bubbleChart3.setBubbleTextColor(Color.parseColor("#ffffff"));

        bubbleChart4.setBubbleColor(Color.parseColor("#F84532"));
        bubbleChart4.setBubbleSize(100);
        bubbleChart4.setBubbleText("Computer");
        bubbleChart4.setBubbleTextColor(Color.parseColor("#ffffff"));

        bubbleChart5.setBubbleColor(Color.parseColor("#FB880D"));
        bubbleChart5.setBubbleSize(100);
        bubbleChart5.setBubbleText("Fridge");
        bubbleChart5.setBubbleTextColor(Color.parseColor("#ffffff"));


        bubbleChart6.setBubbleColor(Color.parseColor("#4482ff"));
        bubbleChart6.setBubbleSize(100);
        bubbleChart6.setBubbleText("Light");
        bubbleChart6.setBubbleTextColor(Color.parseColor("#ffffff"));

/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bubbleChart4.setVisibility(View.GONE);
                bubbleChart3.setVisibility(View.VISIBLE);
            }
        },3000);*/

        // api code
        Handler mHandler = new Handler();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://us-central1-linechartapi-211c3.cloudfunctions.net/DevicesURL";
        ArrayList<Model> deviceModel = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        Thread.sleep(3000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                //Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                                                try {
                                                    JSONArray res = response.getJSONArray("devices");
                                                    Log.i("response1", response.toString());
                                                    Log.i("response2", res.toString());
                                                    for (int i = 0; i < res.length(); i++) {
                                                        JSONObject jsonObject = res.getJSONObject(i);
                                                        String name = jsonObject.getString("name");
                                                        int statusID = jsonObject.getInt("statusId");
                                                        if (statusID == 1) {
                                                            Model m = new Model(name, statusID);
                                                            deviceModel.add(m);
                                                        }
                                                    }
                                                    setNewData(deviceModel);
                                                } catch (Exception e) {
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

        //
        layout.getPhysics().enableFling();
        layout.getPhysics().disablePhysics();

        return view;
    }
    public void setNewData(ArrayList<Model> device){

         Model m[] = new Model[device.size()];

        for (int i = 0; i < device.size(); i++) {
            m[i] = new Model();
        }
        for (int j = 0; j < device.size(); j++) {
            m[j] = device.get(j);
        }
        bubbleChart1.setVisibility(View.VISIBLE);
        bubbleChart1.setBubbleText("fan01");
        bubbleChart1.setBubbleSize(120);

        bubbleChart6.setVisibility(View.VISIBLE);
        bubbleChart6.setBubbleText("Light");
        bubbleChart6.setBubbleSize(100);



        for (int k = 0; k < device.size(); k++) {
            if (m[k].name.equals("fan02")) {
                bubbleChart2.setVisibility(View.VISIBLE);
                bubbleChart2.setBubbleText(m[k].name);
                bubbleChart2.setBubbleSize(100);
                //bubbleChart2.setBubbleColor(R.color.lightGreen);
            } else {
                bubbleChart2.setVisibility(View.GONE);
            }
                if (m[k].name.equals("AC")) {
                    bubbleChart4.setVisibility(View.VISIBLE);
                    bubbleChart4.setBubbleText(m[k].name);
                    bubbleChart4.setBubbleSize(210);
                    //bubbleChart4.setBubbleColor(R.color.tartOrange);
                } else{
                    bubbleChart4.setVisibility(View.GONE);
                } if(m[k].name.equals("Fridge")) {
                    bubbleChart3.setVisibility(View.VISIBLE);
                    bubbleChart3.setBubbleText(m[k].name);
                    bubbleChart3.setBubbleSize(150);
                    //bubbleChart3.setBubbleColor(R.color.coolOrange);
                } else{
                    bubbleChart3.setVisibility(View.GONE);
            } if(m[k].name.equals("Computer")) {
                bubbleChart5.setVisibility(View.VISIBLE);
                bubbleChart5.setBubbleText(m[k].name);
                bubbleChart5.setBubbleSize(180);

                } else{
                    bubbleChart5.setVisibility(View.GONE);
            }
        }

    }
}
