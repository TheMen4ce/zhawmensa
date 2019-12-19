package zhawmensa

import grails.plugin.springsecurity.annotation.Secured
import zhawmensa.domain.MenuPlanService

@Secured("ROLE_ADMIN")
class MenuPlanController implements ExceptionHandlingController {
	static responseFormats = ['json']

    MenuPlanService menuPlanService

    def show() {
        respond menuPlanService.findAllByFacilityId(params.id as long)
    }

    def delete() {
        menuPlanService.deleteById(params.id as long)
        render status: 204
    }
}
