package zhawmensa


class GastronomicFacilityController {

    static responseFormats = ['json']

    def index(){
        List<GastronomicFacility> all = GastronomicFacility.findAll()
        render  GastronomicFacility.findAll()
    }
}
