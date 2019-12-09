package zhawmensa

class ImportFacade {

    private MenuImportSV menuImportSV = new MenuImportSV(new XMLImporter())
    private MenuImportZFV menuImportZFV = new MenuImportZFV(new XMLImporter())

    List<Menu> importMenus(GastronomicFacility facility){
        switch (facility.provider){
            case Provider.SV:
                return menuImportSV.importMenus(facility)
            case Provider.ZFV:
                return menuImportZFV.importMenus(facility)
            default:
                // todo make business exception
                throw new RuntimeException("No import service available for provider: '${facility.provider}'!")
        }
    }
}
