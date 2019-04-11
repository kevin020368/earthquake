package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes ){

        super(context, 0, earthquakes);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Earthquake currentEarthquake = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        //mapping view respectively for data population
        TextView magnitudeView = (TextView) convertView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        TextView locationView = (TextView) convertView.findViewById(R.id.location_offset);
        TextView location2View = (TextView) convertView.findViewById(R.id.location);
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        TextView timeView = (TextView) convertView.findViewById(R.id.time);

        //manipulate magnitude and its background color
        DecimalFormat formatMagnitude = new DecimalFormat("0.0");
        String formattedMagnitude = formatMagnitude.format(currentEarthquake.getMagnitude());
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        //setup text and color
        magnitudeView.setText(formattedMagnitude);
        magnitudeCircle.setColor(magnitudeColor);

        //get location
        String location = currentEarthquake.getLocation();

        //split the location if it contains string "of",else attach it with string "Near the"
        if(location.contains("of")){
            //split it
            String[] parts = location.split("(?<=of)");
            String part1 = parts[0];
            String part2 = parts[1];
            locationView.setText(part1);
            location2View.setText(part2);
        }else{
            //attach "Near the"
            String part1 = "Near the";
            locationView.setText(part1);
            location2View.setText(location);
        }

        //get time in milliseconds
        Long timeInMilliSeconds = currentEarthquake.getTimeInMilliSeconds();

        Date dateObject = new Date(timeInMilliSeconds);

        SimpleDateFormat simpleDate = new SimpleDateFormat("MMM.dd.yyyy" );
        String dateInFormat = simpleDate.format(dateObject);
        dateView.setText(dateInFormat);

        SimpleDateFormat simpleTime = new SimpleDateFormat("h:mm a");
        String timeInFormat = simpleTime.format(dateObject);
        timeView.setText(timeInFormat);

        // Return the completed view to render on screen
        return convertView;
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeFloor = (int) Math.floor(magnitude);
        int magnitudeColorResourceId = 0;

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }
}
