package zhawmensa.domain

import grails.gorm.transactions.Transactional
import zhawmensa.GastronomicFacility
import zhawmensa.I18nService
import zhawmensa.MenuPlan
import zhawmensa.exceptions.ObjectNotFoundException

@Transactional
class MenuPlanService {

    I18nService i18nService

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

    MenuPlan findById(long id) {
        MenuPlan menuPlan = MenuPlan.findById(id)
        if (!menuPlan) {
            throw new ObjectNotFoundException(i18nService.getMessage("menuPlan.error.notFound", [id]))
        }
        return menuPlan
    }

    void deleteById(long id) {
        MenuPlan menuPlanToDelete = findById(id)
        menuPlanToDelete.delete()
    }
}
