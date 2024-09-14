package bll.validators;
/**
 * Defines a validation contract for objects of type T.
 * Implementations of this interface are responsible for validating objects according to specific rules.
 * If the object does not meet the validation criteria, the implementation should indicate this
 * through an exception or by other means as per the design of the specific validator.
 *
 * @param <T> the type of objects this validator can handle
 */
public interface Validator<T> {
    /**
     * Validates an object of type T.
     * <p>
     * Implementing classes should define specific validation logic to ensure that the object
     * complies with the necessary criteria. If the object fails validation, this method should
     * throw an appropriate runtime exception or handle the error according to the application's needs.
     *
     * @param t the object to be validated
     * @throws RuntimeException if the object does not meet the validation criteria
     */
    public void validate(T t);
}
