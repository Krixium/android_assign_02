package ca.bcit.ass2.wang_xia;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.widget.TextView;
import android.widget.Toast;


public class InfoRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_request);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView manufacturer = (TextView)findViewById(R.id.textViewManufacturer);
        manufacturer.setText(String.format("Manufacturer: %s", Build.MANUFACTURER));

        TextView model = (TextView) findViewById(R.id.textViewModel);
        model.setText(String.format("Model: %s", Build.MODEL));

        TextView sdk = (TextView) findViewById(R.id.textViewSdkLevel);
        //note: will throw exception if let program implicitly convert to string via setText
        //something to do with 5.2.2 int format versus 5.2 or 522
        sdk.setText(String.format("Version: %d", Build.VERSION.SDK_INT));

        TextView releaseVersion = (TextView) findViewById(R.id.textViewReleaseVersion);
        releaseVersion.setText(String.format("Version Release: %s", Build.VERSION.RELEASE));

        TextView serialNumber = (TextView) findViewById(R.id.textViewSerialNumber);
        serialNumber.setText(
            String.format(
                "Serial number: %s",
                Settings.Secure.getString(
                    this.getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID
                )
            )
        );
    }
}
