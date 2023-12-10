package dot.compta.backend.validators;

public interface CustomValidator<E> {

    default void validate(E e) {
    }

    default void validateExists(int id) {
    }

    default void validateExist(String name) {
    }

    default void validateNotExist(String name) {
    }

    default void validateNotDeleted(int id) {
    }

    default void validateExistsAndNotDeleted(int id) {
        validateExists(id);
        validateNotDeleted(id);
    }
}
