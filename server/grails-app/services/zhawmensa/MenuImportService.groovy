package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuImportService {

    ImportFacade importFacade = new ImportFacade()

    void importMenuPlans() {

        GastronomicFacility.findAll().each { GastronomicFacility facility ->
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

        importFacade.importMenus(facility).each { Menu menu ->
            menuPlan.addToMenus(menu)
        }

        menuPlan.save()
        println "Imported and saved Menu Plan with ID " + menuPlan.id
    }
}
