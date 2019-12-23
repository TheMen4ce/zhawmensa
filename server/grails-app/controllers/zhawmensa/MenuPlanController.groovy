package zhawmensa

import grails.plugin.springsecurity.annotation.Secured
import zhawmensa.domain.MenuPlanService
import zhawmensa.domain.MenuService

@Secured("ROLE_ADMIN")
class MenuPlanController implements ExceptionHandlingController {
	static responseFormats = ['json']

    MenuPlanService menuPlanService
    MenuService menuService

    def findAllMenus() {
        respond menuService.findAllByMenuPlanId(params.id as long)
    }

    def delete() {
        menuPlanService.deleteById(params.id as long)
        render status: 204
    }
}
