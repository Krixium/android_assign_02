package ca.bcit.ass2.wang_xia;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryDetailsActivity extends AppCompatActivity {

    ImageView flagImage;

    // TODO: Visualize Data (Must)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Bundle extras;
        String[] countryDetails;
        String[] countryBorders;
        String borders;
        TextView countryNameText;
        TextView countryCapitalText;
        TextView countryRegionText;
        TextView countryPopulationText;
        TextView countryAreaText;
        TextView countryBorderText;

        extras = getIntent().getExtras();
        countryDetails = extras.getStringArray(getResources().getString(R.string.countryDetailExtra));
        countryBorders = extras.getStringArray(getResources().getString(R.string.countryBordersExtra));

        countryNameText = (TextView) findViewById(R.id.countryNameText);
        countryCapitalText = (TextView) findViewById(R.id.countryCapitalText);
        countryRegionText = (TextView) findViewById(R.id.countryRegionText);
        countryPopulationText = (TextView) findViewById(R.id.countryPopulationText);
        countryAreaText = (TextView) findViewById(R.id.countryAreaText);
        flagImage = (ImageView) findViewById(R.id.countryFlagImage);
        countryBorderText = (TextView) findViewById(R.id.countryBordersText);

        if (countryDetails != null) {
            countryNameText.setText(String.format(
                getResources().getString(R.string.countryNameLocale), countryDetails[0]
            ));
            countryCapitalText.setText(String.format(
                getResources().getString(R.string.countryCapitalLocale), countryDetails[1]
            ));
            countryRegionText.setText(String.format(
                getResources().getString(R.string.countryRegionLocale), countryDetails[2]
            ));
            countryPopulationText.setText(String.format(
                getResources().getString(R.string.countryPopulationLocale), countryDetails[3]
            ));
            countryAreaText.setText(String.format(
                getResources().getString(R.string.countryAreaLocale), countryDetails[4]
            ));
            new ImageDownloaderAsyncTask().execute(countryDetails[5]);
        }

        if (countryBorders != null) {
            borders = getResources().getString(R.string.countryBorders);

            for (int i = 0; i < countryBorders.length - 1; i++) {
                borders += " " + countryBorders[i] + ",";
            }
            if (countryBorders.length != 0) {
                borders += " " + countryBorders[countryBorders.length - 1];
            }
            countryBorderText.setText(borders);
        }
    }

    private class ImageDownloaderAsyncTask extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... urls) {
            return DataParser.GETDrawable(urls[0]);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                flagImage.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null);
                flagImage.setImageDrawable(result);
            }
        }
    }
}
