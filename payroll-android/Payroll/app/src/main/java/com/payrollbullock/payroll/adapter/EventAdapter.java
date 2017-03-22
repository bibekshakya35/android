package com.payrollbullock.payroll.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.payrollbullock.payroll.R;
import com.payrollbullock.payroll.model.Event;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by bibekshakya on 7/15/16.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private List<Event> eventList;
    private Context context;

    public EventAdapter(Context context, int resource, List<Event> eventList) {
        super(context, resource, eventList);
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       /* //

        when you run setContentView(layout file name), you can run findViewById(id of the widget). You dont need to do something like xyz.findViewById. The context of your app is set to that layout file and all findBiewById call will refer to that layout file.

                There are cases when you need to pick up one more layout file, like a CustomDialog, ListElement or a Custom Toast. At this time you wont want to create a Activity just for these small UI components, that that time you programmatically need to get a programmatic reference to your layout file, so that you can run findViewById on it.

                Inflate, blows the layout like a balloon and give you the balloon for you to watch around it all the colors and objects drawn on it :). Inflate gives you the object reference to that layout to call findViewById on.

                Hope this clears.*/

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.event_item_list, parent, false);
        Event event = eventList.get(position);
        TextView textViewHeader = (TextView) view.findViewById(R.id.tvEheader);
        TextView textViewPlace = (TextView) view.findViewById(R.id.tvEPlace);
        TextView textViewDate = (TextView)view.findViewById(R.id.tvEDate);

        textViewHeader.setText(event.getHeader());
        textViewDate.setText(event.getDateofEvent());
        textViewPlace.setText(event.getPlace());
        return view;

    }

}
