package zhawmensa

import java.text.DateFormat
import java.text.SimpleDateFormat

class MenuImportSV implements MenuImport {
    private static final String SERVICE_URL = "http://micro.sv-group.com/typo3conf/ext/netv_svg_menu/menu_xmlexp/menuexport.xml.php"
    private static final String AUTH = "ahle_zhaw.ch@P1DQJgSGQWwdOYiUOA7nGDDnl80cGWEu7eRXUJbl1cSQoOyaZGbKh9H2acsTqLvF"
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy")

    XMLImporter xmlImporter

    MenuImportSV(XMLImporter xmlImporter) {
        this.xmlImporter = xmlImporter
    }

    @Override
    List<Menu> importMenus(GastronomicFacility facility) {
        List<Menu> menus = []

        Node rootNode = xmlImporter.importXmlFrom(SERVICE_URL + "?branch=${facility.locationId}&authstring=" + AUTH)
        throwIfError(rootNode, facility)

        rootNode.week.day.each { Node day ->
            Date menuDate = parseDate(day)

            day.menus.menu.each { Node menu ->
                try {
                    menus.add(importMenu(menu, menuDate))
                } catch (Exception ignored) {
                    throw new BusinessException("Couldn't parse response in SV Service import! Corrupt data in node: ${menu}")
                }
            }
        }

        return menus
    }

    private Menu importMenu(Node menuNode, Date menuDate) {
        Menu menu = new Menu()
        menu.date = menuDate
        menu.title = menuNode.title.text().trim()
        menu.sideDishes = menuNode.trimmings.text().trim()
        menu.internalPrice = getPrice(menuNode, Price.INTERNAL)
        menu.externalPrice = getPrice(menuNode, Price.EXTERNAL)
        menu.studentPrice = getPrice(menuNode, Price.STUDENT)

        return menu
    }

    BigDecimal getPrice(Node menuNode, Price price) {
        return new BigDecimal(menuNode.prices.price.find { it.@id == price.id }.text())
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

    private static enum Price {
        INTERNAL('0'),
        EXTERNAL('3'),
        STUDENT('6')

        final String id

        private Price(String id) {
            this.id = id
        }
    }
}
