package zhawmensa.menuimport

import org.xml.sax.SAXParseException
import zhawmensa.exceptions.BusinessException

class XmlParsingService {

    /**
     * Parses an XML from a given source
     * @param uri of the given source
     * @return a groovy Node of the complete XML that can easily be parsed
     */
    Node parseXmlFrom(String uri) {
        try {
            return new XmlParser().parse(uri)
        } catch (IOException ex) {
            ex.printStackTrace()
            throw new BusinessException("Error calling service with uri: ${uri}")
        } catch (SAXParseException ex){
            ex.printStackTrace()
            throw new BusinessException("Got invalid XML from uri: ${uri}")
        }
    }
}
