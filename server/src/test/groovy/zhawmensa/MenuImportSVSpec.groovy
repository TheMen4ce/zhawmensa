package zhawmensa

import org.mockito.Mockito
import spock.lang.Specification

class MenuImportSVSpec extends Specification {
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 1234)

    XmlImportService xmlImportService = Mockito.mock(XmlImportService.class)
    MenuImportSV menuImportSV

    void "should parse all menus from static SV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/SVXMLResponse.xml"))
        Mockito.when(xmlImportService.importXmlFrom(Mockito.any())).thenReturn(node)

        when:
        List<Menu> menus = menuImportSV.importMenus(facility)

        then:
        menus.size() == 19
        menus.last().externalPrice == new BigDecimal("2.50")
        menus.last().internalPrice == new BigDecimal("2.30")
        menus.last().studentPrice == new BigDecimal("2.30")
        menus.last().title == "Your Choice"
        menus.last().sideDishes == "Stellen Sie sich Ihr Menü nach Lust und Laune selber zusammen\nPreis pro 100g"
    }

    void "should throw business exception on SV service error"(){
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/SVXMLErrorResponse.xml"))
        Mockito.when(xmlImportService.importXmlFrom(Mockito.any())).thenReturn(node)

        when:
        menuImportSV.importMenus(facility)

        then:
        thrown(BusinessException.class)
    }
}