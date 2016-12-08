package com.sesame.onespace.utils.connectToServer;

/**
 * Created by Thian on 4/11/2559.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    // Response from the HTTP Request
    static InputStream httpResponseStream = null;
    // JSON Response String to create JSON Object
    static String jsonString = "";

    // Method to issue HTTP request, parse JSON result and return JSON Object
    public JSONObject makeHttpRequest(String url, String method) {

        //List<NameValuePair> params

        try {
            // get a Http client
            DefaultHttpClient httpClient = new DefaultHttpClient();

            // If required HTTP method is POST
            if (method == "POST") {
                // Create a Http POST object
                //HttpPost httpPost = new HttpPost(url);
                // Encode the passed parameters into the Http request
                //httpPost.setEntity(new UrlEncodedFormEntity(params));
                // Execute the request and fetch Http response
                //HttpResponse httpResponse = httpClient.execute(httpPost);

                // Extract the result from the response
                //HttpEntity httpEntity = httpResponse.getEntity();
                // Open the result as an input stream for parsing
                //httpResponseStream = httpEntity.getContent();
            }
            // Else if it is GET
            else if (method == "GET") {
                // Format the parameters correctly for HTTP transmission
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                // Add parameters to url in GET format
                //url += "?" + paramString;
                // Execute the request

                HttpGet httpGet = new HttpGet(url);
                // Execute the request and fetch Http response
                HttpResponse httpResponse = httpClient.execute(httpGet);
                // Extract the result from the response
                HttpEntity httpEntity = httpResponse.getEntity();
                // Open the result as an input stream for parsing
                httpResponseStream = httpEntity.getContent();
            }
            // Catch Possible Exceptions
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Create buffered reader for the httpResponceStream
            BufferedReader httpResponseReader = new BufferedReader(
                    new InputStreamReader(httpResponseStream, "iso-8859-1"), 8);
            // String to hold current line from httpResponseReader
            String line = null;
            // Clear jsonString
            jsonString = "";
            // While there is still more response to read
            while ((line = httpResponseReader.readLine()) != null) {
                // Add line to jsonString
                jsonString += (line + "\n");
            }
            // Close Response Stream
            httpResponseStream.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            // Create jsonObject from the jsonString and return it
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            // Return null if in error
            return null;
        }
    }
}