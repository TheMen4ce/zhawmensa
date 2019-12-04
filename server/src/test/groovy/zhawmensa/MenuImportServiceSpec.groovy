package zhawmensa

import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification


class MenuImportServiceSpec extends Specification implements ServiceUnitTest<MenuImportService>, DomainUnitTest<MenuPlan> {

    def setup() {
        MenuImportService service = new MenuImportService()
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 7900)

        expect:
        service.importMenuPlan(facility)
    }
}
