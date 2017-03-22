package com.payrollbullock.payroll.attendance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;


import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.ListFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payrollbullock.payroll.adapter.EmployeeAttendanceAdapter;
import com.payrollbullock.payroll.employee.EmployeeInformation;
import com.payrollbullock.payroll.manager.HttpManager;
import com.payrollbullock.payroll.model.EmployeeAttendance;
import com.payrollbullock.payroll.parser.EmployeeAttendanceJsonParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.payrollbullock.payroll.R;


public class Attendance extends ListFragment {
    private List<EmployeeAttendance> employeeAttendanceList = null;
    //private List<MyTask> myTaskList =null;
    private static final String EMPLOYEE_ATTENDANCE_REST = "http://192.168.0.104:8080/payroll-services-ws/api/secured/employee/attendance";
    TextView textView;
    View view;
    ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void requestData(String uri) {
        if (isOnline()) {

            Intent intentLogin = getActivity().getIntent();
            final String username = intentLogin.getStringExtra("username");
            final String password = intentLogin.getStringExtra("password");
            //start of the volley

            //construct of requestObject many object of request but we gonna use string request
            StringRequest stringRequest = new StringRequest(uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            employeeAttendanceList = EmployeeAttendanceJsonParser.parseEmployeeAttendance(response);
                            updateDisplay();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


            ) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", username, password).getBytes(), Base64.DEFAULT)));
                    return headers;
                }


            };

            //requestQueue
            //add the request to the queue
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(stringRequest);

            //MyTask myTask = new MyTask();

            //myTask.execute(uri,username,password);
        } else {
            Toast.makeText(getContext(), "Sorry SOmething wrong on your Network", Toast.LENGTH_LONG).show();
        }
    }

    protected void updateDisplay() {
        //user e mployeeadatpter class to diplay dat
        EmployeeAttendanceAdapter adapter = new EmployeeAttendanceAdapter(getActivity(), R.layout.employee_attendance_item_list, employeeAttendanceList);
        setListAdapter(adapter);
        ListView listView = (ListView) getActivity().findViewById(android.R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EmployeeInformation.class);
                Bundle bundle = new Bundle();
                for (EmployeeAttendance employeeAttendance : employeeAttendanceList) {
                    employeeAttendance = employeeAttendanceList.get(position);

                    bundle.putInt("id", employeeAttendance.getId());
                    bundle.putString("employeeName", employeeAttendance.getEmployeeName());
                    bundle.putString("fatherName", employeeAttendance.getFatherName());
                    bundle.putString("motherName", employeeAttendance.getMotherName());
                    bundle.putString("employeeDob", employeeAttendance.getEmployeeDob());
                    bundle.putString("employeeCN", employeeAttendance.getEmployeeCitizenshipNumber());
                    bundle.putString("employeeNationality", employeeAttendance.getEmployeeNationality());
                    bundle.putString("employeeDOC", employeeAttendance.getEmployeeDateofCommencement());
                    bundle.putString("bitmap", employeeAttendance.getEmployeePic());
                    bundle.putString("image", employeeAttendance.getEmployeePic());
                    bundle.putString("ms", employeeAttendance.getMaritialStatus());
                    bundle.putString("sex", employeeAttendance.getSex());
                    bundle.putString("eposition", employeeAttendance.getEmployeeJobPosition());
                    bundle.putString("pn", employeeAttendance.getPhoneNumber());
                    bundle.putString("mn", employeeAttendance.getMobileNumber());
                    bundle.putDouble("bs", employeeAttendance.getBasicSalary());
                    bundle.putString("ec", employeeAttendance.getEmployeeClassification());
                    bundle.putString("er", employeeAttendance.getEmployeeRole());
                    bundle.putString("js", employeeAttendance.getJobSpecification());
                    bundle.putString("shift", employeeAttendance.getShift());
                    bundle.putString("department", employeeAttendance.getDepartment());

                }
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public Attendance() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_attendance, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarAttendance);
        progressBar.setVisibility(View.INVISIBLE);
        employeeAttendanceList = new ArrayList<>();

        //myTaskList= new ArrayList<>();
        requestData(EMPLOYEE_ATTENDANCE_REST);
        return view;
    }

    //for the use of volley
/*
    private class MyTask extends AsyncTask<String,String,List<EmployeeAttendance>>{
        @Override
        protected void onPreExecute() {
            if (myTaskList.size()==0){
                progressBar.setVisibility(View.VISIBLE);
            }
            myTaskList.add(this);
        }

        @Override
        protected List<EmployeeAttendance> doInBackground(String... params) {
            String content = HttpManager.getData(params[0],params[1],params[2]);
            employeeAttendanceList = EmployeeAttendanceJsonParser.parseEmployeeAttendance(content);

            //for code that might help me future
            //lazy loading loading image one at a time from web so below line of code is commented out
            // because it was loading all the image once at a time


//            for (EmployeeAttendance employeeAttendance :employeeAttendanceList){
//                try{
//                    InputStream inputStream = (InputStream) new URL(employeeAttendance.getEmployeePic()).getContent();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    employeeAttendance.setImage(bitmap);
//                    inputStream.close();
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }

            return employeeAttendanceList;
        }

        @Override
        protected void onPostExecute(List<EmployeeAttendance> result) {

            myTaskList.remove(this);
            if (myTaskList.size()==0){
                progressBar.setVisibility(View.INVISIBLE);
            }
            if(result !=null) {
                updateDisplay();
            }
            else{
                Toast.makeText(getActivity(),"Sorry No data is loaded ",Toast.LENGTH_LONG).show();
                return;
            }

        }
    }*/
}
