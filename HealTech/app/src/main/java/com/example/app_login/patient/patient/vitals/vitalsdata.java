package com.example.app_login.patient.patient.vitals;

public class vitalsdata {


    private String BP,O,Temp,HR,time,date;
    //pName,spName;

    public vitalsdata(String BP, String O, String Temp, String HR, String date, String time) {
        this.BP = BP;
        this.O = O;
        this.Temp = Temp;
        this.HR = HR;
        this.date = date;
        this.time = time;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getO() {
        return O;
    }

    public void setO(String O) {
        this.O = O;
    }


    public String getTemp() {
        return Temp;
    }

    public void setTemp(String Temp) {this.Temp = Temp;}


    public String getHR() {return HR;}

    public void setHR(String HR) {
        this.HR = HR;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
