package com.mealmentor.enterprise.dao;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
@Repository
public class SpoonacularDAO {
    private final CloseableHttpClient client = HttpClients.createDefault();



    public String get(String foodName, int mealCalorieMax) {
        String mealCalorieMaxString = Integer.toString(mealCalorieMax);
        HttpGet request = new HttpGet("https://api.spoonacular.com/recipes/complexSearch?apiKey=0cea569c323f45d090b3335b48f39341&query=" + foodName + "&addRecipeInformation=true&maxCalories=" + mealCalorieMaxString + "&number=1");

        String returnJsonString = "";

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            returnJsonString = EntityUtils.toString(entity);
            System.out.println(returnJsonString);
            return returnJsonString;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnJsonString;
    }

}
