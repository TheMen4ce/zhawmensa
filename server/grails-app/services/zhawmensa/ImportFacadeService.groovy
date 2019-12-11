package zhawmensa

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
                // todo make business exception
                throw new RuntimeException("No import service available for provider: '${facility.provider}'!")
        }
    }
}
