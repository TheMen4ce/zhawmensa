package zhawmensa

import java.text.DateFormat
import java.text.SimpleDateFormat

class MenuImportSV implements MenuImport {
    private static final String SERVICE_URL = "http://micro.sv-group.com/typo3conf/ext/netv_svg_menu/menu_xmlexp/menuexport.xml.php"
    private static final String AUTH = "ahle_zhaw.ch@P1DQJgSGQWwdOYiUOA7nGDDnl80cGWEu7eRXUJbl1cSQoOyaZGbKh9H2acsTqLvF"
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy")

    XMLImporter xmlImporter

    MenuImportSV(XMLImporter xmlImporter){
        this.xmlImporter = xmlImporter
    }

    @Override
    List<Menu> importMenus(GastronomicFacility facility) {
        List<Menu> menus = []

        Node rootNode = xmlImporter.importXmlFrom(SERVICE_URL + "?branch=${facility.locationId}&authstring=" + AUTH)

        rootNode.week.day.each { Node day ->
            // TODO errorhandling
            Date menuDate = DATE_FORMAT.parse(day.date.text())

            day.menus.menu.each { Node menu ->
                menus.add(importMenu(menu, menuDate))
            }
        }

        return menus
    }

    private Menu importMenu(Node menuNode, Date menuDate) {
        Menu menu = new Menu()
        menu.date = menuDate
        menu.title = menuNode.title.text().trim()
        menu.sideDishes = menuNode.trimmings.text().trim()
        // TODO extract price retrieval into own method. Use configurable constant val for id.
        menu.internalPrice = new BigDecimal(menuNode.prices.price.find { it.@id == '0' }.text())
        menu.externalPrice = new BigDecimal(menuNode.prices.price.find { it.@id == '3' }.text())
        menu.studentPrice = new BigDecimal(menuNode.prices.price.find { it.@id == '6' }.text())

        return menu
    }
}
