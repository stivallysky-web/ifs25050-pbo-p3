package domain.exception;

/**
 * Base checked exception untuk semua kegagalan domain model.
 */
public class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }
}
