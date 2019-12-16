package zhawmensa.exceptions

/**
 * Thrown for exceptions due to expected errors in business logic
 */
class BusinessException extends RuntimeException {

    BusinessException(String message){
        super(message)
    }
}
