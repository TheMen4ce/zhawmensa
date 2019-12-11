package zhawmensa

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class XmlImportServiceSpec extends Specification implements ServiceUnitTest<XmlImportService> {

    void "should parse content from valid file"(){
        expect:
        Node node = service.importXmlFrom("src/test/resources/ZFVXMLResponse.xml")
        node instanceof Node
    }

    void "should throw BusinessException for invalid XML"(){
        when:
        service.importXmlFrom("src/test/resources/InvalidXML.xml")

        then:
        thrown(BusinessException.class)
    }

    void "should throw BusinessException for invalid and inaccessible uri"(){
        when:
        service.importXmlFrom("https://zfv.ch/en/menus/rssMenuPlan?menuId=999999")

        then:
        thrown(BusinessException.class)
    }

}
