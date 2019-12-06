package zhawmensa

/**
 * General exception handling controller that should be implemented by all controllers
 */
trait ExceptionHandlingController {

    def handleBusinessException(BusinessException ex) {
        log.info("Nothing serious. Got a business exception ${ex.message}")

        render(status: 400, text: ex.message)
    }

    def handleAllOtherExceptions(Exception ex) {
        log.error("App crashed! ${ex.message}")
        ex.printStackTrace()

        render(status: 500, text: "Oops! Something went seriously wrong!")
    }

}
