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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CountiresActivity extends AppCompatActivity {

    ListView listView;

    // TODO: Make easier to read (Benny)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countires);

        String[] countries = getIntent().getExtras().getStringArray(getResources().getString(R.string.countriesExtra));
        listView = (ListView) findViewById(R.id.countriesListView);

        assert countries != null;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, countries);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    new CountryDetailAsyncTask().execute(new URL(String.format(getResources().getString(R.string.countryApiLocale), listView.getItemAtPosition(i).toString())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // TODO: Refactor to separate class (Not Important)
    private static String[] parseJSONArrayForDetails(JSONArray jsonArray) throws JSONException {
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

    // TODO: Refactor to separate class (Not Important)
    private static String[] passeJSONArrayForBorders(JSONArray jsonArray) throws JSONException {
        JSONObject country = jsonArray.getJSONObject(0);
        JSONArray bordersJSON = country.getJSONArray("borders");
        String[] borders = new String[bordersJSON.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            borders[i] = bordersJSON.getString(i);
        }

        return borders;
    }

    // TODO: Add progress bar for loading (Must)
    private class CountryDetailAsyncTask extends AsyncTask<URL, Void, String> {

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
            String[] countryDetails = new String[0];
            String[] countryBorders = new String[0];
            try {
                countryDetails = CountiresActivity.parseJSONArrayForDetails(new JSONArray(result));
                countryBorders = CountiresActivity.passeJSONArrayForBorders(new JSONArray(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(CountiresActivity.this, CountryDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.countryDetailExtra), countryDetails);
            intent.putExtra(getResources().getString(R.string.countryBordersExtra), countryBorders);
            startActivity(intent);
        }
    }
}
