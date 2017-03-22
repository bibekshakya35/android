package com.payrollbullock.payroll.event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.payrollbullock.payroll.R;

import org.w3c.dom.Text;

public class EventShow extends AppCompatActivity {
    TextView textViewHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_show);
        Bundle bundle = getIntent().getExtras();
        textViewHeader = (TextView)findViewById(R.id.tvESheader);

        textViewHeader.setText(bundle.getString("header"));
    }
}
