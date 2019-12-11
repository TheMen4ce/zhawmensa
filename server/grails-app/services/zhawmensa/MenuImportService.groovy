package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuImportService {

    ImportFacadeService importFacadeService
    GastronomicFacilityService gastronomicFacilityService
    MenuPlanService menuPlanService

    int importMenuPlans() {
        int importedCount = 0
        Calendar today = Calendar.getInstance()
        int year = today.get(Calendar.YEAR)
        int week = today.get(Calendar.WEEK_OF_YEAR)

        gastronomicFacilityService.findAll().each { GastronomicFacility facility ->
            importedCount += importMenuPlan(facility, year, week)
        }
        return importedCount
    }

    protected int importMenuPlan(GastronomicFacility facility, int year, int week) {
        if (menuPlanService.menuPlanExistsFor(facility, year, week)) {
            log.info("Menu plan for facility ${facility.name} in week ${year}/${week} already exists!")
            return 0
        }

        MenuPlan menuPlan = new MenuPlan(gastronomicFacility: facility, year: year, calendarWeek: week)

        List<Menu> newMenus = importFacadeService.importMenus(facility)
        newMenus.each { Menu menu ->
            menuPlan.addToMenus(menu)
        }

        menuPlanService.store(menuPlan)
        log.info("Imported ${newMenus.size()} menus for ${facility.name}")
        return newMenus.size()
    }
}
