package zhawmensa.security

import grails.plugin.springsecurity.annotation.Secured
import zhawmensa.ExceptionHandlingController

@Secured("ROLE_ADMIN")
class UserController implements ExceptionHandlingController {

    UserService userService

    def changeUsername() {
        def json = request.JSON
        userService.updateUsername(json.oldUsername as String, json.newUsername as String)
        render status: 204
    }
}
