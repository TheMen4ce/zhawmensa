package zhawmensa

import org.mockito.Mockito
import spock.lang.Specification

class MenuImportZFVSpec extends Specification {
    MenuImportZFV menuImportZFV
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 666)
    XMLImporter xml = Mockito.mock(XMLImporter.class)

    def setup() {
        menuImportZFV = new MenuImportZFV(xml)
    }

    void "should parse all menus from static ZFV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/ZFVXMLResponse.xml"))
        Mockito.when(xml.importXmlFrom(Mockito.any())).thenReturn(node)

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