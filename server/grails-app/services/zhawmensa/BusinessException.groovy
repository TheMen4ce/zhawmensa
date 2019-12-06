package zhawmensa

/**
 * Throw for exceptions due to expected errors in business logic
 */
class BusinessException extends RuntimeException {

    BusinessException(String message){
        super(message)
    }
}
