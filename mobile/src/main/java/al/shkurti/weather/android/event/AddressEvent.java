package al.shkurti.weather.android.event;

/**
 * Created by Armando Shkurti on 2015-03-30.
 */
public class AddressEvent {

    public String address;

    public AddressEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
