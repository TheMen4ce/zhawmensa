package zhawmensa

import org.mockito.Mockito
import spock.lang.Specification

class MenuImportZFVSpec extends Specification {
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 666)

    XmlImportService xmlImportService = Mockito.mock(XmlImportService.class)
    MenuImportZFV menuImportZFV

    void "should parse all menus from static ZFV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/ZFVXMLResponse.xml"))
        Mockito.when(xmlImportService.importXmlFrom(Mockito.any())).thenReturn(node)

        when:
        List<Menu> menus = menuImportZFV.importMenus(facility)

        then:
        menus.size() == 18
        menus.last().externalPrice == new BigDecimal("13.80")
        menus.last().internalPrice == new BigDecimal("12.80")
        menus.last().studentPrice == new BigDecimal("10.80")
        menus.last().title == "PH Schnitzel mit"
        menus.last().sideDishes == "Zitronen-Schnitz\nKarotten-Erbsen Gemüse\nund Pommes Frites\nFleisch: Schweiz / Schwein"
    }
}