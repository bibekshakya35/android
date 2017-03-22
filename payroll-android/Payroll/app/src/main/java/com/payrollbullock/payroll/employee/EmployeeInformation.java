package com.payrollbullock.payroll.employee;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.payrollbullock.payroll.R;

import java.util.HashMap;
import java.util.Map;

public class EmployeeInformation extends AppCompatActivity {
    TextView employeename,employeefatherName,mothername,dateofBirth,citizenNumberShip,nationalities,phoneNumber,mobileNumber,maritialStatus,gender,role,shift,department,level,position,jobspecification,
    salary,doc;
    ImageView imageView;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information);
        Bundle bundle = getIntent().getExtras();

        employeename = (TextView)findViewById(R.id.txtview_emp_name);
        employeefatherName = (TextView)findViewById(R.id.textview_fath_name);
        mothername = (TextView)findViewById(R.id.txtview_moth_name);
        dateofBirth = (TextView)findViewById(R.id.txtview_emp_DOB);
        citizenNumberShip = (TextView)findViewById(R.id.txtview_citizen_no);
        nationalities = (TextView)findViewById(R.id.txtview_nation);
        phoneNumber = (TextView)findViewById(R.id.txtview_emp_phone);
        mobileNumber = (TextView)findViewById(R.id.txtview_emp_mob);
        maritialStatus = (TextView)findViewById(R.id.txtview_matrial);
        gender = (TextView)findViewById(R.id.txtview_gen);
        role = (TextView)findViewById(R.id.txtview_role);
        shift =(TextView)findViewById(R.id.txtview_shft);
        department = (TextView)findViewById(R.id.txtview_depart);
        level = (TextView)findViewById(R.id.txtview_level);
        position = (TextView)findViewById(R.id.txtview_posit);
        salary = (TextView)findViewById(R.id.txtview_sal);
        jobspecification = (TextView)findViewById(R.id.txtview_job_spec);
        doc=(TextView)findViewById(R.id.txtview_dot_of_commen);
        requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(bundle.getString("bitmap"), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView = (ImageView)findViewById(R.id.user_profile_photo);
                imageView.setImageBitmap(response);
            }
        },
                80,
                80,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Authorization",String.format("Basic %s", Base64.encodeToString(String.format("%s:%s","bibekshakya35","ros3").getBytes(),Base64.DEFAULT)));
                return header;
            }
        };
        requestQueue.add(imageRequest);
        employeename.append("Name:          "+bundle.getString("employeeName"));
        employeefatherName.append("Father Name:   "+bundle.getString("fatherName"));
        mothername.append("Mother Name:    " +bundle.getString("motherName"));
        dateofBirth.append("DOB:           "+bundle.getString("employeeDob"));
        citizenNumberShip.append("Citizen No:    "+bundle.getString("employeeCN"));
        nationalities.append("Nationalities: "+bundle.getString("employeeNationality"));
        phoneNumber.append("Phone Number:  "+bundle.getString("pn"));
        role.append("Role :      "+bundle.getString("er"));
        mobileNumber.append("Mobile Number:  "+bundle.getString("mn"));
        maritialStatus.append("Maritial Status:    "+bundle.getString("ms"));
        gender.append("Gender:        "+bundle.getString("sex"));
        shift.append("Shift:         "+bundle.getString("shift"));
        department.append("Department:   "+bundle.getString("department"));
        level.append("Level:        "+bundle.getString("ec"));
        position.append("Position:        "+bundle.getString("eposition"));
        jobspecification.append("Job Specification:\n"+bundle.getString("js"));
        salary.append("Salary       "+bundle.getDouble("bs"));
        doc.append("Starter:       "+bundle.getString("employeeDOC"));

    }
}
