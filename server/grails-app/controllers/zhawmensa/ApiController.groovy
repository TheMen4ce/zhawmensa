package zhawmensa

import grails.plugin.springsecurity.annotation.Secured
import zhawmensa.domain.GastronomicFacilityService

/**
 * Public API for retrieving menus without any authentication
 * This is just a show case. The response of this api behaves like
 * https://api.apps.engineering.zhaw.ch/v1/catering/menuplans/years/2019/weeks/50
 */
class ApiController {
	static responseFormats = ['json']

    GastronomicFacilityService gastronomicFacilityService

    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    def index() {
        [gastronomicFacilities: gastronomicFacilityService.findAll(), year: params.year as int, week: params.week as int]
    }
}
