package zhawmensa

class MenuImportZFV implements MenuImport {
    private static final String SERVICE_URL = "https://zfv.ch/en/menus/rssMenuPlan"
    private static def ALL_EXCEPT_DIGITS_AND_SLASH = /[^0-9.\/]/
    private static def DAYS_PROVIDED = 1..6
    private static List<String> EXPECTED_MENU_NODES = ['h3', 'p']
    private static String MENU_NODE_HEADER = "h3"

    XmlImportService xmlImportService

    List<Menu> importMenus(GastronomicFacility facility) {
        List<Menu> menus = []

        Calendar today = Calendar.getInstance()
        int year = today.get(Calendar.YEAR)
        int week = today.get(Calendar.WEEK_OF_YEAR)

        DAYS_PROVIDED.each { int day ->
            // +1 cause Calendar.DAY_OF_WEEK starts on SUNDAY
            today.setWeekDate(year, week, day + 1)
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            today.set(Calendar.MILLISECOND, 0)
            Date menuDate = today.getTime()

            Node rootNode = xmlImportService.importXmlFrom(SERVICE_URL + "?menuId=${facility.locationId}&dayOfWeek=${day}")
            menus.addAll(importMenusOfDay((Node) rootNode.entry.summary.div[0], menuDate))
        }

        return menus
    }

    private List<Menu> importMenusOfDay(Node menusOfADay, Date menuDate) {
        List<Menu> menus = []
        menusOfADay.eachWithIndex { Node menuNode, int index ->
            validateMenuNode(menuNode)

            if (menuNode.name().localPart == MENU_NODE_HEADER) {
                menus.add(importMenu(menuNode, (Node) menusOfADay.value()[index + 1], menuDate))
            }
        }

        return menus
    }

    private Menu importMenu(Node headerNode, Node descriptionNode, Date menuDate) {
        Menu menu = new Menu()
        menu.date = menuDate
        menu.label = headerNode.value()[0].trim()
        String[] dishDescription = descriptionNode.text().trim().split("\n")
        menu.title = dishDescription.head()

        String[] sideDishes = descriptionNode.text().trim().split("\n").tail()
        menu.sideDishes = getFormattedSideDishString(sideDishes)

        String[] prices = headerNode.span.text().replaceAll(ALL_EXCEPT_DIGITS_AND_SLASH, '').split("/")
        menu.studentPrice = new BigDecimal(prices[0])
        menu.internalPrice = new BigDecimal(prices[1])
        menu.externalPrice = new BigDecimal(prices[2])

        return menu
    }

    /**
     * Trims all unnecessary newlines and blanks from input using Groovys 'inject' (-> reduce)
     * @return correctly formatted string (i.e. "sometext\nmoretext")
     */
    private String getFormattedSideDishString(String[] sideDishes) {
        sideDishes.inject("", { String result, String text ->
            result += text.trim() ? text.trim() + "\n" : ""
        }).trim()
    }

    private void validateMenuNode(Node menuNode) {
        if(!EXPECTED_MENU_NODES.contains(menuNode.name().localPart)){
            throw new BusinessException("Unexpected menu node in ZFV Import! Node: ${menuNode}")
        }
    }
}
