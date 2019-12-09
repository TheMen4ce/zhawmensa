package zhawmensa


class GastronomicFacilityController {

    GastronomicFacilityService gastronomicFacilityService

    static responseFormats = ['json']

    def index() {
        respond gastronomicFacilityService.findAll()
    }

    def save(GastronomicFacility facility) {
        GastronomicFacility createdFacility = gastronomicFacilityService.store(facility)
        respond createdFacility
    }

    def update(GastronomicFacility facility) {
        GastronomicFacility updatedFacility = gastronomicFacilityService.store(facility)
        respond updatedFacility
    }

    def delete() {
        if (gastronomicFacilityService.deleteById(params.id as long)) {
            render status: 200
        } else {
            render status: 404
        }
    }
}
