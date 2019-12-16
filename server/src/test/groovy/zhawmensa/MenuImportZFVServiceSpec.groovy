package zhawmensa

import grails.testing.services.ServiceUnitTest
import org.mockito.Mockito
import spock.lang.Specification
import zhawmensa.menuimport.MenuImportZFVService
import zhawmensa.menuimport.XmlImportService

class MenuImportZFVServiceSpec extends Specification implements ServiceUnitTest<MenuImportZFVService>  {
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 666)

    XmlImportService mockImportService = Mockito.mock(XmlImportService.class)

    void setup() {
        service.xmlImportService = mockImportService
    }

    void "should parse all menus from static ZFV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/ZFVXMLResponse.xml"))
        Mockito.when(mockImportService.importXmlFrom(Mockito.any())).thenReturn(node)

        when:
        List<Menu> menus = service.importMenus(facility)

        then:
        menus.size() == 18
        menus.last().externalPrice == new BigDecimal("13.80")
        menus.last().internalPrice == new BigDecimal("12.80")
        menus.last().studentPrice == new BigDecimal("10.80")
        menus.last().title == "PH Schnitzel mit"
        menus.last().sideDishes == "Zitronen-Schnitz\nKarotten-Erbsen Gemüse\nund Pommes Frites\nFleisch: Schweiz / Schwein"
    }
}