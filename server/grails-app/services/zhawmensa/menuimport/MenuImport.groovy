package zhawmensa.menuimport

import zhawmensa.GastronomicFacility
import zhawmensa.Menu

/**
 * Interface to be implemented by all menu import services
 */
interface MenuImport {
    /**
     * Imports all menus from a given facility if there are any
     * @param facility Given facility
     * @return list of imported menus, empty list if no menus found
     */
    List<Menu> importMenus(GastronomicFacility facility)
}
