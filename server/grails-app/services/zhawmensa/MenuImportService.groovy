package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuImportService {

    ImportFacadeService importFacadeService

    int importMenuPlans() {
        int importedCount = 0
        Calendar today = Calendar.getInstance()
        int year = today.get(Calendar.YEAR)
        int week = today.get(Calendar.WEEK_OF_YEAR)

        GastronomicFacility.findAll().each { GastronomicFacility facility ->
            importedCount += importMenuPlan(facility, year, week)
        }
        return importedCount
    }

    protected int importMenuPlan(GastronomicFacility facility, int year, int week) {
        if (MenuPlan.findByGastronomicFacilityAndYearAndCalendarWeek(facility, year, week)) {
            log.info("No menus to import for ${facility.name}")
            return 0
        }

        MenuPlan menuPlan = new MenuPlan(gastronomicFacility: facility, year: year, calendarWeek: week)

        List<Menu> newMenus = importFacadeService.importMenus(facility)
        newMenus.each { Menu menu ->
            menuPlan.addToMenus(menu)
        }

        menuPlan.save()
        log.info("Imported ${newMenus.size()} menus for ${facility.name}")
        return newMenus.size()
    }
}
