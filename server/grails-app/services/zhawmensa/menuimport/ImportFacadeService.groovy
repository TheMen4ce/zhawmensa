package zhawmensa.menuimport

import zhawmensa.GastronomicFacility
import zhawmensa.Menu
import zhawmensa.Provider

/**
 * Provides a common interface for all facilities to import the menus
 */
class ImportFacadeService {

    MenuImportSVService menuImportSVService
    MenuImportZFVService menuImportZFVService

    List<Menu> importMenus(GastronomicFacility facility){
        switch (facility.provider){
            case Provider.SV:
                return menuImportSVService.importMenus(facility)
            case Provider.ZFV:
                return menuImportZFVService.importMenus(facility)
            default:
                throw new IllegalStateException("No import service available for provider: '${facility.provider}'!")
        }
    }
}
