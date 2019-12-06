package zhawmensa

import org.xml.sax.SAXParseException

class XMLImporter {

    Node importXmlFrom(String uri) {
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
