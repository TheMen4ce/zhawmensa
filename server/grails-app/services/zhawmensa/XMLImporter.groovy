package zhawmensa

class XMLImporter {

    Node importXmlFrom(String uri){
        // TODO errorhandling
        return new XmlParser().parse(uri)
    }
}
