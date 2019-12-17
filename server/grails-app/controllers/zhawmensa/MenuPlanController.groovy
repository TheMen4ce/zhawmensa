package zhawmensa

import grails.plugin.springsecurity.annotation.Secured
import zhawmensa.domain.MenuPlanService

@Secured("ROLE_ADMIN")
class MenuPlanController implements ExceptionHandlingController {
	static responseFormats = ['json']

    MenuPlanService menuPlanService

    def findAllByFacilityId() {
        respond menuPlanService.findAllByFacilityId(params.id as long)
    }
}
