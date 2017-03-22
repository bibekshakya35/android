package com.payrollbullock.payroll.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.payrollbullock.payroll.R;
import com.payrollbullock.payroll.model.EmployeeAttendance;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*

*
 * Created by bibekshakya on 7/9/16.

*/

public class EmployeeAttendanceAdapter extends ArrayAdapter<EmployeeAttendance> {
    private Context context;
    private List<EmployeeAttendance> employeeAttendanceList;
    private RequestQueue requestQueue;
    private LruCache<Integer,Bitmap> imageCache;
    ImageView imageView;

    public EmployeeAttendanceAdapter(Context context, int resource, List<EmployeeAttendance> employeeAttendanceList) {
        super(context, resource, employeeAttendanceList);
        this.context=context;
        this.employeeAttendanceList = employeeAttendanceList;


        //this is only memory available
        final int maxMemory =(int)(Runtime.getRuntime().maxMemory()/1024);
        //using 1/8 of what is available
        final int cacheSize =maxMemory/8;
        //passed in by my cacheSize Value
        imageCache =new LruCache<>(cacheSize);
        requestQueue = Volley.newRequestQueue(context);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.employee_attendance_item_list,parent,false);
        //display employee name in textView in the textView WIdget of employee Attendance Fragment
        final EmployeeAttendance employeeAttendance = employeeAttendanceList.get(position);
        TextView textViewEmployee = (TextView)view.findViewById(R.id.tvEmpName);
        textViewEmployee.setText(employeeAttendance.getEmployeeName());
        TextView textViewPosition = (TextView)view.findViewById(R.id.tvP);
        textViewPosition.setText(employeeAttendance.getEmployeeJobPosition());
        TextView textViewRole = (TextView)view.findViewById(R.id.tvR);
        textViewRole.setText(employeeAttendance.getEmployeeRole());
        RadioGroup rg =(RadioGroup)view.findViewById(R.id.toggle);
        int selectedItem = (employeeAttendance.isEmployeeAttendanceStatus()) ? 1:0;
        if (selectedItem>=0){
            rg.check(rg.getChildAt(selectedItem).getId());
        }
        rg.setEnabled(false);
        //b4 condition create refrrence of bitmap
        //if bitmap is in cache which is determined by the imageCache,put(employeeAttendance.getId,bitmapofCOtainer)
        Bitmap bitmap =imageCache.get(employeeAttendance.getId());

        if (bitmap!=null) {

            imageView = (ImageView) view.findViewById(R.id.imgViewEmployee);
            imageView.setImageBitmap(employeeAttendance.getImage());
        }
        else
        {
           /* EmployeeAttendanceViewAndBitmap employeeAttendanceViewAndBitmap = new EmployeeAttendanceViewAndBitmap();
            employeeAttendanceViewAndBitmap.employeeAttendance = employeeAttendance;
            employeeAttendanceViewAndBitmap.view = view;
            ImageLoader imageLoader =new ImageLoader();
            imageLoader.execute(employeeAttendanceViewAndBitmap);*/
            ImageRequest imageRequest = new ImageRequest(employeeAttendance.getEmployeePic(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView = (ImageView) view.findViewById(R.id.imgViewEmployee);
                            imageView.setImageBitmap(response);
                            imageCache.put(employeeAttendance.getId(), response);
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
        }
        return view;
    }

    //for lazy loading we need three objet employeeAttendance itself, View of current instance or context and Bitmap

  /*  class EmployeeAttendanceViewAndBitmap{
        private EmployeeAttendance employeeAttendance;
        private View view;
        private Bitmap bitmap;
    }*/
    //now we need to apply asych thread inside the adapter class

    //so first param deals with the incomeing value  and second will be proogress and last one will be result produce b dobackgroun
   /* private class ImageLoader extends AsyncTask<EmployeeAttendanceViewAndBitmap,Void,EmployeeAttendanceViewAndBitmap>
    {
        @Override
        protected EmployeeAttendanceViewAndBitmap doInBackground(EmployeeAttendanceViewAndBitmap... params) {

            EmployeeAttendanceViewAndBitmap employeeAttendanceViewAndBitmap =params[0];
            EmployeeAttendance employeeAttendance = employeeAttendanceViewAndBitmap.employeeAttendance;


            try{

                InputStream inputStream = (InputStream)new URL(employeeAttendance.getEmployeePic()).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                employeeAttendanceViewAndBitmap.bitmap=bitmap;
                inputStream.close();
                return employeeAttendanceViewAndBitmap;
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(EmployeeAttendanceViewAndBitmap employeeAttendanceViewAndBitmap) {
            //first load image from content and at the same time saving image in memory
            ImageView imageView =(ImageView)employeeAttendanceViewAndBitmap.view.findViewById(R.id.imgViewEmployee);
            imageView.setImageBitmap(employeeAttendanceViewAndBitmap.bitmap);

            //adding image in bitmap
            imageCache.put(employeeAttendanceViewAndBitmap.employeeAttendance.getId(),employeeAttendanceViewAndBitmap.bitmap);
            //employeeAttendanceViewAndBitmap.employeeAttendance.setImage(employeeAttendanceViewAndBitmap.bitmap);
        }
    }*/
}
