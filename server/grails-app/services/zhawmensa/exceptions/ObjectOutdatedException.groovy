package zhawmensa.exceptions

/**
 * Thrown if a domain object is about to be stored with an outdated version. That means it was probably changed by another user.
 */
class ObjectOutdatedException extends RuntimeException {

    ObjectOutdatedException(String domain){
        super("The ${domain} has changed in the meantime, probably due to another user. Please reload and try again!")
    }
}
