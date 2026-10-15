package domain.exception;

/**
 * Exception yang dilempar saat entitas tidak ditemukan berdasarkan ID.
 */
public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
