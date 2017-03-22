package com.payrollbullock.payroll.employee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.kevinsawicki.http.HttpRequest;
import com.payrollbullock.payroll.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class EmployeeUploadPicture extends AppCompatActivity implements View.OnClickListener {
    private Button buttonChose;
    private Button buttonUpload;

    private ImageView imageView;



    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private RequestQueue requestQueue;

    private String UPLOAD_URL = "http://192.168.0.104:8080/payroll-services-ws/api/secured/image/upload";

    private String key_image = "image";

    private String filePathStore;

    public String getFilePathStore() {
        return filePathStore;
    }

    public void setFilePathStore(String filePathStore) {
        this.filePathStore = filePathStore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_upload_picture);

        buttonChose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);



        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadImage();

        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void uploadImage() {
        if (isOnline()) {
            final ProgressDialog loading = ProgressDialog.show(this, "uploading...", "please wait....", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    setFilePathStore(response);
                    Toast.makeText(getApplicationContext(), "upload successfully", Toast.LENGTH_LONG).show();
                    Intent goEmployeeAddInformation = new Intent(EmployeeUploadPicture.this,EmployeeAddInformation.class);
                    goEmployeeAddInformation.putExtra("imageName",getFilePathStore());
                    startActivity(goEmployeeAddInformation);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(EmployeeUploadPicture.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", "bibekshakya35", "ros3").getBytes(), Base64.DEFAULT)));
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String image =getStringName(bitmap);


                    Map<String,String> params = new HashMap<>();
                    params.put(key_image,image);
                    return params;
                }
            };
            requestQueue.add(stringRequest);



        } else {
            Toast.makeText(getApplicationContext(), "please make sure your mobile is not shit", Toast.LENGTH_LONG).show();
        }

    }

    public String getStringName(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            /*filePathStore = getRealPathFromURI(filePath);*/
            try {
                //geting bitmap from library
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //setting the bitmap to the image view
                imageView.setImageBitmap(bitmap);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //for getting android file path from uri

 /*   public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }*/

}
