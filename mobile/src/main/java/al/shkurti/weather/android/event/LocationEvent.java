package al.shkurti.weather.android.event;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class LocationEvent {

    public double latitude;
    public double longitude;

    public LocationEvent(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
