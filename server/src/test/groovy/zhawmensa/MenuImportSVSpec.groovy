package zhawmensa

import org.mockito.Mockito
import spock.lang.Specification

class MenuImportSVSpec extends Specification {
    MenuImportSV menuImportSV
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 1234)
    XMLImporter xml = Mockito.mock(XMLImporter.class)

    def setup() {
        menuImportSV = new MenuImportSV(xml)
    }

    void "should parse all menus from static SV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/SVXMLResponse.xml"))
        Mockito.when(xml.importXmlFrom(Mockito.any())).thenReturn(node)

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
}