package com.encore.ete.utils;

public class RestContext {

    private RestData rest;

    public RestData getRestData(){
        if (rest == null){
            rest = new RestData();
        }
        return rest;
    }

    public void setRestData(RestData rest){
        this.rest = rest;
    }

}
