package com.apiMashup.Controllers;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.apiMashup.ApiMashupException;
import com.apiMashup.GithubRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GithubController {
    String endpoint;
    String query;
    HttpURLConnection conn;

    Logger logger = LoggerFactory.getLogger(GithubController.class);

    public GithubController(String endpoint, String query) {
        this.endpoint = endpoint;
        this.query = query;
    }

    private String endpointWithQueryParm() {
        return endpoint + "?q=" + query;
    }

    private void checkConnection() throws Exception {
        int responseCode;
        String errStr;
        String endpointWithQueryParm = endpointWithQueryParm();
        try {
            URL url = new URL(endpointWithQueryParm);
            conn = (HttpURLConnection)url.openConnection();
            responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                errStr = String.format("Failed to connect to Github. Error code %d returned", responseCode);
                logger.error(errStr);
                throw new ApiMashupException(errStr);
            }
            logger.info("Successfully opened connection to Github!");
        } catch (MalformedURLException e) {
            errStr = String.format("Malformed URL: endpoint %s invalid", endpoint);
            logger.error(errStr);
            throw new ApiMashupException(errStr, e);
        } catch (Exception e) {
            errStr = "Failed to connect to Github.";
            logger.error(errStr);
            throw new ApiMashupException(errStr, e);
        }
    }

    public ArrayList<GithubRepo> sendRequest() throws Exception {
        int count = 0;
        GithubRepo repo;
        String errStr;
        try {
            checkConnection();
            InputStream inputStream = conn.getInputStream();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));

            JSONArray ja = (JSONArray) jsonObject.get("items");
            Iterator i = ja.iterator();

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            ArrayList<GithubRepo> al = new ArrayList<GithubRepo>();

            while (i.hasNext() && count < 10) {
                JSONObject jsonRepo = (JSONObject) i.next();
                al.add(mapper.readValue(jsonRepo.toJSONString(), GithubRepo.class));
                count++;
            }
            return al;
        } catch (Exception e) {
            errStr = "Failed to send GET Request to Github";
            logger.error(errStr);
            throw new ApiMashupException(errStr, e);
        }
    }
}
