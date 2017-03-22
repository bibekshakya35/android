package com.payrollbullock.payroll.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.payrollbullock.payroll.R;

public class EmployeeAddInformation extends AppCompatActivity {
    TextView imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_information);
        String imagename = getIntent().getStringExtra("imageName");
        imageName = (TextView)findViewById(R.id.tvImageAddName);
        imageName.setText(imagename);
    }
}
