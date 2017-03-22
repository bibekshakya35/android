package com.payrollbullock.payroll.parser;

import com.payrollbullock.payroll.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bibekshakya on 7/15/16.
 */
public class EventJSONParser {

    public static List<Event> getEventParserList(String content) {
        List<Event> eventList = null;
        //so content is arrive in json array need to minimize
        try {
            JSONArray jsonArray = new JSONArray(content);
            eventList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Event event =new Event();
                event.setEventId(jsonObject.getInt("eventId"));
                event.setDateofEvent(jsonObject.getString("dateofEvent"));
                event.setContent(jsonObject.getString("content"));
                event.setSpecification(jsonObject.getString("specification"));
                event.setHeader(jsonObject.getString("header"));
                event.setPlace(jsonObject.getString("place"));
                eventList.add(event);

            }
            return eventList;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }


    }
}
