package com.vega.app.geo.manager;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.vega.BuildConfig;
import com.vega.app.geo.entity.GeoEntity;

/**
 * Component created on 22/12/2015.
 *
 * @author dmartin
 */
public class GeoLocationManager implements LocationListener {

    private static final String TAG = "GeoLocationManager";

    private Context context;

    private LocationManager locationManager;

    private String bestProvider;

    private GeoEntity geoEntity;

    public GeoLocationManager(Context context) {
        this.context = context;
    }

    public void initGeoLocationManager() {
        initLocationManager();
        recoverBestProvider();
        startLocationManager();
    }

    public GeoEntity recoverCurrentLocation() {

        if (locationManager == null || bestProvider == null) {
            initGeoLocationManager();
        }

        if (bestProvider != null) {
            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location != null) {
                onLocationChanged(location);
            }
        }

        return geoEntity;
    }

    public void stopLocationManager() {
        locationManager.removeUpdates(this);
    }

    public GeoEntity getGeoEntity() {

        if (geoEntity != null) {
            return geoEntity;
        }

        return recoverCurrentLocation();

    }

    private void initLocationManager() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private void recoverBestProvider() {
        bestProvider = locationManager.getBestProvider(getCriteria(), true);
    }

    private void startLocationManager() {
        if (bestProvider != null) {
            locationManager.requestLocationUpdates(bestProvider, BuildConfig.TIME_BW_GEO_UPDATES,
                    BuildConfig.DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        return criteria;
    }

    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        geoEntity = new GeoEntity();
        geoEntity.setLatitude(location.getLatitude());
        geoEntity.setLongitude(location.getLongitude());

        String newLocationInfo = "Location changed. [Latitude: " + geoEntity.getLatitude()
                + " | " + "Longitude: " + geoEntity.getLongitude() + "]";

        Log.i("onLocationChanged", newLocationInfo);
    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *                 <p/>
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *                 <p/>
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "status changed. " + provider + " " + status + " " + extras.toString());
    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "Provider enabled. " + provider);
    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "Provider disabled. " + provider);
        geoEntity = null;
    }

}
