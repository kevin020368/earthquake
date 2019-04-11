package com.example.android.quakereport;

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private Long mTimeInMilliSeconds;
    private String mUrl;

    //constructor
    public Earthquake (double mag, String loc, Long timeInMilliSeconds, String url){
        mMagnitude = mag;
        mLocation = loc;
        mTimeInMilliSeconds = timeInMilliSeconds;
        mUrl = url;
    }

    //getter
    public double getMagnitude(){ return mMagnitude; }
    public String getLocation(){ return mLocation; }
    public Long getTimeInMilliSeconds(){ return mTimeInMilliSeconds;}
    public String getUrl(){return mUrl;}
}
