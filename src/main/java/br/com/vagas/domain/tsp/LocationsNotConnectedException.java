package br.com.vagas.domain.tsp;

public class LocationsNotConnectedException extends RuntimeException {

   
    private static final long serialVersionUID = 1L;

    public LocationsNotConnectedException() {
    }

    public LocationsNotConnectedException(String message) {
        super(message);
    }

    public LocationsNotConnectedException(Throwable cause) {
        super(cause);
    }

    public LocationsNotConnectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationsNotConnectedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
