package zhawmensa

class MenuPlanController implements ExceptionHandlingController {
	static responseFormats = ['json']

    MenuPlanService menuPlanService

    def findAllByFacilityId() {
        respond menuPlanService.findAllByFacilityId(params.id as long)
    }
}
