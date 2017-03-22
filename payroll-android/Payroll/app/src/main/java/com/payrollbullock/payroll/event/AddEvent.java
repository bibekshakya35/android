package com.payrollbullock.payroll.event;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.payrollbullock.payroll.R;
import com.payrollbullock.payroll.home.Home;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity {
    Button btnAdd;
    EditText header, date, place, content, spec;
    private RequestQueue requestQueue;
    public static final String url = "http://192.168.0.104:8080/payroll-services-ws/api/secured/event/add";
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        header = (EditText) findViewById(R.id.etHeader);
        date = (EditText) findViewById(R.id.etDate);
        place = (EditText) findViewById(R.id.etPlace);
        content = (EditText) findViewById(R.id.etContent);
        spec = (EditText) findViewById(R.id.etSpec);
        requestQueue = Volley.newRequestQueue(this);
        params = new HashMap<>();
    }

    public void onBtnAdd(View view) {
        params.put("dateofEvent", date.getText().toString());
        params.put("content", content.getText().toString());
        params.put("header", header.getText().toString());
        params.put("place", place.getText().toString());
        params.put("specification", spec.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getApplicationContext(), header.getText().toString() + " has been added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                params.put("Authorization", String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", "bibekshakya35", "ros3").getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
