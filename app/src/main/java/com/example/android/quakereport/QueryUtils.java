package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * return new URL object from the given String url
     * @param stringUrl
     * @return url
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        // make url
        URL url = createUrl(requestUrl);
        // get result from http request
        String jsonResponse = makeHttpRequest(url);
        // parse json form result, and return as earthquake
        List<Earthquake> earthquakes = parsingJson(jsonResponse);
        return earthquakes;
    }

    /**
     * make http connection, and return result as String
     */
    private static String makeHttpRequest(URL url) {
        String jSonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jSonResponse;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jSonResponse = convertStreamToString(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON result", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jSonResponse;
    }

    private static String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static List<Earthquake> parsingJson(String response) {
        List<Earthquake> earthquakes = new ArrayList<>();
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(response)) {
            return null;
        }
        if (response != null) {
            try {
                JSONObject baseJsonObject = new JSONObject(response);
                // Getting JSON Array node
                JSONArray features = baseJsonObject.getJSONArray("features");

                // looping through All Contacts
                for (int i = 0; i < features.length(); i++) {
                    JSONObject c = features.getJSONObject(i);
                    JSONObject properties = c.getJSONObject("properties");

                    //mapping magnitude, place, and date to JSON attributes
                    double magnitude = properties.getDouble("mag");
                    String location = properties.getString("place");
                    Long time = properties.getLong("time");
                    //Extract the value for the key called "url"
                    String url = properties.getString("url");
                    earthquakes.add(new Earthquake(magnitude, location, time, url));
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }
        }
        return earthquakes;
    }
}

