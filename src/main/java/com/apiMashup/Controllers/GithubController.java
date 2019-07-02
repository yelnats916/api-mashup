package com.apiMashup.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.apiMashup.ApiMashupException;

public class GithubController {
    String endpoint;
    String query;
    HttpURLConnection conn;

    public GithubController(String endpoint, String query) {
        this.endpoint = endpoint;
        this.query = query;
    }

    private String endpointWithQueryParm() {
        return endpoint + "?q=" + query;
    }

    private void checkConnection() throws Exception {
        int responseCode;
        String endpointWithQueryParm = endpointWithQueryParm();
        try {
            URL url = new URL(endpointWithQueryParm);
            conn = (HttpURLConnection)url.openConnection();
            responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new ApiMashupException(String.format("Failed to connect to Github. Error code %d returned", responseCode));
            }
        } catch (MalformedURLException e) {
            throw new ApiMashupException(String.format("Malformed URL: endpoint %s invalid", endpoint), e);
        } catch (Exception e) {
            //log here
            throw new ApiMashupException("Failed to connect to Github.", e);
        }
    }

    public ArrayList<JSONObject> sendRequest() throws Exception {
        int count = 0;
        try {
            checkConnection();
            InputStream inputStream = conn.getInputStream();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));

            JSONArray ja = (JSONArray) jsonObject.get("items");
            Iterator i = ja.iterator();

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            ArrayList<JSONObject> al = new ArrayList<JSONObject>();

            while (i.hasNext() && count < 10) {
                JSONObject jsonRepo = (JSONObject) i.next();
                al.add(jsonRepo);
                count++;
            }
            return al;


        } catch (Exception e) {
            throw new ApiMashupException("Failed to send Request");
        }
    }
}
