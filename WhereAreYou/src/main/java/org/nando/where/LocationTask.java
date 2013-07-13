package org.nando.where;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import java.util.List;
import java.util.Locale;

/**
 * Created by fernandoMac on 13/07/13.
 */
public class LocationTask extends AsyncTask<Location,Void,LocationPojo> {

    private MainActivity myActivity;

    public LocationTask(MainActivity activity) {
        this.myActivity = activity;
    }

    @Override
    protected LocationPojo doInBackground(Location... locations) {
        LocationPojo pojo = new LocationPojo();
        Geocoder geocoder = new Geocoder(myActivity, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(locations[0].getLatitude(),locations[0].getLongitude(),1);

        } catch(Exception e) {
            pojo.errorMessage = e.getMessage();
        }
        if(addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            pojo.postcode = address.getPostalCode();
            pojo.altitude = locations[0].getAltitude() + "";
            pojo.locality = address.getLocality();
            pojo.latitude = address.getLatitude() +"";
            pojo.longtitude = address.getLongitude() +"";
            pojo.address1 = address.getAddressLine(1);
            pojo.featureName = address.getFeatureName();
            StringBuffer sbuff = new StringBuffer();
            for(int i=1; i <= address.getMaxAddressLineIndex(); i++) {
                sbuff.append(address.getAddressLine(i) + "\n");
            }
            pojo.allAddresses = sbuff.toString();
        }
        else {
            pojo.errorMessage = "no address found!";
        }


        return pojo;
    }

    protected void onPostExecute(LocationPojo pojo) {
        myActivity.setResults(pojo);
    }


}
