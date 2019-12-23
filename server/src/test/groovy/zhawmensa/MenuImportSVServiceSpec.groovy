package zhawmensa

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.LoggingEvent
import ch.qos.logback.core.Appender
import grails.testing.services.ServiceUnitTest
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import spock.lang.Specification
import zhawmensa.menuimport.MenuImportSVService
import zhawmensa.menuimport.XmlParsingService



class MenuImportSVServiceSpec extends Specification implements ServiceUnitTest<MenuImportSVService> {
    GastronomicFacility facility = new GastronomicFacility(name: "Test", locationId: 1234)
    XmlParsingService mockImportService = Mockito.mock(XmlParsingService.class)

    void setup() {
        service.xmlParsingService = mockImportService
    }

    void "should parse all menus from static SV service response"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/SVXMLResponse.xml"))
        Mockito.when(mockImportService.parseXmlFrom(Mockito.any())).thenReturn(node)

        when:
        List<Menu> menus = service.importMenus(facility)

        then:
        menus.size() == 19
        menus.last().externalPrice == new BigDecimal("2.50")
        menus.last().internalPrice == new BigDecimal("2.30")
        menus.last().studentPrice == new BigDecimal("2.30")
        menus.last().title == "Your Choice"
        menus.last().sideDishes == "Stellen Sie sich Ihr Menü nach Lust und Laune selber zusammen\nPreis pro 100g"
    }

    void "should log error on SV service error"() {
        given:
        Node node = new XmlParser().parse(new File("src/test/resources/SVXMLErrorResponse.xml"))

        when:
        Mockito.when(mockImportService.parseXmlFrom(Mockito.any())).thenReturn(node)
        Appender mockedAppender = Mockito.mock(Appender)
        Logger logger = LoggerFactory.getLogger("zhawmensa.menuimport.MenuImportSVService")
        logger.addAppender(mockedAppender)

        service.importMenus(facility)

        ArgumentCaptor<Appender> argumentCaptor = ArgumentCaptor.forClass(Appender)
        Mockito.verify(mockedAppender,
                Mockito.times(1)).doAppend(argumentCaptor.capture())
        logger.detachAppender(mockedAppender)

        then:
        argumentCaptor.getAllValues().size() == 1
        List<LoggingEvent> loggingEvents = argumentCaptor.getAllValues() as List<LoggingEvent>
        loggingEvents[0].getLevel() == Level.ERROR
    }
}
