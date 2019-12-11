package zhawmensa

import java.text.DateFormat
import java.text.SimpleDateFormat

class MenuImportSVService implements MenuImport {
    private static final String SERVICE_URL = "http://micro.sv-group.com/typo3conf/ext/netv_svg_menu/menu_xmlexp/menuexport.xml.php"
    private static final String AUTH = "ahle_zhaw.ch@P1DQJgSGQWwdOYiUOA7nGDDnl80cGWEu7eRXUJbl1cSQoOyaZGbKh9H2acsTqLvF"
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy")

    XmlImportService xmlImportService

    @Override
    List<Menu> importMenus(GastronomicFacility facility) {
        List<Menu> menus = []

        Node rootNode = xmlImportService.importXmlFrom(SERVICE_URL + "?branch=${facility.locationId}&authstring=" + AUTH)
        throwIfError(rootNode, facility)

        Map<String, String> labelMap = readObjectsIntoIdMap((List<Node>) rootNode.settings.offers.offer)
        Map<String, String> priceMap = readObjectsIntoIdMap((List<Node>) rootNode.settings.prices.price)

        rootNode.week.day.each { Node day ->
            Date menuDate = parseDate(day)

            day.menus.menu.each { Node menu ->
                try {
                    menus.add(importMenu(menu, menuDate, labelMap, priceMap))
                } catch (Exception ignored) {
                    throw new BusinessException("Couldn't parse response in SV Service import! Corrupt data in node: ${menu}")
                }
            }
        }

        return menus
    }

    /**
     * Reads objects with an ID and description (i.e. prices or offers) into a map for later use
     * @return Map<"id", "description">
     */
    private Map<String,String> readObjectsIntoIdMap(List<Node> nodes) {
        return nodes.collectEntries { Node offer ->
            [(offer.@id): offer.description.text()]
        }
    }

    private Menu importMenu(Node menuNode, Date menuDate, Map labelMap, Map<String, String> priceMap) {
        Menu menu = new Menu()
        menu.date = menuDate
        menu.title = menuNode.title.text().trim()
        menu.label = labelMap.get(menuNode.@offer)
        menu.sideDishes = menuNode.trimmings.text().trim()
        menu.studentPrice = getPrice(menuNode, priceMap.find{it.value == 'STUD'}.key)
        menu.internalPrice = getPrice(menuNode, priceMap.find{it.value == 'INT'}.key)
        menu.externalPrice = getPrice(menuNode, priceMap.find{it.value == 'EXT'}.key)

        return menu
    }

    BigDecimal getPrice(Node menuNode, String priceId) {
        return new BigDecimal(menuNode.prices.price.find { it.@id == priceId }.text())
    }

    private Date parseDate(Node day) {
        try {
            DATE_FORMAT.parse(day.date.text())
        } catch (Exception ignored) {
            throw new BusinessException("Couldn't parse date in SV Service import! Date: ${day.date}")
        }
    }

    private void throwIfError(Node rootNode, GastronomicFacility facility) {
        if (rootNode.@status == "error") {
            throw new BusinessException("Couldn't load data for ${facility.name} with ID ${facility.locationId}. " +
                    "SV Service threw an error: ${rootNode.@errormsg}")
        }
    }

}
