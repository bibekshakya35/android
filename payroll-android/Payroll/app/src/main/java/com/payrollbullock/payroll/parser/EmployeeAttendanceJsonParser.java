package com.payrollbullock.payroll.parser;

import com.payrollbullock.payroll.model.EmployeeAttendance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bibekshakya on 7/9/16.
 */
public class EmployeeAttendanceJsonParser {
    public static List<EmployeeAttendance> parseEmployeeAttendance(String content){
        List<EmployeeAttendance> employeeAttendanceList = null;
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(content);

         employeeAttendanceList =new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            EmployeeAttendance employeeAttendance = new EmployeeAttendance();


            employeeAttendance.setId(jsonObject.getInt("id"));
            employeeAttendance.setEmployeeName(jsonObject.getString("employeeName"));
            employeeAttendance.setFatherName(jsonObject.getString("fatherName"));
            employeeAttendance.setMotherName(jsonObject.getString("motherName"));
            employeeAttendance.setEmployeeDob(jsonObject.getString("employeeDob"));
            employeeAttendance.setEmployeeCitizenshipNumber(jsonObject.getString("employeeCitizenshipNumber"));
            employeeAttendance.setEmployeeNationality(jsonObject.getString("employeeNationality"));
            employeeAttendance.setEmployeeDateofCommencement(jsonObject.getString("employeeDateofCommencement"));
            employeeAttendance.setMaritialStatus(jsonObject.getString("maritialStatus"));
            employeeAttendance.setSex(jsonObject.getString("sex"));
            employeeAttendance.setEmployeeJobPosition(jsonObject.getString("employeeJobPosition"));
            employeeAttendance.setPhoneNumber(jsonObject.getString("phoneNumber"));
            employeeAttendance.setMobileNumber(jsonObject.getString("mobileNumber"));
            employeeAttendance.setBasicSalary(jsonObject.getDouble("basicSalary"));
            employeeAttendance.setEmployeeClassification(jsonObject.getString("employeeClassification"));
            employeeAttendance.setEmployeeRole(jsonObject.getString("employeeRole"));
            employeeAttendance.setJobSpecification(jsonObject.getString("jobSpecification"));
            employeeAttendance.setShift(jsonObject.getString("shift"));
            employeeAttendance.setDepartment(jsonObject.getString("department"));
            employeeAttendance.setEmployeeAttendanceStatus(jsonObject.getBoolean("employeeAttendanceStatus"));
            employeeAttendance.setEmployeePic(jsonObject.getString("employeePic"));

            employeeAttendanceList.add(employeeAttendance);
        }
            return employeeAttendanceList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
