package org.nando.where;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;


public class MainActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

    private TextView longTextView;
    private TextView latTextView;
    private TextView postcodeTextView;
    private TextView altitudeTextView;
    private TextView localityTextView;
    private TextView errMessage;
    private TextView address1TextView;
    private TextView allAddressesTextView;
    private TextView featureNameTextView;

    private LocationClient locationClient;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longTextView = (TextView) findViewById(R.id.longTextView);
        latTextView = (TextView) findViewById(R.id.latTextView);
        postcodeTextView = (TextView) findViewById(R.id.postCodeField);
        altitudeTextView = (TextView) findViewById(R.id.altitudeField);
        localityTextView = (TextView) findViewById(R.id.localityField);
        address1TextView = (TextView) findViewById(R.id.addres1TextView);
        allAddressesTextView = (TextView) findViewById(R.id.allAddressesTextView);
        featureNameTextView = (TextView) findViewById(R.id.featureNameTextView);
        errMessage = (TextView) findViewById(R.id.errMessage);

    }

    protected void onResume() {
        super.onResume();
        setupLocationClientIfNeeded();
        locationClient.connect();
    }

    protected void onPause() {
        super.onPause();
        locationClient.disconnect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setupLocationClientIfNeeded() {
        if(locationClient == null) {
            locationClient = new LocationClient(getApplicationContext(),this,this);
        }
    }

    private void clearTextFields() {
        latTextView.setText("");
        longTextView.setText("");
        postcodeTextView.setText("");
        localityTextView.setText("");
        altitudeTextView.setText("");
        errMessage.setText("");
        address1TextView.setText("");
        allAddressesTextView.setText("");
        featureNameTextView.setText("");
    }

    public void pressLocateButton(View view) {
        location = locationClient.getLastLocation();
        LocationTask task = new LocationTask(this);
        clearTextFields();
        task.execute(location);
    }



    public void setResults(LocationPojo pojo) {
        if(pojo.errorMessage != null) {
            errMessage.setText(pojo.errorMessage);
        }
        else {
            latTextView.setText(pojo.latitude);
            longTextView.setText(pojo.longtitude);
            postcodeTextView.setText(pojo.postcode);
            localityTextView.setText(pojo.locality);
            altitudeTextView.setText(pojo.altitude);
            featureNameTextView.setText(pojo.featureName);
            address1TextView.setText(pojo.address1);
            allAddressesTextView.setText(pojo.allAddresses);
        }
    }


    //location implementation methods

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
