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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.payrollbullock.payroll.manager.HttpManager;
import com.payrollbullock.payroll.home.Home;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private ProgressBar progressBar;
    List<MyTask> myTasksList;
    private static final String RESTLOGIN="http://192.168.0.104:8080/payroll-services-ws/api/secured/message";
    private EditText usernameEditText;
    private EditText passwordEdittext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar =(ProgressBar)findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);
        myTasksList = new ArrayList<>();

        usernameEditText = (EditText)findViewById(R.id.etUsername);
        passwordEdittext = (EditText)findViewById(R.id.etPass);
    }

    public void onBtnClick(View view){
        Button buttonLogin = (Button)findViewById(R.id.btnSingIn);
        if (view.getId()==R.id.btnSingIn){
            //updateDisplay();
            if (isOnline()){
                requestData(RESTLOGIN);
            }
            else{
                Toast.makeText(Login.this, "Not Connect WIth Network", Toast.LENGTH_LONG).show();
            }
        }
        else if (view.getId()==R.id.btnSignUp){
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
        }
    }
    protected boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }
    private void requestData(String uri){
        MyTask myTask = new MyTask();
        myTask.execute(uri,usernameEditText.getText().toString(),passwordEdittext.getText().toString());
    }
    public void updateDisplay(){
        Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
        Intent mainActivity = new Intent(Login.this,Home.class);
        mainActivity.putExtra("username",usernameEditText.getText().toString());
        mainActivity.putExtra("password",passwordEdittext.getText().toString());
        startActivity(mainActivity);
    }
    //param progress and result
    private class MyTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            if (myTasksList.size()==0){
                progressBar.setVisibility(View.VISIBLE);
            }
            myTasksList.add(this);
        }


        @Override
        protected String doInBackground(String... params) {
            //param 0 which is come from requestData
            String content = HttpManager.getData(params[0],params[1],params[2]);
            return content;

        }

        //content of dobackground pass to the dopostexcute
        @Override
        protected void onPostExecute(String result) {

            myTasksList.remove(this);
            if (myTasksList.size()==0){
                progressBar.setVisibility(View.INVISIBLE);
            }
            if (result==null){
                Toast.makeText(Login.this,"Sorry Username or password doesnot match",Toast.LENGTH_LONG).show();
                return;
            }

            //list of object from content
            //from json

            updateDisplay();
        }

    }

}
