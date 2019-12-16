package zhawmensa

import zhawmensa.domain.MenuPlanService

class MenuPlanController implements ExceptionHandlingController {
	static responseFormats = ['json']

    MenuPlanService menuPlanService

    def findAllByFacilityId() {
        respond menuPlanService.findAllByFacilityId(params.id as long)
    }
}
