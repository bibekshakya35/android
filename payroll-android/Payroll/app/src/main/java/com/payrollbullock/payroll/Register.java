package com.payrollbullock.payroll;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.payrollbullock.payroll.manager.HttpManager;
import com.payrollbullock.payroll.param.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    ProgressBar progressBar;
    List<MyTask> myTaskList;
    Button btnRegister;
    EditText usernameET;
    EditText fullNameET;
    EditText passET;
    EditText emailET;

    EditText confirmET;

    private static final String RESTSIGNUP="http://192.168.0.104:8080/payroll-services-ws/api/secured/register";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myTaskList = new ArrayList<>();
        progressBar = (ProgressBar)findViewById(R.id.progressBar_register);
        progressBar.setVisibility(View.INVISIBLE);
        btnRegister =(Button)findViewById(R.id.btnRegister);
        usernameET = (EditText)findViewById(R.id.reg_username);
        passET =(EditText)findViewById(R.id.reg_password);
        emailET = (EditText)findViewById(R.id.reg_email);
        fullNameET = (EditText) findViewById(R.id.reg_fullname);

        confirmET = (EditText)findViewById(R.id.reg_confirm_password);


    }

    public void onRegisterBtnClick(View view){
        if (isOnline()){
            if (confirmET.getText().toString().equals(passET.getText().toString())){
                requestData(RESTSIGNUP);
            }
            else{
                Toast.makeText(getApplicationContext(),"Dumbass password doesnot match ",Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"Sorry, Please dont be dumb ass by not connecting into network",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void requestData(String uri){
        RequestParam requestParam = new RequestParam();
        requestParam.setMethod("GET");
        //requestParam.setMethod("POST");
        requestParam.setUri(uri);
        if (usernameET.getText().toString().length()<10){
            Toast.makeText(getApplicationContext(),"Sorry username is less than 10 letter",Toast.LENGTH_LONG).show();
        }else {
            requestParam.setParam("fullname", fullNameET.getText().toString());
            requestParam.setParam("emailid", emailET.getText().toString());
            requestParam.setParam("username", usernameET.getText().toString());
            requestParam.setParam("password", passET.getText().toString());
            MyTask myTask = new MyTask();
            myTask.execute(requestParam);
        }
    }
    public void displayUpdate(){
        Toast.makeText(getApplicationContext(),usernameET.getText().toString()+" is successfully added",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Register.this,Login.class);
        intent.putExtra("username",usernameET.getText().toString());
        startActivity(intent);
    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }

    //create member class for async

    private class MyTask extends AsyncTask<RequestParam,String,String>{
        @Override
        protected void onPreExecute() {
            //check if task size is o or not
            if (myTaskList.size()==0){
                progressBar.setVisibility(View.VISIBLE);
            }
            myTaskList.add(this);
        }
        @Override
        protected String doInBackground(RequestParam... params) {
            //param first contain request param
            String content = HttpManager.getData(params[0],"bibekshakya35","ros3");
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            myTaskList.remove(this);
            if (myTaskList!=null){
                progressBar.setVisibility(View.INVISIBLE);
            }
            if (s==null){
                Toast.makeText(getApplicationContext(),"Sorry, Batman is busy figting for Humanity.. SO that's it",Toast.LENGTH_LONG).show();
                finish();
            }
            //if u survuive then     ok
            displayUpdate();

        }


    }

}
