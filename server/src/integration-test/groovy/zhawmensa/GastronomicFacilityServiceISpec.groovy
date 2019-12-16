package zhawmensa

import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import grails.validation.ValidationException
import spock.lang.Specification
import zhawmensa.domain.GastronomicFacilityService
import zhawmensa.exceptions.ObjectNotFoundException

@Integration
class GastronomicFacilityServiceISpec extends Specification implements ServiceUnitTest<GastronomicFacilityService>{

    void "should not store gastronomic facility with missing value"() {
        given:
        GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 123)

        when:
        service.store(facility)

        then:
        thrown ValidationException.class
    }

    void "should delete gastronomic facility"() {
        given:
        GastronomicFacility facility = service.store(new GastronomicFacility(name: "Test", locationId: 123, provider: Provider.SV))

        when:
        service.deleteById(facility.id)

        then:
        !service.findById(facility.id)
    }

    void "should not delete inexistent facility"() {
        when:
        service.deleteById(999)

        then:
        thrown(ObjectNotFoundException.class)
    }

    void "should store gastronomic facility"() {
        given:
        GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 123, provider: Provider.SV)

        when:
        GastronomicFacility newFacility = service.store(facility)

        then:
        service.findById(newFacility.id)

        cleanup:
        service.deleteById(newFacility.id)
    }


    void "should find all gastronomic facilities"() {
        given:
        GastronomicFacility facility1 = new GastronomicFacility(name: "A", locationId: 123, provider: Provider.SV)
        GastronomicFacility facility2 = new GastronomicFacility(name: "B", locationId: 123, provider: Provider.ZFV)

        when:
        service.store(facility1)
        service.store(facility2)

        then:
        service.findAll().size() == 2

        cleanup:
        service.deleteById(facility1.id)
        service.deleteById(facility2.id)
    }
}
