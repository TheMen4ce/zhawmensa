package zhawmensa.menuimport

import zhawmensa.GastronomicFacility
import zhawmensa.Menu

interface MenuImport {
    List<Menu> importMenus(GastronomicFacility facility)
}
