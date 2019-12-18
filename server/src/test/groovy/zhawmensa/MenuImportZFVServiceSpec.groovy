package zhawmensa

import grails.testing.services.ServiceUnitTest
import org.mockito.Mockito
import spock.lang.Specification
import zhawmensa.menuimport.MenuImportZFVService
import zhawmensa.menuimport.XmlParsingService

class MenuImportZFVServiceSpec extends Specification implements ServiceUnitTest<MenuImportZFVService>  {
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 666)

    XmlParsingService mockImportService = Mockito.mock(XmlParsingService.class)

    void setup() {
        service.xmlParsingService = mockImportService
    }

    void "should parse all menus from static ZFV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/ZFVXMLResponse.xml"))
        Mockito.when(mockImportService.parseXmlFrom(Mockito.any())).thenReturn(node)

        when:
        List<Menu> menus = service.importMenus(facility)

        then:
        menus.size() == 18
        menus.last().externalPrice == new BigDecimal("13.80")
        menus.last().internalPrice == new BigDecimal("12.80")
        menus.last().studentPrice == new BigDecimal("10.80")
        menus.last().label == "voll anders"
        menus.last().title == "PH Schnitzel mit"
        menus.last().sideDishes == "Zitronen-Schnitz\nKarotten-Erbsen Gemüse\nund Pommes Frites\nFleisch: Schweiz / Schwein"
    }

    void "should safely ignore news"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/ZFVXMLNewsResponse.xml"))
        Mockito.when(mockImportService.parseXmlFrom(Mockito.any())).thenReturn(node)

        when:
        List<Menu> menus = service.importMenus(facility)

        then:
        menus.size() == 0
    }
}