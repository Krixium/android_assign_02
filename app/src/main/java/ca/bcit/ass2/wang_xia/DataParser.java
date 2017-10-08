package ca.bcit.ass2.wang_xia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

class DataParser {

    static String convertSpaces(String str) {
        String result = "";
        String[] temp;

        temp = str.split(" ");
        if (temp.length == 1) {
            return temp[0];
        }

        result = temp[0];
        for (int i = 1; i < temp.length; i++) {
            result += "%20" + temp[i];
        }

        return result;
    }

    static String GET(URL url) {
        HttpsURLConnection connection = null;
        String result;

        try {
            connection = (HttpsURLConnection) url.openConnection();
            result = parseInputStream(connection.getInputStream());
        } catch (Exception e) {
            result = parseInputStream(connection != null ? connection.getErrorStream() : null);
            e.printStackTrace();
        }

        return result;
    }

    private static String parseInputStream(InputStream inputStream) {
        String result = "";
        String line;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    static String[] parseJSONArrayToCountry(JSONArray jsonArray) {
        String[] result;
            try {
                result = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    result[i] = jsonArray.getJSONObject(i).getString("name");
                }
            } catch (JSONException e) {
                result = new String[0];
                result[0] = "";
                e.printStackTrace();
            }
        return result;
    }

    static String[] parseJSONArrayForDetails(JSONArray jsonArray) throws JSONException {
        String[] result = new String[6];
        JSONObject country = jsonArray.getJSONObject(0);
        result[0] = country.getString("name");
        result[1] = country.getString("capital");
        result[2] = country.getString("region");
        result[3] = country.getString("population");
        result[4] = country.getString("area");
        result[5] = country.getString("flag");
        return result;
    }

    static String[] passeJSONArrayForBorders(JSONArray jsonArray) throws JSONException {
        JSONObject country = jsonArray.getJSONObject(0);
        JSONArray bordersJSON = country.getJSONArray("borders");
        String[] borders = new String[bordersJSON.length()];

        for (int i = 0; i < bordersJSON.length(); i++) {
            borders[i] = bordersJSON.getString(i);
        }

        return borders;
    }
}
