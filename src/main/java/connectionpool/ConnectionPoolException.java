package connectionpool;

/**
 * Created by homie on 06.09.16.
 */
public class ConnectionPoolException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }
}
