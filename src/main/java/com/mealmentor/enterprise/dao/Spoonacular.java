package com.mealmentor.enterprise.dao;

import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Repository
public class Spoonacular {

    private final CloseableHttpClient client = HttpClients.createDefault();

    //this method accepts two arguments about a Meal
    public JSONObject get(String foodName, int mealCalorieMax) {
        String mealCalorieMaxString = Integer.toString(mealCalorieMax);
        HttpGet request = new HttpGet("https://api.spoonacular.com/recipes/complexSearch?apiKey=0cea569c323f45d090b3335b48f39341&query=" + foodName + "&addRecipeInformation=true&maxCalories=" + mealCalorieMaxString + "&number=1");
        JSONObject finalObject = new JSONObject();
        String returnJsonString = "";

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            returnJsonString = EntityUtils.toString(entity);

            Map<String, String> desiredMap = convertToMap(returnJsonString);
            JSONObject returnResult = new JSONObject(desiredMap);


            finalObject = returnResult;


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalObject;
    }

    public Map<String, String> convertToMap(String args) {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> returnMap = new Map<String, String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
        try {

            // convert JSON string to Map
            Map<String, String> map = mapper.readValue(args, Map.class);

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

//            System.out.println(map);
            returnMap = map;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnMap;
    }

}