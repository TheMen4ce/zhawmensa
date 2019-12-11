package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuPlanService {

    List<MenuPlan> findAllByFacilityId(long facilityId) {
        return MenuPlan.createCriteria().list {
            gastronomicFacility {
                eq('id', facilityId)
            }
            order('year', 'desc')
            order('calendarWeek', 'desc')
        } as List<MenuPlan>
    }

    boolean menuPlanExistsFor(GastronomicFacility facility, int year, int week) {
        return MenuPlan.countByGastronomicFacilityAndYearAndCalendarWeek(facility, year, week) > 0
    }

    MenuPlan store(MenuPlan menuPlan) {
        return menuPlan.save()
    }
}
