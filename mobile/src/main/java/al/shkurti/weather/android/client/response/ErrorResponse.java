package al.shkurti.weather.android.client.response;

/**
 * Created by Armando Shkurti on 2015-03-29.
 */
public class ErrorResponse {

    public String errorResponse;

    public ErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getErrorResponse() {
        return errorResponse;
    }
}
