package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuImportZFVService {
    private static final String SERVICE_URL = "https://zfv.ch/en/menus/rssMenuPlan"

    private static def ALL_EXCEPT_DIGITS_AND_SLASH = /[^0-9.\/]/

    void importMenuPlans() {

        GastronomicFacility.findAllByProvider(Provider.ZFV).each { GastronomicFacility facility ->
            importMenuPlan(facility)
        }
    }

    protected void importMenuPlan(GastronomicFacility facility) {
        Calendar today = Calendar.getInstance()
        int year = today.get(Calendar.YEAR)
        int week = today.get(Calendar.WEEK_OF_YEAR)


        if (MenuPlan.findByGastronomicFacilityAndYearAndCalendarWeek(facility, year, week)) {
            // TODO throw exception and catch in global controller
            println "I already have data!"
            return
        }

        MenuPlan menuPlan = new MenuPlan(gastronomicFacility: facility, year: year, calendarWeek: week)

        (1..6).each { int day ->
            println "Importing day " + day
            // +1 cause Calendar.DAY_OF_WEEK starts on SUNDAY
            today.setWeekDate(year, week, day + 1)
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            today.set(Calendar.MILLISECOND, 0)
            Date menuDate = today.getTime()

            Node xml_response = new XmlParser().parse(SERVICE_URL + "?menuId=${facility.locationId}&dayOfWeek=${day}")
            List<Menu> menus = getMenusFromNode(xml_response.entry.summary.div[0], menuDate)
            menus.each { Menu menu -> menuPlan.addToMenus(menu) }
        }

        menuPlan.save()
        println "Imported and saved Menu Plan with ID " + menuPlan.id
    }

    private List<Menu> getMenusFromNode(Node node, Date menuDate) {
        List<Menu> menus = []
        node.eachWithIndex { Node menuNode, int index ->
            if (menuNode.name().localPart == "h3") {
                menus.add(createMenuFrom(menuNode, (Node) node.value()[index + 1], menuDate))
            }
        }

        return menus
    }

    private Menu createMenuFrom(Node headerNode, Node descriptionNode, Date menuDate) {
        Menu menu = new Menu()
        menu.date = menuDate
        menu.label = headerNode.value()[0].trim()
        String[] dishDescription = descriptionNode.text().trim().split("\n")
        menu.title = dishDescription.head()
        menu.sideDishes = descriptionNode.text().trim().split("\n").tail().inject("", { result, text -> result += text.trim() ? text.trim() + "\n" : "" })

        String[] prices = headerNode.span.text().replaceAll(ALL_EXCEPT_DIGITS_AND_SLASH, '').split("/")
        menu.studentPrice = new BigDecimal(prices[0])
        menu.internalPrice = new BigDecimal(prices[1])
        menu.externalPrice = new BigDecimal(prices[2])

        return menu
    }
}
