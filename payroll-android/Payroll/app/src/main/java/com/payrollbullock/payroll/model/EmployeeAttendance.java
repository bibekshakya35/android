package com.payrollbullock.payroll.model;

import android.graphics.Bitmap;

/**
 * Created by bibekshakya on 7/9/16.
 */
public class EmployeeAttendance {
    private int id;
    private String employeeName;
    private String fatherName;
    private String motherName;
    private String employeeDob;
    private String employeeCitizenshipNumber;
    private String employeeNationality;
    private String employeeDateofCommencement;
    private String employeePic;
    private String maritialStatus;
    private String sex;
    private String employeeJobPosition;
    private String phoneNumber;
    private String mobileNumber;
    private double basicSalary;
    private String employeeClassification;
    private String employeeRole;
    private String jobSpecification;
    private String shift;
    private String department;


    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private boolean employeeAttendanceStatus;

    public EmployeeAttendance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeJobPosition() {
        return employeeJobPosition;
    }

    public void setEmployeeJobPosition(String employeeJobPosition) {
        this.employeeJobPosition = employeeJobPosition;
    }

    public boolean isEmployeeAttendanceStatus() {
        return employeeAttendanceStatus;
    }

    public void setEmployeeAttendanceStatus(boolean employeeAttendanceStatus) {
        this.employeeAttendanceStatus = employeeAttendanceStatus;
    }

    public String getEmployeePic() {
        return employeePic;
    }

    public void setEmployeePic(String employeePic) {
        this.employeePic = employeePic;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEmployeeDob() {
        return employeeDob;
    }

    public void setEmployeeDob(String employeeDob) {
        this.employeeDob = employeeDob;
    }



    public String getEmployeeCitizenshipNumber() {
        return employeeCitizenshipNumber;
    }

    public void setEmployeeCitizenshipNumber(String employeeCitizenshipNumber) {
        this.employeeCitizenshipNumber = employeeCitizenshipNumber;
    }

    public String getEmployeeNationality() {
        return employeeNationality;
    }

    public void setEmployeeNationality(String employeeNationality) {
        this.employeeNationality = employeeNationality;
    }

    public String getEmployeeDateofCommencement() {
        return employeeDateofCommencement;
    }

    public void setEmployeeDateofCommencement(String employeeDateofCommencement) {
        this.employeeDateofCommencement = employeeDateofCommencement;
    }



    public String getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getEmployeeClassification() {
        return employeeClassification;
    }

    public void setEmployeeClassification(String employeeClassification) {
        this.employeeClassification = employeeClassification;
    }

    public String getJobSpecification() {
        return jobSpecification;
    }

    public void setJobSpecification(String jobSpecification) {
        this.jobSpecification = jobSpecification;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }







}