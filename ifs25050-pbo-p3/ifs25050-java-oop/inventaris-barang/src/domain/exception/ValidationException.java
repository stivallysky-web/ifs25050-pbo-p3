package domain.exception;

/**
 * Exception yang dilempar saat aturan validasi data gagal dipenuhi.
 */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}
