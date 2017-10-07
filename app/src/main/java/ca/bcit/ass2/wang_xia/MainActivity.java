package ca.bcit.ass2.wang_xia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> regionsList = new ArrayList<>();
        Collections.addAll(regionsList, getResources().getStringArray(R.array.regionsArray));

        final ListView listView = (ListView) findViewById(R.id.regionsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, regionsList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                urlString = String.format(getResources().getString(R.string.regionApiLocale), listView.getItemAtPosition(i));
                try {
                    new ContinentAsyncTask().execute(new URL(urlString));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // TODO: Refactor to separate class (Not Important)
    private static String[] parseJSONArrayToCountry(JSONArray jsonArray) {
        String[] result;
            try {
                result = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    result[i] = jsonArray.getJSONObject(i).getString("name");
                }
            } catch (JSONException e) {
                result = new String[1];
                result[0] = "Error";
                e.printStackTrace();
            }

        return result;
    }

    // TODO: Add progress bar for loading (Must)
    private class ContinentAsyncTask extends AsyncTask<URL, Void, String> {

        HttpsURLConnection connection;
        InputStream inputStream;
        BufferedReader bufferedReader;

        // TODO: Refactor to reduce code repeating (Must)
        @Override
        protected String doInBackground(URL... urls) {
            String line;
            String result = "";

            try {
                connection = (HttpsURLConnection) urls[0].openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                inputStream = connection.getErrorStream();
                e.printStackTrace();
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
            } catch (IOException e) {
                result = "Couldn't read anything";
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            String[] countries = new String[0];
            try {
                countries = MainActivity.parseJSONArrayToCountry(new JSONArray(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(MainActivity.this, CountriesActivity.class);
            intent.putExtra(getResources().getString(R.string.countriesExtra), countries);
            startActivity(intent);
        }
    }
}
