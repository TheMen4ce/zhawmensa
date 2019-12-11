package zhawmensa


class GastronomicFacilityController implements ExceptionHandlingController {

    GastronomicFacilityService gastronomicFacilityService

    static responseFormats = ['json']

    def index() {
        respond gastronomicFacilityService.findAll()
    }

    def save(GastronomicFacility facility) {
        respond gastronomicFacilityService.store(facility)
    }

    def update(GastronomicFacility facility) {
        respond gastronomicFacilityService.store(facility)
    }

    def delete() {
        if (gastronomicFacilityService.deleteById(params.id as long)) {
            render status: 200
        } else {
            render status: 404
        }
    }
}
