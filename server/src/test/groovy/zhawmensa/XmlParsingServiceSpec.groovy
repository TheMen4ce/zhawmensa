package zhawmensa

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import zhawmensa.exceptions.BusinessException
import zhawmensa.menuimport.XmlParsingService

class XmlParsingServiceSpec extends Specification implements ServiceUnitTest<XmlParsingService> {

    void "should parse content from valid file"(){
        expect:
        Node node = service.parseXmlFrom("src/test/resources/ZFVXMLResponse.xml")
        node instanceof Node
    }

    void "should throw BusinessException for invalid XML"(){
        when:
        service.parseXmlFrom("src/test/resources/InvalidXML.xml")

        then:
        thrown(BusinessException.class)
    }

    void "should throw BusinessException for invalid and inaccessible uri"(){
        when:
        service.parseXmlFrom("https://zfv.ch/en/menus/rssMenuPlan?menuId=999999")

        then:
        thrown(BusinessException.class)
    }

}
