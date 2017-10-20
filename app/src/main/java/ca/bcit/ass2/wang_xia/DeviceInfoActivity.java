package ca.bcit.ass2.wang_xia;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        TextView manufacturerTextView = (TextView) findViewById(R.id.manufacturerText);
        TextView modelTextView = (TextView) findViewById(R.id.modelText);
        TextView versionTextView = (TextView) findViewById(R.id.versionText);
        TextView versionReleaseTextView = (TextView) findViewById(R.id.versionReleaseText);
        TextView serialNumberTextView = (TextView) findViewById(R.id.serialNumberText);

        manufacturerTextView.setText(String.format(
                getResources().getString(R.string.manufacturerLocale), Build.MANUFACTURER
        ));
        modelTextView.setText(String.format(
                        getResources().getString(R.string.modelLocale), Build.MODEL
        ));
        versionTextView.setText(String.format(
                getResources().getString(R.string.versionLocale), Build.VERSION.SDK_INT
        ));
        versionReleaseTextView.setText(String.format(
                        getResources().getString(R.string.versionReleaseLocale), Build.VERSION.RELEASE
        ));
        serialNumberTextView.setText(String.format(
                        getResources().getString(R.string.serialNumberLocale), Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID)
        ));
    }
}
