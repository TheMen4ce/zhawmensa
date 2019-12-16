package zhawmensa

import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import org.mockito.Mockito
import spock.lang.Specification
import zhawmensa.domain.GastronomicFacilityService
import zhawmensa.domain.MenuPlanService
import zhawmensa.menuimport.ImportFacadeService
import zhawmensa.menuimport.MenuImportSVService
import zhawmensa.menuimport.MenuImportService
import zhawmensa.menuimport.MenuImportZFVService

@Integration
class MenuImportServiceISpec extends Specification implements ServiceUnitTest<MenuImportService> {
    MenuImportSVService mockMenuImportSVService = Mockito.mock(MenuImportSVService.class)
    MenuImportZFVService mockMenuImportZFVService = Mockito.mock(MenuImportZFVService.class)
    GastronomicFacilityService mockGastronomicFacilityService = Mockito.mock(GastronomicFacilityService.class)
    MenuPlanService mockMenuPlanService = Mockito.mock(MenuPlanService.class)
    ImportFacadeService mockImportFacadeService = new ImportFacadeService()

    void setup() {
        service.gastronomicFacilityService = mockGastronomicFacilityService
        service.menuPlanService = mockMenuPlanService
        service.importFacadeService = mockImportFacadeService
        service.importFacadeService.menuImportSVService = mockMenuImportSVService
        service.importFacadeService.menuImportZFVService = mockMenuImportZFVService
    }

    void "should call both menu import service and return with correct menu count"() {
        given:
        GastronomicFacility facilitySV = new GastronomicFacility(provider: Provider.SV)
        GastronomicFacility facilityZFV = new GastronomicFacility(provider: Provider.ZFV)
        List<GastronomicFacility> facilities = [facilitySV, facilityZFV]

        Mockito.when(mockGastronomicFacilityService.findAll()).thenReturn(facilities)
        Mockito.when(mockMenuImportSVService.importMenus(facilitySV)).thenReturn([new Menu()])
        Mockito.when(mockMenuImportZFVService.importMenus(facilityZFV)).thenReturn([new Menu()])

        when:
        int importedMenus = service.importMenuPlans()

        then:
        importedMenus == 2
    }

    void "expect IllegalState when no provider given"() {
        given:
        List<GastronomicFacility> facilities = [new GastronomicFacility()]
        Mockito.when(mockGastronomicFacilityService.findAll()).thenReturn(facilities)

        when:
        service.importMenuPlans()

        then:
        thrown(IllegalStateException.class)
    }
}
