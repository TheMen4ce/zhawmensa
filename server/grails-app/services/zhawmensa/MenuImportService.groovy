package zhawmensa

import grails.gorm.transactions.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class MenuImportService {
    private static final String SERVICE_URL = "http://micro.sv-group.com/typo3conf/ext/netv_svg_menu/menu_xmlexp/menuexport.xml.php"
    private static final String AUTH = "ahle_zhaw.ch@P1DQJgSGQWwdOYiUOA7nGDDnl80cGWEu7eRXUJbl1cSQoOyaZGbKh9H2acsTqLvF"
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy")

    void importMenuPlans() {

        GastronomicFacility.findAllByProvider(Provider.SV).each { GastronomicFacility facility ->
            importMenuPlan(facility)
        }
    }

    protected void importMenuPlan(GastronomicFacility facility) {
        // TODO errorhandling
        Node xml_response = new XmlParser().parse(SERVICE_URL + "?branch=${facility.locationId}&authstring=" + AUTH)

        Calendar today = Calendar.getInstance()
        int year = today.get(Calendar.YEAR)
        int week = today.get(Calendar.WEEK_OF_YEAR)


        if(MenuPlan.findByGastronomicFacilityAndYearAndCalendarWeek(facility, year, week)) {
            // TODO throw exception and catch in global controller
            println "I already have data!"
            return
        }

        MenuPlan menuPlan = new MenuPlan(gastronomicFacility: facility, year: year, calendarWeek: week)

        xml_response.week.day.each { Node day ->
            // TODO errorhandling
            Date menuDate = DATE_FORMAT.parse(day.date.text())

            day.menus.menu.each { Node menu ->
                menuPlan.addToMenus(getMenuFromDescription(menu, menuDate))
            }
        }

        menuPlan.save()
        println "Imported and saved Menu Plan with ID " + menuPlan.id
    }

    private Menu getMenuFromDescription(Node menuNode, Date menuDate) {
        Menu menu = new Menu()
        menu.date = menuDate
        menu.title = menuNode.title.text()
        menu.sideDishes = menuNode.trimmings.text()
        // TODO extract price retrieval into own method. Use configurable constant val for id.
        menu.internalPrice = new BigDecimal(menuNode.prices.price.find { it.@id == '0' }.text())
        menu.externalPrice = new BigDecimal(menuNode.prices.price.find { it.@id == '3' }.text())
        menu.studentPrice = new BigDecimal(menuNode.prices.price.find { it.@id == '6' }.text())

        return menu
    }
}
