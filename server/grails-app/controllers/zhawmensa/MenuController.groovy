package zhawmensa

class MenuController implements ExceptionHandlingController {
	static responseFormats = ['json']

    MenuService menuService

    def findAllByMenuPlanId() {
        respond menuService.findAllByMenuPlanId(params.id as long)
    }
}
