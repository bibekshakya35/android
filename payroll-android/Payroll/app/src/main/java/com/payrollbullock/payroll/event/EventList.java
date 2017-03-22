package com.payrollbullock.payroll.event;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payrollbullock.payroll.R;
import com.payrollbullock.payroll.adapter.EventAdapter;
import com.payrollbullock.payroll.model.Event;
import com.payrollbullock.payroll.parser.EventJSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventList extends Fragment {

    private List<Event> eventList;

    private RequestQueue requestQueue;
    View view;
    public static final String REST_URL = "http://192.168.0.104:8080/payroll-services-ws/api/secured/event/list";

    public EventList() {
    }

    protected void requestData(String url) {
        if (isOnline()) {
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    eventList = EventJSONParser.getEventParserList(response);
                    updateDisplay();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headerParams = new HashMap<>();
                    headerParams.put("Authorization",String.format("Basic %s", Base64.encodeToString(String.format("%s:%s","bibekshakya35","ros3").getBytes(),Base64.DEFAULT)));
                    return headerParams;
                }
            };
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(getContext(), "Cannot connect to web", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else return false;
    }
    protected void updateDisplay(){
        EventAdapter eventAdapter = new EventAdapter(getActivity(),R.layout.event_item_list,eventList);

        ListView listView = (ListView)getActivity().findViewById(R.id.list1);
        listView.setAdapter(eventAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),EventShow.class);
                Bundle valueBundle =new Bundle();
                for (Event event : eventList){
                    event = eventList.get(position);
                    valueBundle.putInt("eventId",event.getEventId());
                    valueBundle.putString("dateE",event.getDateofEvent());
                    valueBundle.putString("header",event.getHeader());
                    valueBundle.putString("place",event.getPlace());
                    valueBundle.putString("content",event.getContent());
                    valueBundle.putString("spec",event.getSpecification());
                }
                intent.putExtras(valueBundle);
                startActivity(intent);

            }

        });



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_event_list, container, false);
        eventList = new ArrayList<>();
        requestData(REST_URL);

        return view;

    }
}
