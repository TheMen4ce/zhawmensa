package zhawmensa.exceptions

/**
 * Thrown if a domain object wasn't found in the DB. Should render a 404
 */
class ObjectNotFoundException extends RuntimeException {

    ObjectNotFoundException(String message){
        super(message)
    }
}
