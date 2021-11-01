package com.mealmentor.enterprise.dao;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//uses the API found at this website https://tdeecalculator.org/
@Repository
public class TDEEDAO {
    private final CloseableHttpClient client = HttpClients.createDefault();
    String returnJsonString = "";
    public int post(String heightFt, String heightIn, String weightLbs, String goalWeightLbs, String age, String activity, String goalType, String bodyFat, String waist, String gender) throws URISyntaxException, IOException {

        String heightFtString = heightFt;
        String heightInString = heightIn;
        String weightLbsString = weightLbs;
        String goalWeightString = goalWeightLbs;
        String yearOfAgeString = age;
//        sedentary = "1.2"
//        Lightly active = "1.375"
//        Moderately active = "1.55"
//        Very active = "1.725"
//        Extra active = "1.9"
        String activityValueString = activity;
        //sedentary = "0"
        //Lightly active = "2"
        //Moderately active = "4"
        //Very active = "6"
        //Extra active = "7"
        String workoutsPerWeek = "";
        if(activity.equals("1.2")){
            workoutsPerWeek = "0";
        } else if(activity.equals("1.375")){
            workoutsPerWeek = "2";
        } else if(activity.equals("1.55")){
            workoutsPerWeek = "4";
        } else if(activity.equals("1.725")){
            workoutsPerWeek = "6";
        } else if(activity.equals("1.9")){
            workoutsPerWeek = "7";
        }


        //Aggressive weight loss = "1"
        //Faster weight loss = "2"
        //Weight loss = "3"
        String goalTypeString = goalType;
        //user can type in any value they see fit
        String bodyFatPercentageString = bodyFat;
        //user can type in any value they see fit
        String waistInchesString = waist;
        //only male and female choices are available
        String genderString = gender;
        //"imperial" for american standard
        // "std" for metric standard (this website app is UK based to metric is standard to them)
        String measurementStandard = "imperial";
        //MIFFLIN-ST JEOR FORMULA = "mst"
        //HARRIS-BENEDICT FORMULA = "hb"
        //KATCH-MACARDLE FORMULA = "km"
        //CUNNINGHAM FORMULA = "cunn"
        //AVERAGE = "avrg"
        String basalMetabolicRatePrefix = "km";

        URL url = new URL("https://tdeecalculator.org/wp-admin/admin-ajax.php");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("authority", "tdeecalculator.org");
        http.setRequestProperty("sec-ch-ua", "'Chromium';v='94', 'Google Chrome';v='94', '; Not A Brand ';v=' 99 '");
        http.setRequestProperty("accept", "*/*");
        http.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.setRequestProperty("x-requested-with", "XMLHttpRequest");
        http.setRequestProperty("sec-ch-ua-mobile", "?0");
        http.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
        http.setRequestProperty("sec-ch-ua-platform", "'macOS'");
        http.setRequestProperty("origin", "https://tdeecalculator.org");
        http.setRequestProperty("sec-fetch-site", "same-origin");
        http.setRequestProperty("sec-fetch-mode", "cors");
        http.setRequestProperty("sec-fetch-dest", "empty");
        http.setRequestProperty("referer", "https://tdeecalculator.org/");
        http.setRequestProperty("accept-language", "en-US,en;q=0.9");
        http.setRequestProperty("cookie", "_ga=GA1.2.2026410794.1631562231");

        String data = "action=wmp_diet_form_processing&mwp_diet_nonce=82c47b6c3e&mwp_diet_data=mwp_height_ft%3D" + heightFtString + "%26mwp_height_in%3D" + heightInString + "%26mwp_weight_lbs%3D" + weightLbsString + "%26mwp_goal_weight_lbs%3D" + goalWeightString + "%26mwp_age_year%3D" + yearOfAgeString + "%26mwp_activity%3D" + activityValueString + "%26mwp_workouts_per_week%3D" + workoutsPerWeek + "%26mwp_goal_type%3D" + goalTypeString + "%26mwp_rest_tdee%3D-10%26mwp_workout_tdee%3D10%26mwp_bodyfat%3D" + bodyFatPercentageString + "%26mwp_waist_in%3D" + waistInchesString + "%26mwp_gender%3D" + genderString + "%26mwp_metric%3D" + measurementStandard + "%26mwp_bmr%3D" + basalMetabolicRatePrefix + "%26mwp_bmr_sm%3D0%26mwp_bmr_custom%3D0%26mwp_tdee%3Dcalc%26mwp_tdee_sm%3D0%26mwp_tdee_custom%3D0%26mwp-diet-nonce%3D82c47b6c3e%26_wp_http_referer%3D%252F";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

        //here we parse the response body stringified-JSON line by line into a buffered reader and then return as a string
        InputStream inputStream;
        if (200 <= http.getResponseCode() && http.getResponseCode() <= 299) {
            inputStream = http.getInputStream();
        } else {
            inputStream = http.getErrorStream();
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        inputStream));
        StringBuilder response = new StringBuilder();
        String currentLine;
        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);
        in.close();
        String responseBody = response.toString();
        returnJsonString = responseBody;
        System.out.println(responseBody);
        http.disconnect();

//        String returnJsonStringFormatted = "";
//
//        String backslash = "\\\\";
//        char backslashChar = backslash.charAt(0);
//        String backslashString = String.valueOf(backslashChar);
//        System.out.println(backslashString);
//
//        char singleQuote = '"';
//        String singleQuoteString = String.valueOf(singleQuote);
//        System.out.println(singleQuoteString);
//
//       for(int i = 0;i<returnJsonString.length()-1;i++){
//            if(String.valueOf(returnJsonString.charAt(i)).equals(singleQuoteString)){
//                returnJsonStringFormatted = returnJsonStringFormatted + backslashString;
//            }
//            returnJsonStringFormatted = returnJsonStringFormatted + returnJsonString.charAt(i);
//       }
        String tDEE = returnJsonString.substring(54, 58);
        System.out.println("calories burned per day factoring in exercise: " + tDEE);
        String minimumCalories = returnJsonString.substring(109, 113);
        System.out.println("minimun calorie intake before muscle loss: " + minimumCalories);

        int calorieThreshold = (((Integer.parseInt(tDEE) - Integer.parseInt(minimumCalories)) / 4) * 3) + Integer.parseInt(minimumCalories);
        System.out.println("tdee minus min calorie intake divided by four. then multiplied by 3. Once this amount is added to the minimum calorie amount we have: " + calorieThreshold);
        //This is a test

        return calorieThreshold;
    }
}
