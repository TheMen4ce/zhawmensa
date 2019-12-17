package zhawmensa

import grails.plugin.springsecurity.annotation.Secured
import zhawmensa.domain.GastronomicFacilityService
import zhawmensa.exceptions.ObjectOutdatedException

@Secured("ROLE_ADMIN")
class GastronomicFacilityController implements ExceptionHandlingController {

    GastronomicFacilityService gastronomicFacilityService

    static responseFormats = ['json']

    def index() {
        respond gastronomicFacilityService.findAll()
    }

    def save(GastronomicFacility facility) {
        respond gastronomicFacilityService.store(facility)
    }

    def update() {
        GastronomicFacility facility = gastronomicFacilityService.findById(params.id as long)
        long clientVersion = request.JSON.version
        bindData(facility, request.JSON)
        if (facility.version > clientVersion) {
            throw new ObjectOutdatedException("facility")
        }

        respond gastronomicFacilityService.store(facility)
    }

    def delete() {
        gastronomicFacilityService.deleteById(params.id as long)
        render status: 204
    }
}
